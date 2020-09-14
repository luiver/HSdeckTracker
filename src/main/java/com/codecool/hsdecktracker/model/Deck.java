package com.codecool.hsdecktracker.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "decks")
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    //@ManyToMany(mappedBy = "deck", cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
    @ManyToMany
    private List<Card> cards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Deck() {
    }

    public Deck(String name) {
        this.name = name;
    }

    public void addCardToDeck(Card card){
        cards.add(card);
        card.setDeck(this);
    }

    public void removeCardFromDeck(Card card){
        cards.remove(card);
        card.setDeck(null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
