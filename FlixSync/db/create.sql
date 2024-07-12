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
    CONSTRAINT fk_mc_movie FOREIGN KEY(id_movie) REFERENCES Movie(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_mc_category FOREIGN KEY(id_category) REFERENCES Category(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Tv_Show(
    id SERIAL NOT NULL,
    title VARCHAR(255) NOT NULL,
    average_duration INTERVAL,
    summary TEXT,
    seasons INT DEFAULT 0,
    CONSTRAINT pk_tv_show PRIMARY KEY(id)
);

CREATE TABLE Episode(
    id_tv_show INT NOT NULL,
    season INT NOT NULL,
    number INT NOT NULL,
    name VARCHAR(255),
    duration INTERVAL,
    release_date DATE,
    director VARCHAR(100) NOT NULL,
    summary TEXT,
    CONSTRAINT pk_episode PRIMARY KEY(id_tv_show, season, number),
    CONSTRAINT fk_ep_tv_show FOREIGN KEY(id_tv_show) REFERENCES Tv_Show(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Tv_Show_Category(
    id_tv_show INT NOT NULL,
    id_category INT NOT NULL,
    CONSTRAINT pk_tv_show_category PRIMARY KEY(id_tv_show, id_category),
    CONSTRAINT fk_tc_tv_show FOREIGN KEY(id_tv_show) REFERENCES Tv_Show(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_tc_category FOREIGN KEY(id_category) REFERENCES Category(id) ON UPDATE CASCADE ON DELETE CASCADE
);
