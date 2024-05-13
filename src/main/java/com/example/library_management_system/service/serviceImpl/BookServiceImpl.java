package com.example.library_management_system.service.serviceImpl;

import com.example.library_management_system.dto.BookRequest;
import com.example.library_management_system.dto.UpdateBookRequest;
import com.example.library_management_system.entity.Book;
import com.example.library_management_system.exception.BadRequestException;
import com.example.library_management_system.exception.NotFoundException;
import com.example.library_management_system.repository.BookRepository;
import com.example.library_management_system.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book>  getAllBooks(){
        return bookRepository.findAll();
    }
    @Override
    public Book getBookDetailsById(Long bookId){
        return bookRepository.findById(bookId)
                .orElseThrow(()-> new NotFoundException("Book not found with id: " + bookId, HttpStatus.NOT_FOUND));
    }
    @Override
    public ResponseEntity<String> removeBookFromLibrary(Long bookId){
        if (!bookRepository.existsById(bookId)) throw new  NotFoundException("Book not found with id: " + bookId, HttpStatus.NOT_FOUND);
        bookRepository.deleteById(bookId);

        return ResponseEntity.status(HttpStatus.OK).body("Book successfully removed from the library");
    }
    @Transactional
    @Override
    public ResponseEntity<Book> addBook(BookRequest request){
        if (bookRepository.existsByIsbn(request.getIsbn())) throw new  BadRequestException(
                "A Book is already existing with ISBN number :" + request.getIsbn(),
                HttpStatus.BAD_REQUEST);

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());
        book.setIsbn(request.getIsbn());
        book.setAvailableForBorrowing(true);

        bookRepository.save(book);

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
    @Override
    public ResponseEntity<String> updateBookDetails(Long bookId, UpdateBookRequest request){
        Book book = getBookDetailsById(bookId);

        book.setTitle(request.getAuthor());
        book.setTitle(request.getTitle());

        bookRepository.save(book);

        return ResponseEntity.status(HttpStatus.OK).body("Book details successfully updated");
    }
}
