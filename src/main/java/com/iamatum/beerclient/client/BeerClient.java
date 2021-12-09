package com.iamatum.beerclient.client;

import com.iamatum.beerclient.domain.Beer;
import com.iamatum.beerclient.domain.BeerPagedList;
import com.iamatum.beerclient.domain.BeerStyleEnum;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BeerClient {
    Mono<BeerPagedList> listBeers(Integer pageNumber,
                                        Integer pageSize,
                                        String beerName,
                                        BeerStyleEnum beerStyleEnum,
                                        Boolean showInventoryOnHand
    );

    Mono<ResponseEntity<Void>> createBeer(Beer beer);

    Mono<ResponseEntity> updateBeer(Integer beerId);

    Mono<ResponseEntity> deleteBeer(Integer beerId);

    Mono<Beer> getBeerByUpc(String upc);
    Mono<Beer> findById(UUID id,Boolean showInventoryOnHand);

}
