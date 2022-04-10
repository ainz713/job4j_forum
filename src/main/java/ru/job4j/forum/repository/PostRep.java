package ru.job4j.forum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public interface PostRep extends CrudRepository<Post, Integer> {

    @Override
    @Query("SELECT DISTINCT a FROM Post a "
            + "LEFT JOIN FETCH a.comments r "
            + "ORDER BY a.id")
    Iterable<Post> findAll();

    @Override
    @Query("SELECT DISTINCT a FROM Post a "
            + "LEFT JOIN FETCH a.comments r "
            + "WHERE a.id = ?1")
    Optional<Post> findById(Integer integer);
}
