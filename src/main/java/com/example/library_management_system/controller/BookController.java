package com.example.library_management_system.controller;

import com.example.library_management_system.dto.BookRequest;
import com.example.library_management_system.dto.UpdateBookRequest;
import com.example.library_management_system.entity.Book;
import com.example.library_management_system.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @PostMapping("/add-book")
    public ResponseEntity<Book> addBook(@RequestBody BookRequest request){
        return bookService.addBook(request);
    }

    @GetMapping("/get-all-books")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/get-book-detail/{bookId}")
    public Book getBookById(@PathVariable Long bookId){
        return bookService.getBookDetailsById(bookId);
    }

    @DeleteMapping("/remove-book")
    public ResponseEntity<String> removeBook(@RequestParam Long bookId){
        return bookService.removeBookFromLibrary(bookId);
    }

    @PutMapping("/update-book-details/{bookId}")
    public ResponseEntity<String> updateBook(@PathVariable Long bookId, @RequestBody UpdateBookRequest request){
        return bookService.updateBookDetails(bookId, request);
    }
}
