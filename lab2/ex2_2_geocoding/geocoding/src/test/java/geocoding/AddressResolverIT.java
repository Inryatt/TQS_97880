package geocoding;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.NoSuchElementException;
import java.util.Optional;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class AddressResolverIT {

    TqsBasicHttpClient httpClient;
    AddressResolver addrResolver;



    @BeforeEach
    void init(){
        httpClient = new TqsBasicHttpClient();
        addrResolver = new AddressResolver(httpClient);
    }

    @Test
    void whenResolveAlboiGps_returnCaisAlboiAddress() throws ParseException, IOException, URISyntaxException {
        Optional<Address> res = addrResolver.findAddressForLocation(40.640661, -8.656688);
        Address result = res.get();
        assertEquals( result, new Address( "Cais do Alboi", "Glória e Vera Cruz", "Centro", "3800-246", null) );
        return;
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {
        Optional<Address> result = addrResolver.findAddressForLocation(-300, -810);
        assertThrows(NoSuchElementException.class, ()-> result.get());
        
    }
}