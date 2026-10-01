package com.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User_24110198 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String fullname;
    private Integer phone;
    private String passwd;
    private Date signupDate;
    private Date lastLogin;
    private boolean isAdmin;
}