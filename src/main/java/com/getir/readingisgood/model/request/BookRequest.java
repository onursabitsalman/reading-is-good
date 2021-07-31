package com.getir.readingisgood.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Name cannot be null or blank!")
    @Size(max = 255, message = "Name is too long!")
    private String name;

    @NotBlank(message = "Author cannot be null or blank!")
    @Size(max = 255, message = "Author is too long!")
    private String author;

    @NotNull(message = "Page cannot be null!")
    @Min(value = 1, message = "Page should be greater than 0!")
    private Long page;

    @NotNull(message = "Stock cannot be null!")
    @Min(value = 0, message = "Stock is not valid!")
    private Long stock;

    @NotNull(message = "Price cannot be null!")
    @Min(value = 0, message = "Price is not valid!")
    private Double price;
}
