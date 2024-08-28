package com.example.Letterbooks.repositories;

import com.example.Letterbooks.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
