package com.codecool.hsdecktracker.entitymenager;

import com.codecool.hsdecktracker.model.Card;
import com.codecool.hsdecktracker.model.Deck;
import com.codecool.hsdecktracker.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBPopulator {

    public DBPopulator() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HSdeckTrackerPU");
        EntityManager em = emf.createEntityManager();

        //populateDb(em);
        em.clear(); //clear hibernate cache - force next statements to read data from db
        em.close();
        emf.close();
    }

    private void populateDb(EntityManager em) {
        Deck deck1 = new Deck("RafałDeck");
        Deck deck2 = new Deck("JanekDeck");
        Deck deck3 = new Deck("DominikDeck");

        User user1 = new User("Rafał","r@r.com","123");
        User user2 = new User("Jan","j@j.com","123");
        User user3 = new User("Dominik","d@d.com","123");
        user1.addDeckToUser(deck1);
        user2.addDeckToUser(deck2);
        user3.addDeckToUser(deck3);

        List<Card> cards = jsonCardParser();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Card card : cards) {
            em.persist(card);
        }
        fillDeckWithCards(deck1,cards,10);
        fillDeckWithCards(deck2,cards,100);
        fillDeckWithCards(deck3,cards,1000);
        em.persist(deck1);
        em.persist(deck2);
        em.persist(deck3);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        transaction.commit();
    }

    private void fillDeckWithCards(Deck deck, List<Card> cards, int index) {
        try {
            for (int i = index; i < index + 30; i++) {
                deck.addCardToDeck(cards.get(i));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //TODO extract more universal method to parse json
    private List<Card> jsonCardParser() {
        List<Card> cards = new ArrayList<>();
        try {
            Object object = new JSONParser()
                    .parse(new FileReader("src/main/resources/JSON/cards.collectible.json"));
            JSONArray jsonArray = (JSONArray) object;
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                Card card = new ObjectMapper().readValue(jsonObject.toString(), Card.class);
                card.setDustCost(card.getRarity().getCost());
                cards.add(card);
            }
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
        return cards;
    }
}
