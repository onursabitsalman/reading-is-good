package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.exceptions.CustomException;
import com.getir.readingisgood.exceptions.ResourceNotFoundException;
import com.getir.readingisgood.model.modelmapper.BookMapper;
import com.getir.readingisgood.model.request.BookRequest;
import com.getir.readingisgood.model.request.BookStockRequest;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.utils.ResponseMessages;
import com.getir.readingisgood.utils.ValidateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private ValidateUtil validateUtil;

    @Test
    public void save_successfully() {
        BookRequest bookRequest = BookRequest.builder()
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(100L)
                .build();

        Book book = Book.builder()
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(100L)
                .build();
        when(bookRepository.save(any())).thenReturn(book);
        GenericReturnValue<Long> result = bookService.saveBook(bookRequest);
        assertEquals(new GenericReturnValue<>(book.getId()).getValue(), result.getValue());
    }

    @Test
    public void updateBookStock_successfully() {
        BookStockRequest bookStockRequest = new BookStockRequest(1L, 10L);

        Book book = Book.builder()
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(100L)
                .build();

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.increaseBookStock(anyLong(), anyLong())).thenReturn(1);

        GenericReturnValue<String> result = bookService.updateBookStock(bookStockRequest);

        assertEquals(new GenericReturnValue<>(ResponseMessages.BOOK_STOCK_UPDATED).getValue(), result.getValue());
    }

    @Test
    public void updateBookStock_withDecreaseStockBiggerThanAvailableStock_expectedCustomException() {
        BookStockRequest bookStockRequest = new BookStockRequest(1L, -10L);

        Book book = Book.builder()
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(5L)
                .build();

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        assertThrows(CustomException.class, () -> bookService.updateBookStock(bookStockRequest));
    }

    @Test
    public void updateBookStock_withCouldNotFindBook_expectedCustomException() {
        BookStockRequest bookStockRequest = new BookStockRequest(1L, -10L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBookStock(bookStockRequest));
    }


}