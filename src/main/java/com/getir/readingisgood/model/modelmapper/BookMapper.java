package com.getir.readingisgood.model.modelmapper;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.model.request.BookRequest;
import com.getir.readingisgood.model.response.BookResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookRequest bookRequest);
    BookResponse toBookResponse(Book book);
}
