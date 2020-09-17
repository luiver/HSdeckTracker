package com.codecool.hsdecktracker.servlets;

import com.codecool.hsdecktracker.entitymenager.CardMenager;
import com.codecool.hsdecktracker.model.Card;
import org.json.simple.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.json.JSONArray.*;

@WebServlet(name="CardServlet", urlPatterns = {"/api/v1/cards"}, loadOnStartup = 2)
public class CardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String cardClass = request.getParameter("class");
        List<String> cards = CardMenager.getCardMenagerInstance().getCardByClass(cardClass);
        String output = "{\"cards\":" + cards + "}";
        out.println(output);
    }
}
