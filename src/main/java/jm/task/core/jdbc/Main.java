package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;


public class Main {
    public static void main(String[] args) {

        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser("Иван", "Иванов", (byte) 30);
        userDaoHibernate.saveUser("Сергей", "Сергеевич", (byte) 32);
        userDaoHibernate.saveUser("Александр", "Александров", (byte) 22);
        userDaoHibernate.saveUser("Алексей", "Навальный", (byte) 45);

        System.out.println((userDaoHibernate.getAllUsers()));
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();
    }
}
