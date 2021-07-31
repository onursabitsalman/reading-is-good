package com.getir.readingisgood.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_book")
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id")
    @SequenceGenerator(name = "book_id", sequenceName = "book_id_sequence", allocationSize = 100)
    private Long id;

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

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Set<Order> orders;

}
