package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    List<Post> getPostsByUsername(String username);

    List<Post> getAllPosts();

    Result<?> addPost(PostDto postDto);
}
