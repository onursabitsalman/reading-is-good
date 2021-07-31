package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.exceptions.ResourceNotFoundException;
import com.getir.readingisgood.model.modelmapper.BookMapper;
import com.getir.readingisgood.model.request.BookRequest;
import com.getir.readingisgood.model.request.BookStockRequest;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.utils.ErrorMessages;
import com.getir.readingisgood.utils.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ValidateUtil validateUtil;

    @Transactional
    public GenericReturnValue<Long> saveBook(BookRequest bookRequest) {
        validateUtil.validate(bookRequest);
        Book book = bookMapper.toEntity(bookRequest);
        log.info("Added book: {}", bookRequest.toString());
        return new GenericReturnValue<>(bookRepository.save(book).getId());
    }

    @Transactional
    public GenericReturnValue<Integer> updateBookStock(BookStockRequest bookStockRequest) {
        validateUtil.validate(bookStockRequest);

        if (!bookRepository.existsById(bookStockRequest.getId())) {
            throw new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND);
        }
        log.info("Updated book id: {}, new stock: {}", bookStockRequest.getId(), bookStockRequest.getStock());
        return new GenericReturnValue<>(bookRepository.updateBookStock(bookStockRequest));
    }

}
