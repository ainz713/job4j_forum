package ru.job4j.forum.control;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.PostService;


@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class PostControlTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser
    public void shouldReturnPost() throws Exception {
        Post post = Post.of("some title", "some description", new User());
        post.setId(1);
        when(postService.findPostById(1)).thenReturn(post);
        mockMvc.perform(get("/post?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("user", "post"))
                .andExpect(model().attribute("post",
                        hasProperty("id", is(1))))
                .andExpect(model().attribute("post",
                        hasProperty("name", is("some title"))))
                .andExpect(model().attribute("post",
                        hasProperty("description", is("some description"))));
    }

    @Test
    @WithMockUser
    public void shouldReturnEditPost() throws Exception {
        Post post = Post.of("some title", "some description", new User());
        post.setId(1);
        when(postService.findPostById(1)).thenReturn(post);
        mockMvc.perform(get("/edit?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("user", "post"))
                .andExpect(model().attribute("post",
                        hasProperty("id", is(1))))
                .andExpect(model().attribute("post",
                        hasProperty("name", is("some title"))))
                .andExpect(model().attribute("post",
                        hasProperty("description", is("some description"))));
    }

    @Test
    @WithMockUser
    public void shouldReturnCreatePost() throws Exception {
        this.mockMvc.perform(get("/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("create"));
    }

    @Test
    @WithMockUser
    public void shouldReturnEditPostError() throws Exception {
        this.mockMvc.perform(get("/edit"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
