package com.example.demo.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public    class ComplaintCategory {

    @OneToOne
    @JoinColumn(name  = "category_id")
    private  long   category_id;

    private String category_name;

}
