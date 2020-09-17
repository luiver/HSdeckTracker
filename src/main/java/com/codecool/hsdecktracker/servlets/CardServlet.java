package com.codecool.hsdecktracker.servlets;

import com.codecool.hsdecktracker.entitymenager.CardMenager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="CardServlet", urlPatterns = {"/api/v1/cards"}, loadOnStartup = 2)
public class CardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String cardClass = request.getParameter("class") != null?request.getParameter("class"):"any";
        List<String> cards = CardMenager.getCardMenagerInstance().getCardByClass(cardClass);
        String output = "{\"cards\":" + cards + "}";
        out.println(output);
    }

    @Override
    protected  void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int rowsAffected = CardMenager.getCardMenagerInstance().deleteCardById(id);
        out.println("{\"rows affected\":" + rowsAffected + "}");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String inputCard = request.getParameter("card");
        response.setCharacterEncoding("UTF-8");
        if (CardMenager.getCardMenagerInstance().addCard(inputCard)){
            response.setStatus(200);
            out.println("One row affected, \n" + inputCard + "\n added to database.");
        } else {
            response.setStatus(400);
            out.println("Provide card in json format.");
        }
    }
}
