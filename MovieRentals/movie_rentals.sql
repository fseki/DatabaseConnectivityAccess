/* Creation of the database and tables */

CREATE DATABASE movie_rentals;
USE movie_rentals;

CREATE TABLE user(user_id int UNSIGNED auto_increment NOT NULL, username varchar(20) NOT NULL, 
password varchar(50) NOT NULL, email varchar(30) NOT NULL, fname varchar(20) NOT NULL, 
lname varchar(20) NOT NULL,role varchar (10) NOT NULL, constraint user_pk primary key(user_id));

CREATE TABLE genres(genre_id int UNSIGNED NOT NULL, genre_name varchar(20) NOT NULL, constraint genres_pk primary key(genre_id));

CREATE TABLE director(director_id int unsigned NOT NULL, director_fname varchar(20) NOT NULL, 
director_lname varchar(20) NOT NULL, constraint director_pk primary key(director_id));

CREATE TABLE studio(studio_id int unsigned not null, studio_name varchar(30) not null, studio_city varchar(20), constraint studio_pk primary key(studio_id));

CREATE TABLE actor(actor_id int unsigned not null, actor_fname varchar(20) not null, actor_lname varchar(20) not null, 
actor_dob date, actor_age int, actor_place_of_birth varchar(40), constraint actor_pk primary key(actor_id));

CREATE TABLE movie(movie_id int unsigned not null, movie_title varchar(40) not null, movie_year date not null, 
genre_id int unsigned not null, studio_id int unsigned not null, age_rating int, constraint movie_pk primary key(movie_id), 
constraint movie_genre_id_fk foreign key(genre_id) references genres(genre_id) on delete cascade on update cascade, 
constraint movie_studio_id_fk foreign key(studio_id) references studio(studio_id) on update cascade on delete cascade);

CREATE TABLE director_movie(director_id int unsigned not null, movie_id int unsigned not null, 
constraint director_movie_director_id_fk foreign key(director_id) references director(director_id) on delete cascade on update cascade,
constraint director_movie_movie_id_fk foreign key(movie_id) references movie(movie_id) on update cascade on delete cascade);

CREATE TABLE movies_on_loan(movie_id int unsigned not null, user_id int unsigned not null, date_due date not null, 
returned boolean not null, constraint movies_on_loan_movie_id_fk foreign key(movie_id) references movie(movie_id) on update cascade on delete cascade,
constraint movies_on_loan_user_id_fk foreign key(user_id) references user(user_id) on update cascade on delete cascade);

CREATE TABLE actors_movie(actor_id int unsigned not null, movie_id int unsigned not null, 
constraint actors_movie_actor_id_fk foreign key(actor_id) references actor(actor_id) on update cascade on delete cascade,
constraint actors_movie_movie_id_fk foreign key(movie_id) references movie(movie_id) on update cascade on delete cascade);

/* Data insertion */
INSERT INTO user values(1, "narf666", "test123", "fxs5520@rit.edu", "Fran", "Seki", "administrator");
INSERT INTO user values(1, "micaubica", "test123", "mxp1111@rit.edu", "Marko", "Parac", "administrator");
INSERT INTO user values(1, "starboy", "test123", "fxs5520@rit.edu", "Matija", "Ozetksi", "administrator");
INSERT INTO user values(1, "chenga", "test123", "fxs5520@rit.edu", "Adi", "Cengic", "administrator");
INSERT INTO user values(1, "zivac", "test123", "mxz9982@rit.edu", "Marko", "Zivko", "administrator");

INSERT INTO genres values(1, "Thriller");
INSERT INTO genres values(2, "Horror");
INSERT INTO genres values(3, "Drama");
INSERT INTO genres values(4, "Sci-Fi");
INSERT INTO genres values(5, "Documentary");

INSERT INTO director values(1, "Pierre", "Morel");
INSERT INTO director values(2, "Scott", "Derrickson");
INSERT INTO director values(3, "Steven", "Spielberg");
INSERT INTO director values(4, "Christopher", "Nolan");
INSERT INTO director values(5, "Louie", "Psihoyos");

INSERT INTO studio values(1, "20th Century Fox", "Los Angeles");
INSERT INTO studio values(2, "Summit Entertainment", "Santa Monica");
INSERT INTO studio values(3, "Paramount Pictures", "Hollywood");
INSERT INTO studio values(4, "Warner Bros. Pictures", "Burbank");
INSERT INTO studio values(5, "Lionsgate", "Santa Monica");

INSERT INTO actor values(1, "Liam", "Neeson", "1952-7-6", 64, "Ballymena, N. Ireland");
INSERT INTO actor values(2, "Ethan", "Hawke", "1970-11-6", 46, "Austin, US");
INSERT INTO actor values(3, "Tom", "Hanks", "1956-7-9", 60, "Concord, US");
INSERT INTO actor values(4, "Matthew", "McConaughey", "1969-11-4", 47, "Uvalde, US");
INSERT INTO actor values(5, "Rie", "O'Barry", "1939-10-14", 77, "Florida, US");

INSERT INTO movie values(1, "Taken", "2008-2-27", 1, 1, 18);
INSERT INTO movie values(2, "Sinister", "2012-3-11", 2, 2, 18);
INSERT INTO movie values(3, "Saving Private Ryan", "1998-7-24", 3, 3, 16);
INSERT INTO movie values(4, "Interstellar", "2014-11-6", 4, 4, 12);
INSERT INTO movie values(5, "The Cove", "2009-7-31", 5, 5, 12);

INSERT INTO director_movie values(1, 1);
INSERT INTO director_movie values(2, 2);
INSERT INTO director_movie values(3, 3);
INSERT INTO director_movie values(4, 4);
INSERT INTO director_movie values(5, 5);

INSERT INTO actors_movie values(1, 1);
INSERT INTO actors_movie values(2, 2);
INSERT INTO actors_movie values(3, 3);
INSERT INTO actors_movie values(4, 4);
INSERT INTO actors_movie values(5, 5);

INSERT INTO movies_on_loan values(2, 1, "2017-6-15", false);









