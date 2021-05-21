package com.example.TechTalentBlog.repo;

import com.example.TechTalentBlog.blogPost.BlogPost;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
//    List<BlogPost> findAll(id);
BlogPost findById(long id);
}
