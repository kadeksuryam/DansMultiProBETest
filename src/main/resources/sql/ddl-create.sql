CREATE TABLE IF NOT EXISTS users (
    id serial PRIMARY KEY,
    username VARCHAR (50) UNIQUE NOT NULL,
    password VARCHAR (100) NOT NULL,
    email VARCHAR (50)  NOT NULL
);
