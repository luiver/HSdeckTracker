package com.codecool.hsdecktracker.servlets;

import com.codecool.hsdecktracker.DAO.CardDao;
import com.codecool.hsdecktracker.DAO.DeckDao;
import com.codecool.hsdecktracker.DAO.UserDao;
import com.codecool.hsdecktracker.model.Card;
import com.codecool.hsdecktracker.model.Deck;
import com.codecool.hsdecktracker.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Decks", urlPatterns = {"api/v1/decks"}, loadOnStartup = 2)
public class DeckServlet extends HttpServlet {
    private final DeckDao deckDAO;
    private final UserDao userDAO;
    private final CardDao cardDAO;

    public DeckServlet() {
        this.deckDAO = new DeckDao("decks");
        this.userDAO = new UserDao("users");
        this.cardDAO = new CardDao("cards");
    }

    @Override // TODO display list all decks
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        //URI uri = new URI();
        List<Deck> deckList = deckDAO.getAll();
        List<User> userList = userDAO.getAll();
        for (int i = 0; i <deckList.size() ; i++) {
            deckList.get(i).setUser(userList.get(i));
            //deckList.get(i).setUser(userList.get(userList.indexOf(deckList.get(i).getId()))); //TODO improve  matching user id with deck id
            List<Card> deckCards = null;
            try {
                deckCards = cardDAO.getAllCardsFromDeckByID((int) deckList.get(i).getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            deckList.get(i).setCards(deckCards);
        }
        StringBuilder builder = new StringBuilder();

        //List<Card> deckList = cardDAO.getAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(deckList);
        //String json = objectMapper.writeValueAsString(deckList);
        System.out.println(json);

        builder.append(json);
        out.println(builder);
    }

    @Override //TODO add new deck
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("addDeck") != null) {
            //TODO add logic for adding new deck
//            String id = req.getParameter("add");
//            int itemID = Integer.parseInt(id);
//            ShoppingCartServlet.cart.add(stock.getItemById(itemID));
        }
        doGet(req, resp);
    }

    @Override //TODO delete deck
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("deleteDeck") != null) {
            //TODO add logic

        }
        doGet(req, resp);
    }


    @Override //TODO update deck
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("updateDeck") != null) {
            //TODO add logic

        }
        doGet(req, resp);
    }
}
