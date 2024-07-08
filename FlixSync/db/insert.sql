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

INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('The Little Things', '2 hours 7 minutes', '2021-05-05', 'John Lee Hancock',
       'Kern County Deputy Sheriff Joe Deacon is sent to Los Angeles for what should have been a quick evidence-gathering' ||
       ' assignment. Instead, he becomes embroiled in the search for a serial killer who is terrorizing the city.');

INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('Black Widow', '2 hours 14 minutes', '2021-07-08', 'Cate Shortland',
       'Natasha Romanoff confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises.');

INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('Joker', '2 hours 2 minutes', '2019-10-03', 'Todd Phillips',
       'Arthur Fleck, a party clown and a failed stand-up comedian, leads an impoverished life with his ailing mother.' ||
       ' However, when society shuns him and brands him as a freak, he decides to embrace the life of crime and chaos in Gotham City.');

INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('Spider-Man: Homecoming', '2 hours 13 minutes', '2017-07-06', 'Jon Watts',
       'Peter Parker balances his life as an ordinary high school student in Queens with his superhero alter-ego Spider-Man,' ||
       ' and finds himself on the trail of a new menace prowling the skies of New York City.');

INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('Spider-Man: Far From Home', '2 hours 9 minutes', '2019-07-04', 'Jon Watts',
       'Following the events of Avengers: Endgame (2019), Spider-Man must step up to take on new threats in a world that has changed forever.');

INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('Spider-Man: No Way Home', '2 hours 28 minutes', '2021-12-16', 'Jon Watts',
       'With Spider-Man''s identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous' ||
       ' foes from other worlds start to appear, forcing Peter to discover what it truly means to be Spider-Man.');

INSERT INTO Movie(name, duration, release_date, director, summary)
VALUES('The Beekeeper', '1 hour 45 minutes', '2024-01-11', 'David Ayer',
       'One man''s brutal campaign for vengeance takes on national stakes after he is revealed to be a former operative' ||
       ' of a powerful and clandestine organization known as "Beekeepers."');


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

INSERT INTO Movie_Category(id_movie, id_category) VALUES (4, 6);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (4, 8);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (4, 16);

INSERT INTO Movie_Category(id_movie, id_category) VALUES (5, 1);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (5, 2);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (5, 18);

INSERT INTO Movie_Category(id_movie, id_category) VALUES (6, 6);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (6, 8);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (6, 21);

INSERT INTO Movie_Category(id_movie, id_category) VALUES (7, 1);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (7, 2);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (7, 18);

INSERT INTO Movie_Category(id_movie, id_category) VALUES (8, 1);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (8, 2);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (8, 5);

INSERT INTO Movie_Category(id_movie, id_category) VALUES (9, 1);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (9, 2);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (9, 10);

INSERT INTO Movie_Category(id_movie, id_category) VALUES (10, 1);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (10, 6);
INSERT INTO Movie_Category(id_movie, id_category) VALUES (10, 21);
