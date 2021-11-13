INSERT INTO cinema (code, name, address) values ('FIRST', 'F&F Cinema', '123 Main Street, New York, NY 10030');

INSERT INTO ticket_price (cinema_id, description, price_in_cents) values ((select id from cinema where code = 'FIRST'), 'Normal price.', 1000);
INSERT INTO ticket_price (cinema_id, description, price_in_cents) values ((select id from cinema where code = 'FIRST'), 'Children age < 15.', 500);

INSERT INTO movie (code, name, imdb_id) values ('F&F1', 'The Fast and the Furious', 'tt0232500');
INSERT INTO movie (code, name, imdb_id) values ('F&F2', '2 Fast 2 Furious', 'tt0322259');
INSERT INTO movie (code, name, imdb_id) values ('F&F3', 'The Fast and the Furious: Tokyo Drift', 'tt0463985');
INSERT INTO movie (code, name, imdb_id) values ('F&F4', 'Fast & Furious', 'tt1013752');
INSERT INTO movie (code, name, imdb_id) values ('F&F5', 'Fast Five', 'tt1596343');
INSERT INTO movie (code, name, imdb_id) values ('F&F6', 'Fast & Furious 6', 'tt1905041');
INSERT INTO movie (code, name, imdb_id) values ('F&F7', 'Furious 7', 'tt2820852');
INSERT INTO movie (code, name, imdb_id) values ('F&F8', 'The Fate of the Furious', 'tt4630562');

INSERT INTO movie_showing (cinema_id, movie_id, uuid, date_time, room) values ((select id from cinema where code = 'FIRST'), (select id from movie where code = 'F&F1'), 'bf528757-e408-4fb9-be9b-82bef4cf50e3', '2021-12-01 12:30:00.00', '1A');
INSERT INTO movie_showing (cinema_id, movie_id, uuid, date_time, room) values ((select id from cinema where code = 'FIRST'), (select id from movie where code = 'F&F1'), '7ad19792-9ea3-4f38-9656-016ebe8f00c6', '2021-12-02 12:30:00.00', '2A');