package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private final Util util;

    {
        util = new Util();
    }

    @Override
    public void createUsersTable() {

        String createTableSQL = "CREATE TABLE IF NOT EXISTS user " +
                "(`id` bigint(19) NOT NULL AUTO_INCREMENT, " +
                "`name` varchar(45) NOT NULL, " +
                "`lastName` varchar(45) NOT NULL, " +
                "`age` tinyint(3) NOT NULL, " +
                "PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8";

        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(createTableSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String tableName = "user";
        try
                (PreparedStatement preparedStatement = util.getConnection().prepareStatement("SHOW TABLES LIKE ?")) {
            preparedStatement.setString(1, tableName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Таблица " + tableName + " не найдена");
            } else {
                String sql = "DROP TABLE user;";
                preparedStatement.executeUpdate(sql);
                System.out.println("Таблица " + tableName + " успешно удалена");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user (`name`, `lastName`, `age`) VALUES (?,?,?)";

        try (PreparedStatement preparedStatementstatement = util.getConnection().prepareStatement(sql)) {
            preparedStatementstatement.setString(1, name);
            preparedStatementstatement.setString(2, lastName);
            preparedStatementstatement.setByte(3, age);
            preparedStatementstatement.executeUpdate();


            System.out.println("User успешно создан");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE `id` = " + id;

        try (PreparedStatement pstatement = util.getConnection().prepareStatement(sql)) {
            pstatement.executeUpdate();
            System.out.println("User c id" + id + " успешно удален");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM user";

        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge((byte) rs.getInt("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            List<User> userLists = new ArrayList<>();
            return userLists;
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {

        String sql = "DROP TABLE IF EXISTS user";

        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица успешно очищена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
