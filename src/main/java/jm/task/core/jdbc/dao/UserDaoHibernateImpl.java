package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Session session = Util.getSessionFactory().openSession();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("new user " + user.getName());
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            String sql = "From " + User.class.getSimpleName();
            List<User> list = session.createQuery(sql).list();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM " + User.class.getSimpleName()).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }
}
