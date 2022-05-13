package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.dropUsersTable();
        userDao.createUsersTable();
        userDao.saveUser("Ivan", "Petrov", (byte) 34);
        userDao.saveUser("Oleg", "Ivanov", (byte) 45);
        userDao.getAllUsers().forEach(System.out::println);
        userDao.cleanUsersTable();

    }
}
