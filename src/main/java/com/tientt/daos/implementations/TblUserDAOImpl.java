package com.tientt.daos.implementations;

import com.tientt.commons.Role;
import com.tientt.daos.interfaces.TblUserDAO;
import com.tientt.entities.TblRole;
import com.tientt.entities.TblUser;


import javax.persistence.TypedQuery;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.NoResultException;


public class TblUserDAOImpl implements TblUserDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("P0014PU");

    @Override
    public TblUser findUserByEmailAndPassword(String email, String password) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT t FROM TblUser t WHERE t.email = :email AND t.password = :password";
        TypedQuery<TblUser> query = em.createQuery(jpql, TblUser.class);
        query.setParameter("email",email);
        query.setParameter("password", password);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public int countUserByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT COUNT(t.email) FROM TblUser t WHERE t.email = :email";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("email", email);
        return query.getSingleResult().intValue();
    }

    @Override
    public void insertAccount(TblUser user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TblRole role = em.find(TblRole.class, Role.STUDENT);
        user.setRole(role);
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public TblUser findUserByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        return em.find(TblUser.class, email);
    }
}
