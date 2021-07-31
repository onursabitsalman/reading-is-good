package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.model.request.BookStockRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Query("UPDATE Book b SET b.stock = :#{#bookStockRequest.stock} WHERE b.id = :#{#bookStockRequest.id}")
    int updateBookStock(BookStockRequest bookStockRequest);

    @Modifying
    @Query("UPDATE Book b SET b.name = :#{#book.name}, b.author = :#{#book.author}, b.page = :#{#book.page}" +
            ", b.stock = :#{#book.stock}, b.price = :#{#book.price} WHERE b.id = :#{#book.id}")
    int updateBook(Book book);
}
