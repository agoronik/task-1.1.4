package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String query_string = "CREATE TABLE IF NOT EXISTS Users1 (" +
                "id int auto_increment primary key," +
                "name varchar(255) not null," +
                "lastName varchar(255) not null," +
                "age int(11) not null)";
        Session session = Util.buildSessionFactory().getSession();
        session.beginTransaction();
        session.createNativeQuery(query_string);
        session.getTransaction().commit();
        System.out.println("Таблица создана!");
        session.close();

    }

    @Override
    public void dropUsersTable() {
        String query_string = "DROP TABLE IF EXISTS Users1";
        Session session = Util.buildSessionFactory().getSession();
        session.beginTransaction();
        session.createNativeQuery(query_string);
        session.getTransaction().commit();
        System.out.println("Таблица удалена!");
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = Util.buildSessionFactory().getSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        session.close();
        System.out.println("User с именем – " + name + " добавлен в базу данных");

    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.buildSessionFactory().getSession();
        session.beginTransaction();
        User user = new User();
        user.setId(id);
        session.delete(user);
        session.getTransaction().commit();
        System.out.println("User с Id – " + id + " удалён и базы данных");
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        String query_string = "SELECT * FROM Users1";

        Session session = Util.buildSessionFactory().getSession();
        session.beginTransaction();

        List<User> users = session.createNativeQuery(query_string, User.class).list();
        Arrays.stream(users.toArray()).forEach(System.out::println);
        //users.forEach(System.out::println);

        session.getTransaction().commit();
        session.close();

        return users;
    }

    @Override
    public void cleanUsersTable() throws JDBCException {
        Session session = Util.buildSessionFactory().getSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE Users1").executeUpdate();
        System.out.println("Таблица очищена!");
        transaction.commit();
    }
}
