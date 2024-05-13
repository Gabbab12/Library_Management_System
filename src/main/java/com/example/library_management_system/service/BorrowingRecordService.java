package com.example.library_management_system.service;

import com.example.library_management_system.entity.BorrowingRecord;
import org.springframework.http.ResponseEntity;

public interface BorrowingRecordService {
    ResponseEntity<BorrowingRecord> borrowBookByPatron(Long bookId);

    ResponseEntity<BorrowingRecord> returnBook(Long borrowingRecordId);
}
