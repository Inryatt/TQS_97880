package tqs.hw.covidtracker.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CountryDTOTest {

    CountryDTO cd;
    String cdName = "Iceland";
    Integer cdCases = 1234;
    Integer ph =0;
    Double phf=0.0;
    String phs ="Placeholder";

    @BeforeEach
    void init(){
        //Why did i make this so long
        cd = new CountryDTO((long) System.currentTimeMillis(),cdName,cdCases,ph,ph,ph,ph,ph,ph,ph,ph,ph,ph,ph,phf,phf,phf,ph,phs,ph,ph);
    }

    @AfterEach
    void tearDown(){
        cd =null;
    }

    @Test
    void testGetName(){
        assertEquals(cd.getName(), cdName);
    }

    @Test
    void testGetCases(){
        assertEquals(cd.getCases(), cdCases);
    }

    @Test
    void testSetName(){
        String newCountry = "Finland";
        cd.setName(newCountry);
        assertEquals(cd.getName(), newCountry);
    }

    @Test
    void testSetCases(){
        Integer newCases = 12345;
        cd.setCases(newCases);
        assertEquals(cd.getCases(), newCases);
    }
}
