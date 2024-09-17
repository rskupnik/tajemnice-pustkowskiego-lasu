package com.github.rskupnik.tpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void test() {
        System.out.println("Testing");
        var entity = new SessionEntity();
        entity.setCode("teStCodE");
        sessionRepository.save(entity);
        System.out.println("Added test entity");

        var entity2 = sessionRepository.findById(entity.getId());
        System.out.println("Found entity with code: " + entity2.get().getCode() + " and id " + entity2.get().getId());
    }
}
