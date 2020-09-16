package com.codecool.hsdecktracker.DAO;

import com.codecool.hsdecktracker.model.Deck;

import java.sql.*;
import java.util.List;

public class DeckDao extends PostgresDAO<Deck> implements DAO<Deck>{

    public DeckDao(String tableName) {
        super(tableName);
    }

    @Override
    Deck create(ResultSet resultSet) throws SQLException {
        Deck deck = new Deck();
            deck.setId(resultSet.getLong("id"));
            deck.setName(resultSet.getString("name"));
            
        return deck;
    }

    @Override
    public Deck getById(Long id) throws ElementNotFoundException, SQLException {
        return getElementById(id);
    }

    @Override
    public boolean insert(Deck deck) throws ElementNotFoundException, SQLException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO decks" +
                    "(name) VALUES " +
                    "(?)");
            //preparedStatement.setLong(1, deck.getId());
            preparedStatement.setString(1, deck.getName());
            //preparedStatement.setInt(3, deck.getReward());

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
    public boolean update(Deck deck) throws ElementNotFoundException, SQLException {
        Long id = deck.getId();
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE decks SET " +
                    "name=? WHERE id = ?");
            preparedStatement.setString(1, deck.getName());


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
    public List<Deck> getAll() {
        try {
            return getAllElements();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("error while getting all Decks");
    }
}
