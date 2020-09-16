package com.codecool.hsdecktracker.entitymenager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityMenager {

    public void menageTransaction(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HSdeckTrackerPU");
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(org.hibernate.Session.class);
        SessionFactory factory = session.getSessionFactory();
        System.out.println(factory.toString());
        System.out.println("factory to string");
        em.clear(); //clear hibernate cache - force next statements to read data from db
        em.close();
        emf.close();
    }

}
