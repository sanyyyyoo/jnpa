package com.servlet.register;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/login")
public class login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uemail = request.getParameter("username");
        String upwd = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Corrected Driver Name
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jnpa", "root", "Sanyogita@2004");

            // Validate login
            PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE uemail=? AND upwd=?");
            pst.setString(1, uemail);
            pst.setString(2, upwd);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                session.setAttribute("name", rs.getString("uname"));

                List<String[]> usersList = new ArrayList<>();
                PreparedStatement pst2 = con.prepareStatement("SELECT id, uname, uemail,complaint, umobile,status FROM complaints");
                ResultSet rs1 = pst2.executeQuery();

                while (rs1.next()) {
                    String[] user = {rs1.getString("id"), rs1.getString("uname"), rs1.getString("uemail"), rs1.getString("complaint"), rs1.getString("umobile"),rs1.getString("status")};
                    usersList.add(user);
                    System.out.println("User retrieved: " + rs1.getString("uname"));
                }

                System.out.println("Total users retrieved: " + usersList.size()); 
                session.setAttribute("usersList", usersList);

                dispatcher = request.getRequestDispatcher("dashboard.jsp");
            } else {
                request.setAttribute("status", "failed");
                request.setAttribute("successMessage", "Username or password incorrect");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }

            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

