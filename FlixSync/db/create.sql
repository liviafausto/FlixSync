SET search_path TO public;

CREATE TABLE Movie(
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    duration INTERVAL,
    release_date DATE,
    director VARCHAR(100) NOT NULL,
    summary TEXT,
    CONSTRAINT pk_movie PRIMARY KEY(id)
);

CREATE TABLE Category(
    id SERIAL NOT NULL,
    name VARCHAR(50),
    CONSTRAINT pk_category PRIMARY KEY(id)
);

CREATE TABLE Movie_Category(
    id_movie INT NOT NULL,
    id_category INT NOT NULL,
    CONSTRAINT pk_movie_category PRIMARY KEY(id_movie, id_category),
    CONSTRAINT fk_movie FOREIGN KEY(id_movie) REFERENCES Movie(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_category FOREIGN KEY(id_category) REFERENCES Category(id) ON UPDATE CASCADE ON DELETE CASCADE
);

