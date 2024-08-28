package com.example.Letterbooks.controllers;

import com.example.Letterbooks.models.Book;
import com.example.Letterbooks.models.User;
import com.example.Letterbooks.models.UserBook;
import com.example.Letterbooks.services.BookService;
import com.example.Letterbooks.services.UserBookService;
import com.example.Letterbooks.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final UserService userService;
    private final UserBookService userBookService;

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.listBooks());
        return "books";
    }

    @GetMapping("/books/{id}")
    public String bookPage(@PathVariable Long id, Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        Book book = bookService.getBookById(id);
        UserBook userBook;
        if(principal != null) {
            userBook = userBookService.getUserBookByUserAndBook(user, book);
        } else {
            userBook = null;
        }
        model.addAttribute("user", user);
        model.addAttribute("book", book);
        model.addAttribute("image", book.getImage());
        model.addAttribute("userBook", userBook);
        return "book-page";
    }

    @PostMapping("/books/create")
    public String createBook(@RequestParam("file") MultipartFile file, Book book, Principal principal) throws IOException {
        bookService.saveBook(file, book, principal);
        return "redirect:/books";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

}
