/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Student;
import Model.StudentTable;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author froys
 */
@WebServlet(name = "AddStudentServlet", urlPatterns = {"/addstudent"})
public class AddStudentServlet extends HttpServlet {

    private static final String SUCCESS_PAGE = "addSuccess.jsp";
    private static final String FAILURE_PAGE = "addFailure.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Retrieve and validate data from the form (index.html)
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String gpaStr = request.getParameter("gpa");
        
        // Input conversion
        double gpa = 0.0;
        try {
            gpa = Double.parseDouble(gpaStr);
        } catch (NumberFormatException e) {
            // Error handling for invalid GPA, though index.html enforces number input
            System.err.println("Controller Error: Invalid GPA format received.");
        }
        
        // 2. Create the Model object (Student Entity)
        Student newStudent = new Student(id, name, gpa);
        
        // 3. Call the Model Component (StudentTable) to insert data
        // result = 1 (Success), 0 (Duplicate ID), -1 (General Error)
        int result = StudentTable.insertStudent(newStudent);
        
        // 4. Always retrieve ALL students for the studentList.jsp (Requirement)
        List<Student> studentList = StudentTable.findAllStudent();
        
        // Set student list as a request attribute for the JSPs to use
        request.setAttribute("studentList", studentList);
        
        String targetPage;
        
        if (result == 1) {
            // Success: Set the added student object for display on the success page
            request.setAttribute("student", newStudent); 
            targetPage = SUCCESS_PAGE;
            
        } else { 
            // Failure: Duplicate ID (result == 0) or General Error (result == -1)
            // Both failure cases direct to the same error page (addFailure.jsp)
            targetPage = FAILURE_PAGE;
        }
        
        // 5. Forward the request and data to the determined View (JSP)
        RequestDispatcher rd = request.getRequestDispatcher(targetPage);
        rd.forward(request, response);
    }
    
    /**
     * Handles the HTTP <code>GET</code> method. Redirects to the input form.
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests back to the form page to ensure proper usage
        response.sendRedirect("index.html");
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Student Add Controller Servlet for MVC assignment";
    }
}
