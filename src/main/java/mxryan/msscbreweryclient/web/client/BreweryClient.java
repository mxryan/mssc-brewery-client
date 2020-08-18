package mxryan.msscbreweryclient.web.client;

import mxryan.msscbreweryclient.web.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "breweryclient", ignoreUnknownFields = false)
public class BreweryClient {
    public final String BEER_PATH_V1 = "/api/v1/beer/";
    private String apihost;
    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }

    public BeerDto getBeerById(UUID uuid) {
        String url = apihost + BEER_PATH_V1 + uuid.toString();
        return restTemplate.getForObject(url, BeerDto.class);
    }

    public void updateBeer(UUID uuid, BeerDto beerDto) {
        String url = apihost + BEER_PATH_V1 + uuid.toString();
        restTemplate.put(url, beerDto);
    }

    public URI saveNewBeer(BeerDto beerDto) {
        String url = apihost + BEER_PATH_V1;
        return restTemplate.postForLocation(url, beerDto);
    }


    public void deleteBeer(UUID uuid) {
        restTemplate.delete(apihost + BEER_PATH_V1 + uuid);
    }

}
