package tqs.hw.covidtracker.boundary;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.hw.covidtracker.connection.BasicHttpClient;
import tqs.hw.covidtracker.service.CountryManagerService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

//TODO LOGS
@RestController
@RequestMapping("api/v1/")
public class CovidController {

    private final CountryManagerService countryService;

    public CovidController( ) throws URISyntaxException {
        this.countryService=new CountryManagerService((new BasicHttpClient()));
    }

    @GetMapping("/all")
    ArrayList<Map> getAllData() throws URISyntaxException {
        return convertThisList( countryService.getAllData());
    }

    @GetMapping("/countries")
    ArrayList<Map> getAllCountries() throws URISyntaxException, IOException {
        return convertThisList( countryService.getAllCountryData());
    }

    @GetMapping("/countries/{name}")
    Map getOneCountry(
            @PathVariable(value = "name") String name) {
        return convertThis( countryService.getCountryData(name));
    }

    @GetMapping("/stats")
    Map getStats() {
        return convertThis( countryService.getCacheStats());
    }


    // Utils
    ArrayList<Map> convertThisList(String inp){

        Type listType = new TypeToken<ArrayList<Map>>(){}.getType();
        return new Gson().fromJson(inp, listType);
    }

    Map convertThis(String inp){
        Gson gson = new Gson();
        //return new Gson().fromJson(inp, CountryDTO.class);
        return gson.fromJson(inp, Map.class);
    }
}
