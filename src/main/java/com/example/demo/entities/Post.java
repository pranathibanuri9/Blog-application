package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    @SequenceGenerator(name = "post_sequence", sequenceName = "post_seq", initialValue = 1, allocationSize = 1)

    private Integer postId;

//    @Column(name="title",length = 100,nullable = false)
    private String title;
//    @Column(length = 1000000000)
    private String content;
    private  String imageName;
    private Date addedDate;
    @JsonIgnore
    @ManyToOne
    private Category category;
    @JsonIgnore
    @ManyToOne
    private User user;
    @OneToMany (mappedBy = "post",cascade =CascadeType.ALL)
    private Set<Comment> comments=new HashSet<>();


}
