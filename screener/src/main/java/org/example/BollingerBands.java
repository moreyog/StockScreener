package org.example;

import java.util.List;

public class BollingerBands {
    private int period;
    private double multiplier;

    public BollingerBands(int period, double multiplier) {
        this.period = period;
        this.multiplier = multiplier;
    }

    public double calculateSMA(List<Double> prices) {
        double sum = 0.0;
        int n = prices.size();

        for (double price : prices) {
            sum += price;
        }

        return sum / n;
    }

    public double calculateStandardDeviation(List<Double> prices, double sma) {
        double sum = 0.0;
        int n = prices.size();

        for (double price : prices) {
            sum += Math.pow(price - sma, 2);
        }

        double variance = sum / n;

        return Math.sqrt(variance);
    }

    public double calculateUpperBand(double sma, double standardDeviation) {
        return sma + (multiplier * standardDeviation);
    }

    public double calculateLowerBand(double sma, double standardDeviation) {
        return sma - (multiplier * standardDeviation);
    }

    public void generateSignals(List<Double> prices, Stock stock) {
        int n = prices.size();

        if (n < period) {
            System.out.println("Insufficient data");
            return;
        }

        // Calculate SMA and standard deviation for first 'period' days
        List<Double> firstPrices = prices.subList(0, period);
        double sma = calculateSMA(firstPrices);
        double standardDeviation = calculateStandardDeviation(firstPrices, sma);

        // Calculate upper and lower bands for first 'period' days
        double upperBand = calculateUpperBand(sma, standardDeviation);
        double lowerBand = calculateLowerBand(sma, standardDeviation);

        // Print initial bands
       // System.out.printf("Upper band: %.2f, Lower band: %.2f\n", upperBand, lowerBand);

        // Loop through remaining days and generate trading signals
        for (int i = period; i < n; i++) {
            double price = prices.get(i);

            // Add current price to SMA calculation
            sma = ((sma * period) + price) / (period + 1);

            // Calculate standard deviation using updated SMA and prices for past 'period' days
            List<Double> pricesForStdDev = prices.subList(i - period + 1, i + 1);
            standardDeviation = calculateStandardDeviation(pricesForStdDev, sma);

            // Calculate upper and lower bands using updated SMA and standard deviation
            upperBand = calculateUpperBand(sma, standardDeviation);
            lowerBand = calculateLowerBand(sma, standardDeviation);

            // Print current bands
            //System.out.printf("Upper band: %.2f, Lower band: %.2f\n", upperBand, lowerBand);

            // Generate trading signals
            if (price > upperBand) {
                System.out.println("Sell signal generated : " + stock);
            } else if (price < lowerBand) {
                System.out.println("Buy signal generated : " + stock);
            }
        }
    }

    public void trade(List<Stock> stocks) {
        for (int i = 20; i < stocks.size(); i++) {
            List<Stock> subList = stocks.subList(i-20,i+1);
            List<Double> prices = subList.stream().map(Stock::getClose).toList();
            //System.out.println(subList.get(subList.size()-1));
            generateSignals(prices,subList.get(subList.size()-1));
        }
    }
}