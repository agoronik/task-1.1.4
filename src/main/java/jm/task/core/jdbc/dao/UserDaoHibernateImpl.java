package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
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
        try (Session session = Util.buildSessionFactory()) {
            session.getSession();
            session.beginTransaction();
            session.createNativeQuery(query_string);
            System.out.println("Таблица создана!");
            session.getTransaction().commit();
        } catch (HibernateException he) {
            throw new HibernateException("Ошибка создания таблицы", he);
        }

    }

    @Override
    public void dropUsersTable() {
        String query_string = "DROP TABLE IF EXISTS Users1";
        try (Session session = Util.buildSessionFactory()) {
            session.getSession();
            session.beginTransaction();
            session.createNativeQuery(query_string);
            session.getTransaction().commit();
            System.out.println("Таблица удалена!");
        } catch (HibernateException he) {
            throw new HibernateException("Ошибка удаления таблицы", he);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.buildSessionFactory().getSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException he) {
            throw new HibernateException("Ошибка добавления пользователя", he);
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.buildSessionFactory().getSession()) {
            session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("User с Id – " + id + " удалён и базы данных");
        } catch (HibernateException he) {
            throw new HibernateException("Ошибка удаления пользователя", he);
        }
    }

    @Override
    public List<User> getAllUsers() {
        String query_string = "SELECT * FROM Users1";
        try (Session session = Util.buildSessionFactory().getSession()) {
            session.beginTransaction();
            List<User> users = session.createNativeQuery(query_string, User.class).list();
            Arrays.stream(users.toArray()).forEach(System.out::println);
            session.getTransaction().commit();
            return users;
        } catch (HibernateException he) {
            throw new HibernateException("Ошибка полученя списка пользователей", he);
        }
    }

    @Override
    public void cleanUsersTable() throws JDBCException {
        try (Session session = Util.buildSessionFactory().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE Users1").executeUpdate();
            System.out.println("Таблица очищена!");
            transaction.commit();
        } catch (HibernateException he) {
            throw new HibernateException("Ошибка очистки таблицы Users1", he);
        }
    }
}
