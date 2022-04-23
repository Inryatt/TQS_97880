package tqs.hw.covidtracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import tqs.hw.covidtracker.connection.BasicHttpClient;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CachingTest {

    @Mock( lenient = true)
    private BasicHttpClient basicHttpClient= Mockito.mock(BasicHttpClient.class);

    @InjectMocks
    private CountryManagerService countryManagerService=new CountryManagerService(basicHttpClient);;

    public CachingTest() throws URISyntaxException {
        when(countryManagerService.getCountryData("Finland","none")).thenReturn(   "{\"updated\":1650189016685,\"country\":\"Finland\",\"countryInfo\":{\"_id\":246,\"iso2\":\"FI\",\"iso3\":\"FIN\",\"lat\":64,\"long\":26,\"flag\":\"https://disease.sh/assets/img/flags/fi.png\"},\"cases\":949583,\"todayCases\":0,\"deaths\":3517,\"todayDeaths\":0,\"recovered\":46000,\"todayRecovered\":0,\"active\":900066,\"critical\":29,\"casesPerOneMillion\":170908,\"deathsPerOneMillion\":633,\"tests\":10591416,\"testsPerOneMillion\":1906270,\"population\":5556093,\"continent\":\"Europe\",\"oneCasePerPeople\":6,\"oneDeathPerPeople\":1580,\"oneTestPerPeople\":1,\"activePerOneMillion\":161996.21,\"recoveredPerOneMillion\":8279.2,\"criticalPerOneMillion\":5.22}");
    }

    @BeforeEach
    void init()  {
        countryManagerService.cache.clear();
    }

    @Test
    void whenSearch_isCached() throws URISyntaxException {
        assertFalse(countryManagerService.cache.contains("api/v1/countries/finland"));
        countryManagerService.getCountryData("finland","none");
        assertTrue(countryManagerService.cache.contains("api/v1/countries/finland"));
    }

    @Test
    void whenTTLExpires_isGone() throws  InterruptedException {
        assertFalse(countryManagerService.cache.contains("api/v1/countries/finland"));

        countryManagerService.getCountryData("finland","none");
        assertTrue(countryManagerService.cache.contains("api/v1/countries/finland"));

        Thread.sleep(countryManagerService.cache.ttl);
        assertFalse(countryManagerService.cache.contains("api/v1/countries/finland"));

    }

    @Test
    void whenGetResult_AddToStats() throws Exception {
        Integer ph = 12;
        String phs = "Europe";

        Integer countBefore = countryManagerService.cache.getStats().getRequests();
        countryManagerService.getCountryData("finland","none");
        Integer countAfter = countryManagerService.cache.getStats().getRequests();

        assertEquals((int) countAfter, countBefore + 1);
    }

    @Test
    void whenGetCacheHit_IncreaseHitStat() throws Exception {
        Integer ph = 12;
        String phs = "Europe";

        Integer hitsBefore = countryManagerService.cache.getStats().getRequests();
        countryManagerService.getCountryData("finland","none");
        Integer hitsAfter = countryManagerService.cache.getStats().getRequests();

        assertEquals((int) hitsAfter, hitsBefore + 1);
    }


}

