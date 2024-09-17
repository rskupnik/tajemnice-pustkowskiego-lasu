package com.github.rskupnik.tpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    private final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionEntity generateSession() {
        var entity = new SessionEntity();
        entity.setId(UUID.randomUUID().toString().toUpperCase().substring(0, 5));
        sessionRepository.save(entity);
        logger.info("Generated session id {}", entity.getId());
        return entity;
    }

    public Optional<SessionEntity> getSession(String id) {
        return sessionRepository.findById(id);
    }
}
