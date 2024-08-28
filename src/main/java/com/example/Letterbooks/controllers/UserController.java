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

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserBookService userBookService;
    private final BookService bookService;

    @GetMapping("/")
    public String mainPage(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "index";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    @GetMapping("/users/{username}")
    public String userPage(@PathVariable String username, Model model) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "user-page";
    }

    @GetMapping("/users/{username}/books")
    public String userBooks(@PathVariable String username, Model model) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("books", user.getUserBooks());
        return "books";
    }

    @PostMapping("/users/{username}/books/{bookTitle}/read")
    public String readBook(@RequestParam("userId") Long userId,
                           @RequestParam("bookId") Long bookId,
                           Model model) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);
        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userService.readBook(userBook);
        return "books";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user) {
        userService.createUser(user);
        return "redirect:/login";
    }

}