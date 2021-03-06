package tqs.hw.covidtracker.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.hw.covidtracker.connection.BasicHttpClient;

@ExtendWith(MockitoExtension.class)
public class CountryManageServiceTest {
    static String name = "finland";

    @Mock( lenient = true)
    private BasicHttpClient basicHttpClient;

    @InjectMocks
    private CountryManagerService countryManagerService;


    @Test
    void whenGetAllCountries_ReturnAll() throws IOException, URISyntaxException {

        String responseAll="{\"updated\":1650403889565,\"cases\":505587189,\"todayCases\":436183,\"deaths\":6226643,\"todayDeaths\":1681,\"recovered\":457574586,\"todayRecovered\":1057940,\"active\":41785960,\"critical\":41976,\"casesPerOneMillion\":64862,\"deathsPerOneMillion\":798.8,\"tests\":6227169716,\"testsPerOneMillion\":788793.08,\"population\":7894554232,\"oneCasePerPeople\":0,\"oneDeathPerPeople\":0,\"oneTestPerPeople\":0,\"activePerOneMillion\":5293.01,\"recoveredPerOneMillion\":57960.79,\"criticalPerOneMillion\":5.32,\"affectedCountries\":228}";
        Mockito.when(basicHttpClient.doHttpGet("https://disease.sh/v3/covid-19/all")).thenReturn(responseAll);
        String r = countryManagerService.getAllData();
        assertEquals(responseAll,r);

    }
    @Test
    void whenGetAllData_ReturnIt() throws IOException, URISyntaxException {

        String responseAll = "[{\"updated\":1650187216459,\"country\":\"Afghanistan\",\"countryInfo\":{\"_id\":4,\"iso2\":\"AF\",\"iso3\":\"AFG\",\"lat\":33,\"long\":65,\"flag\":\"https://disease.sh/assets/img/flags/af.png\"},\"cases\":178418,\"todayCases\":31,\"deaths\":7678,\"todayDeaths\":2,\"recovered\":161517,\"todayRecovered\":43,\"active\":9223,\"critical\":1124,\"casesPerOneMillion\":4406,\"deathsPerOneMillion\":190,\"tests\":935313,\"testsPerOneMillion\":23098,\"population\":40493402,\"continent\":\"Asia\",\"oneCasePerPeople\":227,\"oneDeathPerPeople\":5274,\"oneTestPerPeople\":43,\"activePerOneMillion\":227.77,\"recoveredPerOneMillion\":3988.72,\"criticalPerOneMillion\":27.76},{\"updated\":1650187216366,\"country\":\"Albania\",\"countryInfo\":{\"_id\":8,\"iso2\":\"AL\",\"iso3\":\"ALB\",\"lat\":41,\"long\":20,\"flag\":\"https://disease.sh/assets/img/flags/al.png\"},\"cases\":274462,\"todayCases\":0,\"deaths\":3496,\"todayDeaths\":0,\"recovered\":270608,\"todayRecovered\":0,\"active\":358,\"critical\":2,\"casesPerOneMillion\":95559,\"deathsPerOneMillion\":1217,\"tests\":1791981,\"testsPerOneMillion\":623909,\"population\":2872185,\"continent\":\"Europe\",\"oneCasePerPeople\":10,\"oneDeathPerPeople\":822,\"oneTestPerPeople\":2,\"activePerOneMillion\":124.64,\"recoveredPerOneMillion\":94216.77,\"criticalPerOneMillion\":0.7}]";

        Mockito.when(basicHttpClient.doHttpGet("https://disease.sh/v3/covid-19/countries/")).thenReturn(responseAll);
        String r = countryManagerService.getAllCountryData();
        assertEquals(responseAll,r);

    }

    @Test
    void whenGetOneCountry_ReturnIt() throws IOException {

        String responseFin = "{\"updated\":1650189016685,\"country\":\"Finland\",\"countryInfo\":{\"_id\":246,\"iso2\":\"FI\",\"iso3\":\"FIN\",\"lat\":64,\"long\":26,\"flag\":\"https://disease.sh/assets/img/flags/fi.png\"},\"cases\":949583,\"todayCases\":0,\"deaths\":3517,\"todayDeaths\":0,\"recovered\":46000,\"todayRecovered\":0,\"active\":900066,\"critical\":29,\"casesPerOneMillion\":170908,\"deathsPerOneMillion\":633,\"tests\":10591416,\"testsPerOneMillion\":1906270,\"population\":5556093,\"continent\":\"Europe\",\"oneCasePerPeople\":6,\"oneDeathPerPeople\":1580,\"oneTestPerPeople\":1,\"activePerOneMillion\":161996.21,\"recoveredPerOneMillion\":8279.2,\"criticalPerOneMillion\":5.22}";

        Mockito.when(basicHttpClient.doHttpGet("https://disease.sh/v3/covid-19/countries/finland")).thenReturn(responseFin);

        assertEquals(responseFin,countryManagerService.getCountryData("finland","none"));

    }

