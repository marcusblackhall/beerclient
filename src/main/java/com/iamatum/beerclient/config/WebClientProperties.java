package com.iamatum.beerclient.config;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

public class WebClientProperties {

    public static final String BEER_V1_PATH = "/api/v1/beer" ;
    public static final String BEER_V1_PATH_ID = "/api/v1/beer/{id}";
    public static final String BEERUPC_V1_PATH_ID = "/api/v1/beerUpc" ;
    public static String BASE_URL = "http://api.springframework.guru";

}
