package com.springframework.controllers.v1;

import com.springframework.api.v1.model.CategoryDTO;
import com.springframework.api.v1.model.CategoryListDTO;
import com.springframework.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping({"","/"})
    public ResponseEntity<CategoryListDTO> getAllCategories(){

        return new ResponseEntity<>(
                new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);
    }
    @GetMapping({"/{name}","/{name}/"})
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name){

        return new ResponseEntity<>(
                categoryService.getCategoryByName(name),HttpStatus.OK);
    }
}
