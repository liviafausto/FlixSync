SET search_path TO public;

-- MOVIE --
INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('Inception', '2 hours 28 minutes', '2010-09-24', 'Christopher Nolan',
       'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of' ||
       ' planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.');


-- CATEGORY --
INSERT INTO Category(name) VALUES('Science Fiction');
INSERT INTO category(name) VALUES('Thriller');


-- MOVIE_CATEGORY --
INSERT INTO Movie_Category(id_movie, id_category) VALUES (1, 1);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (1, 2);
