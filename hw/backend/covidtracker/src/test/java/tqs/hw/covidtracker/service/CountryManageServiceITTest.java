package tqs.hw.covidtracker.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.hw.covidtracker.connection.BasicHttpClient;

@ExtendWith(MockitoExtension.class)
public class CountryManageServiceITTest {
    private final BasicHttpClient basicHttpClient = new BasicHttpClient();

    private final CountryManagerService countryManagerService = new CountryManagerService(basicHttpClient);

    public CountryManageServiceITTest() throws URISyntaxException {
    }


    @Test
    void whenGetAllCountries_ReturnAll() throws IOException, URISyntaxException {

        String data = countryManagerService.getAllCountryData();
        Type listType = new TypeToken<ArrayList<Map>>(){}.getType();
         ArrayList<Map> countries = new Gson().fromJson(data, listType);

        assertEquals(228, countries.size());

    }

    @Test
    void whenGetOneCountry_ReturnIt()  {

        Map country= new Gson().fromJson(countryManagerService.getCountryData("finland","none"), Map.class);

        assertEquals("Finland", country.get("country"));

    }

    @Test
    void whenGetNonexistent_ReturnError() {

        String responseErr = """
                {"message":"Country not found or doesn't have any cases"}""";
        assertEquals(responseErr, countryManagerService.getCountryData("notaplace","none"));

    }


}