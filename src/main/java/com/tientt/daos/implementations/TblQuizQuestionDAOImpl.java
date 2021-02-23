package com.tientt.daos.implementations;

import com.tientt.daos.interfaces.TblQuizQuestionDAO;
import com.tientt.entities.TblQuizChoice;
import com.tientt.entities.TblQuizQuestion;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class TblQuizQuestionDAOImpl implements TblQuizQuestionDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("P0014PU");
    @Override
    public TblQuizQuestion findTblQuizQuestionByID(String ID) {
        EntityManager em = emf.createEntityManager();
        return em.find(TblQuizQuestion.class, ID);
    }

    @Override
    public void updateTblQuizQuestion(TblQuizQuestion quizQuestion) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (TblQuizChoice quizChoice: quizQuestion.getQuizChoiceList()){
            em.merge(quizChoice);
        }
        em.merge(quizQuestion);
        em.getTransaction().commit();
    }
}
