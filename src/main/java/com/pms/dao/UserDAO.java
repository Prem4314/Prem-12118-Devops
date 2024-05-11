package com.pms.dao;

import com.pms.bean.User;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO {

    @Autowired
    private EntityManager em;

    @Transactional
    public String saveUser(User user) {
        try {
            em.persist(user);
            return "saved successfully..!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Not saved successfully..!";
        }
    }

    public List<User> findAll() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Transactional
    public String deleteUser(Long id) {
        User obj = em.find(User.class, id);
        em.remove(obj);
        return "Object Deleted";
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Transactional
    public User save(User user) {
        return em.merge(user);
    }
}
