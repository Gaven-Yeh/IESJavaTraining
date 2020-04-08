package com.gaven.ratingsdataservice.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserRating {
    private List<Rating> userRating;
}
