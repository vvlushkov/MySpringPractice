INSERT INTO role_table VALUES
    (1, 'Admin'),
    (2, 'User');

INSERT INTO user_table (login, user_password, email, first_name, last_name, birthday, role_role_id) VALUES
    ('admin1', 'password', 'admin@email', 'Admin', 'Admin', '2000-01-01', 1),
    ('user1', 'password', 'user@email', 'User1FN', 'User1LN', '2000-01-01', 2);