package com.mixmatias.mtcomerce.services;

import com.mixmatias.mtcomerce.dto.ProductDTO;
import com.mixmatias.mtcomerce.entities.Product;
import com.mixmatias.mtcomerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

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
    @Transactional
    public ProductDTO insert (ProductDTO productDTO){
        Product product = new Product();
        copyDtoToEntity(productDTO, product);
        product = productRepository.save(product); //Faco isso pq eu quero que exiba o id que foi salvo,
        //se nao fizer isso, o id será null
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update (Long id, ProductDTO productDTO){
        Product product = productRepository.getReferenceById(id); //Deixa o objeto monitorado pela JPA
        copyDtoToEntity(productDTO, product);
        product = productRepository.save(product); //Faco isso pq eu quero que exiba o id que foi salvo,
        //se nao fizer isso, o id será null
        return new ProductDTO(product);
    }

    @Transactional
    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    private void copyDtoToEntity(ProductDTO productDTO, Product product){
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());
    }
}
