package com.example.demo.controllers;

import com.example.demo.entities.Comment;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.CommentDto;
import com.example.demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto created=this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<CommentDto>(created,HttpStatus.CREATED);

    }

    @DeleteMapping ("/post/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment( @PathVariable Integer commentId){
        this.commentService.DeleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("deleted succesfully",true),HttpStatus.OK);

    }
}
