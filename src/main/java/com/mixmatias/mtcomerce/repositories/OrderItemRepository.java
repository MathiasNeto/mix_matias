package com.mixmatias.mtcomerce.repositories;

import com.mixmatias.mtcomerce.entities.OrderItem;
import com.mixmatias.mtcomerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem , OrderItemPK> {

}
