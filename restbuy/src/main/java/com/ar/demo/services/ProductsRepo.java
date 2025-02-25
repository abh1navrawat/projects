package com.ar.demo.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ar.demo.models.Product;

public interface ProductsRepo extends JpaRepository<Product, Integer>{

}
