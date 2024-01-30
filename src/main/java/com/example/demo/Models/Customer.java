package com.example.demo.Models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private double contactNumber;
    private String address;

    private Boolean isActive;
    private Boolean isDeleted;


    private Date createdAt;
    private Date updatedAt;
    private Date createdOn;
    private Date updatedOn;

}
