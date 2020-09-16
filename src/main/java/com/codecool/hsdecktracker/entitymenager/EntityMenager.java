package com.codecool.hsdecktracker.entitymenager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityMenager {

    public void menageTransaction(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HSdeckTrackerPU");
        EntityManager em = emf.createEntityManager();

        em.clear(); //clear hibernate cache - force next statements to read data from db
        em.close();
        emf.close();
    }

}
