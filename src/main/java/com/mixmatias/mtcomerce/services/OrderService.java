package com.mixmatias.mtcomerce.services;

import java.time.Instant;

import com.mixmatias.mtcomerce.dto.OrderDTO;
import com.mixmatias.mtcomerce.dto.OrderItemDTO;
import com.mixmatias.mtcomerce.entities.Order;
import com.mixmatias.mtcomerce.entities.OrderItem;
import com.mixmatias.mtcomerce.entities.Product;
import com.mixmatias.mtcomerce.entities.User;
import com.mixmatias.mtcomerce.enums.OrderStatus;
import com.mixmatias.mtcomerce.repositories.OrderItemRepository;
import com.mixmatias.mtcomerce.repositories.OrderRepository;
import com.mixmatias.mtcomerce.repositories.ProductRepository;
import com.mixmatias.mtcomerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
	public OrderDTO insert(OrderDTO dto) {
		
    	Order order = new Order();
    	
    	order.setMoment(Instant.now());
    	order.setStatus(OrderStatus.WAITING_PAYMENT);
    	
    	User user = userService.authenticated();
    	order.setClient(user);
    	
    	for (OrderItemDTO itemDto : dto.getItems()) {
    		Product product = productRepository.getReferenceById(itemDto.getProductId());
    		OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
    		order.getItems().add(item);
    	}
    	
    	repository.save(order);
    	orderItemRepository.saveAll(order.getItems());
    	
    	return new OrderDTO(order);
	}
}
