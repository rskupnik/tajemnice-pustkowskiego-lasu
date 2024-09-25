package com.github.rskupnik.tpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class WebController {

    private final Logger logger = LoggerFactory.getLogger(WebController.class);

    private final SessionService sessionService;

    @Autowired
    public WebController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @RequestMapping(path = {"/", "/mapa"})
    public String map(HttpServletRequest request, Model model, HttpServletResponse response) {
        return handleSession(request, response, model).orElse("pages/map");
    }

    @RequestMapping("/evidence")
    public String evidence(HttpServletRequest request, Model model, HttpServletResponse response) {
        return handleSession(request, response, model).orElse("pages/evidence");
    }

    @RequestMapping("/session-change")
    public String sessionChange(HttpServletRequest request, Model model, HttpServletResponse response) {
        var errorRouting = handleSession(request, response, model);
        if (errorRouting.isPresent()) {
            return errorRouting.get();
        }

        model.addAttribute("sessionChange", new SessionChangeDTO());
        return "pages/sessionChange";
    }

    @PostMapping("/session-change/exec")
    public String executeSessionChange(@ModelAttribute SessionChangeDTO sessionChange, Model model, HttpServletResponse response) {
        var session = sessionService.getSession(sessionChange.getId().trim().toUpperCase());
        if (session.isEmpty()) {
            logger.warn("Session not found when trying to change session: {}", sessionChange.getId());
            // TODO: Feedback to user?
            return "pages/begin";
        }

        Cookie sessionIdCookie = new Cookie("sessionId", sessionChange.getId());
        sessionIdCookie.setPath("/");
        response.addCookie(sessionIdCookie);

        model.addAttribute("sessionChange", sessionChange);
        model.addAttribute("userSession", SessionDto.fromEntity(session.get()));

        // TODO: Feedback to user
        return "pages/sessionChange";
    }

    private Optional<String> handleSession(HttpServletRequest request, HttpServletResponse response, Model model) {
        var cookies = request.getCookies();
        if (cookies == null || cookies.length == 0 || !sessionIdCookieExists(cookies)) {
            setNewSessionCookie(response);
            return Optional.of("pages/begin");
        }

        var sessionIdCookie = retrieveCookie("sessionId", cookies);
        var session = sessionService.getSession(sessionIdCookie.getValue().trim().toUpperCase());
        if (session.isEmpty()) {
            // Invalid session, generate a new one?
            logger.warn("Session not found: {}", sessionIdCookie.getValue());
            setNewSessionCookie(response);
            return Optional.of("pages/begin");
        }

        model.addAttribute("userSession", SessionDto.fromEntity(session.get()));

        return Optional.empty();
    }

    private void setNewSessionCookie(HttpServletResponse response) {
        var session = sessionService.generateSession();
        Cookie sessionIdCookie = new Cookie("sessionId", session.getId());
        sessionIdCookie.setPath("/");
        response.addCookie(sessionIdCookie);
    }

    private boolean sessionIdCookieExists(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("sessionId")) {
                return true;
            }
        }
        return false;
    }

    private Cookie retrieveCookie(String name, Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }
}
