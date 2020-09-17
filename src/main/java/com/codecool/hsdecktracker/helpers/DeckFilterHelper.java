package com.codecool.hsdecktracker.helpers;

import com.codecool.hsdecktracker.model.Deck;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeckFilterHelper {

    public DeckFilterHelper() {
    }

    public void filterDeckListByCardClass(HttpServletRequest request, List<Deck> deckList) {
        //shows only chosen class cards in decks
        if (request.getParameter("cardClass") != null) {
            String parameter = request.getParameter("cardClass");
            for (Deck deck : deckList) {
                for (int j = 0; j < deck.getCards().size(); j++) {
                    deck.getCards().removeIf(card -> !card.getCardClass().getStatus().equals(parameter));
                }
            }
        }
    }

    public void filterDeckListByUserName(HttpServletRequest request, List<Deck> deckList) {
        //shows only decks created by specific user
        if (request.getParameter("userName") != null) {
            String parameter = request.getParameter("userName");
            System.out.println(parameter);
            deckList.removeIf(deck -> !deck.getUser().getName().equals(parameter));
        }
    }
}
