package com.example.library_management_system.service;

import com.example.library_management_system.dto.BookRequest;
import com.example.library_management_system.dto.UpdateBookRequest;
import com.example.library_management_system.entity.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookDetailsById(Long bookId);

    ResponseEntity<String> removeBookFromLibrary(Long bookId);

    ResponseEntity<Book> addBook(BookRequest request);

    ResponseEntity<String> updateBookDetails(Long bookId, UpdateBookRequest request);
}
