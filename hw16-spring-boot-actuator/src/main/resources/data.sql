insert into authors(full_name)
values ('Jack London'), ('Steven King'), ('Chuck Palahniuk');

insert into genres(name)
values ('Adventure'), ('Scary'), ('Thriller');

insert into books(title, author_id, genre_id)
values ('Sea wolf', 1, 1), ('IT', 2, 2), ('Fight Club', 3, 3), ('The Green Mile', 2, 2);

insert into comments (book_id, commentary)
values (1, 'Very interesting'), (1, 'About love'),  (1, 'The philosophical struggle'),  (2, 'Scary'), (2, 'Really scary, but interesting'), (3, 'Brad Pitt'), (4, 'I advise you to read at night');
