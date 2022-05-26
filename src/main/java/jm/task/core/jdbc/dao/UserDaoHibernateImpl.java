package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS User (" +
                    "id bigint PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastName VARCHAR(45) NOT NULL, " +
                    "age tinyint NOT NULL)";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Таблица User создана");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    @Override
    public void dropUsersTable() {
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS sys.user");
            query.executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("user " + name + " добавлен!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user == null) {
                System.out.println("user id " + id + " не найден!");
            } else {
                session.delete(user);
                System.out.println("user id " + id + " удален!");
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        List<User> users = null;
        try {
            users = session.createQuery("FROM User").list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE User");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        session.close();
    }
}