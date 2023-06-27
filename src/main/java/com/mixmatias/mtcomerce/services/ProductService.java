package com.mixmatias.mtcomerce.services;

import com.mixmatias.mtcomerce.dto.ProductDTO;
import com.mixmatias.mtcomerce.entities.Product;
import com.mixmatias.mtcomerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Transactional(readOnly = true) //Isso diz para o SpringJpa que a consulta irá ser somente de leitura
    //assim a consulta ficara mais otimizada e rapida;
    public ProductDTO findById(Long id){
        Optional<Product> result = productRepository.findById(id);
        return new ProductDTO(result.get());
    }
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        return productRepository.findAll(pageable).map(ProductDTO::new);
    }

}
