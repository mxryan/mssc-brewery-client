package mxryan.msscbreweryclient.web.client;

import mxryan.msscbreweryclient.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    @Test
    void getBeerById() {
        BeerDto beer = client.getBeerById(UUID.randomUUID());
        assertNotNull(beer);
    }

    @Test
    void testSaveNewBeer() {
        // given
        BeerDto beerDto = BeerDto.builder().beerName("La Farquada").build();
        URI uri = client.saveNewBeer(beerDto);
        assertNotNull(uri);
    }

    @Test
    void updateBeerTest() {
        BeerDto beerDto = BeerDto.builder().beerName("Tripalistaticolisimo").build();
        client.updateBeer(UUID.randomUUID(), beerDto);
    }

    @Test
    void deleteBeerTest() {
        client.deleteBeer(UUID.randomUUID());
    }
}