package com.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author_24110198 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int authorId;
    private String authorName;
    private Date dateOfBirth;

    private List<Book_24110198> books;
}