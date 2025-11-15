package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllBookComments(long bookId) {
        Query query = em.createQuery("select c from Comment c where c.book.id = :bookId");
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            return insert(comment);
        }
        return update(comment);
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteAllCommentsByBookId(long bookId) {
        Query query = em.createQuery("delete from Comment c where c.book.id = :bookId");
        query.setParameter("bookId", bookId);
        query.executeUpdate();
    }


    private Comment insert(Comment comment) {
        em.persist(comment);
        return comment;
    }

    private Comment update(Comment comment) {
        return em.merge(comment);
    }
}
