package dao;

import dto.CSVFile;
import myexception.MyException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CSVFileDAO {

    private Properties prop = new Properties();
    private CSVFile csvFile;
    private Connection con;
    private PreparedStatement getCSVFileSt;
    private PreparedStatement addDataFromCSVFileSt;
    private PreparedStatement setDataFromCSVFileSt;
    private ResultSet rs;

    public CSVFileDAO() throws MyException {
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            con = new UtilJDBC().getConnection();
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException("\"Ошибка!\\nОтсутствует соединение с базой данных\"");
        }
    }

    public List<CSVFile> getCSVFile() {
        List<CSVFile> products = new ArrayList<CSVFile>();
        try {
            if (getCSVFileSt == null) {
                getCSVFileSt = con.prepareStatement(prop.getProperty("getCSVFileSt"));
            }
            rs = getCSVFileSt.executeQuery();
            csvFile = null;
            while (rs.next()) {
                csvFile = new CSVFile(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
                products.add(csvFile);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    public CSVFile addDataFromCSVFile(CSVFile csvFile) {
        try {
            if (addDataFromCSVFileSt == null) {
                addDataFromCSVFileSt = con.prepareStatement(prop.getProperty("addDataFromCSVFileSt"));
            }
            addDataFromCSVFileSt.setString(1, csvFile.getName());
            addDataFromCSVFileSt.setString(2, csvFile.getSurname());
            addDataFromCSVFileSt.setString(3, csvFile.getLogin());
            addDataFromCSVFileSt.setString(4, csvFile.getEmail());
            addDataFromCSVFileSt.setString(5, csvFile.getPhoneNumber());

            addDataFromCSVFileSt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return csvFile;
    }

    public CSVFile setCSVFile(CSVFile csvFile) {
        try {
            if (setDataFromCSVFileSt == null) {
                setDataFromCSVFileSt = con.prepareStatement(prop.getProperty("setDataFromCSVFileSt"));
            }
            setDataFromCSVFileSt.setString(1, csvFile.getName());
            setDataFromCSVFileSt.setString(2, csvFile.getLogin());
            setDataFromCSVFileSt.setString(3, csvFile.getEmail());
            setDataFromCSVFileSt.setString(4, csvFile.getPhoneNumber());
            setDataFromCSVFileSt.setString(5, csvFile.getSurname());

            setDataFromCSVFileSt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return csvFile;
    }

    public void conCloseCSVFile() {
        try {
            if (getCSVFileSt != null) {
                getCSVFileSt.close();
            }
            if (addDataFromCSVFileSt != null) {
                addDataFromCSVFileSt.close();
            }
            if (setDataFromCSVFileSt != null) {
                setDataFromCSVFileSt.close();
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
