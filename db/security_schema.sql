CREATE TABLE authorities(
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users(
                      id SERIAL PRIMARY KEY,
                      username VARCHAR(50) NOT NULL UNIQUE,
                      password VARCHAR(100) NOT NULL,
                      enabled BOOLEAN DEFAULT true,
                      authority_id INT NOT NULL REFERENCES authorities(id)
);

INSERT INTO authorities (name) VALUES ('ROLE_USER');
INSERT INTO authorities (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (username, password, enabled, authority_id)
VALUES ('admin', 'admin', true,
        (SELECT id FROM authorities WHERE name = 'ROLE_ADMIN'));
INSERT INTO users (username, password, enabled, authority_id)
VALUES ('user', '123', true,
        (SELECT id FROM authorities WHERE name = 'ROLE_USER'));
INSERT INTO users (username, password, enabled, authority_id)
VALUES ('spammer', '123', true,
        (SELECT id FROM authorities WHERE name = 'ROLE_USER'));