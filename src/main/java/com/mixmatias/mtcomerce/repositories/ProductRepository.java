package com.mixmatias.mtcomerce.repositories;

import com.mixmatias.mtcomerce.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select obj " +
            "from Product obj " +
            "where UPPER(obj.name) like upper(concat('%', :name, '%'))")
    Page<Product> searchByName(String name, Pageable pageable);

}
