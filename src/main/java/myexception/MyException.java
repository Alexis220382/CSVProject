package myexception;

import java.sql.SQLException;

public class MyException extends SQLException {

    public MyException(String s){
        super(s);
    }
}
