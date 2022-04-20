package ru.job4j.forum.control;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.CommentService;
import ru.job4j.forum.service.PostService;
import ru.job4j.forum.service.UserService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class GeneralPostControlTest {

    @MockBean
    private PostService posts;

    @MockBean
    private UserService userService;

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void savePost() throws Exception {
        this.mockMvc.perform(post("/post/save")
                        .param("name", "Куплю ладу-грант. Дорого.")
                        .param("description", "Рассматриваю пробег до 50 тысяч"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(posts).savePost(argument.capture());
        assertThat(argument.getValue().getName(), is("Куплю ладу-грант. Дорого."));
    }

    @Test
    @WithMockUser
    public void saveComment() throws Exception {
        Post post = Post.of("test", "test", new User());
        post.setId(1);
        when(posts.findPostById(1)).thenReturn(post);
        this.mockMvc.perform(post("/comment/save")
                        .param("postId", "1")
                        .param("text", "Рассматриваю пробег до 50 тысяч"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Comment> argument = ArgumentCaptor.forClass(Comment.class);
        verify(commentService).saveComment(argument.capture());
        assertThat(argument.getValue().getText(), is("Рассматриваю пробег до 50 тысяч"));
        assertThat(argument.getValue().getPost().getId(), is(1));
    }

    @Test
    @WithMockUser
    public void deletePost() throws Exception {
        this.mockMvc.perform(post("/post/delete")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(posts).deletePost(argument.capture());
        assertThat(argument.getValue(), is(1));
    }

    @Test
    @WithMockUser
    public void reg() throws Exception {
        this.mockMvc.perform(post("/reg")
                        .param("username", "test")
                        .param("password", "test"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userService).save(argument.capture());
        assertThat(argument.getValue().getUsername(), is("test"));
    }
}
