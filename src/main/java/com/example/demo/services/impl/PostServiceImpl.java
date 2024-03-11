package com.example.demo.services.impl;

import com.example.demo.entities.Category;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.execeptions.ResourceNotFoundExeception;
import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.repositories.PostRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.Postservice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements Postservice {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {

        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundExeception("user","user id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundExeception("category","category_id",categoryId));

        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("dafault.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost=this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);


    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundExeception("Post","Post_Id",postId));
        Category category=this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCategory(category);
        Post updated=this.postRepo.save(post);



        return this.modelMapper.map(updated,PostDto.class);
    }

    @Override
    public void deletepost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundExeception("Post","Post_Id",postId));
        this.postRepo.delete(post);


    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
//        int pagesize=5;
//        int pageNumber=1;


        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();

        }else{
            sort=Sort.by(sortBy).descending();
        }
        Pageable p= PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost=this.postRepo.findAll(p);
        List<Post>allPosts=pagePost.getContent();

        List<PostDto> postDtos=allPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundExeception("post","post_id",postId));
        PostDto postDto=this.modelMapper.map(post,PostDto.class);
        return postDto;
    }



    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundExeception("User","User_id",userId));
        List<Post> posts=this.postRepo.findByUser(user);


        return posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }
//    @Override
//    public List<PostDto> getPostByCategory(Integer categoryId) {
//        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundExeception("Category","category_id",categoryId));
//        List<Post> posts=this.postRepo.findByCategory(cat);
//        return posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
//    }
    @Override
    public PostResponse getPostByCategoryPage(Integer categoryId,Integer pageNumber, Integer pageSize){
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundExeception("Category","category_id",categoryId));
        Pageable p=PageRequest.of(pageNumber,pageSize);
        Page<Post> pagepost=this.postRepo.findByCategory(cat,p);
        List<Post>posts=pagepost.getContent();
        List<PostDto> allposts=posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse p1=new PostResponse();
        p1.setContent(allposts);
        p1.setPageNumber(pagepost.getNumber());
        p1.setPageSize(pagepost.getSize());
        p1.setTotalElements(pagepost.getTotalElements());
        p1.setTotalPages(pagepost.getTotalPages());
        p1.setLastPage(pagepost.isLast());

        return p1;

    };

    //    @Override
//    public List<PostDto> serachPosts(String keyword) {
//        List<Post> posts=this.postRepo.findByTitleContaining(keyword);
//        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
//        return postDtos;
//    }
    @Override
    public List<PostDto> serachPosts(String query) {
        List<Post> posts=this.postRepo.searchPost("%"+query+"%");
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
