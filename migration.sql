USE movies_db;

CREATE TABLE movies_db.categories (
  id   INT(10) UNSIGNED AUTO_INCREMENT
    PRIMARY KEY,
  name VARCHAR(256) NOT NULL
);

CREATE TABLE movies_db.movies (
  id        INT(10) UNSIGNED AUTO_INCREMENT
    PRIMARY KEY,
  title     VARCHAR(300)    NOT NULL,
  rating    INT DEFAULT '0' NULL,
  thumbnail VARCHAR(256)    NULL
);

CREATE TABLE movies_db.movies_categories (
  movie_id    INT(10) UNSIGNED NOT NULL,
  category_id INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (movie_id, category_id),
  CONSTRAINT movies_categories_ibfk_1
  FOREIGN KEY (movie_id) REFERENCES movies_db.movies (id),
  CONSTRAINT movies_categories_ibfk_2
  FOREIGN KEY (category_id) REFERENCES movies_db.categories (id)
);

CREATE INDEX category_id
  ON movies_categories (category_id);

CREATE TABLE movies_db.users (
  id       INT(10) UNSIGNED AUTO_INCREMENT
    PRIMARY KEY,
  username VARCHAR(50)  NOT NULL,
  password VARCHAR(256) NOT NULL
);
