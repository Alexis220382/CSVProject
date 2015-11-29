package utils;

import com.opencsv.CSVReader;
import dto.CSVFile;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilReadCSVFile {

    public List<CSVFile> readCSVFile(String PATH) throws IOException {

        CSVReader csvReader = new CSVReader(new FileReader(PATH), ',', '\'', 1);
        List<CSVFile> list = new ArrayList<CSVFile>();
        CSVFile csvFile = null;
        String[] row = null;
        while ((row = csvReader.readNext()) != null) {
            csvFile = new CSVFile(
                            row[0],
                            row[1],
                            row[2],
                            row[3],
                            row[4]);
            list.add(csvFile);
        }
        csvReader.close();
//
//
//
//
//
//        CsvReader reader = new CsvReader(PATH);
//        List<CSVFile> list = new ArrayList<CSVFile>();
//        CSVFile csvFile = null;
//        reader.readHeaders();
//        while (reader.readRecord()) {
//            csvFile = new CSVFile(
//                    Integer.parseInt(reader.get("id")),
//                    reader.get("name"),
//                    reader.get("surname"),
//                    reader.get("login"),
//                    reader.get("email"),
//                    reader.get("phone_number"));
//            list.add(csvFile);
//
//        }
//        reader.close();
        return list;
    }
}
