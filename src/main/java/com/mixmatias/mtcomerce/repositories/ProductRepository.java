package com.mixmatias.mtcomerce.repositories;

import com.mixmatias.mtcomerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
