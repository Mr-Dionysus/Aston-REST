package org.example.mappers;

import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.example.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {
    private final PostMapperImpl postMapper = new PostMapperImpl();

    @Test
    @DisplayName("Post to a Post DTO")
    void postToPostDTO() {
        String expectedText = "test text";
        int expectedLikes = 0;
        int expectedDislikes = 0;

        int expectedUserId = 1;
        String expectedLogin = "login";
        String expectedPassword = "password";
        User expectedUser = new User(expectedUserId, expectedLogin, expectedPassword);

        Post beforeDTOpost = new Post();
        beforeDTOpost.setText(expectedText);
        beforeDTOpost.setLikes(expectedLikes);
        beforeDTOpost.setDislikes(expectedDislikes);
        beforeDTOpost.setAuthor(expectedUser);

        PostDTO expectedPost = new PostDTO();
        expectedPost.setText(expectedText);
        expectedPost.setLikes(expectedLikes);
        expectedPost.setDislikes(expectedDislikes);
        expectedPost.setAuthor(expectedUser);

        PostDTO actualPost = postMapper.postToPostDTO(beforeDTOpost);

        assertEquals(expectedPost, actualPost);
    }
}