package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.LibraryUser;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
    LibraryUser findByLogin(String login);
}
