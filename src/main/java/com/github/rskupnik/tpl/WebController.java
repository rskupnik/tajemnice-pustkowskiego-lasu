package com.github.rskupnik.tpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    private final SessionService sessionService;

    @Autowired
    public WebController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @RequestMapping(path = {"/", "/mapa"})
    public String map(HttpServletRequest request, Model model, HttpServletResponse response) {
        var cookies = request.getCookies();
        if (cookies == null || cookies.length == 0 || !sessionIdCookieExists(cookies)) {
            // TODO: Direct to begin page and handle logic there
            //return "pages/begin";

            // TODO: BELOW IS TEMPORARY FOR TESTING
            System.out.println("GENERATING SESSION");
            var session = sessionService.generateSession();
            System.out.println("SESSION GENERATED");
            Cookie sessionIdCookie = new Cookie("sessionId", session.getId());
            sessionIdCookie.setPath("/");
            response.addCookie(sessionIdCookie);

            return "pages/map";
        }

        var sessionIdCookie = retrieveCookie("sessionId", cookies);
        System.out.println("SESSION ID COOKIE VALUE " + sessionIdCookie.getValue());
        // TODO: Need a persistent DB to test this properly (cookie not present anymore after reload)
        var session = sessionService.getSession(sessionIdCookie.getValue()).orElseThrow();  // TODO: What if session not found?

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

    private boolean sessionIdCookieExists(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName());
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
