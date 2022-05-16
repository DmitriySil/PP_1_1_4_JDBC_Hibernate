package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        //UserDao userDao = new UserDaoJDBCImpl();
        //Util.getSessionFactory().openSession();
        UserDao hibernate = new UserDaoHibernateImpl();

        hibernate.dropUsersTable();
        hibernate.createUsersTable();
        hibernate.saveUser("Ivan", "Petrov", (byte) 34);
        hibernate.saveUser("Oleg", "Ivanov", (byte) 45);
        hibernate.saveUser("Ben", "Ivanov", (byte) 23);
        hibernate.saveUser("Boris", "Ivanov", (byte) 64);
        hibernate.removeUserById(2);
        hibernate.getAllUsers().forEach(System.out::println);
        hibernate.cleanUsersTable();
        try {
            Util.getSessionFactory().close();
            Util.getConnection().commit();
            Util.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
