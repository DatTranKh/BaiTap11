package com.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book_24110198 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int bookid;
    private Integer isbn;
    private String title;
    private String publisher;
    private BigDecimal price;
    private String description;
    private Date publishDate;
    private String coverImage;
    private Integer quantity;

    private List<Author_24110198> authors;
    private List<Rating_24110198> ratings;
    private int reviewCount;  
    private double avgRating;  
}