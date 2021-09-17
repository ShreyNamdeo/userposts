package com.example.posts.service;

import com.example.posts.dtos.UserPostsDto;
import com.example.posts.dtos.postDtos.Post;
import com.example.posts.dtos.userDtos.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataExtractionService {

    @Value("${url.users}")
    private String usersBaseUrl;

    @Value("${url.posts}")
    private String postsBaseUrl;

    public List<UserPostsDto> getAllUsersCombinedWithPosts(List<User> users, List<Post> posts) {
        List<UserPostsDto> userPostResp = new ArrayList<>();
        users.forEach(user -> {
            List<Post> userPosts = new ArrayList<>();
            if (posts.stream().anyMatch(post -> post.getUserId().equals(user.getId())))
                userPosts = posts.stream().filter(post -> post.getUserId().equals(user.getId())).collect(Collectors.toList());
            userPostResp.add(new UserPostsDto(user, userPosts));
        });
        return userPostResp;
    }

    public List<User> getAllUsers(){
        return getUsersFromResp(WebClient.create().get().uri(usersBaseUrl).retrieve().bodyToMono(JsonNode.class).block());
    }

    private List<User> getUsersFromResp(JsonNode response) {
        if (response != null){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<User>> typeReference = new TypeReference<>() {};
            List<User> userList = new ArrayList<>();
            try{
                InputStream inputStream = new ByteArrayInputStream(response.toString().getBytes());
                userList = objectMapper.readValue(inputStream,typeReference);
            }catch (Exception e){
                e.printStackTrace();
            }
            return userList;
        }
        return new ArrayList<>();
    }

    public List<Post> getAllPosts(){
        return getPostsFromResponse(WebClient.create().get().uri(postsBaseUrl).retrieve().bodyToMono(JsonNode.class).block());
    }

    private List<Post> getPostsFromResponse(JsonNode response) {
        if (response != null){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<Post>> typeReference = new TypeReference<>() {};
            List<Post> postList = new ArrayList<>();
            try{
                InputStream inputStream = new ByteArrayInputStream(response.toString().getBytes());
                postList = objectMapper.readValue(inputStream,typeReference);
            }catch (Exception e){
                e.printStackTrace();
            }
            return postList;
        }
        return new ArrayList<>();
    }
}
