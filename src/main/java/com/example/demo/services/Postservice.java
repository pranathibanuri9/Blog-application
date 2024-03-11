package com.example.demo.services;

import com.example.demo.entities.Post;
import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;

import java.util.List;

public interface Postservice {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    PostDto updatePost(PostDto postDto,Integer postId);

    void deletepost(Integer postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    PostDto getPostById(Integer postId);

//    List<PostDto> getPostByCategory(Integer categoryId);

    PostResponse getPostByCategoryPage(Integer categoryId,Integer pageNumber, Integer pageSize);

    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> serachPosts(String query);



}
