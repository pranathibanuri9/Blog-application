package com.example.demo.controllers;

import com.example.demo.config.AppConstants;
import com.example.demo.entities.Post;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;
import com.example.demo.services.FileService;
import com.example.demo.services.Postservice;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PostController {
    @Autowired
    Postservice postservice;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {

        PostDto createPost = this.postservice.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);


    }

    //    get by User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {

        List<PostDto> postDtos = this.postservice.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);

    }

    //    @GetMapping("/category/{categoryId}/posts")
//    public ResponseEntity<List<PostDto>> getPostBycategory(@PathVariable Integer categoryId){
//
//        List<PostDto> postDtos=this.postservice.getPostByCategory(categoryId);
//        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
//
//    }
//    @GetMapping("/category/{categoryId}/posts")
//    public ResponseEntity<PostResponse> getpostbyCategorypage(@PathVariable Integer categoryId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
//        PostResponse allpost = this.postservice.getPostByCategoryPage(categoryId, pageNumber, pageSize);
//        return new ResponseEntity<PostResponse>(allpost, HttpStatus.OK);
//
//    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getpostbyCategorypage(@PathVariable Integer categoryId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        PostResponse allpost = this.postservice.getPostByCategoryPage(categoryId, pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(allpost, HttpStatus.OK);

    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllposts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.Page_Number, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.Page_Size, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR , required = false) String sortDir) {
        PostResponse allpost = this.postservice.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(allpost, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId) {
        PostDto postDto = this.postservice.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);

    }

    @DeleteMapping("/postd/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId) {
        this.postservice.deletepost(postId);
        return new ApiResponse("post is successfully deleted", true);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId, @RequestBody PostDto postDto) {
        PostDto postDto1 = this.postservice.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(postDto1, HttpStatus.OK);

    }

//    @GetMapping("/posts/search/{keywords}")
//    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
//
//        List<PostDto> result=this.postservice.serachPosts(keywords);
//        return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
//
//    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@RequestParam("query") String query) {

        List<PostDto> result = this.postservice.serachPosts(query);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

//
    }

//    post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image")MultipartFile image,@PathVariable Integer postId) throws IOException {
        PostDto postDto=this.postservice.getPostById(postId);
        String filename=this.fileService.uploadImage(path,image);

        postDto.setImageName(filename);
        PostDto updatePost=this.postservice.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);

    }

    @GetMapping(value = "post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
