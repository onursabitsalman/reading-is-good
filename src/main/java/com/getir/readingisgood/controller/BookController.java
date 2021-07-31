package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.BookRequest;
import com.getir.readingisgood.model.request.BookStockRequest;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GenericReturnValue<Long>> saveBook(@RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.saveBook(bookRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<GenericReturnValue<Integer>> updateBookStock(@RequestBody BookStockRequest bookStockRequest) {
        return ResponseEntity.ok(bookService.updateBookStock(bookStockRequest));
    }
}
