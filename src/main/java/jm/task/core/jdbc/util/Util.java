package jm.task.core.jdbc.util;

import java.sql.*;

import jm.task.core.jdbc.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;

public class Util {

    public static final String URL = "jdbc:mysql://localhost:3306/task113";
    public static final String DB_USER = "admin";
    public static final String DB_PASSW = "******";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    public static final String SHOW_SQL = "true";
    public static final String COTEXT_CLASS = "thread";

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private static Session session = buildSessionFactory();

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, DB_USER, DB_PASSW);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return connection;
    }

    public static Session buildSessionFactory() {
        try {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, DB_USER);
                settings.put(Environment.PASS, DB_PASSW);
                settings.put(Environment.DIALECT, DIALECT);
                settings.put(Environment.SHOW_SQL, SHOW_SQL);
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, COTEXT_CLASS);
                //settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory();
            } catch (Throwable e) {
                System.err.println("Ошибка создания сессии " + e);
                throw new ExceptionInInitializerError(e);
            }
            session = sessionFactory.openSession();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return session;
    }

}
