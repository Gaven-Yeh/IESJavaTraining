package com.gaven.moviecatalogservice.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserRating {
    private String userId;
    private List<Rating> userRating;
}
