package tqs.lab2.stocks;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.NotNull;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.*;

@ExtendWith(MockitoExtension.class)
public class PortfolioHamcrestTest {

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
        assertThat(stockP, isNotNull()) ; // Deprecated but the internet won't tell me what's the non deprecated way >:(
    }

    @Test
    void portfolioEmptyOnCreationTest() {
        assertThat(stockP.stocks,is(empty())); // :/
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
