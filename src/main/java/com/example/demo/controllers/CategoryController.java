package com.example.demo.controllers;

import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.CategoryDto;
import com.example.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

@PostMapping("/cate")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
     CategoryDto createCategory=this.categoryService.createCategory(categoryDto);
     return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
}

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory( @Valid @RequestBody CategoryDto categorydto,@PathVariable Integer catId){
        CategoryDto updatedCategory=this.categoryService.updateCategory(categorydto,catId);
        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
    }


    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully!!",true),HttpStatus.OK);
    }


    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getsinglecategory(@PathVariable Integer catId){
        CategoryDto categoryDto=this.categoryService.getSingleCategory(catId);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getcategory(){
        List<CategoryDto> categoryDtos=this.categoryService.getCategories();
        return  ResponseEntity.ok(categoryDtos);
    }








}
