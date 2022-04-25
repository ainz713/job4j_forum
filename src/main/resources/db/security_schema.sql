
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