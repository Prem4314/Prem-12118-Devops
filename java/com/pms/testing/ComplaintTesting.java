package com.pms.testing;

import com.pms.bean.Complaint;
import com.pms.controller.ComplaintController;
import com.pms.repo.ComplaintRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComplaintControllerTest {

@Autowired
private ComplaintController complaintController;

@Autowired
private ComplaintRepo complaintRepo;

@Test
@Order(1)
void testRegisterComplaint() {
    Complaint complaint = new Complaint();
    complaint.setDescription("Test complaint");

    Complaint registeredComplaint = complaintController.registerComplaint(complaint);

    Assertions.assertNotNull(registeredComplaint);
    Assertions.assertNotNull(registeredComplaint.getComplaintId());
    Assertions.assertEquals("Test complaint", registeredComplaint.getDescription());
    Assertions.assertEquals("Pending", registeredComplaint.getStatus());
}

@Test
@Order(2)
void testGetAllComplaints() {
    List<Complaint> complaintList = complaintController.getAllComplaints();
    Assertions.assertFalse(complaintList.isEmpty());
}

@Test
@Order(3)
void testUpdateComplaint() {
    Long complaintId = 1L;
    Complaint updatedComplaint = new Complaint();
    updatedComplaint.setDescription("Updated complaint");

    Complaint result = complaintController.updateComplaint(complaintId, updatedComplaint);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(updatedComplaint.getDescription(), result.getDescription());
}

@Test
@Order(4)
void testUpdateComplaintStatus() {
    Long complaintId = 1L;
    String status = "Resolved";

    ResponseEntity<String> responseEntity = complaintController.updateComplaintStatus(complaintId, status);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals("Complaint status updated successfully.", responseEntity.getBody());
}

@Test
@Order(5)
void testDeleteComplaint() {
    Long complaintId = 1L;

    complaintController.deleteComplaint(complaintId);

    Assertions.assertFalse(complaintRepo.findById(complaintId).isPresent());
}

@Test
@Order(6)
void testUpdateNonExistingComplaint() {
    Long nonExistingComplaintId = 100L;
    Complaint updatedComplaint = new Complaint();
    updatedComplaint.setDescription("Updated complaint");

    Assertions.assertThrows(RuntimeException.class, () -> complaintController.updateComplaint(nonExistingComplaintId, updatedComplaint));
}

@Test
@Order(7)
void testUpdateComplaintStatusForNonExistingComplaint() {
    Long nonExistingComplaintId = 100L;
    String status = "Resolved";

    ResponseEntity<String> responseEntity = complaintController.updateComplaintStatus(nonExistingComplaintId, status);

    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("Complaint not found with id: " + nonExistingComplaintId, responseEntity.getBody());
}
}