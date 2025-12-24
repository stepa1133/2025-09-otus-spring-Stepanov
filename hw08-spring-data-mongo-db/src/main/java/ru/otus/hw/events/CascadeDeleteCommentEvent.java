package ru.otus.hw.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Book;
import lombok.AllArgsConstructor;
import ru.otus.hw.repositories.CommentRepository;


@Component
@AllArgsConstructor
public class CascadeDeleteCommentEvent extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        var bookId = event.getSource()
                .getString("_id");
        commentRepository.deleteAllByBookId(bookId);
    }
}
