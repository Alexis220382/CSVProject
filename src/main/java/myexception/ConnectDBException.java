package myexception;

import java.sql.SQLException;

public class ConnectDBException extends SQLException {

    public ConnectDBException(String s){
        super(s);
    }
}
