package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {

        System.out.println("База данных подключена");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void createUsersTable() {

        String request = ("CREATE TABLE sys.user (" +
                "id bigint AUTO_INCREMENT primary key," +
                "name VARCHAR(45) NOT NULL," +
                "lastName varchar(45) NOT NULL, " +
                "age int(3))");

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {

            statement.executeUpdate(request);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
        }
    }

    public void dropUsersTable() {
        String request = ("DROP TABLE sys.user");

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {

            statement.executeUpdate(request);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String request = ("INSERT INTO sys.user (name, lastName, age) Values(?, ?, ?)");

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(request);) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User " + name + " добавлен!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String request = ("DELETE FROM sys.user WHERE Id = ?");
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(request);) {

            preparedStatement.setLong(1, id);
            Integer count = preparedStatement.executeUpdate();
            System.out.println("user удалён, " + count + " строк удалено");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String request = ("SELECT * FROM sys.user");
        List list = new ArrayList();

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {

            ResultSet resultSet = statement.executeQuery(request);

            while (resultSet.next()) {
                Long userId = resultSet.getLong("id");
                String userName = resultSet.getString("name");
                String userLastName = resultSet.getString("lastName");
                Byte userAge = resultSet.getByte("age");

                User user = new User(userId, userName, userLastName, userAge);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String request = ("DELETE FROM sys.user");

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {

            statement.executeUpdate(request);
            System.out.println("Данные таблицы удалены");
        } catch (SQLException e) {
        }
    }
}
