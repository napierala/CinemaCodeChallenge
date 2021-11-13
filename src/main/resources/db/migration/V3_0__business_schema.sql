CREATE TABLE cinema (
  id IDENTITY NOT NULL,
  code VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL,
  CONSTRAINT cinema_primary_key PRIMARY KEY (id),
  CONSTRAINT cinema_code_unique_key UNIQUE KEY (code)
);

CREATE TABLE movie (
  id IDENTITY NOT NULL,
  code VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  imdb_id VARCHAR(255) NOT NULL,
  CONSTRAINT movie_primary_key PRIMARY KEY (id),
  CONSTRAINT movie_unique_key UNIQUE KEY (code)
);

CREATE TABLE movie_rating (
  id IDENTITY NOT NULL,
  movie_id BIGINT,
  user_id BIGINT,
  rating INT NOT NULL,
  CONSTRAINT movie_rating_primary_key PRIMARY KEY (id),
  CONSTRAINT movie_rating_movie_id_fkey FOREIGN KEY (movie_id) references movie (id),
  CONSTRAINT movie_rating_user_id_fkey FOREIGN KEY (user_id) references users (id)
);

CREATE TABLE movie_showing (
  id IDENTITY NOT NULL,
  cinema_id BIGINT,
  movie_id BIGINT,
  date_time TIMESTAMP NOT NULL,
  room VARCHAR(255) NOT NULL,
  CONSTRAINT movie_showing_primary_key PRIMARY KEY (id),
  CONSTRAINT movie_showing_cinema_id_fkey FOREIGN KEY (cinema_id) references cinema (id),
  CONSTRAINT movie_showing_movie_id_fkey FOREIGN KEY (movie_id) references movie (id)
);

CREATE TABLE ticket_price (
  id IDENTITY NOT NULL,
  cinema_id BIGINT,
  description VARCHAR(255) NOT NULL,
  price_in_cents INT NOT NULL,
  CONSTRAINT ticket_price_primary_key PRIMARY KEY (id),
  CONSTRAINT ticket_price_cinema_id_fkey FOREIGN KEY (cinema_id) references cinema (id)
);