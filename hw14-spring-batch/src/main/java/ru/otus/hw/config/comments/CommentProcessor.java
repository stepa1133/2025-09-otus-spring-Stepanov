package ru.otus.hw.config.comments;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.books.BookProcessor;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.mongo.MongoComment;
@Component
public class CommentProcessor implements ItemProcessor<Comment, MongoComment> {
    @Override
    public MongoComment process(Comment item) throws Exception {
        BookProcessor bookProcessor = new BookProcessor();
        return new MongoComment(String.valueOf(item.getId()),
                    bookProcessor.process(item.getBook()), item.getCommentary());
    }
}
