package org.example;

import java.util.*;

public class MACDAlgo {

    private double fastEMA = 12;
    private double slowEMA = 26;
    private double signalEMA = 9;
    private double MACDLine = 0;
    private double signalLine = 0;
    private double prevMACDLine = 0;

    public void trade(List<Stock> stocks) {

        RSIAlgo rsiAlgo = new RSIAlgo();

        List<Double> prices = stocks.stream().map(Stock::getClose).toList();

        List<Double> macdLine = calculateMACDLine(prices);
        List<Double> signalLineList = calculateSignalLine(macdLine);

        for (int i = 0; i < stocks.size(); i++) {
            MACDLine = macdLine.get(i);
            signalLine = signalLineList.get(i);
            if (MACDLine > signalLine && prevMACDLine <= signalLine) {
                // Buy signal
                System.out.println("Buy at price: " + stocks.get(i));
                //rsiAlgo.calculateRSI()
            } else if (MACDLine < signalLine && prevMACDLine >= signalLine) {
                // Sell signal
                System.out.println("Sell at price: " + stocks.get(i));
            }
            prevMACDLine = MACDLine;
        }
    }

    public void tradeAndRSIAndSuperTrend(List<Stock> stocks) {


        RSIAlgo rsiAlgo = new RSIAlgo();
        SuperTrendStrategy superTrendStrategy = new SuperTrendStrategy();

        List<Double> prices = stocks.stream().map(Stock::getClose).toList();

        List<Double> macdLine = calculateMACDLine(prices);
        List<Double> signalLineList = calculateSignalLine(macdLine);

        for (int i = 0; i < stocks.size(); i++) {
            System.out.println("==============================================");
            MACDLine = macdLine.get(i);
            signalLine = signalLineList.get(i);
            if (MACDLine > signalLine && prevMACDLine <= signalLine) {
                // Buy signal
                System.out.println("Buy at price: " + stocks.get(i));

                if(i > 15) {
                    rsiAlgo.trade(stocks.subList(i-15,i));
                    superTrendStrategy.trade(stocks.subList(i-10,i+1));
                }
            } else if (MACDLine < signalLine && prevMACDLine >= signalLine) {
                // Sell signal
                System.out.println("Sell at price: " + stocks.get(i));

                if(i > 15) {
                    rsiAlgo.trade(stocks.subList(i-14,i+1));
                    superTrendStrategy.trade(stocks.subList(i-10,i+1));
                }
            }
            prevMACDLine = MACDLine;
            System.out.println("==============================================");
        }

    }
    private List<Double> calculateMACDLine(List<Double> prices) {
        List<Double> macdLine = new ArrayList<>();
        double fastEMAValue = 0;
        double slowEMAValue = 0;
        for (int i = 0; i < prices.size(); i++) {
            if (i == 0) {
                fastEMAValue = prices.get(i);
                slowEMAValue = prices.get(i);
            } else {
                fastEMAValue = (prices.get(i) - fastEMAValue) * (2 / (fastEMA + 1)) + fastEMAValue;
                slowEMAValue = (prices.get(i) - slowEMAValue) * (2 / (slowEMA + 1)) + slowEMAValue;
            }
            macdLine.add(fastEMAValue - slowEMAValue);
        }
        return macdLine;
    }

    private List<Double> calculateSignalLine(List<Double> macdLine) {
        List<Double> signalLine = new ArrayList<>();
        double signalEMAValue = 0;
        for (int i = 0; i < macdLine.size(); i++) {
            if (i == 0) {
                signalEMAValue = macdLine.get(i);
            } else {
                signalEMAValue = (macdLine.get(i) - signalEMAValue) * (2 / (signalEMA + 1)) + signalEMAValue;
            }
            signalLine.add(signalEMAValue);
        }
        return signalLine;
    }
}
