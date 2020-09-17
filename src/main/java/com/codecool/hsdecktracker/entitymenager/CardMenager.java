package com.codecool.hsdecktracker.entitymenager;

import com.codecool.hsdecktracker.model.Card;
import com.codecool.hsdecktracker.model.CardClass;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CardMenager {
    static CardMenager cardMenager;

    public static CardMenager getCardMenagerInstance(){
        if (cardMenager == null) cardMenager = new CardMenager();
        return cardMenager;
    }

    public List<String> getCardByClass(String  playerClass){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HSdeckTrackerPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Card> query;
        try {
            query = em.createNamedQuery("Card.getByClass", Card.class).setParameter("playerClass", CardClass.valueOf(playerClass));
        } catch (IllegalArgumentException e){
            query = em.createNamedQuery("Card.getAll", Card.class);
        }
        List<Card> results = query.getResultList();
        List<String> resultsJSON = new ArrayList<>();
        results.forEach(r -> resultsJSON.add(r.JSONrepresentation()));
        em.clear(); //clear hibernate cache - force next statements to read data from db
        em.close();
        emf.close();
        return resultsJSON;
    }

    public int deleteCardById(String id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HSdeckTrackerPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createNamedQuery("Card.deleteById").setParameter("cardId", id);
        int rowsAffected = query.executeUpdate();
        em.getTransaction().commit();
        em.clear(); //clear hibernate cache - force next statements to read data from db
        em.close();
        emf.close();
        return rowsAffected;
    }
}
