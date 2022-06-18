DROP TABLE IF EXISTS user_table;
DROP TABLE IF EXISTS role_table;

CREATE TABLE IF NOT EXISTS role_table
(
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_table
(
    user_id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login VARCHAR(20) NOT NULL UNIQUE,
    user_password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birthday DATE NOT NULL,
    role_role_ID INT NOT NULL,
    FOREIGN KEY (role_role_ID) REFERENCES role_table (role_ID)
);