package com.example.Letterbooks.repositories;

import com.example.Letterbooks.models.Book;
import com.example.Letterbooks.models.User;
import com.example.Letterbooks.models.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    UserBook findByUserAndBook(User user, Book book);
}
