package com.tientt.daos.implementations;

import com.tientt.daos.interfaces.TblSubjectDAO;
import com.tientt.entities.TblSubject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class TblSubjectDAOImpl implements TblSubjectDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("P0014PU");

    @Override
    public TblSubject findTblSubjectByID(String subjectID) {
        EntityManager em = emf.createEntityManager();
        return em.find(TblSubject.class, subjectID);
    }

    @Override
    public List<TblSubject> findAllTblSubject() {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblSubject AS t";
        TypedQuery<TblSubject> query = em.createQuery(jpql, TblSubject.class);
        return query.getResultList();
    }
}
