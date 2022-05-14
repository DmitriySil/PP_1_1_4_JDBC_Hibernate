package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    static String url;
    static String login;
    static String pwd;
    static Connection connection = null;

    private static final SessionFactory session = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure(new File("src\\main\\resources\\hibernate.cfg.xml")).buildSessionFactory();
        } catch (ExceptionInInitializerError e) {

            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory getSessionFactory() {
        return session;
    }

    public static void close() {
        session.close();
    }

    public static Connection getConnection() {
        if (connection == null) {
            try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
                Properties properties = new Properties();
                properties.load(fis);
                url = properties.getProperty("db.url");
                login = properties.getProperty("db.name");
                pwd = properties.getProperty("db.password");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                connection = DriverManager.getConnection(url, login, pwd);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    // реализуйте настройку соеденения с БД
}
