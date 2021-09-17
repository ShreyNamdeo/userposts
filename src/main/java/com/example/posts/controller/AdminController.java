package com.example.posts.controller;

import com.example.posts.dtos.UserPostsDto;
import com.example.posts.service.DataExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    DataExtractionService dataExtractionService;

    @GetMapping("/all/users")
    public List<UserPostsDto> getAllUsersWithPosts(){
        return dataExtractionService.getAllUsersCombinedWithPosts(dataExtractionService.getAllUsers(), dataExtractionService.getAllPosts());
    }
}
