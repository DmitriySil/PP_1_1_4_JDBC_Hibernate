package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    List<User> list = new LinkedList<>();

    {
        try {
            statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS users "
                    + "(id INT(5) NOT NULL AUTO_INCREMENT,"
                    + " username VARCHAR(50), "
                    + " lastname VARCHAR(50), "
                    + " age INT(5), "
                    + "PRIMARY KEY(id));";
    private static final String DROP = "DROP TABLE IF EXISTS users";
    private static final String SELECT_QUERY = "SELECT * FROM users";
    private static final String INSERT = "INSERT INTO users(username, lastname, age) values(?, ?, ?)";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String CLEAN_TABLE = "TRUNCATE users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            statement.executeUpdate(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
            try {
                statement.executeUpdate(DROP);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            preparedStatement = Util.getConnection().prepareStatement(INSERT);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            preparedStatement = Util.getConnection().prepareStatement(DELETE_USER_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try {
            resultSet = statement.executeQuery(SELECT_QUERY);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("username");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            statement.executeUpdate(CLEAN_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
