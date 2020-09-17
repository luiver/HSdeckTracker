package com.codecool.hsdecktracker.servlets;

import com.codecool.hsdecktracker.DAO.UserDao;
import com.codecool.hsdecktracker.model.Deck;
import com.codecool.hsdecktracker.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] splittedURI = request.getRequestURI().split("/");
        if (splittedURI.length < 5) {
            JsonObject jsonUser = (JsonObject) JsonParser.parseReader(request.getReader());
            User newUser = new ObjectMapper().readValue(jsonUser.toString(), User.class);
            userDao.insert(newUser);
            response.setStatus(HttpServletResponse.SC_CREATED);
            System.out.println(newUser.toString());
        }else{
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "User creation only on '/users'!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] elements = request.getRequestURI().split("/");
        if (elements.length < 5) {
            deleteAllUsers();
        } else {
            userDao.deleteElement(Long.parseLong(elements[4]));
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private void deleteAllUsers() {
        userDao.removeAllUsers("users");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        List<User> userList;
        String[] splitedURI = request.getRequestURI().split("/");
        if (splitedURI.length < 5) {
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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] elements = request.getRequestURI().split("/");
        if (elements.length < 5) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not yet implemented try supply the id ie.: '/Users/88'");
        } else {
            long userId = Long.parseLong(elements[4]);
            JsonObject jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();
            User newUser = new ObjectMapper().readValue(jsonObject.toString(), User.class);
            newUser.setId(userId);
            if (userDao.getById(userId) != null) {
                userDao.update(newUser);
            } else {
                userDao.insert(newUser);
            }
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    private List<User> getUserById(int id) {
        List<User> userList = new ArrayList<>();
        User user;
        user = userDao.getById((long) id);
        userList.add(user);
        return userList;
    }
}
