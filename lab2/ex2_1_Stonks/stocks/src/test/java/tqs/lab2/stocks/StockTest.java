package tqs.lab2.stocks;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StockTest {
    

    Stock st;
    String stockName = "Test";
    Integer stockQuantity = 3;

    @BeforeEach
	void init(){
		st = new Stock(stockName,stockQuantity);
	}

    @AfterEach
    void tearDown(){
        st = null;
    }

    @Test
    void testGetLabel(){
        assertTrue(st.getLabel()==stockName);
    }

    @Test
    void testGeQuantity(){
        assertTrue(st.getQuantity()==stockQuantity);
    }

    @Test
    void testSetLabel(){
        String newLabel = "other";
        st.setLabel(newLabel);
        assertTrue(st.getLabel()==newLabel);
    }

    @Test
    void testSetQuantity(){
        Integer newQuan = 13;
        st.setQuantity(newQuan);
        assertTrue(st.getQuantity()==newQuan);
    }

}
