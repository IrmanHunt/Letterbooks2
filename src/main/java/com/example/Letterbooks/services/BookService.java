package com.example.Letterbooks.services;

import com.example.Letterbooks.models.Book;
import com.example.Letterbooks.models.Image;
import com.example.Letterbooks.models.User;
import com.example.Letterbooks.repositories.BookRepository;
import com.example.Letterbooks.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    public void saveBook(MultipartFile file, Book book, Principal principal) throws IOException {
        book.setAddedBy(getUserByPrincipal(principal).getUsername());
        Image image = null;

        if (!file.isEmpty()) {
            image = toImageEntity(file);
            book.setImage(image);
        }

        log.info("Saving new book: name: {}, author {}", book.getName(), book.getAuthor());
        bookRepository.save(book);
    }

    public User getUserByPrincipal(Principal principal) {
        if(principal == null)
            return new User();
        return userRepository.findByUsername(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setSize(file.getSize());
        image.setContentType(file.getContentType());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