    @Test
    void whenGetNonexistent_ReturnError() throws IOException {

        String responseErr =  """
                {
                "message": "Country not found or doesn't have any cases"
                }
                """;;


        Mockito.when(basicHttpClient.doHttpGet("https://disease.sh/v3/covid-19/countries/notaplace")).thenReturn(responseErr);

        assertEquals(countryManagerService.dataNotFound(),countryManagerService.getCountryData("notaplace","None"));

    }

    @Test
    void whenCountryCacheHit_NoRequest() throws IOException {
        String responseFin = "{\"updated\":1650189016685,\"country\":\"Finland\",\"countryInfo\":{\"_id\":246,\"iso2\":\"FI\",\"iso3\":\"FIN\",\"lat\":64,\"long\":26,\"flag\":\"https://disease.sh/assets/img/flags/fi.png\"},\"cases\":949583,\"todayCases\":0,\"deaths\":3517,\"todayDeaths\":0,\"recovered\":46000,\"todayRecovered\":0,\"active\":900066,\"critical\":29,\"casesPerOneMillion\":170908,\"deathsPerOneMillion\":633,\"tests\":10591416,\"testsPerOneMillion\":1906270,\"population\":5556093,\"continent\":\"Europe\",\"oneCasePerPeople\":6,\"oneDeathPerPeople\":1580,\"oneTestPerPeople\":1,\"activePerOneMillion\":161996.21,\"recoveredPerOneMillion\":8279.2,\"criticalPerOneMillion\":5.22}";

        Mockito.when(basicHttpClient.doHttpGet("https://disease.sh/v3/covid-19/countries/finland")).thenReturn(responseFin);

        countryManagerService.getCountryData("finland","None");
        countryManagerService.getCountryData("finland","None");
        verify(basicHttpClient, times(1)).doHttpGet(Mockito.any());


    }
    @Test
    void whenAllDataCacheHit_NoRequest() throws IOException, URISyntaxException {
        String responseAll="{\"updated\":1650403889565,\"cases\":505587189,\"todayCases\":436183,\"deaths\":6226643,\"todayDeaths\":1681,\"recovered\":457574586,\"todayRecovered\":1057940,\"active\":41785960,\"critical\":41976,\"casesPerOneMillion\":64862,\"deathsPerOneMillion\":798.8,\"tests\":6227169716,\"testsPerOneMillion\":788793.08,\"population\":7894554232,\"oneCasePerPeople\":0,\"oneDeathPerPeople\":0,\"oneTestPerPeople\":0,\"activePerOneMillion\":5293.01,\"recoveredPerOneMillion\":57960.79,\"criticalPerOneMillion\":5.32,\"affectedCountries\":228}";
        Mockito.when(basicHttpClient.doHttpGet("https://disease.sh/v3/covid-19/all")).thenReturn(responseAll);
        countryManagerService.getAllData();
        countryManagerService.getAllData();
        verify(basicHttpClient, times(1)).doHttpGet(Mockito.any());


    }

    @Test
    void whenAllCountryCacheHit_NoRequest() throws IOException, URISyntaxException {
        String responseAll = "[{\"updated\":1650187216459,\"country\":\"Afghanistan\",\"countryInfo\":{\"_id\":4,\"iso2\":\"AF\",\"iso3\":\"AFG\",\"lat\":33,\"long\":65,\"flag\":\"https://disease.sh/assets/img/flags/af.png\"},\"cases\":178418,\"todayCases\":31,\"deaths\":7678,\"todayDeaths\":2,\"recovered\":161517,\"todayRecovered\":43,\"active\":9223,\"critical\":1124,\"casesPerOneMillion\":4406,\"deathsPerOneMillion\":190,\"tests\":935313,\"testsPerOneMillion\":23098,\"population\":40493402,\"continent\":\"Asia\",\"oneCasePerPeople\":227,\"oneDeathPerPeople\":5274,\"oneTestPerPeople\":43,\"activePerOneMillion\":227.77,\"recoveredPerOneMillion\":3988.72,\"criticalPerOneMillion\":27.76},{\"updated\":1650187216366,\"country\":\"Albania\",\"countryInfo\":{\"_id\":8,\"iso2\":\"AL\",\"iso3\":\"ALB\",\"lat\":41,\"long\":20,\"flag\":\"https://disease.sh/assets/img/flags/al.png\"},\"cases\":274462,\"todayCases\":0,\"deaths\":3496,\"todayDeaths\":0,\"recovered\":270608,\"todayRecovered\":0,\"active\":358,\"critical\":2,\"casesPerOneMillion\":95559,\"deathsPerOneMillion\":1217,\"tests\":1791981,\"testsPerOneMillion\":623909,\"population\":2872185,\"continent\":\"Europe\",\"oneCasePerPeople\":10,\"oneDeathPerPeople\":822,\"oneTestPerPeople\":2,\"activePerOneMillion\":124.64,\"recoveredPerOneMillion\":94216.77,\"criticalPerOneMillion\":0.7}]";

        Mockito.when(basicHttpClient.doHttpGet("https://disease.sh/v3/covid-19/countries/")).thenReturn(responseAll);
       countryManagerService.getAllCountryData();
        countryManagerService.getAllCountryData();
        verify(basicHttpClient, times(1)).doHttpGet(Mockito.any());


    }


}

