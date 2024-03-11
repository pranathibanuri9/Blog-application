package com.example.demo.repositories;

import com.example.demo.entities.Category;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    Page<Post> findByCategory(Category category, Pageable pageable);
//    List<Post> findByCategory(Category category);
//    List<Post> findByTitleContaining(String title);
//@Query("SELECT p FROM Post p WHERE " +
//        "p.content LIKE CONCAT('%',:query, '%')" +
//        "Or p.title LIKE CONCAT('%', :query, '%')")
//    List<Post> searchPost(String query);

    @Query("select p from Post p where p.title like:key")
    List<Post> searchPost(@Param("key") String query);

}
