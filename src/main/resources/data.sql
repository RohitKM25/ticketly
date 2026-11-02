INSERT INTO shows (id, movie_title, total_seats) 
VALUES (1, 'Inception', 50) 
ON CONFLICT (id) DO NOTHING;

INSERT INTO shows (id, movie_title, total_seats) 
VALUES (2, 'Interstellar', 60) 
ON CONFLICT (id) DO NOTHING;
