package com.github.rskupnik.tpl;

public record SessionDto(String id, Boolean yearbookFound, Boolean diaryFound, Boolean killerGuessed) {

    public SessionEntity toEntity() {
        var entity = new SessionEntity();
        entity.setId(id);
        entity.setYearbookFound(yearbookFound);
        entity.setDiaryFound(diaryFound);
        entity.setKillerGuessed(killerGuessed);
        return entity;
    }

    public static SessionDto fromEntity(SessionEntity entity) {
        return new SessionDto(
                entity.getId(),
                entity.getYearbookFound(),
                entity.getDiaryFound(),
                entity.getKillerGuessed()
        );
    }
}
