SET search_path TO public;

-- MOVIE --
INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('Inception', '2 hours 28 minutes', '2010-09-24', 'Christopher Nolan',
       'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of' ||
       ' planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.');

INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('Oppenheimer', '3 hours 1 minute', '2023-07-20', 'Christopher Nolan',
       'The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.');

INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('I Care A Lot', '1 hour 58 minutes', '2021-02-20', 'J Blakeson',
       'A crooked legal guardian who drains the savings of her elderly wards meets her match when a woman she tries to' ||
       ' swindle turns out to be more than she first appears.');


-- CATEGORY --
INSERT INTO Category(name) VALUES('Action');
INSERT INTO Category(name) VALUES('Adventure');
INSERT INTO Category(name) VALUES('Animation');
INSERT INTO Category(name) VALUES('Biography');
INSERT INTO Category(name) VALUES('Comedy');
INSERT INTO Category(name) VALUES('Crime');
INSERT INTO Category(name) VALUES('Documentary');
INSERT INTO Category(name) VALUES('Drama');
INSERT INTO Category(name) VALUES('Family');
INSERT INTO Category(name) VALUES('Fantasy');
INSERT INTO Category(name) VALUES('Film-Noir');
INSERT INTO Category(name) VALUES('History');
INSERT INTO Category(name) VALUES('Horror');
INSERT INTO Category(name) VALUES('Music');
INSERT INTO Category(name) VALUES('Musical');
INSERT INTO Category(name) VALUES('Mystery');
INSERT INTO Category(name) VALUES('Romance');
INSERT INTO Category(name) VALUES('Science Fiction');
INSERT INTO Category(name) VALUES('Short');
INSERT INTO Category(name) VALUES('Sport');
INSERT INTO Category(name) VALUES('Thriller');
INSERT INTO Category(name) VALUES('War');
INSERT INTO Category(name) VALUES('Western');


-- MOVIE_CATEGORY --
INSERT INTO Movie_Category(id_movie, id_category) VALUES (1, 1);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (1, 2);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (1, 18);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (1, 21);

INSERT INTO Movie_Category(id_movie, id_category) VALUES (2, 4);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (2, 8);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (2, 12);

INSERT INTO Movie_Category(id_movie, id_category) VALUES (3, 5);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (3, 6);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (3, 8);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (3, 21);

