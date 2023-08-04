package com.mixmatias.mtcomerce.repositories;

import com.mixmatias.mtcomerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
