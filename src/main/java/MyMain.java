
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import dao.CSVFileDAO;
import dto.CSVFile;
import myexception.MyException;
import paging.Paging;
import progress.Progress;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyMain{

   public static void main(String[] args) throws IOException, MyException {

       Progress frame = new Progress();
       frame.getProgressBar("Это тестовый прогресс", 700);
//        frame.pack();
//        frame.setVisible(true);
//        frame.setLocation(600, 300);
//        frame.setSize(500, 70);
//        frame.iterate(500);
//        frame.dispose();

   }
}
