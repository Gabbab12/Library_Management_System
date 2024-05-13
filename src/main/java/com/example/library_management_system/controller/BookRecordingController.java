package com.example.library_management_system.controller;

import com.example.library_management_system.entity.BorrowingRecord;
import com.example.library_management_system.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record")
public class BookRecordingController {
    private final BorrowingRecordService recordService;

    @PostMapping("/borrow-book/{bookId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId){
        return recordService.borrowBookByPatron(bookId);
    }

    @PutMapping("/return-borrowed-book/{borrowingRecordId}")
    public ResponseEntity<BorrowingRecord> returnBorrowedBook(@PathVariable Long borrowingRecordId){
        return recordService.returnBook(borrowingRecordId);
    }
}
