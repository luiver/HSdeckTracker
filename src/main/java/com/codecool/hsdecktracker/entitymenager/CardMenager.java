package com.codecool.hsdecktracker.entitymenager;

import com.codecool.hsdecktracker.model.Card;
import com.codecool.hsdecktracker.model.CardClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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
        TypedQuery<Card> query = em.createNamedQuery("Card.getByClass", Card.class).setParameter("playerClass", CardClass.valueOf(playerClass));
        List<Card> results = query.getResultList();
        List<String> resultsJSON = new ArrayList<>();
        results.forEach(r -> resultsJSON.add(r.JSONrepresentation()));
        em.clear(); //clear hibernate cache - force next statements to read data from db
        em.close();
        emf.close();
        return resultsJSON;
    }
}
