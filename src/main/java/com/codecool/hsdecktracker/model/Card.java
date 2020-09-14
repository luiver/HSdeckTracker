package com.codecool.hsdecktracker.model;

import javax.persistence.*;

@Entity(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, unique = true)
    private String idString;

    @Enumerated(value = EnumType.STRING)
    private PlayerClass playerClass;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private CardSet cardSet;

    private String text;

    private int manaCost;

    private int attack;

    private int health;

    @Enumerated(value = EnumType.STRING)
    private Rarity rarity;

    //TODO Test it it works: @Enumerated(value = EnumType.ORDINAL) instead of current solution
    private int dustCost;

    @ManyToOne(fetch= FetchType.LAZY)
    private Deck deck;


    //TODO zrobić buildera?
    public Card(String idString, PlayerClass playerClass, Type type, String name, CardSet cardSet, String text, int manaCost, int attack, int health, Rarity rarity) {
        this.idString = idString;
        this.playerClass = playerClass;
        this.type = type;
        this.name = name;
        this.cardSet = cardSet;
        this.text = text;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.rarity = rarity;
        this.dustCost = rarity.getCost();
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardSet getCardSet() {
        return cardSet;
    }

    public void setCardSet(CardSet cardSet) {
        this.cardSet = cardSet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public int getDustCost() {
        return dustCost;
    }

    public void setDustCost(int dustCost) {
        this.dustCost = dustCost;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
