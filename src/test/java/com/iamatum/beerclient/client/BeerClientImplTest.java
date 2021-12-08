package com.iamatum.beerclient.client;

import com.iamatum.beerclient.config.WebClientConfig;
import com.iamatum.beerclient.domain.Beer;
import com.iamatum.beerclient.domain.BeerPagedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BeerClientImplTest {

    BeerClient beerClient;

    @Test
    void listBeers() {

        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null,
                null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();
        assertThat(pagedList).isNotNull();
        assertThat(pagedList.getContent().size()).isGreaterThan(0);

    }

    @Test
    void listBeers10() {

        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10,
                null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();
        assertThat(pagedList).isNotNull();
        assertThat(pagedList.getContent().size()).isEqualTo(10);

    }

    @Test
    void listBeersNoRecords() {

        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(10, 20,
                null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();
        assertThat(pagedList).isNotNull();
        assertThat(pagedList.getContent().size()).isEqualTo(0);

    }

    @Test
    void shouldRetrieveBeerById() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10,
                null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();
        Beer testBeer = pagedList.getContent().get(0);

        Mono<Beer> beerMono = beerClient.findById(testBeer.getId(), false);
        Beer beer = beerMono.block();
        assertThat(beer).isNotNull();
        assertThat(beer.getBeerName()).isEqualTo(testBeer.getBeerName());

    }
    @Test
    void shouldRetrieveBeerByUpc() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10,
                null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();
        Beer testBeer = pagedList.getContent().get(0);

        Mono<Beer> beerMono = beerClient.getBeerByUpc(testBeer.getUpc());
        Beer beer = beerMono.block();
        assertThat(beer).isNotNull();
        assertThat(beer.getBeerName()).isEqualTo(testBeer.getBeerName());

    }


    @BeforeEach
    void setUp() {
        beerClient = new BeerClientImpl(new WebClientConfig().webClient());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createBeer() {
    }

    @Test
    void updateBeer() {
    }

    @Test
    void deleteBeer() {
    }

    @Test
    void getBeerByUpc() {
    }
}