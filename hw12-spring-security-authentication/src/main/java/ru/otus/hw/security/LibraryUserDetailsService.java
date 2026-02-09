package ru.otus.hw.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.LibraryUser;
import ru.otus.hw.repositories.LibraryUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryUserDetailsService implements UserDetailsService {

    private final LibraryUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LibraryUser libraryUser = repository.findByLogin(username);
        if (libraryUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(libraryUser.getLogin(),
                        libraryUser.getPassword(), List.of(new SimpleGrantedAuthority(libraryUser.getRole())));

    }
}
