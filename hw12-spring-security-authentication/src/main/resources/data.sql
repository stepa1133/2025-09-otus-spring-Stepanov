insert into authors(full_name)
values ('Jack London'), ('Steven King'), ('Chuck Palahniuk');

insert into genres(name)
values ('Adventure'), ('Scary'), ('Thriller');

insert into books(title, author_id, genre_id)
values ('Sea wolf', 1, 1), ('IT', 2, 2), ('Fight Club', 3, 3), ('The Green Mile', 2, 2);

insert into comments (book_id, commentary)
values (1, 'Very interesting'), (1, 'About love'),  (1, 'The philosophical struggle'),  (2, 'Scary'), (2, 'Really scary, but interesting'), (3, 'Brad Pitt'), (4, 'I advise you to read at night');

insert into users (login, password, role)
values ('admin', '{bcrypt}$2a$10$9FaL5Tq490tJGxpP/5K4m.6fiTsAubyXTkk00EmXoql7JI3dusiJi', 'ROLE_ADMIN'), ('tests', '{bcrypt}$2a$10$k7VzZH0ja1Xs0RpO9pOiveBrxdv9I82CbY6QIK0x9KIHTDc1eZOgG', 'VIEWER');
