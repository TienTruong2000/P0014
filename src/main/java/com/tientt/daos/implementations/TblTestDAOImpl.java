package com.tientt.daos.implementations;

import com.tientt.daos.interfaces.TblTestDAO;
import com.tientt.entities.TblTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class TblTestDAOImpl implements TblTestDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("P0014PU");
    @Override
    public void insertTest(TblTest test) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(test);
        em.getTransaction().commit();

    }

    @Override
    public List<TblTest> findAllTestOrderByCreateDate() {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblTest t order by t.createDate DESC ";
        TypedQuery<TblTest> query = em.createQuery(jpql, TblTest.class);
        return query.getResultList();
    }

    @Override
    public List<TblTest> findTestBySubjectOrderByCreateDate(String subjectID) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblTest t WHERE t.subject.ID = :subjectID ORDER BY t.createDate DESC ";
        TypedQuery<TblTest> query = em.createQuery(jpql, TblTest.class);
        query.setParameter("subjectID", subjectID);
        return query.getResultList();
    }

    @Override
    public TblTest findTestByID(String ID) {
        EntityManager em = emf.createEntityManager();
        return em.find(TblTest.class, ID);
    }
}
