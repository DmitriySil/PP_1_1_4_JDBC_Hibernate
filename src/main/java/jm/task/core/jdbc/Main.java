package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        UserDao userDao = new UserDaoJDBCImpl();

        userDao.dropUsersTable();
        userDao.createUsersTable();
        userDao.saveUser("Ivan", "Petrov", (byte) 34);
        userDao.saveUser("Oleg", "Ivanov", (byte) 45);
        userDao.saveUser("Ben", "Ivanov", (byte) 23);
        userDao.saveUser("Boris", "Ivanov", (byte) 64);
        userDao.getAllUsers().forEach(System.out::println);
        userDao.cleanUsersTable();
        try {
            Util.getConnection().commit();//про коммиты не совсем понял я
            Util.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
