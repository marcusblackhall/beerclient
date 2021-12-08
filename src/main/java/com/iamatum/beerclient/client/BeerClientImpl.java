package com.iamatum.beerclient.client;

import com.iamatum.beerclient.config.WebClientProperties;
import com.iamatum.beerclient.domain.Beer;
import com.iamatum.beerclient.domain.BeerPagedList;
import com.iamatum.beerclient.domain.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
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
                uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_ID)
                        .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                        .build(id)

        ).retrieve().bodyToMono(Beer.class);
    }

    @Override
    public Mono<Beer> getBeerByUpc(String upc) {
        return webClient.get().uri(
                uriBuilder -> uriBuilder.path(WebClientProperties.BEERUPC_V1_PATH_ID +"/{upc}")
                        .build(upc)).retrieve().bodyToMono(Beer.class);
    }


    @Override
    public Mono<ResponseEntity> createBeer() {
        return null;
    }

    @Override
    public Mono<ResponseEntity> updateBeer(Integer beerId) {
        return null;
    }


    @Override
    public Mono<ResponseEntity> deleteBeer(Integer beerId) {
        return null;
    }


}
