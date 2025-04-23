package com.example.CompProductSystem.api.Product.ProdutsDetailEntity;

import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TV")
public class TV extends Product {

}
