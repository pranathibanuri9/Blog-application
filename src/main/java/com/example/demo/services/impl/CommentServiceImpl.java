package com.example.demo.services.impl;

import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.execeptions.ResourceNotFoundExeception;
import com.example.demo.payloads.CommentDto;
import com.example.demo.repositories.CommentRepo;
import com.example.demo.repositories.PostRepo;
import com.example.demo.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundExeception("post","post_id",postId));
        Comment comment=this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        Comment savedcomment=this.commentRepo.save(comment);
        return this.modelMapper.map(savedcomment,CommentDto.class);
    }

    @Override
    public void DeleteComment(Integer commentId) {
        Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundExeception("comment","comment_id",commentId));
        this.commentRepo.delete(com);

    }
}
