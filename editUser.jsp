<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%
    String id = request.getParameter("id");
    String name = "";
    String email = "";
    String complaint = "";
    String mobile = "";
    String status="";
    

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jnpa", "root", "Sanyogita@2004");

        PreparedStatement pst = con.prepareStatement("SELECT * FROM complaints WHERE id = ?");
        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            name = rs.getString("uname");
            email = rs.getString("uemail");
            complaint=rs.getString("complaint");
            mobile = rs.getString("umobile");
            status = rs.getString("status");
        }

        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
    <h2>Edit User Details</h2>
    <form action="updateUser" method="post">
        <input type="hidden" name="id" value="<%= id %>">
        <label>Name:</label>
        <input type="text" name="name" value="<%= name %>"><br><br>

        <label>Email:</label>
        <input type="email" name="email" value="<%= email %>"><br><br>

        <label>Complaint:</label>
        <input type="text" name="complaint" value="<%= complaint%>"><br><br>
       
        <label>Mobile:</label>s
        <input type="text" name="mobile" value="<%= mobile %>"><br><br>
        
        <label>Status:</label>s
        <input type="text" name="status" value="<%= status %>"><br><br>

        <input type="submit" value="Update">
    </form>
</body>
</html>
