package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostRep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class PostService {

    private final PostRep postRepository;

    public PostService(PostRep postRepository) {
        this.postRepository = postRepository;
    }

    public void savePost(Post post) {
        postRepository.findById(post.getId()).ifPresent(original -> original.getComments()
                .forEach(post::addComment));
        postRepository.save(post);
    }

    public void deletePost(int id) {
        postRepository.deleteById(id);
    }

    public Post findPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }
}
