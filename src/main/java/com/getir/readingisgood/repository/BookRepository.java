package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.model.request.BookStockRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Query("UPDATE Book b SET b.stock = :#{#bookStockRequest.stock} WHERE b.id = :#{#bookStockRequest.id}")
    int updateBookStock(BookStockRequest bookStockRequest);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Book b SET b.stock = b.stock - :quantity WHERE b.id = :id")
    int decreaseBookStock(@Param("id") Long id, @Param("quantity") Long quantity);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Book b SET b.stock = b.stock + :quantity WHERE b.id = :id")
    int increaseBookStock(@Param("id") Long id, @Param("quantity") Long quantity);
}
