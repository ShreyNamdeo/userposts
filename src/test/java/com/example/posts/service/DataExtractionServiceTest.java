package com.example.posts.service;

import com.example.posts.config.ApplicationConfig;
import com.example.posts.dtos.postDtos.Post;
import com.example.posts.dtos.userDtos.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class DataExtractionServiceTest {
    private WebClient client;

    private final MockWebServer mockWebServer = new MockWebServer();

    private static ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    DataExtractionService dataExtractionService;

    @Mock
    ApplicationConfig applicationConfig;

    List<Post> posts = new ArrayList<>();
    List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp(){
        client = WebClient.create(mockWebServer.url("/").toString());
        Mockito.when(applicationConfig.getClient()).thenReturn(client);
        Post post1 = new Post();
        post1.setId(1);
        post1.setUserId(1);
        post1.setBody("Test Body 1");

        Post post2 = new Post();
        post2.setId(2);
        post2.setUserId(1);
        post2.setBody("Test Body 2");

        posts.add(post1);
        posts.add(post2);

        User user1 = new User();
        user1.setId(1);
        user1.setName("ABC");

        User user2 = new User();
        user2.setId(2);
        user2.setName("XYZ");

        users.add(user1);
        users.add(user2);
    }

    @Test
    void getPostsTest() throws Exception{
        String json = mapper.writeValueAsString(posts);
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(json));
        List<Post> postResp = dataExtractionService.getAllPosts();
        Assertions.assertEquals(postResp.get(0).getId(), posts.get(0).getId());
    }

    @Test
    void getUsersTest() throws Exception{
        String json = mapper.writeValueAsString(users);
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(json));
        List<Post> userResp = dataExtractionService.getAllPosts();
        Assertions.assertEquals(userResp.get(0).getId(), users.get(0).getId());
    }
}
