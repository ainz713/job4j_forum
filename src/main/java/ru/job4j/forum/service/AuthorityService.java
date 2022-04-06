package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.repository.AuthorityRep;

@Service
public class AuthorityService {

    private final AuthorityRep authorityRepository;

    public AuthorityService(AuthorityRep authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority findByName(String name) {
        return authorityRepository.findByName(name);
    }
}
