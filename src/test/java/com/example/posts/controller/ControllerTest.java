package com.example.posts.controller;

import com.example.posts.dtos.UserPostsDto;
import com.example.posts.dtos.postDtos.Post;
import com.example.posts.dtos.userDtos.User;
import com.example.posts.service.DataExtractionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdminController.class)
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DataExtractionService dataExtractionService;

    List<UserPostsDto> userPostsDtos = new ArrayList<>();

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        User user =new User();
        user.setId(1);
        user.setName("ABC");

        Post post1 = new Post();
        post1.setId(1);
        post1.setUserId(1);
        post1.setBody("Test Body 1");

        Post post2 = new Post();
        post2.setId(2);
        post2.setUserId(1);
        post2.setBody("Test Body 2");

        List<Post> posts = Arrays.asList(post1, post2);
        UserPostsDto userPostsDto = new UserPostsDto(user, posts);
        userPostsDtos.add(userPostsDto);
    }

    @Test
    void getAllUsersWithPostsTest() throws Exception{
        Mockito.when(dataExtractionService.getAllUsersCombinedWithPosts(Mockito.anyList(), Mockito.anyList())).thenReturn(userPostsDtos);
        mockMvc.perform(get("/admin/all/users")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)));
    }
}

