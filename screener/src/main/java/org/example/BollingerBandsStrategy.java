package org.example;

import java.util.Arrays;

public class BollingerBandsStrategy {
    private double[] data;
    private int period;
    private double multiplier;

    public BollingerBandsStrategy(double[] data, int period, double multiplier) {
        this.data = data;
        this.period = period;
        this.multiplier = multiplier;
    }

    public double[] getUpperBand() {
        double[] sma = getSMA();
        double[] std = getStd();

        double[] upperBand = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            upperBand[i] = sma[i] + (std[i] * multiplier);
        }

        return upperBand;
    }

    public double[] getLowerBand() {
        double[] sma = getSMA();
        double[] std = getStd();

        double[] lowerBand = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            lowerBand[i] = sma[i] - (std[i] * multiplier);
        }

        return lowerBand;
    }

    private double[] getSMA() {
        double[] sma = new double[data.length];

        for (int i = 0; i < data.length; i++) {
            int start = Math.max(0, i - period + 1);
            int end = i + 1;

            double sum = 0;
            for (int j = start; j < end; j++) {
                sum += data[j];
            }

            sma[i] = sum / (end - start);
        }

        return sma;
    }

    private double[] getStd() {
        double[] sma = getSMA();
        double[] std = new double[data.length];
        double[] squaredDiff = new double[data.length];

        for (int i = 0; i < data.length; i++) {
            int start = Math.max(0, i - period + 1);
            int end = i + 1;

            double sum = 0;
            for (int j = start; j < end; j++) {
                double diff = data[j] - sma[i];
                squaredDiff[j] = diff * diff;
                sum += squaredDiff[j];
            }

            double variance = sum / (end - start - 1);
            std[i] = Math.sqrt(variance);
        }

        return std;
    }
}
