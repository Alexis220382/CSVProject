package dao;

import dto.CSVFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PagingDAO {

    Properties prop = new Properties();
    Connection con;
    PreparedStatement getCountSt;
    PreparedStatement getAllSt;
    ResultSet rs;

    public PagingDAO(){
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            con = new UtilJDBC().getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<CSVFile> getAllSortedPagingCSVFile(String column, int begin_item, int rows_on_page){
        List<CSVFile> csvFiles = new ArrayList<CSVFile>();
        try {
            if (getAllSt == null) {
                getAllSt = con.prepareStatement("SELECT name, surname, login, email, phoneNumber FROM csv ORDER BY "
                        +column+" LIMIT "+begin_item+","+rows_on_page);
            }
            rs = getAllSt.executeQuery();
            CSVFile csvFile = null;
            while (rs.next()){
                csvFile = new CSVFile(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
                csvFiles.add(csvFile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }

    public int getCountRows(){
        int count = 0;
        try {
            if (getCountSt == null) {
                getCountSt = con.prepareStatement(prop.getProperty("getCountSt"));
            }
            rs = getCountSt.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void conClosePaging() {
        try {
            if (getCountSt != null) {
                getCountSt.close();
            }
            if (getAllSt != null) {
                getAllSt.close();
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
