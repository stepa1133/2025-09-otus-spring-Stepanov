insert into authors(full_name)
values ('Jack London'), ('Steven King'), ('Chuck Palahniuk');

insert into genres(name)
values ('Adventure'), ('Scary'), ('Thriller');

insert into books(title, author_id, genre_id)
values ('Sea wolf', 1, 1), ('IT', 2, 2), ('Fight Club', 3, 3), ('The Green Mile', 2, 2);

insert into comments (book_id, commentary)
values (1, 'Very interesting'), (1, 'About love'),  (1, 'The philosophical struggle'),
       (2, 'Scary'), (2, 'Really scary, but interesting'),
       (3, 'Brad Pitt'),
       (4, 'I advise you to read at night');

insert into users (login, password, role)
values ('editor', '{bcrypt}$2a$10$gNl97wfGTsMRN1Sv/yII9uJ67WDAdBtuu2BbvyUSN3GBnYubT7Iii', 'ROLE_EDITOR'),
       ('reader', '{bcrypt}$2a$10$gNl97wfGTsMRN1Sv/yII9uJ67WDAdBtuu2BbvyUSN3GBnYubT7Iii', 'ROLE_READER'),
       ('free', '{bcrypt}$2a$10$gNl97wfGTsMRN1Sv/yII9uJ67WDAdBtuu2BbvyUSN3GBnYubT7Iii', 'ROLE_FREEREADER');


INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'editor'),
(2, 0, 'ROLE_READER'),
(3, 0, 'ROLE_FREEREADER');

INSERT INTO acl_class (id, class) VALUES
    (1, 'ru.otus.hw.models.Book'),
    (2, 'ru.otus.hw.models.Author'),
    (3, 'ru.otus.hw.models.Genre'),
    (4, 'ru.otus.hw.models.Comment');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 1, 0),
(2, 1, 2, NULL, 1, 0),
(3, 1, 3, NULL, 1, 0),
(4, 1, 4, NULL, 1, 0),

(5, 2, 1, NULL, 1, 0),
(6, 2, 2, NULL, 1, 0),
(7, 2, 3, NULL, 1, 0),

(8,  3, 1, NULL, 1, 0),
(9,  3, 2, NULL, 1, 0),
(10, 3, 3, NULL, 1, 0),

(11, 4, 1, NULL, 1, 0),
(12, 4, 2, NULL, 1, 0),
(13, 4, 3, NULL, 1, 0),
(14, 4, 4, NULL, 1, 0),
(15, 4, 5, NULL, 1, 0),
(16, 4, 6, NULL, 1, 0),
(17, 4, 7, NULL, 1, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
( 1,  1, 1, 1, 31, 1, 1, 1),
( 2,  2, 1, 1, 31, 1, 1, 1),
( 3,  3, 1, 1, 31, 1, 1, 1),
( 4,  4, 1, 1, 31, 1, 1, 1),

( 5,  5, 1, 1, 31, 1, 1, 1),
( 6,  6, 1, 1, 31, 1, 1, 1),
( 7,  7, 1, 1, 31, 1, 1, 1),

( 8,  8, 1, 1, 31, 1, 1, 1),
( 9,  9, 1, 1, 31, 1, 1, 1),
(10, 10, 1, 1, 31, 1, 1, 1),

(11, 11, 1, 1, 31, 1, 1, 1),
(12, 12, 1, 1, 31, 1, 1, 1),
(13, 13, 1, 1, 31, 1, 1, 1),
(14, 14, 1, 1, 31, 1, 1, 1),
(15, 15, 1, 1, 31, 1, 1, 1),
(16, 16, 1, 1, 31, 1, 1, 1),
(17, 17, 1, 1, 31, 1, 1, 1),

                        -- права reader на чтение книг
(18,  1, 2, 2, 1, 1, 1, 1),
(19,  2, 2, 2, 1, 1, 1, 1),
(20,  3, 2, 2, 1, 1, 1, 1),
(21,  4, 2, 2, 1, 1, 1, 1),

                        -- права FREEREADER на чтение книг запрещаем ( кроме одной)
(22,  1, 3, 3, 1, 1, 1, 1),
(23,  2, 3, 3, 1, 0, 1, 1),
(24,  3, 3, 3, 1, 0, 1, 1),
(25,  4, 3, 3, 1, 0, 1, 1),

                        -- права reader на чтение и создание комментариев
(26,  11, 2, 2, 5, 1, 1, 1),
(27,  12, 2, 2, 5, 1, 1, 1),
(28,  13, 2, 2, 5, 1, 1, 1),
(29,  14, 2, 2, 5, 1, 1, 1),
(30,  15, 2, 2, 5, 1, 1, 1),
(31,  16, 2, 2, 5, 1, 1, 1),
(32,  17, 2, 2, 5, 1, 1, 1),

                        -- права reader на чтение авторов
(33,  5, 2, 2, 1, 1, 1, 1),
(34,  6, 2, 2, 1, 1, 1, 1),
(35,  7, 2, 2, 1, 1, 1, 1),

                        -- права reader на чтение жанров
(36,  8, 2, 2, 1, 1, 1, 1),
(37,  9, 2, 2, 1, 1, 1, 1),
(38, 10, 2, 2, 1, 1, 1, 1);


