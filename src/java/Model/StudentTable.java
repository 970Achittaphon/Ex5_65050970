/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model; // Ensure this package matches your project structure

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * Data Access Object (DAO) for the Student Entity. 
 * Manages all persistence operations using JPA.
 */
public class StudentTable {

    // IMPORTANT: Create the EntityManagerFactory once for efficiency.
    private static final String PERSISTENCE_UNIT_NAME = "Ex5_65050970PU"; 
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    /**
     * Finds a student by their ID.
     * @param id The student ID (Primary Key).
     * @return The Student object if found, otherwise null.
     */
    public static Student findStudentById(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Inserts a new student record into the database.
     * Checks if the student ID already exists (Requirement).
     * @param stu The Student entity to insert.
     * @return 1 for success, 0 for duplicate ID, -1 for general error.
     */
    public static int insertStudent(Student stu) {
        // Check for duplicate ID to meet the assignment's requirement
        if (findStudentById(stu.getStudentid()) != null) {
            return 0; // Duplicate ID error code
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(stu); // Add the new student entity
            em.getTransaction().commit();
            return 1; // Success code
        } catch (Exception e) {
            System.err.println("Error inserting student: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return -1; // General error code
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves all student records from the database.
     * @return A List of all Student objects.
     */
    public static List<Student> findAllStudent() {
        EntityManager em = emf.createEntityManager();
        List<Student> studentList = null;
        try {
            // Uses the NamedQuery "Student.findAll" defined in Student.java
            TypedQuery<Student> query = em.createNamedQuery("Student.findAll", Student.class);
            studentList = query.getResultList();
            return studentList;
        } catch (Exception e) {
            System.err.println("Error retrieving all students: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }
}
