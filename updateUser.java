package com.servlet.register;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/updateUser")
public class updateUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String complaint = request.getParameter("complaint");
        String umobile = request.getParameter("mobile");
        String status = request.getParameter("status");
        
        Connection con = null;
        RequestDispatcher dispatcher = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jnpa", "root", "Sanyogita@2004");

            PreparedStatement pst = con.prepareStatement("UPDATE complaints SET uname=?, uemail=?, complaint=?, umobile=?,status=? WHERE id=?");
            pst.setString(1, uname);
            pst.setString(2, uemail);
            pst.setString(3, complaint);
            pst.setString(4, umobile);
            pst.setString(5, status);
            pst.setString(6, id);

            int rowCount = pst.executeUpdate();
            dispatcher = request.getRequestDispatcher("dashboard.jsp");
            if (rowCount > 0) {
                request.setAttribute("updateMessage", "User updated successfully!");
                System.out.println("Update successful for ID: " + id);
            } else {
                request.setAttribute("updateMessage", "Update failed!");
                System.out.println("Update failed for ID: " + id);
            }

            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


