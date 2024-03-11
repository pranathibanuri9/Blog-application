package com.example.demo.services;

import com.example.demo.payloads.CategoryDto;

import java.util.List;


public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
     CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
     void deleteCategory(Integer categoryId);
    CategoryDto getSingleCategory(Integer categoryId);
    List<CategoryDto> getCategories();
}
