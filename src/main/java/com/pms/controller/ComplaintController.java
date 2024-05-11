package com.pms.controller;

import com.pms.bean.Complaint;
import com.pms.dao.ComplaintDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintDao dao;

    @GetMapping("/all")
    public List<Complaint> getAllComplaints() {
        return dao.findAll();
    }

    @PostMapping("/register")
    public Complaint registerComplaint(@RequestBody Complaint complaint) {
        complaint.setStatus("Pending");
        return dao.save(complaint);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComplaint(@PathVariable Long id) {
        dao.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Complaint updateComplaint(@PathVariable Long id, @RequestBody Complaint updatedComplaint) {
        Optional<Complaint> optionalComplaint = dao.findById(id);
        if (optionalComplaint.isPresent()) {
            Complaint existingComplaint = optionalComplaint.get();
            existingComplaint.setDescription(updatedComplaint.getDescription());
            return dao.save(existingComplaint);
        } else {
            throw new RuntimeException("Complaint not found with id: " + id);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateComplaintStatus(@PathVariable Long id, @RequestParam String status) {
        Optional<Complaint> optionalComplaint = dao.findById(id);
        if (optionalComplaint.isPresent()) {
            Complaint complaint = optionalComplaint.get();
            complaint.setStatus(status);
            dao.save(complaint);
            return ResponseEntity.ok("Complaint status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint not found with id: " + id);
        }
    }
}
