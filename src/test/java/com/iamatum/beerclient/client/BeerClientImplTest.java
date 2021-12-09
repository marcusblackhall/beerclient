package com.iamatum.beerclient.client;

import com.iamatum.beerclient.config.WebClientConfig;
import com.iamatum.beerclient.domain.Beer;
import com.iamatum.beerclient.domain.BeerPagedList;
import com.iamatum.beerclient.domain.BeerStyleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;

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

    /**
     * Demonstates threading model.
     */
    @Test
    @DisplayName("Demonstrate thread natur of reactive")
    void functionalReactiveTest() throws InterruptedException {

        AtomicReference<String> name = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10,
                null, null, null);;

                // without stopping for the thread of subscribe you will probably not see the print statement
        beerPagedListMono
                .map( beerPagedList -> beerPagedList.getContent().get(0).getId())
                .map( id -> beerClient.findById(id,false))
                .flatMap(mono -> mono)
                .subscribe(
                        beer -> {
                            System.out.println("Beer is " + beer.getBeerName());
                            name.set("I found it");
                            countDownLatch.countDown();
                        }

                );
        countDownLatch.await();
        assertThat(name.get()).isEqualTo("I found it");

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

        Beer beer = Beer.builder()
                .beerName("Boddingtons")
                .beerStyle(BeerStyleEnum.ALE)
                .upc("235454444444")
                .price(new BigDecimal("10.81"))
                .build();

        Mono<ResponseEntity<Void>> response = beerClient.createBeer(beer);

        ResponseEntity responseEntity = response.block();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    void updateBeer() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10,
                null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();
        Beer testBeer = pagedList.getContent().get(0);


        Beer updateBeer = Beer.builder()
                .beerName("Marcus changed beer")
                .beerStyle(testBeer.getBeerStyle())
                .upc(testBeer.getUpc())
                .price(testBeer.getPrice())
                .build();

        Mono<ResponseEntity<Void>> response = beerClient.updateBeer(testBeer.getId(), updateBeer);

        ResponseEntity responseEntity = response.block();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }

    @Test
    @DisplayName("Delete a Beer given a uuid test")
    void shouldHandleExceptionForDeleteWithNonExistingId() {

        UUID uuid = UUID.randomUUID();



        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeer(uuid);
        ResponseEntity<Void> responseEntity = responseEntityMono.onErrorResume(throwable ->  {
                    if (throwable instanceof WebClientResponseException){
                        WebClientResponseException exception = (WebClientResponseException) throwable;
                        return Mono.just(ResponseEntity.status(exception.getStatusCode()).build());
                    } else {
                        throw new RuntimeException(throwable);
                    }

                }).block();




        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    @DisplayName("Delete a Beer given a uuid test")
    void deleteBeer() {

        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10,
                null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();
        Beer deleteBeer = pagedList.getContent().get(0);
        Mono<ResponseEntity<Void>> response = beerClient.deleteBeer(deleteBeer.getId());
        ResponseEntity responseEntity = response.block();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    void getBeerByUpc() {
    }
}