package com.github.rskupnik.tpl;

public record SessionDto(String id) {

    public SessionEntity toEntity() {
        var entity = new SessionEntity();
        entity.setId(id);
        return entity;
    }

    public static SessionDto fromEntity(SessionEntity entity) {
        return new SessionDto(
                entity.getId()
        );
    }
}
