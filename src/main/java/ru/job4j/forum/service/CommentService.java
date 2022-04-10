package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.repository.CommentRep;

@Service
public class CommentService {
    private final CommentRep commentRep;

    public CommentService(CommentRep commentRep) {
        this.commentRep = commentRep;
    }

    public void saveComment(Comment comment) {
        commentRep.save(comment);
    }
}
