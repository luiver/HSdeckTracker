package com.codecool.hsdecktracker;

import com.codecool.hsdecktracker.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HSdeckTrackerPU");
        EntityManager em = emf.createEntityManager();

        populateDb(em);
        em.clear(); //clear hibernate cache - force next statements to read data from db
        em.close();
        emf.close();
    }

    public static void populateDb(EntityManager em) {

        Card card = new Card("AT_132", PlayerClass.HUNTER, Type.SPELL,"Justicar Trueheart",Set.NAAX,"test text",6,6,3,Rarity.LEGENDARY,true);
        Deck deck = new Deck(1, "newDeck1");
        deck.addCardToDeck(card);
        User user = new User(1,"tester","test@test.com","123");



        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(card);
        em.persist(deck);
        em.persist(user);
        transaction.commit();



//        transaction.begin();
//        em.persist(student2);
//        em.persist(address2);
//        em.persist(classBp2);
//        transaction.commit();



    }
}
