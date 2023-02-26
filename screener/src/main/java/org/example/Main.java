package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String csvFile = "D:\\Data\\test\\csv data\\titan.csv";

        System.out.println("Scanning ....." + csvFile);
        //Input

        ReadCSVFile readCSVFile = new ReadCSVFile();
        MACDAlgo macdAlgo = new MACDAlgo();
        RSIAlgo rsiAlgo = new RSIAlgo();
        SuperTrendStrategy superTrendStrategy = new SuperTrendStrategy();
        BollingerBands bollingerBands = new BollingerBands(20,2);

        //Data read
        List<Stock> stocks = readCSVFile.readStockData(csvFile);

        //MCAD Alogo call
        //macdAlgo.trade(stocks);
        //macdAlgo.tradeAndRSIAndSuperTrend(stocks);
       //superTrendStrategy.tradeOverall(stocks);
        //rsiAlgo.trade(stocks);
        bollingerBands.trade(stocks);

        System.out.println("Scanning .....End");
    }
}