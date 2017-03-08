CREATE TABLE users (
  id integer not null primary key,
  email varchar(255) not null,
  password varchar(255) not null
);
CREATE UNIQUE INDEX unique_index_users_on_email ON users (email);
