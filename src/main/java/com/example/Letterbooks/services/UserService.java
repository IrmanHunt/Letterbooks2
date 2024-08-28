package com.example.Letterbooks.services;

import com.example.Letterbooks.models.Book;
import com.example.Letterbooks.models.User;
import com.example.Letterbooks.models.UserBook;
import com.example.Letterbooks.repositories.UserBookRepository;
import com.example.Letterbooks.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserBookRepository userBookRepository;

    public void createUser(User user) {
        if(userRepository.findByUsername(user.getUsername()) != null) {
            return;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("Saving new User with name: {}", user.getUsername());
    }

    @Transactional
    public User getUserByPrincipal(Principal principal) {
        if(principal == null)
            return null;
        return userRepository.findByUsername(principal.getName());
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void readBook(UserBook userBook) {
        userBookRepository.save(userBook);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

//    public void readBook(UserBook userBook) {
//        userBookRepository.save(userBook);
//        log.info("Saving new UserBook with id: {}", userBook.getDateRead());
//    }
}

