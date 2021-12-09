package com.iamatum.beerclient.client;

import com.iamatum.beerclient.config.WebClientProperties;
import com.iamatum.beerclient.domain.Beer;
import com.iamatum.beerclient.domain.BeerPagedList;
import com.iamatum.beerclient.domain.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    final WebClient webClient;

    @Override
    public Mono<BeerPagedList> listBeers(Integer pageNumber,
                                         Integer pageSize,
                                         String beerName,
                                         BeerStyleEnum beerStyleEnum,
                                         Boolean showInventoryOnHand
    ) {

        return webClient.get().uri(
                        uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH)
                                .queryParamIfPresent("pageNumber", Optional.ofNullable(pageNumber))
                                .queryParamIfPresent("pageSize", Optional.ofNullable(pageSize))
                                .queryParamIfPresent("beerName", Optional.ofNullable(beerName))
                                .queryParamIfPresent("beerStyleEnum", Optional.ofNullable(beerStyleEnum))
                                .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(beerStyleEnum))
                                .build()
                )
                .retrieve()
                .bodyToMono(BeerPagedList.class);
    }

    @Override
    public Mono<Beer> findById(UUID id, Boolean showInventoryOnHand) {
        return webClient.get().uri(
                uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
                        .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                        .build(id)

        ).retrieve().bodyToMono(Beer.class);
    }

    @Override
    public Mono<Beer> getBeerByUpc(String upc) {
        return webClient.get().uri(
                uriBuilder -> uriBuilder.path(WebClientProperties.BEERUPC_V1_PATH_GET_BY_UPC )
                        .build(upc)).retrieve().bodyToMono(Beer.class);
    }


    @Override
    public Mono<ResponseEntity<Void>> createBeer(Beer beer) {
        return webClient.post().uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH).build())
                .body(BodyInserters.fromValue(beer))
                .retrieve()
                .toBodilessEntity();



    }

    @Override
    public Mono<ResponseEntity<Void>> updateBeer(UUID beerId, Beer beer) {

        return webClient.put().uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID).build(beerId))
                .body(BodyInserters.fromValue(beer))
                .retrieve()
                .toBodilessEntity();


    }


    @Override
    public Mono<ResponseEntity<Void>> deleteBeer(UUID beerId) {

        return webClient.delete().uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID).build(beerId))
                .retrieve()
                .toBodilessEntity();
    }


}
