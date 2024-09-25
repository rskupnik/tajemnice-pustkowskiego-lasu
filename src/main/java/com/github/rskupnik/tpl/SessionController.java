package com.github.rskupnik.tpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("sessions/generate")
    public SessionDto generateSession() {
        return SessionDto.fromEntity(sessionService.generateSession());
    }

    @GetMapping("sessions/{id}")
    public ResponseEntity<SessionDto> getSession(@PathVariable String id) {
        if (id == null || id.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return sessionService.getSession(id.strip().toUpperCase())
                .map(
                        sessionEntity -> ResponseEntity.ok(SessionDto.fromEntity(sessionEntity))
                ).orElseGet(
                        () -> ResponseEntity.notFound().build()
                );

    }

}
