package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoComment;
import ru.otus.hw.models.mongo.MongoGenre;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "dropDb", author = "stepanov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "createAuthors", author = "stepanov")
    public void createAuthors(MongockTemplate template) {
        template.insert(new MongoAuthor("4","Daria Dontsova"), "authors");  // Явное указание коллекции
    }

    @ChangeSet(order = "003", id = "createGenres", author = "stepanov")
    public void createGenres(MongockTemplate template) {
        template.insert(new MongoGenre("4", "Detective"), "genres");
    }

    @ChangeSet(order = "004", id = "createBooks", author = "stepanov")
    public void createBooks(MongockTemplate template) {
        List<MongoGenre> genres = template.findAll(MongoGenre.class, "genres");
        List<MongoAuthor> authors = template.findAll(MongoAuthor.class, "authors");
        template.insert(new MongoBook("1", "My husband's wife", authors.get(0), genres.get(0)), "books");
    }

    @ChangeSet(order = "005", id = "createBookComments", author = "stepanov")
    public void createBookComments(MongockTemplate template) {
        List<MongoBook> books = template.findAll(MongoBook.class, "books");
        template.insert(new MongoComment("999", books.get(0), "Do not read"), "comments");
    }

}
