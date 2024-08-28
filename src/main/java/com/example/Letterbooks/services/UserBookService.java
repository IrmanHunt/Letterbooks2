package com.example.Letterbooks.services;

import com.example.Letterbooks.models.Book;
import com.example.Letterbooks.models.User;
import com.example.Letterbooks.models.UserBook;
import com.example.Letterbooks.repositories.UserBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBookService {
    private final UserBookRepository userBookRepository;

    public UserBook getUserBookByUserAndBook(User user, Book book) {
        return userBookRepository.findByUserAndBook(user, book);
    }
}
