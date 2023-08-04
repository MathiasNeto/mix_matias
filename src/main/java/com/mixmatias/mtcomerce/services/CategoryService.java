package com.mixmatias.mtcomerce.services;

import java.util.List;

import com.mixmatias.mtcomerce.dto.CategoryDTO;
import com.mixmatias.mtcomerce.entities.Category;
import com.mixmatias.mtcomerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> result = repository.findAll();
        return result.stream().map(x -> new CategoryDTO(x)).toList();
    }
}
