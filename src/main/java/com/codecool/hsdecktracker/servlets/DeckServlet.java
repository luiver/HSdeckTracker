package com.codecool.hsdecktracker.servlets;

import com.codecool.hsdecktracker.DAO.CardDao;
import com.codecool.hsdecktracker.DAO.DeckDao;
import com.codecool.hsdecktracker.DAO.UserDao;
import com.codecool.hsdecktracker.model.Card;
import com.codecool.hsdecktracker.model.Deck;
import com.codecool.hsdecktracker.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Decks", urlPatterns = {"api/v1/decks", "api/v1/decks/*"}, loadOnStartup = 2)
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
        StringBuilder builder = new StringBuilder();
        List<Deck> deckList;
        String[] elements = request.getRequestURI().split("/");
        if (elements.length<5) {
            deckList = getAllDecks();
        } else {
            deckList = getDeckByID(Integer.parseInt(elements[4]));
        }

        String json;
        if (deckList.isEmpty()) {
            response.setStatus(404);
            json = "Could not find requested resources";
        } else {
            response.setStatus(200);
            response.setContentType("application/json");
            json = serializeListToJSONString(deckList);
        }
        builder.append(json);
        out.println(builder);
    }

    private List<Deck> getDeckByID(int id) {
        List<Deck> deckList = new ArrayList<>();
        Deck deck = null;
        try {
            deck = deckDAO.getById((long) id);
            deck.setCards(cardDAO.getAllCardsFromDeckByID((int) deck.getId()));
            deck.setUser(userDAO.getUserByDeckId(deck.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        deckList.add(deck);
        return deckList;
    }

    private List<Deck> getAllDecks() {
        List<Deck> deckList = deckDAO.getAll();
        //List<User> userList = userDAO.getAll();
        for (Deck deck : deckList) {
            //deckList.get(i).setUser(userList.get(i));
            //deckList.get(i).setUser(userList.get(userList.indexOf(deckList.get(i).getId()))); //TODO improve  matching user id with deck id
            deck.setUser(userDAO.getUserByDeckId(deck.getId()));
            List<Card> deckCards = null;
            try {
                deckCards = cardDAO.getAllCardsFromDeckByID((int) deck.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            deck.setCards(deckCards);
        }
        return deckList;

    }

    private String serializeListToJSONString(List<Deck> deckList) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(deckList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }


    @Override //TODO add new deck
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();
        Deck deck = new ObjectMapper().readValue(jsonObject.toString(), Deck.class);
        System.out.println(deck);
        deckDAO.insert(deck);
        doGet(request, response);//do we need this?
    }

    @Override //TODO delete deck
    protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        String[] elements = request.getRequestURI().split("/");
        if (elements.length<5) {
            deleteAllDecks();
        } else {
            deleteDeckByID(Long.parseLong(elements[4]));
        }
        //doGet(request, resp);
    }

    private void deleteDeckByID(long deckID) {
        deckDAO.delete(deckID);
        deckDAO.deleteCardsDecks(deckID);
    }

    private void deleteAllDecks() {
        deckDAO.removeAllDeckDataFromTable("decks");
        deckDAO.removeAllDeckDataFromTable("cards_decks");
    }


    @Override //TODO update deck
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("updateDeck") != null) {
            //TODO add logic

        }
        doGet(req, resp);
    }
}
