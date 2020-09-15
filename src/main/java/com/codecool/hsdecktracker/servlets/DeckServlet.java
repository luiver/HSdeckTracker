package com.codecool.hsdecktracker.servlets;

import com.codecool.hsdecktracker.DAO.DeckDao;
import com.codecool.hsdecktracker.model.Deck;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Decks", urlPatterns = {"/decks"}, loadOnStartup = 2)
public class DeckServlet extends HttpServlet {
    private final DeckDao deckDAO;

    public DeckServlet() {
        this.deckDAO = new DeckDao("decks");
    }

    @Override // TODO display list all decks
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        List<Deck> deckList = deckDAO.getAll();
        StringBuilder builder = new StringBuilder();
        for (Deck deck : deckList) {
            builder.append("<div>");
            builder.append("<span> Deck id: " + deck.getId() + "</span>");
            builder.append("<span> Deck name: " + deck.getName() + "\n</span>");
            builder.append("</div>");
        }

        out.println(
                "<html>\n" +
                        "<head><title>Decks List</title></head>\n" +
                        "<body>\n" +
                        "<h1>Browse decks</h1>" +
                        "<br/>" +
                        "<div>" + builder.toString() + "</div>" +
                        "<br/>" +
                        "<span> Add new Deck: </span>"+
                        "<form method=\"POST\">"+
                        "<label for=\"addDeck\"></label><br>"+
                        "<input type=\"text\" id=\"addDeck\" name=\"addDeck\" value=\"Add deck name...\">"+
                        "<input type=\"submit\" name=\"addDeck\" value=\"Add\" >"+
                        "</form>"+
                        "<button type=\"button\"><a href=\"/cards\">Go Check Whole Collection Of Cards</a></button>" +
                        "</body></html>"
        );
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
}
