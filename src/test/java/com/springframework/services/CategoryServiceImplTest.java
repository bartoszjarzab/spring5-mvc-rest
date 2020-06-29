package com.springframework.services;

import com.springframework.api.v1.mapper.CategoryMapper;
import com.springframework.api.v1.model.CategoryDTO;
import com.springframework.domain.Category;
import com.springframework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    private static final Long ID=2L;
    private static final String NAME ="John";


    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService=new CategoryServiceImpl(CategoryMapper.INSTANCE,categoryRepository);
    }

    @Test
    void getAllCategories() {

        //given
        List<Category> categoryList = new ArrayList<>();
        Category cat1 = new Category();
        cat1.setId(ID);
        cat1.setName(NAME);
        categoryList.add(cat1);
        categoryList.add(new Category());


        when(categoryRepository.findAll()).thenReturn(categoryList);

        //when
        List<CategoryDTO> categoryDTOList =categoryService.getAllCategories();

        //then
        assertEquals(2, categoryDTOList.size());
    }

    @Test
    void getCategoryByName() {
        //given
        Category cat1 = new Category();
        cat1.setId(ID);
        cat1.setName(NAME);

        when(categoryRepository.findByName(anyString())).thenReturn(cat1);

        //when
        CategoryDTO categoryDTO =categoryService.getCategoryByName(NAME);


        //then
        assertEquals(ID,categoryDTO.getId());
        assertEquals(NAME,categoryDTO.getName());

    }
}