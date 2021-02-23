package com.tientt.daos.implementations;

import com.tientt.commons.Constant;
import com.tientt.daos.interfaces.TblQuestionDAO;
import com.tientt.entities.TblQuestion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class TblQuestionDAOImpl implements TblQuestionDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("P0014PU");

    @Override
    public List<TblQuestion> findAllQuestionByPageOrderByCreateDate(int page) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblQuestion AS t ORDER BY t.createDate DESC ";
        TypedQuery<TblQuestion> query = em.createQuery(jpql, TblQuestion.class);
        int firstIndex = (page - 1) * Constant.PAGE_SIZE;
        query.setFirstResult(firstIndex);
        query.setMaxResults(Constant.PAGE_SIZE);
        return query.getResultList();
    }

    @Override
    public int countPage() {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT COUNT(t.ID) FROM TblQuestion AS t";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public List<TblQuestion> findQuestionByContentAndSubjectAndStatusAndPageOrderByCreateDate
            (String content, String subjectID, String statusString, int page) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblQuestion AS t WHERE t.content LIKE :content AND " +
                "t.subject.ID LIKE :subjectID AND t.status LIKE :status ORDER BY t.createDate DESC ";
        TypedQuery<TblQuestion> query = em.createQuery(jpql, TblQuestion.class);
        int firstIndex = (page - 1) * Constant.PAGE_SIZE;
        query.setFirstResult(firstIndex);
        query.setMaxResults(Constant.PAGE_SIZE);
        query.setParameter("content", "%" + content + "%");
        query.setParameter("subjectID", "%" + subjectID + "%");
        query.setParameter("status", "%" + statusString + "%");
        return query.getResultList();
    }

    @Override
    public int countPageByContentAndSubjectAndStatus(String content, String subjectID, String statusString) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT COUNT(t.ID) FROM TblQuestion AS t WHERE t.content LIKE :content AND " +
                "t.subject.ID LIKE :subjectID AND t.status LIKE :status";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("content", "%" + content + "%");
        query.setParameter("subjectID", "%" + subjectID + "%");
        query.setParameter("status", "%" + statusString + "%");
        return query.getSingleResult().intValue();

    }


    @Override
    public void updateTblQuestion(TblQuestion question) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(question);
        em.getTransaction().commit();
    }

    @Override
    public TblQuestion findTblQuestionByID(String questionID) {
        EntityManager em = emf.createEntityManager();
        return em.find(TblQuestion.class, questionID);
    }

    @Override
    public void insertTblQuestion(TblQuestion question) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(question);
        em.getTransaction().commit();
    }


    @Override
    public int countNumberOfQuestionBySubjectIDAndStatus(String subjectID, int status) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT COUNT(t.ID) FROM TblQuestion t WHERE t.subject.ID = :subjectID " +
                "AND t.status = :status";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("subjectID", subjectID);
        query.setParameter("status", status);
        Long count = query.getSingleResult();
        return count.intValue();
    }


    @Override
    public TblQuestion getTblQuestionBySubjectIDAndStatusAndIndex(String subjectID, int status, int index) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblQuestion AS t WHERE t.subject.ID = :subjectID " +
                "AND t.status = :status";
        TypedQuery<TblQuestion> query = em.createQuery(jpql, TblQuestion.class);
        query.setParameter("subjectID", subjectID);
        query.setParameter("status", status);
        query.setFirstResult(index);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
