package com.github.rskupnik.tpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        var cookies = request.getCookies();
        if (cookies == null || cookies.length == 0 || !sessionIdCookieExists(cookies)) {
            setNewSessionCookie(response);
            return "pages/begin";
        }

        var sessionIdCookie = retrieveCookie("sessionId", cookies);
        var session = sessionService.getSession(sessionIdCookie.getValue());
        if (session.isEmpty()) {
            // Invalid session, generate a new one?
            logger.warn("Session not found: {}", sessionIdCookie.getValue());
            setNewSessionCookie(response);
            return "pages/begin";
        }

        model.addAttribute("session", session);

        return "pages/map";
    }

    @RequestMapping("/begin")
    public String begin(HttpServletResponse response, Model model) {
        var session = sessionService.generateSession();
        System.out.println("Session generated");
        Cookie sessionIdCookie = new Cookie("sessionId", session.getId());
        sessionIdCookie.setPath("/");
        response.addCookie(sessionIdCookie);
        return "/";
    }

    @RequestMapping("/evidence")
    public String evidence(Model model) {
        //model.addAttribute("hello", "world");
        return "pages/evidence";
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
