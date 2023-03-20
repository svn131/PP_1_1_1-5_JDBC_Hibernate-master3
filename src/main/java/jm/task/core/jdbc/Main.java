package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {


        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Bob", "Marley", (byte) 36);
        userService.saveUser("Tony", "Montana", (byte) 40);
        userService.saveUser("Carl", "Cox", (byte) 60);
        userService.saveUser("Biatrix", "Kiddo", (byte) 30);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();






    }

}