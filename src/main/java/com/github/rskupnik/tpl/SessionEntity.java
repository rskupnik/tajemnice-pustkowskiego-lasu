package com.github.rskupnik.tpl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class SessionEntity {

    @Id
    private String id;
    private Boolean yearbookFound = false;
    private Boolean diaryFound = false;
    private Boolean killerGuessed = false;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Boolean getYearbookFound() {
        return yearbookFound;
    }

    public void setYearbookFound(Boolean yearbookFound) {
        this.yearbookFound = yearbookFound;
    }

    public Boolean getDiaryFound() {
        return diaryFound;
    }

    public void setDiaryFound(Boolean dairyFound) {
        this.diaryFound = dairyFound;
    }

    public Boolean getKillerGuessed() {
        return killerGuessed;
    }

    public void setKillerGuessed(Boolean killerGuessed) {
        this.killerGuessed = killerGuessed;
    }
}
