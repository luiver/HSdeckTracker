package com.codecool.hsdecktracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "cards")
//@org.hibernate.annotations.NamedQueries({
//        @org.hibernate.annotations.NamedQuery(  name="Card.getByClass",
//                                                query ="SELECT c FROM card.c WHERE c.card_class = :playerClass")
//})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long card_id;

    @Column(nullable = false, unique = true, name = "id_string")
    private String id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "card_class")
    private CardClass cardClass;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "card_type")
    private Type type;

    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "card_set")
    private CardSet set;

    private String text;
    @Column(name = "mana_cost")
    private int cost;

    private int attack;

    private int health;

    @Enumerated(value = EnumType.STRING)
    private Rarity rarity;

    //TODO Test it it works: @Enumerated(value = EnumType.ORDINAL) instead of current solution
    @Column(name = "dust_cost")
    private int dustCost;

    @ManyToMany(fetch= FetchType.LAZY)
    private List<Deck> deck = new ArrayList<>();

    public Card(){
        super();
    }

    public void addDeck(Deck deck){
        this.deck.add(deck);
    }

    public void removeDeck(Deck deck){
        this.deck.remove(deck);
    }

    //TODO zrobić buildera?
    public Card(String id, CardClass cardClass, Type type, String name, CardSet set, String text, int cost, int attack, int health, Rarity rarity) {
        this.id = id;
        this.cardClass = cardClass;
        this.type = type;
        this.name = name;
        this.set = set;
        this.text = text;
        this.cost = cost;
        this.attack = attack;
        this.health = health;
        this.rarity = rarity;
        this.dustCost = rarity.getCost();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CardClass getCardClass() {
        return cardClass;
    }

    public void setCardClass(CardClass cardClass) {
        this.cardClass = cardClass;
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

    public CardSet getSet() {
        return set;
    }

    public void setSet(CardSet set) {
        this.set = set;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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

    public List<Deck> getDeck() {
        return deck;
    }

    public void setDeck(List<Deck> deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return "Card{" +
                "idString='" + id + '\'' +
                ", playerClass=" + cardClass +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", cardSet=" + set +
                ", text='" + text + '\'' +
                ", manaCost=" + cost +
                ", attack=" + attack +
                ", health=" + health +
                ", rarity=" + rarity +
                ", dustCost=" + dustCost +
                '}';
    }
}
