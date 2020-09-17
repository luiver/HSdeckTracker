package com.codecool.hsdecktracker.servlets;

import com.codecool.hsdecktracker.DAO.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = {"api/v1/users", "api/v1/users/*"}, loadOnStartup = 2)
public class UserServlet extends HttpServlet {
    private final UserDao userDao;

    public UserServlet() {
        this.userDao = new UserDao("users");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
