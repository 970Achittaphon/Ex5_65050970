<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Added Successfully</title>
</head>
<body>
    <h2>Student Added Successfully</h2>
    
    <p>ID: ${requestScope.student.studentid}</p>
    <p>Name: ${requestScope.student.studentname}</p>
    <p>GPA: ${requestScope.student.gpa}</p>
    <p style="font-weight: bold;">is added</p>
    
    <hr>
    
    <jsp:include page="studentList.jsp" />
    
    <p><a href="index.html">Back to Add Student</a></p> 
</body>
</html>