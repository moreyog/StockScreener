package org.example;

import java.util.List;

public class SuperTrendStrategy {

    public enum Trend {
        UP,
        DOWN
    }

    public void tradeOverall(List<Stock> stocks) {

        for (int i = 10; i < stocks.size(); i++) {

            List<Stock>  subList = stocks.subList(i-10,i+1);
            List<Double> closes = subList.stream().map(Stock::getClose).toList();
            List<Double> highs = subList.stream().map(Stock::getHigh).toList();
            List<Double> lows = subList.stream().map(Stock::getLow).toList();

            SuperTrendStrategy.Trend trend = SuperTrendStrategy.getTrend(closes, highs, lows, 10, 1.0);

            // Print result
            if (trend == SuperTrendStrategy.Trend.UP) {
                System.out.println("Buy signal!"+ stocks.get(i));
            } else if (trend == SuperTrendStrategy.Trend.DOWN) {
                System.out.println("Sell signal!" + stocks.get(i));
            } else {
               // System.out.println("No signal.");
            }
        }
    }

    public void trade(List<Stock> stocks) {

       // for (int i = 10; i < stocks.size(); i++) {

           // List<Stock>  subList = stocks.subList(i-10,i);
            List<Double> closes = stocks.stream().map(Stock::getClose).toList();
            List<Double> highs = stocks.stream().map(Stock::getHigh).toList();
            List<Double> lows = stocks.stream().map(Stock::getLow).toList();

            SuperTrendStrategy.Trend trend = SuperTrendStrategy.getTrend(closes, highs, lows, 10, 1.0);

            // Print result
            if (trend == SuperTrendStrategy.Trend.UP) {
                System.out.println(">>>>> SuperTrend Buy signal!"+ stocks.get(stocks.size() - 1));
            } else if (trend == SuperTrendStrategy.Trend.DOWN) {
                System.out.println("<<<<< SuperTrend  Sell signal!" + stocks.get(stocks.size() - 1));
            } else {
                // System.out.println("No super trend signal.");
            }
       // }
    }

    public static Trend getTrend(List<Double> closes, List<Double> highs, List<Double> lows, int period, double multiplier) {

        double[] atr = new double[closes.size()];
        double[] upperBand = new double[closes.size()];
        double[] lowerBand = new double[closes.size()];

        // Calculate ATR
        double prevClose = closes.get(0);
        double prevHigh = highs.get(0);
        double prevLow = lows.get(0);

        for (int i = 1; i < closes.size(); i++) {
            double tr = Math.max(
                    Math.max(highs.get(i) - lows.get(i), Math.abs(highs.get(i) - prevClose)),
                    Math.abs(lows.get(i) - prevClose));
            atr[i] = ((period - 1) * atr[i - 1] + tr) / period;

            // Calculate upper and lower bands
            double ma = (closes.get(i) + closes.get(i - 1)) / 2;
            upperBand[i] = ma + multiplier * atr[i];
            lowerBand[i] = ma - multiplier * atr[i];

            prevClose = closes.get(i);
            prevHigh = highs.get(i);
            prevLow = lows.get(i);
        }

        // Determine trend
        if (closes.get(closes.size() - 1) > upperBand[closes.size() - 1]) {
            return Trend.UP;
        } else if (closes.get(closes.size() - 1) < lowerBand[closes.size() - 1]) {
            return Trend.DOWN;
        } else {
            return null;
        }
    }
}
