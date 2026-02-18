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
values ('librarian', '{bcrypt}$2a$10$gNl97wfGTsMRN1Sv/yII9uJ67WDAdBtuu2BbvyUSN3GBnYubT7Iii', 'LIBRARIAN'),
       ('reader', '{bcrypt}$2a$10$gNl97wfGTsMRN1Sv/yII9uJ67WDAdBtuu2BbvyUSN3GBnYubT7Iii', 'READER'),
       ('admin', '{bcrypt}$2a$10$gNl97wfGTsMRN1Sv/yII9uJ67WDAdBtuu2BbvyUSN3GBnYubT7Iii', 'ADMIN');


INSERT INTO system_message(id, content) VALUES
(1,'First Level Message'),
(2,'Second Level Message'),
(3,'Third Level Message');


INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'ADMIN'),
(2, 1, 'LIBRARIAN'),
(3, 1, 'READER'),
(4, 0, 'ROLE_EDITOR');

INSERT INTO acl_class (id, class) VALUES
    (1, 'ru.otus.hw.models.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 3, 0),
(2, 1, 2, NULL, 3, 0),
(3, 1, 3, NULL, 3, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),
(3, 1, 3, 3, 1, 1, 1, 1),
(4, 2, 1, 2, 1, 1, 1, 1),
(5, 2, 2, 3, 1, 1, 1, 1),
(6, 3, 1, 3, 1, 1, 1, 1),
(7, 3, 2, 3, 2, 1, 1, 1);