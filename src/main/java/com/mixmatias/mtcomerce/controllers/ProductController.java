package com.mixmatias.mtcomerce.controllers;

import com.mixmatias.mtcomerce.dto.ProductDTO;
import com.mixmatias.mtcomerce.entities.Product;
import com.mixmatias.mtcomerce.repositories.ProductRepository;
import com.mixmatias.mtcomerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable  Long id){
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok(productDTO);
    }
    @GetMapping()
    public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(name = "name", defaultValue = "")String name,  Pageable pageable){
        Page<ProductDTO> productDTOS = productService.findAll(name, pageable);
        return ResponseEntity.ok(productDTOS);
    }
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDTO){
        productDTO = productService.insert(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(productDTO.getId()).toUri();
        //Colocar uri é uuma boa pratica pq no cabecalho da resposta vem a uri do produto criado
        return ResponseEntity.created(uri).body(productDTO);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable  Long id, @RequestBody ProductDTO productDTO){
        productDTO = productService.update(id, productDTO);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
