package com.springframework.controllers.v1;

import com.springframework.api.v1.model.CategoryDTO;
import com.springframework.api.v1.model.CategoryListDTO;
import com.springframework.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping({"","/"})
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories(){

        return new CategoryListDTO(categoryService.getAllCategories());
    }
    @GetMapping({"/{name}","/{name}/"})
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name){

        return categoryService.getCategoryByName(name);
    }
}
