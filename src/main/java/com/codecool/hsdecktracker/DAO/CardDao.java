package com.codecool.hsdecktracker.DAO;

import com.codecool.hsdecktracker.model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CardDao extends PostgresDAO<Card> implements DAO<Card>{
    public CardDao(String tableName) {
        super(tableName);
    }

    @Override
    Card create(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setCard_id(resultSet.getInt("card_id"));
        card.setId(resultSet.getString("id"));
        //card.setCardClass();
        //card.setType();
        card.setName(resultSet.getString("name"));
        //card.setSet();
        card.setText(resultSet.getString("text"));
        card.setCost(resultSet.getInt("cost"));
        card.setAttack(resultSet.getInt("attack"));
        card.setHealth(resultSet.getInt("health"));
        //card.getRarity();
        card.setDustCost(resultSet.getInt("dust_cost"));

        return card;
    }

    @Override
    public Card getById(Long id) throws ElementNotFoundException, SQLException {
        return getElementById(id);
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
        } catch (SQLException  e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("error while getting all Cards");
    }
}
