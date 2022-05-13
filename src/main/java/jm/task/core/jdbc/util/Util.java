package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    static String url;
    static  String login;
    static String pwd;
    static Connection connection = null;

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    // реализуйте настройку соеденения с БД
}
