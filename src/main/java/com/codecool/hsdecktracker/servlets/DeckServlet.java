package com.codecool.hsdecktracker.servlets;

import com.codecool.hsdecktracker.DAO.CardDao;
import com.codecool.hsdecktracker.DAO.DeckDao;
import com.codecool.hsdecktracker.DAO.UserDao;
import com.codecool.hsdecktracker.helpers.DeckFilterHelper;
import com.codecool.hsdecktracker.model.Card;
import com.codecool.hsdecktracker.model.Deck;
import com.codecool.hsdecktracker.helpers.JsonParserHelper;
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
    private final JsonParserHelper<Deck> jsonParserHelper;
    private final DeckFilterHelper deckFilterHelper;

    public DeckServlet() {
        this.deckDAO = new DeckDao("decks");
        this.userDAO = new UserDao("users");
        this.cardDAO = new CardDao("cards");
        this.jsonParserHelper = new JsonParserHelper<>();
        this.deckFilterHelper = new DeckFilterHelper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        StringBuilder builder = new StringBuilder();
        List<Deck> deckList;
        String[] elements = request.getRequestURI().split("/");
        if (elements.length<5) {
            deckList = getAllDecks();
            deckFilterHelper.filterDeckListByUserName(request, deckList);
        } else {
            deckList = getDeckByID(Long.parseLong(elements[4]));
        }
        deckFilterHelper.filterDeckListByCardClass(request, deckList);
        String json;
        if (deckList.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Could not find requested resources");
            json = "Could not find requested resources";
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=utf-8");
            json = jsonParserHelper.serializeListToJSONString(deckList);
        }
        builder.append(json);
        out.println(builder);
    }

    private List<Deck> getDeckByID(long id) {
        List<Deck> deckList = new ArrayList<>();
        Deck deck = null;
        try {
            if (deckDAO.getById(id)==null){
                return deckList;
            }
            deck = deckDAO.getById(id);
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
        for (Deck deck : deckList) {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] elements = request.getRequestURI().split("/");
        if (elements.length<5) {
            JsonObject jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();
            Deck deck = new ObjectMapper().readValue(jsonObject.toString(), Deck.class);
            System.out.println(deck);
            deckDAO.insert(deck);
            deckDAO.insertDeckCardsIntoCardsDeckTable(deck);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Deck creation only on '/decks'!");
        }
        //doGet(request, response);//do we need this?
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        String[] elements = request.getRequestURI().split("/");
        if (elements.length < 5 ) {
            deleteAllDecks();
        } else {
            deleteDeckByID(Long.parseLong(elements[4]));
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        //doGet(request, resp);
    }

    private void deleteDeckByID(long deckID) {
        deckDAO.deleteCardsDecks(deckID);
        deckDAO.delete(deckID);
    }

    private void deleteAllDecks() {
        deckDAO.removeAllDeckDataFromTable("cards_decks");
        deckDAO.removeAllDeckDataFromTable("decks");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] elements = request.getRequestURI().split("/");
        if (elements.length < 5) {
            //dont know what to do here update all Decks ?
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not yet implemented try supply the id ie.: '/decks/88'");
        } else {
            long deckId = Long.parseLong(elements[4]);
            JsonObject jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();
            Deck deck = new ObjectMapper().readValue(jsonObject.toString(), Deck.class);
            deck.setId(deckId);
            if (deckDAO.getById(deckId)!=null) {
                deckDAO.update(deck); //update deck when existing
            } else {
                deckDAO.insert(deck); //create new deck if not existing
            }
            deckDAO.deleteCardsDecks(deck.getId());
            deckDAO.insertDeckCardsIntoCardsDeckTable(deck);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        //doGet(req, resp);
    }
}
