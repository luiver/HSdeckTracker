package com.codecool.hsdecktracker.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idString;

    @Enumerated(value = EnumType.STRING)
    private PlayerClass playerClass;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Set set;

    private String text;

    private int manaCost;

    private int attack;

    private int health;

    @Enumerated(value = EnumType.STRING)
    private Rarity rarity;

    //TODO Test it it works: @Enumerated(value = EnumType.ORDINAL) instead of current solution
    private int dustCost;

    private boolean isPremium;

//    //TODO
//    private List<Ability> abilities;

    @OneToMany(mappedBy = "cards", fetch = FetchType.LAZY) //or ManyToMany?
    private Deck deck;


    //TODO zrobić buildera?
    public Card(String idString, PlayerClass playerClass, Type type, String name, Set set, String text, int manaCost, int attack, int health, Rarity rarity, boolean isPremium) {
        this.idString = idString;
        this.playerClass = playerClass;
        this.type = type;
        this.name = name;
        this.set = set;
        this.text = text;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.rarity = rarity;
        this.dustCost = rarity.getCost();
        this.isPremium = isPremium;
        //this.abilities = abilities;
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

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
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

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

//    public List<Ability> getAbilities() {
//        return abilities;
//    }
//
//    public void setAbilities(List<Ability> abilities) {
//        this.abilities = abilities;
//    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
