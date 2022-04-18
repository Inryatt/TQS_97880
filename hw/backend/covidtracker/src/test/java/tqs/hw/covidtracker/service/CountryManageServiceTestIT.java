package tqs.hw.covidtracker.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.hw.covidtracker.connection.BasicHttpClient;
import tqs.hw.covidtracker.data.CountryDTO;

@ExtendWith(MockitoExtension.class)
public class CountryManageServiceTestIT {

    private CountryManagerService countryManagerService;
    private BasicHttpClient basicHttpClient;

    @BeforeAll
    void setUp() throws URISyntaxException {
        basicHttpClient = new BasicHttpClient();
        countryManagerService = new CountryManagerService(basicHttpClient);
    }


    @Test
    void whenGetAllCountries_ReturnAll() throws IOException, URISyntaxException, ParseException {

        String data = countryManagerService.getAllCountryData();

        Type listType = new TypeToken<ArrayList<CountryDTO>>(){}.getType();
        ArrayList<CountryDTO> countries = new Gson().fromJson(data, listType);
        assertEquals(228, countries.size());

    }

    @Test
    void whenGetOneCountry_ReturnIt() throws IOException, URISyntaxException, ParseException {
        CountryDTO country = new Gson().fromJson(countryManagerService.getCountryData("finland"), CountryDTO.class);


    assertEquals("Finland", country.getName());

    }

    @Test
    void whenGetNonexistent_ReturnError() throws IOException, URISyntaxException, ParseException {

        String responseErr = """
                {
                  "message": "Country not found or doesn't have any cases"
                }""";

        assertEquals(responseErr, countryManagerService.getCountryData("notaplace"));

    }


}