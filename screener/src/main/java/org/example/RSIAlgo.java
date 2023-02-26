package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RSIAlgo {

    public static <T> Stream<T> getSliceOfStream(Stream<T> stream, int startIndex,
                                                 int endIndex)
    {
        return stream .collect(Collectors.toList())

                // Fetch the subList between the specified index
                .subList(startIndex, endIndex + 1)

                // Convert the subList to stream
                .stream();
    }
    public void trade(List<Stock> stocks) {
        List<Double> closingPrices = stocks.stream().map(Stock::getClose).toList();
        //for (int i = 15; i < stocks.size(); i++) {

           // Stream<Stock> slicedStocks = getSliceOfStream(stocks.stream(),0,15);
           // Stream<Stock> slicedStocks = getSliceOfStream(stocks.stream(),stocks.size()-14,stocks.size()-1);
           // List<Double> closingPrices = slicedStocks.map(Stock::getClose).toList();


            double rsi = calculateRSI(closingPrices);

            if (rsi >= 70) {
               //System.out.println("Stock is overbought : " + rsi + " : " + stocks.get(i) );
                System.out.println("Stock is overbought : " + rsi);
            } else if (rsi <= 30) {
                //System.out.println("Stock is oversold : " + rsi + " : " + stocks.get(i) );
                System.out.println("Stock is oversold : " + rsi);
            } else {
                System.out.println("Within range and Current RSI : "  + rsi );
            }
       // }


    }

    public Double calculateRSI( List<Double> closes) {

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

        return rsi;
    }
}
