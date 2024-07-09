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


-- TV_SHOW --
INSERT INTO Tv_Show(title, average_duration, summary)
VALUES('Evil', '48 minutes',
       'A skeptical clinical psychologist joins a priest-in-training and a blue collar contractor as they investigate' ||
       ' supposed abnormal events, including, demonic possession, and other extraordinary occurrences to see if there' ||
       ' is a scientific explanation or if something truly supernatural is at work.');


-- TV_SHOW_CATEGORY --
INSERT INTO Tv_Show_Category(id_tv_show, id_category) VALUES(1, 6);
INSERT INTO Tv_Show_Category(id_tv_show, id_category) VALUES(1, 8);
INSERT INTO Tv_Show_Category(id_tv_show, id_category) VALUES(1, 13);


-- EPISODE --
INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 1, 'Genesis 1', '44 minutes', '2019-09-26', 'Robert King',
       'The Catholic Church hires forensic psychologist Kristen Bouchard to determine whether a serial killer is ' ||
       'possessed by a demon or merely a psychopath.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 2, '177 Minutes', '43 minutes', '2019-10-03 ', 'Ron Underwood',
       'Kristen, David and Ben investigate a supposed miracle when a teenage girl comes back to life after being' ||
       ' declared dead for almost three hours; Kristen meets with her former boss and runs into Leland Townsend.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 3, '3 Stars', '41 minutes', '2019-10-10', 'Gloria Muzio',
       'After a high-strung theater producer''s behavior turns from demanding to what is believed to be demonic,' ||
       ' Kristen, David and Ben must assess the situation; Kristen discredits Leland Townsend, before he can ruin' ||
       ' a 17-year-old boy''s life.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 4, 'Rose390', '42 minutes', '2019-10-17', 'Peter Sollett',
       'The team is hired to evaluate a 9-year-old boy who takes a liking to David, which leaves them hopeful they' ||
       ' can curb his violent behavior. Kristen worries about her young daughters who lie about a horror game their' ||
       ' grandmother bought behind her back.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 5, 'October 31', '42 minutes', '2019-10-24', 'Tess Malone',
       'David and Kristen disagree about an exorcism. Ben tries to debunk TV ghost hunters. The girls play a' ||
       ' dangerous game with a psychotic schoolmate during a Halloween sleepover.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 6, 'Let x = 9', '43 minutes', '2019-11-07', 'Kevin Rodney Sullivan',
       'The Catholic Church asks Kristen, David and Ben to assess the veracity of a local prophetess,' ||
       ' Grace Ling, and they are shaken when they see one of her visions come to life. Also, David struggles' ||
       ' with jealousy when he sees God speaking with the prophetess, and to Kristen''s dismay, her mother,' ||
       ' Sheryl, and Leland begin a relationship.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 7, 'Vatican III', '42 minutes', '2019-11-14', 'Jim McKay',
       'When Bridget confesses to murder during her exorcism, Monsignor Korecki asks Kristen, David and Ben' ||
       ' to investigate if the details match any open cases and if the woman really is possessed by a demon.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 8, '2 Fathers', '42 minutes', '2019-11-21', 'James Whitmore Jr.',
       'When David recognizes one of the sigils in the Poveglia Codex from his father Leon''s artwork, he and' ||
       ' Kristen journey to a remote art commune to investigate its meaning and how it''s related to David''s family.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 9, 'Exorcism Part 2', '42 minutes', '2019-12-05', 'Frederick E.O. Toye',
       'David is shocked to learn he is being sued for inflicting severe psychological harm on Caroline Hopkins,' ||
       ' after he assisted in her exorcism. The accusation leads him to question his future as a priest, especially' ||
       ' once he develops a very close connection with his defense attorney, Renée Harris.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 10, '7 Swans a Singin''', '42 minutes', '2019-12-12', 'John Dahl',
       'Kristen, David and Ben are called to investigate an insidiously addictive Christmas song that''s spreading' ||
       ' among an increasing number of students, and the dangerous relationship between online influencers and their' ||
       ' impressionable young followers.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 11, 'Room 320', '42 minutes', '2020-01-09', 'Peter Sollett',
       'After David is badly wounded, he is determined to fight off the menacing presence of death from his hospital bed.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 12, 'Justice x 2', '43 minutes', '2020-01-16', 'Rob Hardy',
       'Leland tries to get serial killer Orson Leroux''s conviction overturned. Kristen is in court when her daughter,' ||
       ' Laura, needs emergency heart surgery. David is a witness to a woman''s evil nature.');

INSERT INTO Episode(id_tv_show, season, number, name, duration, release_date, director, summary)
VALUES(1, 1, 13, 'Book 27', '42 minutes', '2020-01-30', 'Michael Zinberg',
       'David, Kristen and Ben assess whether a pregnant woman is possessed when she claims one of the twins she''s' ||
       ' carrying is evil. Their investigation leads to a fertility clinic where they discover a connection to all of' ||
       ' their encounters throughout the season. Also, Kristen questions one of her daughters'' capacity for evil upon' ||
       ' realizing that she also used that fertility clinic.');
