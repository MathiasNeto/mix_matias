package com.mixmatias.mtcomerce.services;

import com.mixmatias.mtcomerce.dto.ProductDTO;
import com.mixmatias.mtcomerce.entities.Product;
import com.mixmatias.mtcomerce.repositories.ProductRepository;
import com.mixmatias.mtcomerce.services.exceptions.DatabaseException;
import com.mixmatias.mtcomerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Transactional(readOnly = true) //Isso diz para o SpringJpa que a consulta irá ser somente de leitura
    //assim a consulta ficara mais otimizada e rapida;
    public ProductDTO findById(Long id){
        Product result = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
        return new ProductDTO(result);
    }
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String name, Pageable pageable){
        return productRepository.searchByName(name, pageable).map(ProductDTO::new);
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
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id Not Found");
        }
        copyDtoToEntity(productDTO, product);
        product = productRepository.save(product); //Faco isso pq eu quero que exiba o id que foi salvo,
        //se nao fizer isso, o id será null
        return new ProductDTO(product);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException(
                    "Id Not Found"
            );
        }
        try{
            productRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Data integrity failure");
        }
    }

    private void copyDtoToEntity(ProductDTO productDTO, Product product){
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());
    }
}
