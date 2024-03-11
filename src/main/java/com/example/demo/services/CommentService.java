package com.example.demo.services;

import com.example.demo.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void DeleteComment(Integer commentId);
}
