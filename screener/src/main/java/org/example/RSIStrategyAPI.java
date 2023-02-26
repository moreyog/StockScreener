package org.example;

import java.util.ArrayList;
import java.util.List;

public class RSIStrategyAPI {

    public static String getRSISignal(List<Double> closes) {

        // Calculate RSI
        double[] gains = new double[closes.size()];
        double[] losses = new double[closes.size()];

        for (int i = 1; i < closes.size(); i++) {
            double diff = closes.get(i) - closes.get(i - 1);
            if (diff >= 0) {
                gains[i] = diff;
                losses[i] = 0;
            } else {
                gains[i] = 0;
                losses[i] = -diff;
            }
        }

        double avgGain = 0;
        double avgLoss = 0;
        for (int i = 1; i <= 14; i++) {
            avgGain += gains[i];
            avgLoss += losses[i];
        }
        avgGain /= 14;
        avgLoss /= 14;

        double rs = avgGain / avgLoss;
        double rsi = 100 - (100 / (1 + rs));

        System.out.println("RSI " + rsi);
        // Determine trading signal based on RSI
        if (rsi >= 70) {
            return "Sell signal";
        } else if (rsi <= 30) {
            return "Buy signal";
        } else {
            return "No signal";
        }
    }

    public static void main(String[] args) {
        // Example data
        List<Double> closes = new ArrayList<>();
        closes.add(585.5);
        closes.add(588.25);
        closes.add(586.2);
        closes.add(598.15);
        closes.add(587.7);
        closes.add(598.25);
        closes.add(601.2);
        closes.add(600.0);
        closes.add(610.05);
        closes.add(611.2);
        closes.add(610.55);
        closes.add(603.35);
        closes.add(600.2);
        closes.add(602.5);
        closes.add(600.8);

        // Get trading signal based on RSI
        String signal = RSIStrategyAPI.getRSISignal(closes);
        System.out.println("Trading signal: " + signal);

    }
}
