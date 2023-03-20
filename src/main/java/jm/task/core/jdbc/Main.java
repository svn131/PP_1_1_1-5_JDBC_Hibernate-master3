package jm.task.core.jdbc;

import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
//String sql = "INSERT INTO `mydbtest`.`user` (`name`, `lastName`, `age`) VALUES ('"+1+"', '"+2+"', "+3+")";
//        System.out.println(sql);
//        String stringa ="CREATE TABLE IF NOT EXISTS `mydbtest`.`user` (" +
//                "`id` bigint(19) NOT NULL AUTO_INCREMENT," +
//                "`name` varchar(45) NOT NULL," +
//                "`lastName` varchar(45) NOT NULL," +
//                "`age` tinyint(3) NOT NULL," +
//                "PRIMARY KEY (`id`)" +
//                ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
//        System.out.println(stringa);
//  }


        Main main = new Main();
        main.saverrrrrrr();
    }
        private final Util util;

        {util = new Util();}

        public void saverrrrrrr() {
            String sql = "INSERT INTO `mydbtest`.`user` (`name`, `lastName`, `age`) VALUES ('1', '1', 1)";

            try {
                Statement statment = util.getConnection().createStatement();
                statment.executeUpdate("INSERT INTO `mydbtest`.`user` (`name`, `lastName`, `age`) VALUES ('Ivan', 'Ivanov', 11 )");
                System.out.println("USer успешно создан");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



}