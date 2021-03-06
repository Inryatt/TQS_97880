package tqs.hw.covidtracker.boundary;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;
import tqs.hw.covidtracker.connection.BasicHttpClient;
import tqs.hw.covidtracker.service.CountryManagerService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO LOGS
@RestController
@RequestMapping("api/v1/")
public class CovidController   {

    private final CountryManagerService countryService;

    public CovidController( ) throws URISyntaxException {
        this.countryService=new CountryManagerService((new BasicHttpClient()));
    }

    @GetMapping("/all")
    Map<Object,Object> getAllData() throws URISyntaxException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "API - /all was requested.");

        return convertThis( countryService.getAllData());
    }

    @GetMapping("/countries")
    ArrayList< Map<Object,Object>> getAllCountries() throws URISyntaxException, IOException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "API - /countries was requested.");

        return convertThisList( countryService.getAllCountryData());
    }


    @GetMapping("/countries/{name}")
    Map<Object,Object> getOneCountry(
            @PathVariable(value = "name") String name, @RequestParam(value = "time", defaultValue = "none") String time) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "API - /countries/<name> was requested.");

        return convertThis( countryService.getCountryData(name,time));
    }

    @GetMapping("/stats")
    Map<Object,Object> getStats() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "API - /stats was requested.");

        return convertThis( countryService.getCacheStats());
    }


    // Utils
    ArrayList< Map<Object,Object>> convertThisList(String inp){

        Type listType = new TypeToken<ArrayList< Map<Object,Object>>>(){}.getType();
        return new Gson().fromJson(inp, listType);
    }

    Map<Object,Object> convertThis(String inp){
        Gson gson = new Gson();
        return gson.fromJson(inp, Map.class);
    }
}
