package com.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating_24110198 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;
    private int bookId;
    private Integer rating;
    private String reviewText;

    private User_24110198 user;
    private String userFullname;
}