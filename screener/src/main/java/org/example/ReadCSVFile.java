package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;

public class ReadCSVFile {

    public  List<Stock> readStockData(String csvFile) {
        List<Stock> stockDataList = new ArrayList<Stock>();
        CSVReader reader = null;
        String[] line;

        try {
            reader = new CSVReader(new FileReader(csvFile));

            String[] header = reader.readNext();
            while ((line = reader.readNext()) != null) {
                Stock stockData = new Stock();
                stockData.setStockName(line[0]);
                stockData.setDate(line[1]);
                stockData.setOpen(Double.parseDouble(line[2]));
                stockData.setHigh(Double.parseDouble(line[3]));
                stockData.setLow(Double.parseDouble(line[4]));
                stockData.setClose(Double.parseDouble(line[5]));
                stockData.setVolume(Integer.parseInt(line[6]));
                stockDataList.add(stockData);
            }

            // Process data from CSV file
//            for (Stock stockData : stockDataList) {
//                System.out.println(stockData);
//            }

        } catch (IOException e) {
            e.printStackTrace();
//        } catch (CsvValidationException e) {
//            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stockDataList;
    }


}
