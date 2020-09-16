package com.codecool.hsdecktracker.DAO;

import com.codecool.hsdecktracker.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDao extends PostgresDAO<User> implements DAO<User> {
    public UserDao(String tableName) {
        super(tableName);
    }

    @Override
    User create(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

    @Override
    public User getById(Long id) throws ElementNotFoundException, SQLException {
        return getElementById(id);
    }

    @Override
    public boolean insert(User user) throws ElementNotFoundException, SQLException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users" +
                    "(name) VALUES " +
                    "(?)");
            //preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(1, user.getName());
            //preparedStatement.setInt(3, user.getReward());

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
    public boolean update(User user) throws ElementNotFoundException, SQLException {
        Long id = user.getId();
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET " +
                    "name=? WHERE id = ?");
            preparedStatement.setString(1, user.getName());


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
    public List<User> getAll() {
        try {
            return getAllElements();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("error while getting all Users");
    }

    public User getUserByDeckId(Long id) throws ElementNotFoundException {
        User user;
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select email, u.name, password, u.id from decks d join users u on d.user_id = u.id where d.id = ?;");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = create(rs);
                rs.close();
                preparedStatement.close();
                connection.close();
                return user;
            }
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        throw new ElementNotFoundException(this.TABLENAME + " not found");
    }

}
