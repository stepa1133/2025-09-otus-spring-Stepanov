-- 1. Авторы
insert into authors(full_name)
values ('Jack London'), ('Steven King'), ('Chuck Palahniuk');

-- 2. Жанры
insert into genres(name)
values ('Adventure'), ('Scary'), ('Thriller');

-- 3. Книги
-- Jack London (id=1)
insert into books(title, author_id, genre_id) values
                                                  ('Sea Wolf', 1, 1),
                                                  ('White Fang', 1, 1),
                                                  ('Martin Eden', 1, 3),
                                                  ('Burning Daylight', 1, 1),
                                                  ('The Iron Heel', 1, 3),
                                                  ('Before Adam', 1, 1),
                                                  ('The Cruise of the Dazzler', 1, 1),
                                                  ('Moon-Face', 1, 3),
                                                  ('South Sea Tales', 1, 1),
                                                  ('Lost Face', 1, 3);

-- Steven King (id=2)
insert into books(title, author_id, genre_id) values
                                                  ('IT', 2, 2),
                                                  ('The Green Mile', 2, 2),
                                                  ('Doctor Sleep', 2, 2),
                                                  ('Carrie', 2, 2),
                                                  ('Pet Sematary', 2, 2),
                                                  ('Salems Lot', 2, 2),
                                                  ('The Stand', 2, 3),
                                                  ('The Mist', 2, 3),
                                                  ('Under the Dome', 2, 1),
                                                  ('The Dark Tower I', 2, 1),
                                                  ('The Dark Tower II', 2, 3),
                                                  ('Christine', 2, 2),
                                                  ('Cujo', 2, 2),
                                                  ('The Outsider', 2, 3),
                                                  ('Needful Things', 2, 3);

-- Chuck Palahniuk (id=3)
insert into books(title, author_id, genre_id) values
                                                  ('Fight Club', 3, 3),
                                                  ('Invisible Monsters', 3, 3),
                                                  ('Choke', 3, 3),
                                                  ('Diary', 3, 3),
                                                  ('Lullaby', 3, 2),
                                                  ('Haunted', 3, 3),
                                                  ('Survivor', 3, 3),
                                                  ('Rant', 3, 3),
                                                  ('Snuff', 3, 3),
                                                  ('Damned', 3, 3),
                                                  ('Pygmy', 3, 3),
                                                  ('Adjustment Day', 3, 3),
                                                  ('Beautiful You', 3, 3),
                                                  ('Tell-All', 3, 3),
                                                  ('Make Something Up', 3, 3);

-- 4. Комментарии
-- 4. Комментарии (только для существующих book_id 1-40)
insert into comments (book_id, commentary) values
                                               (1,'Very interesting'),(1,'About love'),
                                               (2,'About adventure'),(2,'Great survival story'),
                                               (3,'Inspirational'),(3,'Philosophical struggle'),
                                               (4,'Action packed'),(4,'Love the plot'),
                                               (5,'Thrilling'),(5,'Deep ideas'),
                                               (6,'Classic'),(6,'Well written'),
                                               (7,'Exciting'),(7,'Captivating'),
                                               (8,'Short but powerful'),(8,'Engaging'),
                                               (9,'Atmospheric'),(9,'Adventurous'),
                                               (10,'Compact story'),(10,'Challenging'),
                                               (11,'Scary'),(11,'Gripping'),
                                               (12,'Emotional'),(12,'Suspenseful'),
                                               (13,'Dark'),(13,'Haunting'),
                                               (14,'Intense'),(14,'Unforgettable'),
                                               (15,'Creepy'),(15,'Chilling'),
                                               (16,'Epic'),(16,'Great characters'),
                                               (17,'Tense'),(17,'Well structured'),
                                               (18,'Captivating'),(18,'Action scenes'),
                                               (19,'Suspenseful'),(19,'Psychologically deep'),
                                               (20,'Thrilling'),(20,'Engaging'),
                                               (21,'Scary'),(21,'Memorable'),
                                               (22,'Terrifying'),(22,'Clever plot'),
                                               (23,'Intense'),(23,'Well paced'),
                                               (24,'Exciting'),(24,'Action packed'),
                                               (25,'Dark and deep'),(25,'Amazing'),
                                               (26,'Very interesting'),(26,'Dark humor'),
                                               (27,'Thrilling'),(27,'Thought provoking'),
                                               (28,'Interesting'),(28,'Black comedy'),
                                               (29,'Unique'),(29,'Suspenseful'),
                                               (30,'Dark'),(30,'Psychological'),
                                               (31,'Shocking'),(31,'Unusual'),
                                               (32,'Intense'),(32,'Clever'),
                                               (33,'Twisted'),(33,'Funny'),
                                               (34,'Weird'),(34,'Provocative'),
                                               (35,'Gripping'),(35,'Mind-blowing'),
                                               (36,'Thoughtful'),(36,'Dark satire'),
                                               (37,'Interesting'),(37,'Entertaining'),
                                               (38,'Fun'),(38,'Bizarre'),
                                               (39,'Clever'),(39,'Unexpected'),
                                               (40,'Dark'),(40,'Funny');