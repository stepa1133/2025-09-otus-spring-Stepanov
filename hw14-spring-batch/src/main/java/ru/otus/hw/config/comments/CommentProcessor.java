package ru.otus.hw.config.comments;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.books.BookProcessor;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.mongo.MongoComment;
import org.springframework.beans.factory.annotation.Value;

import static ru.otus.hw.config.JobConfig.COMMENTS_POSTFIX_NAME;

@StepScope
@Component
public class CommentProcessor implements ItemProcessor<Comment, MongoComment> {

    private final String commentPostFixToAdd;

    public CommentProcessor(@Value("#{jobParameters['" + COMMENTS_POSTFIX_NAME + "']}") String commentPostFixToAdd) {
        this.commentPostFixToAdd = commentPostFixToAdd;
    }

    @Override
    public MongoComment process(Comment item) throws Exception {
        BookProcessor bookProcessor = new BookProcessor();
        return new MongoComment(String.valueOf(item.getId()),
                    bookProcessor.process(item.getBook()), item.getCommentary().concat(commentPostFixToAdd));
    }
}
