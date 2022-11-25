package com.BlogSite.TestBlogProject.dto;

import com.BlogSite.TestBlogProject.models.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Role role;
}
