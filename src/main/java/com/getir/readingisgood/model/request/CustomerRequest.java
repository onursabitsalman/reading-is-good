package com.getir.readingisgood.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getir.readingisgood.entity.enums.RoleType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    @NotBlank(message = "Username cannot be null or blank!")
    @Size(max = 100, message = "Username is too long!")
    private String username;

    @NotBlank(message = "Password cannot be null or blank!")
    @Size(max = 255, message = "Password is too long!")
    private String password;

    @NotBlank(message = "Name cannot be null or blank!")
    @Size(max = 255, message = "Name is too long!")
    private String name;

    @NotBlank(message = "Surname cannot be null or blank!")
    @Size(max = 255, message = "Surname is too long!")
    private String surname;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.CUSTOMER;
}
