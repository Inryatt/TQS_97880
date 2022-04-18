package tqs.hw.covidtracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tqs.hw.covidtracker.connection.BasicHttpClient;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.get;

public class CachingTest {

    @Mock( lenient = true)
    private BasicHttpClient basicHttpClient;

    @InjectMocks
    private CountryManagerService countryManagerService;
    @BeforeEach
    void init() throws URISyntaxException {

        countryManagerService.cache.clear();
        when(countryManagerService.getCountryData("Finland")).thenReturn(   "{\"updated\":1650189016685,\"country\":\"Finland\",\"countryInfo\":{\"_id\":246,\"iso2\":\"FI\",\"iso3\":\"FIN\",\"lat\":64,\"long\":26,\"flag\":\"https://disease.sh/assets/img/flags/fi.png\"},\"cases\":949583,\"todayCases\":0,\"deaths\":3517,\"todayDeaths\":0,\"recovered\":46000,\"todayRecovered\":0,\"active\":900066,\"critical\":29,\"casesPerOneMillion\":170908,\"deathsPerOneMillion\":633,\"tests\":10591416,\"testsPerOneMillion\":1906270,\"population\":5556093,\"continent\":\"Europe\",\"oneCasePerPeople\":6,\"oneDeathPerPeople\":1580,\"oneTestPerPeople\":1,\"activePerOneMillion\":161996.21,\"recoveredPerOneMillion\":8279.2,\"criticalPerOneMillion\":5.22}");


    }
    @Test
    void whenSearch_isCached() throws URISyntaxException {
        assertFalse(countryManagerService.cache.contains("api/v1/countries/finland"));
        countryManagerService.getCountryData("finland");
        assertTrue(countryManagerService.cache.contains("api/v1/countries/finland"));
    }

    @Test
    void whenTTLExpires_isGone() throws URISyntaxException {

        countryManagerService.getCountryData("finland");
        assertTrue(countryManagerService.cache.contains("api/v1/countries/finland"));

        // TODO find way to sleep

        assertFalse(countryManagerService.cache.contains("api/v1/countries/finland"));

    }

    @Test
    void whenGetResult_AddToStats() throws Exception {
        Integer ph = 12;
        String phs = "Europe";

        Integer countBefore = countryManagerService.cache.getStats().getRequests();
        countryManagerService.getCountryData("finland");
        Integer countAfter = countryManagerService.cache.getStats().getRequests();

        assertEquals((int) countAfter, countBefore + 1);
    }

    @Test
    void whenGetCacheHit_IncreaseHitStat() throws Exception {
        Integer ph = 12;
        String phs = "Europe";

        Integer hitsBefore = countryManagerService.cache.getStats().getRequests();
        countryManagerService.getCountryData("finland");
        Integer hitsAfter = countryManagerService.cache.getStats().getRequests();

        assertEquals((int) hitsAfter, hitsBefore + 1);
    }


}

