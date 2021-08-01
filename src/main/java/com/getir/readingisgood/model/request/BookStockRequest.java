package com.getir.readingisgood.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookStockRequest {

    @NotNull(message = "Id cannot be null!")
    private Long id;

    @NotNull(message = "Stock cannot be null!")
    private Long stock;
}
