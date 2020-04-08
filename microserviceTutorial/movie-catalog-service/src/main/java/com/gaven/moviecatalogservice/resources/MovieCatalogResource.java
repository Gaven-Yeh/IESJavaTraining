package com.gaven.moviecatalogservice.resources;

import com.gaven.moviecatalogservice.entity.CatalogItem;
import com.gaven.moviecatalogservice.entity.Movie;
import com.gaven.moviecatalogservice.entity.Rating;
import com.gaven.moviecatalogservice.entity.UserRating;
import com.gaven.moviecatalogservice.service.MovieInfo;
import com.gaven.moviecatalogservice.service.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        // get all rated movie ids
        UserRating ratings = userRatingInfo.getUserRating(userId);
        return ratings.getUserRating().stream().map(rating -> {
            // for each movie id, call movie info service and get details
            // and put them all together
            return movieInfo.getCatalogItem(rating);
        })
                .collect(Collectors.toList());
    }





}
