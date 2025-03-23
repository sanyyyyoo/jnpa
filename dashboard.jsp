<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>

<%
    HttpSession session1 = request.getSession(false);
    if (session1 == null || session1.getAttribute("name") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<String[]> usersList = (List<String[]>) session1.getAttribute("usersList");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h2>Welcome, <%= session1.getAttribute("name") %>!</h2>
    <h3>Registered Complaints</h3>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>email</th>
            <th>Complaint</th>
            <th>Mobile</th>
            <th>Status</th>
        </tr>

        <% if (usersList != null) { 
            for (String[] user : usersList) { %>
                <tr>
                    <td><%= user[0] %></td>
                    <td><%= user[1] %></td>
                    <td><%= user[2] %></td>
                    <td><%= user[3] %></td>
                    <td><%= user[4] %></td>
                    <td><%= user[5] %></td>
                    
                    <td>
                <a href="editUser.jsp?id=<%= user[0] %>">Edit</a>
            </td>
                </tr>
        <%  } } else { %>
            <tr><td colspan="5">No users found</td></tr>
        <% } %>
    </table>
<%
    String updateMessage = (String) request.getAttribute("updateMessage");
    if (updateMessage != null) {
%>
        <div style="color: green; font-weight: bold;"><%= updateMessage %></div>
<%
    }
%>

    <br>
    <a href="logout.jsp">Logout</a>
</body>
</html>

