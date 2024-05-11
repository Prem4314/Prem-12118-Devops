package com.pms.dao;

import com.pms.bean.Complaint;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ComplaintDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Complaint save(Complaint complaint) {
        entityManager.persist(complaint);
        return complaint;
    }

    public List<Complaint> findAll() {
        return entityManager.createQuery("SELECT c FROM Complaint c", Complaint.class).getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        Complaint complaint = entityManager.find(Complaint.class, id);
        if (complaint != null) {
            entityManager.remove(complaint);
        } else {
            throw new RuntimeException("Complaint not found with id: " + id);
        }
    }

    public Optional<Complaint> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Complaint.class, id));
    }

    @Transactional
    public Complaint update(Complaint complaint) {
        return entityManager.merge(complaint);
    }
}
