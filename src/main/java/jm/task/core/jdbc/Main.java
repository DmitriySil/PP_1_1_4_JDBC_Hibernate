package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        //UserDao userDao = new UserDaoJDBCImpl();
        UserDao hibernate = new UserDaoHibernateImpl();
        //Session session = Util.getSessionFactory().openSession();

        hibernate.dropUsersTable();
        hibernate.createUsersTable();
        hibernate.saveUser("Ivan", "Petrov", (byte) 34);
        hibernate.saveUser("Oleg", "Ivanov", (byte) 45);
        hibernate.saveUser("Ben", "Ivanov", (byte) 23);
        hibernate.saveUser("Boris", "Ivanov", (byte) 64);
        hibernate.getAllUsers().forEach(System.out::println);
        hibernate.cleanUsersTable();
        try {
            Util.getConnection().commit();//про коммиты не совсем понял я
            Util.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
