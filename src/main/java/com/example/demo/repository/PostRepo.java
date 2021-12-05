package com.example.demo.repository;

import com.example.demo.model.Post;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository

public interface PostRepo extends JpaRepository<Post, Long> {

    @Override
    Optional<Post> findById(Long aLong);


    @Override
    Post save(Post post);

    void delete(Post post);



    Collection<Post> findAllByOrderByCreationDateDesc();


}
