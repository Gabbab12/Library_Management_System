package com.example.library_management_system.service.serviceImpl;

import com.example.library_management_system.entity.Book;
import com.example.library_management_system.entity.BorrowingRecord;
import com.example.library_management_system.entity.User;
import com.example.library_management_system.exception.BadRequestException;
import com.example.library_management_system.exception.NotFoundException;
import com.example.library_management_system.repository.BookRepository;
import com.example.library_management_system.repository.BorrowingRecordRepository;
import com.example.library_management_system.service.AuthService;
import com.example.library_management_system.service.BookService;
import com.example.library_management_system.service.BorrowingRecordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BorrowingRecordServiceImpl implements BorrowingRecordService {
    private final BorrowingRecordRepository recordRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final AuthServiceImpl authServiceImpl;

    @Transactional
    @Override
    public ResponseEntity<BorrowingRecord> borrowBookByPatron(Long bookId){
        Book book = bookService.getBookDetailsById(bookId);
        User patron = authServiceImpl.getCurrentUser();

        if (!book.isAvailableForBorrowing()) throw new BadRequestException("Book is not available for borrowing", HttpStatus.BAD_REQUEST);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setReturned(false);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setReturnDate(LocalDateTime.now().plusDays(14));

        recordRepository.save(borrowingRecord);

        book.setAvailableForBorrowing(false);
        bookRepository.save(book);

        return ResponseEntity.ok(borrowingRecord);
    }

    @Transactional
    @Override
    public ResponseEntity<BorrowingRecord> returnBook(Long borrowingRecordId) {
        BorrowingRecord borrowingRecord = recordRepository.findById(borrowingRecordId)
                .orElseThrow(() -> new NotFoundException("record not found", HttpStatus.NOT_FOUND));

        borrowingRecord.setReturned(true);
        borrowingRecord.setReturnDate(LocalDateTime.now());

        BorrowingRecord updatedBorrowingRecord = recordRepository.save(borrowingRecord);

        Book book = borrowingRecord.getBook();
        book.setAvailableForBorrowing(true);
        bookRepository.save(book);

        return ResponseEntity.ok(updatedBorrowingRecord);
    }
}
