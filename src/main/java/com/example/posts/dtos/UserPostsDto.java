package com.example.posts.dtos;

import com.example.posts.dtos.postDtos.Post;
import com.example.posts.dtos.userDtos.User;

import java.util.List;

public class UserPostsDto extends User {
    private List<Post> posts;

    public UserPostsDto(){}

    public UserPostsDto(User user, List<Post> posts){
        super(user);
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
