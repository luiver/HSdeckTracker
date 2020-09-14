package com.codecool.hsdecktracker.model;

public enum Rarity {
    COMMON(40),
    RARE(100),
    EPIC(400),
    LEGENDARY(1600),
    FREE(0);

    private final int cost;

    Rarity(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
