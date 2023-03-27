package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import javax.persistence.Query;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util;
    private Transaction transaction = null;

    {
        util = new Util();
    }

    public UserDaoHibernateImpl() {

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
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSesionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try (Session session = Util.getSesionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }

            session.save(user);

            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSesionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> usersLists = new ArrayList<>();

        try (Session session = Util.getSesionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            usersLists = session.createQuery("from User", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return usersLists;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSesionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();


            Query query = session.createQuery("delete from User");
            query.executeUpdate();


            session.getTransaction().commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

