package dao;

import myexception.ConnectDBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class UtilJDBC {

    private Properties prop = new Properties();

    public Connection getConnection() throws ConnectDBException {
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            Class.forName(prop.getProperty("Driver")).newInstance();
            return DriverManager.getConnection(prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ConnectDBException("Ошибка!\nОтсутствует соединение с базой данных");
        }
    }


}
