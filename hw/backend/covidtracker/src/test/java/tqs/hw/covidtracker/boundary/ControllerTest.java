package tqs.hw.covidtracker.boundary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tqs.hw.covidtracker.service.CountryManagerService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CovidController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CountryManagerService service;


    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenGetResult_GetCorrectResult() throws Exception {

        when(service.getCountryData("Finland")).thenReturn(
                """
                       {"updated":1650208218969,"country":"Finland","countryInfo":{"_id":246,"iso2":"FI","iso3":"FIN","lat":64,"long":26,"flag":"https://disease.sh/assets/img/flags/fi.png"},"cases":949583,"todayCases":0,"deaths":3517,"todayDeaths":0,"recovered":46000,"todayRecovered":0,"active":900066,"critical":29,"casesPerOneMillion":170908,"deathsPerOneMillion":633,"tests":10591416,"testsPerOneMillion":1906270,"population":5556093,"continent":"Europe","oneCasePerPeople":6,"oneDeathPerPeople":1580,"oneTestPerPeople":1,"activePerOneMillion":161996.21,"recoveredPerOneMillion":8279.2,"criticalPerOneMillion":5.22}
                """
        );
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/countries/finland")).andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("Finland"));
    }

    @Test
    void whenGetStats_GetStats() throws Exception {
       when(service.getCacheStats()).thenReturn(
                """
                    {"hits":0.0,"miss":0.0,"requests":5.0}
                """
        );

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/stats")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hits").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.miss").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.requests").exists());


    }
}
