package com.codecool.hsdecktracker.servlets;

import com.codecool.hsdecktracker.DAO.UserDao;
import com.codecool.hsdecktracker.model.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"api/v1/users", "api/v1/users/*"}, loadOnStartup = 2)
public class UserServlet extends HttpServlet {
    private final UserDao userDao;

    public UserServlet() {
        this.userDao = new UserDao("users");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
//        StringBuilder sb = new StringBuilder();
        List<User> userList = userDao.getAll();
        userList = userDao.getAll();
        String[] splitedURI = request.getRequestURI().split("/");
        if (splitedURI.length<5) {
            userList = userDao.getAll();
        } else {
            userList = getUserById(Integer.parseInt(splitedURI[4]));
        }

        String json;
        if (userList.isEmpty()) {
            response.setStatus(404);
            json = "Could not find requested resources";
        } else {
            response.setStatus(200);
            response.setContentType("application/json");
            json = new Gson().toJson(userList);
        }

        out.write(json);
    }

    private List<User> getUserById(int id) {
        List<User> userList = new ArrayList<>();
        User user = null;
        try {
            user = userDao.getById((long) id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userList.add(user);
        return userList;
    }
}
