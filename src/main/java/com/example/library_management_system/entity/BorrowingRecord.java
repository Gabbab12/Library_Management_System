package com.example.library_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord extends AuditBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime returnDate;
    private boolean isReturned;
    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;
    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "patron_id", referencedColumnName = "id", nullable = false)
    private User patron;
}
