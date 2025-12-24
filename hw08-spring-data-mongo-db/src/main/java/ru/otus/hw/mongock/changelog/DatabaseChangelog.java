package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "dropDb", author = "stepanov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "createAuthors", author = "stepanov")
    public void createAuthors(MongockTemplate template) {
        Author jackLondon = new Author("1","Jack London");
        Author stevenKing = new Author("2", "Steven King");
        Author chuckPalahniuk = new Author("3", "Chuck Palahniuk");
        template.insertAll(List.of(jackLondon, stevenKing, chuckPalahniuk));
    }


    @ChangeSet(order = "003", id = "createGenres", author = "stepanov")
    public void createGenres(MongockTemplate template) {
        Genre adventure = new Genre("1", "Adventure");
        Genre scary = new Genre("2", "Scary");
        Genre thriller = new Genre("3", "Thriller");
        template.insertAll(List.of(adventure, scary, thriller));
    }

    @ChangeSet(order = "004", id = "createBooks", author = "stepanov")
    public void createBooks(MongockTemplate template) {

        List<Genre> genres = template.findAll(Genre.class);
        List<Author> authors = template.findAll(Author.class);

        Book seaWolf = new Book("1", "Sea wolf", authors.get(0), genres.get(0));
        Book it = new Book("2", "IT", authors.get(1), genres.get(1));
        Book fightClub = new Book("3", "Fight Club", authors.get(2), genres.get(2));
        Book greenMile = new Book("4","The Green Mile", authors.get(1), genres.get(1));

        template.insertAll(List.of(seaWolf, it, fightClub, greenMile));
    }

    @ChangeSet(order = "005", id = "createBookComments", author = "stepanov")
    public void createBookComments(MongockTemplate template) {
        List<Book> books = template.findAll(Book.class);

        template.insert(new Comment("1", books.get(0), "Very interesting"));
        template.insert(new Comment("2", books.get(0), "About love"));
        template.insert(new Comment("3", books.get(0), "The philosophical struggle"));
        template.insert(new Comment("4", books.get(1), "Scary"));
        template.insert(new Comment("5", books.get(1), "Really scary, but interesting"));
        template.insert(new Comment("6", books.get(2), "Brad Pitt"));
        template.insert(new Comment("7", books.get(3), "Must read at night"));
    }

}
