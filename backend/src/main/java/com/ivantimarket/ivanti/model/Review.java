package com.ivantimarket.ivanti.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Getter
@Setter
public class Review {

    @Transient
    public static final String SEQUENCE_NAME = "review_sequence";

    @Id
    private long id;
    private long userId;
    private long packageId;
    private int rating;

}
