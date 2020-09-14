package com.codecool.hsdecktracker;

import com.codecool.hsdecktracker.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//import org.json.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HSdeckTrackerPU");
        EntityManager em = emf.createEntityManager();

        populateDb(em);
        em.clear(); //clear hibernate cache - force next statements to read data from db
        em.close();
        emf.close();
        //jsonCardParser();
    }

    public static void populateDb(EntityManager em) {

//        Card card = new Card("AT_132", CardClass.HUNTER, Type.SPELL,"Justicar Trueheart", CardSet.NAXX,"test text",6,6,3,Rarity.LEGENDARY);
//        Card card2 = new Card("AT_999", CardClass.HUNTER, Type.SPELL,"XXXXXXXX", CardSet.NAXX,"test text2",5,5,5,Rarity.LEGENDARY);
//        Deck deck = new Deck("newDeck1");
//        Deck deck2 = new Deck("newDeck2");
//        deck.addCardToDeck(card);
//        deck2.addCardToDeck(card);
//        deck2.addCardToDeck(card2);
//        User user = new User("tester","test@test.com","123");
//        User user2 = new User("1111111","test1@test.com","123");
//        user.addDeckToUser(deck);
//        user2.addDeckToUser(deck2);

//        EntityTransaction transaction = em.getTransaction();
//        transaction.begin();
//        em.persist(card);
//        em.persist(card2);
//        em.persist(deck);
//        em.persist(deck2);
//        em.persist(user);
//        em.persist(user2);
//        transaction.commit();

        List<Card> cards = jsonCardParser();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Card card : cards) {
            em.persist(card);
        }
        transaction.commit();

    }


    private static List<Card> jsonCardParser() {
        List<Card> cards = new ArrayList<>();
        try {
            URL url = new URL("https://api.hearthstonejson.com/v1/25770/enUS/cards.collectible.json");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            Object object = new JSONParser().parse(new FileReader("src/main/resources/JSON/cards.collectible.json"));
            //Object object = new JSONParser().parse(new FileReader("https://api.hearthstonejson.com/v1/25770/enUS/cards.collectible.json"));
            JSONArray jsonArray = (JSONArray) object;

            System.out.println(jsonArray.size());

            for (int i = 0; i <jsonArray.size() ; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                //System.out.println(jsonObject.toString());
                Card card = new ObjectMapper().readValue(jsonObject.toString(), Card.class);
                //System.out.println(card);
                card.setDustCost(card.getRarity().getCost());
                //System.out.println(card);
                cards.add(card);
            }

        } catch (IOException | ParseException e){
            e.printStackTrace();
        }

        return cards;
    }
}
