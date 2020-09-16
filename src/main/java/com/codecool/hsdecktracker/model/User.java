package com.codecool.hsdecktracker.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = { "decks" })
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Deck> decks;

    public User() {
    }

    @JsonCreator
    public User(@JsonProperty("name")String name, @JsonProperty("email")String email, @JsonProperty("password")String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.decks = new ArrayList<>();
    }

    public void addDeckToUser(Deck deck){
        decks.add(deck);
        deck.setUser(this);
    }

    public void removeDeckFromUser(Deck deck){
        decks.remove(deck);
        deck.setUser(null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }
}
