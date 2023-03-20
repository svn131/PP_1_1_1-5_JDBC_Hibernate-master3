package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//@Component
public class UserDaoJDBCImpl implements UserDao {
    // А как закрыть соединение  методах если оно происходит в классе утил ?
    private final Util util;

    {util = new Util();}






    public void createUsersTable() {
//        String sql = "CREATE TABLE IF NOT EXISTS `mydbtest`.`user` (" +
//                "`id` bigint(19) NOT NULL AUTO_INCREMENT," +
//                "`name` varchar(45) NOT NULL," +
//                "`lastName` varchar(45) NOT NULL," +
//                "`age` tinyint(3) NOT NULL," +
//                "PRIMARY KEY (`id`)" +
//                ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (`id` bigint(19) NOT NULL AUTO_INCREMENT,`name` varchar(45) NOT NULL,`lastName` varchar(45) NOT NULL,`age` tinyint(3) NOT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8");
//            statement.executeUpdate("CREATE TABLE IF NOT EXISTS USERTABLE(ID BIGINT AUTO_INCREMENT PRIMARY KEY," + " NAME VARCHAR (255), LASTNAME VARCHAR (255), AGE BIGINT)");
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }


    public void dropUsersTable() {
        String tableName = "user";
        try
                (Statement statement = util.getConnection().createStatement()){

            // Проверка, существует ли таблица
            ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE '" + tableName + "'");
            if (!resultSet.next()) {
                System.out.println("Таблица " + tableName + " не найдена");
            } else {
                // Таблица существует, можно её удалить
                String sql = "DROP TABLE user;";
                statement.executeUpdate(sql);
                System.out.println("Таблица " + tableName + " успешно удалена");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    public void dropUsersTable() {
//      String sql = "DROP TABLE IF EXISTS `mydbtest`.`user`;";
//        try {
//            Statement statement = util.getConnection().createStatement();
//            statement.executeUpdate(sql);
//            System.out.println("Таблица успешно удалена");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }



    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user (`name`, `lastName`, `age`) VALUES (?,?,?)";

        try {PreparedStatement preparedStatementstatement = util.getConnection().prepareStatement(sql) ;
            preparedStatementstatement.setString(1,name);
            preparedStatementstatement.setString(2,lastName);
            preparedStatementstatement.setByte(3,age);
            preparedStatementstatement.executeUpdate();

            System.out.println("User успешно создан");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE `id` = "+id;

        try (PreparedStatement pstatement = util.getConnection().prepareStatement(sql)){
            pstatement.executeUpdate();
            System.out.println("User c id"+id+" успешно удален" );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM user";

        try (Statement statement = util.getConnection().createStatement()){
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge((byte) rs.getInt("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    public void cleanUsersTable() {

       String sql = "DROP TABLE IF EXISTS user";

        try (Statement statement = util.getConnection().createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
