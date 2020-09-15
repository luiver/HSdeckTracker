package com.codecool.hsdecktracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum CardClass {
    HUNTER,
    MAGE,
    NEUTRAL,
    PRIEST,
    DRUID,
    PALADIN,
    WARLOCK,
    SHAMAN,
    DEATHKNIGHT,
    DEMONHUNTER,
    ROGUE,
    WARRIOR,
    DREAM,
    INVALID,
    WHIZBANG;

    public String getStatus() {
        return this.name();
    }

    CardClass() {
    }
}
