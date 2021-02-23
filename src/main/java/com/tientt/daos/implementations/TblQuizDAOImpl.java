package com.tientt.daos.implementations;

import com.tientt.daos.interfaces.TblQuizDAO;
import com.tientt.entities.TblQuiz;

import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;

import java.util.List;

public class TblQuizDAOImpl implements TblQuizDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("P0014PU");

    @Override
    public TblQuiz findQuizByTestID(String testID) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblQuiz t WHERE t.test.ID = :testID";
        TypedQuery<TblQuiz> query = em.createQuery(jpql, TblQuiz.class);
        query.setParameter("testID", testID);
        try{
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public TblQuiz findQuizByID(String quizID) {
        EntityManager em = emf.createEntityManager();
        TblQuiz quiz = em.find(TblQuiz.class, quizID);
        return quiz;
    }

    @Override
    public List<TblQuiz> findAllQuizByUserEmail(String email) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblQuiz t WHERE t.user.email = :email ORDER BY t.endTime DESC";
        TypedQuery<TblQuiz> query = em.createQuery(jpql, TblQuiz.class);
        query.setParameter("email", email);
        return query.getResultList();
    }

    @Override
    public List<TblQuiz> findAllQuizByUserEmailAndSubjectID(String userEmail, String subjectID) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblQuiz t WHERE t.user.email = :email AND t.test.subject.ID = :subjectID ORDER BY t.endTime DESC ";
        TypedQuery<TblQuiz> query = em.createQuery(jpql, TblQuiz.class);
        query.setParameter("email", userEmail);
        query.setParameter("subjectID", subjectID);
        return query.getResultList();
    }

    @Override
    public int countCorrectAnswerByQuizID(String quizID) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT COUNT(t) FROM TblQuizChoice AS t " +
                "WHERE t.quizQuestion.quiz.ID = :quizID " +
                "AND t.isCorrect = :isCorrect " +
                "AND t.isSelect = :isSelect";
        TypedQuery<Long> query =
                em.createQuery(jpql, Long.class);
        query.setParameter("quizID", quizID);
        query.setParameter("isCorrect", true);
        query.setParameter("isSelect", true);
        Long count = query.getSingleResult();
        return count.intValue();
    }

    @Override
    public void updateQuiz(TblQuiz quiz) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(quiz);
        em.getTransaction().commit();
    }

    @Override
    public void insertQuiz(TblQuiz quiz) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(quiz);
        em.getTransaction().commit();
    }

    @Override
    public TblQuiz findQuizByTestIDAndUserEmail(String testID, String userEmail) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblQuiz t WHERE t.user.email = :email AND t.test.ID = :testID";
        TypedQuery<TblQuiz> query = em.createQuery(jpql, TblQuiz.class);
        query.setParameter("email", userEmail);
        query.setParameter("testID", testID);
        try{
            return query.getSingleResult();
        } catch (NoResultException ex){
            return null;
        }
    }
}
