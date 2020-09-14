package com.codecool.hsdecktracker;

import com.codecool.hsdecktracker.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

        Card card = new Card("AT_132", PlayerClass.HUNTER, Type.SPELL,"Justicar Trueheart", CardSet.NAAX,"test text",6,6,3,Rarity.LEGENDARY);
        Card card2 = new Card("AT_999", PlayerClass.HUNTER, Type.SPELL,"XXXXXXXX", CardSet.NAAX,"test text2",5,5,5,Rarity.LEGENDARY);
        Deck deck = new Deck("newDeck1");
        Deck deck2 = new Deck("newDeck2");
        deck.addCardToDeck(card);
        deck2.addCardToDeck(card);
        deck2.addCardToDeck(card2);
        User user = new User("tester","test@test.com","123");
        User user2 = new User("1111111","test1@test.com","123");
        user.addDeckToUser(deck);
        user2.addDeckToUser(deck2);


        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(card);
        em.persist(card2);
        em.persist(deck);
        em.persist(deck2);
        em.persist(user);
        em.persist(user2);
        transaction.commit();



//        transaction.begin();
//        em.persist(student2);
//        em.persist(address2);
//        em.persist(classBp2);
//        transaction.commit();



    }
}
