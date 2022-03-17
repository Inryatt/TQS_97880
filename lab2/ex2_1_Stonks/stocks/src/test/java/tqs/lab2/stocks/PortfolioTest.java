package tqs.lab2.stocks;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class PortfolioTest {

    Stock stock,stockE;

    @Mock
    IStockmarketService stockServiceMock = Mockito.mock(IStockmarketService.class);

    @InjectMocks
    StocksPortfolio stockP;

    @BeforeEach
    void init() {
        stock = new Stock("GME", 3);
        stockE = new Stock("ECORP", 2);

        stockP = new StocksPortfolio(stockServiceMock);
    }

    @AfterEach
    void tearDown() {
        stockP = null;
        stock = null;
    }

    @Test
    void portfolioExistsTest() {
        assertTrue(stockP.stocks!=null);
    }

    @Test
    void portfolioEmptyOnCreationTest() {
        assertTrue(stockP.stocks.isEmpty());
    }

    @Test
    void addStockTest() {
        stockP.addStock(stock);
        assertTrue(stockP.stocks.contains(stock));
    }

    @Test
    void getTotalValueTest() {
        when(stockServiceMock.lookUpPrice("GME")).thenReturn(10.0);
        when(stockServiceMock.lookUpPrice("ECORP")).thenReturn(200.0);

        stockP.addStock(stock);
        assertTrue(stockP.getTotalValue() == 30.0);
        verify(stockServiceMock,times(1)).lookUpPrice("GME");
        
        stockP.addStock(stockE);
        assertTrue(stockP.getTotalValue() == 430.0);
        verify(stockServiceMock,times(2)).lookUpPrice("GME");
        verify(stockServiceMock,times(1)).lookUpPrice("ECORP");


    }
}
