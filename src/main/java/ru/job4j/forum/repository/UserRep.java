package ru.job4j.forum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.forum.model.User;

public interface UserRep extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
