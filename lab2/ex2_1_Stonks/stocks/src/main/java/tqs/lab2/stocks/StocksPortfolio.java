package tqs.lab2.stocks;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {

    List<Stock> stocks;
    IStockmarketService stockmarket;

    StocksPortfolio(IStockmarketService sm) {
        stockmarket = sm;
        stocks = new ArrayList<Stock>();

    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double getTotalValue(){
        double value=0;
        for (Stock s : stocks){
            value = value+(stockmarket.lookUpPrice(s.getLabel())*s.getQuantity());
        }
        return value;
    }
}