package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_seq", initialValue = 1, allocationSize = 1)
    private int id;
    private String content;
    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
