package com.codecool.hsdecktracker.DAO;

import com.codecool.hsdecktracker.model.*;
import sun.net.ftp.FtpDirEntry;

import java.sql.*;
import java.util.*;

public class CardDao extends PostgresDAO<Card> implements DAO<Card> {
    public CardDao(String tableName) {
        super(tableName);
    }

    @Override
    Card create(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setCard_id(resultSet.getInt("card_id"));
        card.setId(resultSet.getString("id_string"));
        card.setCardClass(CardClass.valueOf(resultSet.getString("card_class")));
        card.setType(Type.valueOf(resultSet.getString("card_type")));
        card.setName(resultSet.getString("name"));
        card.setSet(CardSet.valueOf(resultSet.getString("card_set")));
        card.setText(resultSet.getString("text"));
        card.setCost(resultSet.getInt("mana_cost"));
        card.setAttack(resultSet.getInt("attack"));
        card.setHealth(resultSet.getInt("health"));
        card.setRarity(Rarity.valueOf(resultSet.getString("rarity")));
        card.setDustCost(resultSet.getInt("dust_cost"));

        return card;
    }

    @Override
    public Card getById(Long id) throws ElementNotFoundException, SQLException {
        Card element;
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + this.TABLENAME + " WHERE card_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                element = create(rs);
                rs.close();
                preparedStatement.close();
                connection.close();
                return element;
            }
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
        }
        throw new ElementNotFoundException(this.TABLENAME + " not found");
    }

    @Override
    public boolean insert(Card card) throws ElementNotFoundException, SQLException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cards" +
                    "(name) VALUES " +
                    "(?)");
            //preparedStatement.setLong(1, card.getId());
            preparedStatement.setString(1, card.getName());
            //preparedStatement.setInt(3, card.getReward());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Card card) throws ElementNotFoundException, SQLException {
        Long id = card.getCard_id();
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cards SET " +
                    "name=? WHERE card_id = ?");
            preparedStatement.setString(1, card.getName());


            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) throws ElementNotFoundException {
        return deleteElement(id);
    }

    @Override
    public List<Card> getAll() {
        try {
            return getAllElements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("error while getting all Cards");
    }

    public List<Integer> getCardsByDeckID(int deckId) throws SQLException {
        List<Integer> cardsIDs = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM cards_decks WHERE deck_id = " + deckId + ";");
            while (rs.next()) {
                cardsIDs.add(rs.getInt("cards_card_id"));
            }
            rs.close();
            statement.close();
            connection.close();
            return cardsIDs;
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
        }
        throw new ElementNotFoundException("decks_users could not be found");
    }

    public List<Card> getAllCardsFromDeckByID(int deckID) throws ElementNotFoundException, SQLException {
        List<Card> elements = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select * from cards_decks cd join cards c on cd.cards_card_id = c.card_id where cd.deck_id = "+deckID+";");
            while (rs.next()) {
                elements.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return elements;
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
        }
        throw new ElementNotFoundException(TABLENAME + " could not be found");
    }
}
