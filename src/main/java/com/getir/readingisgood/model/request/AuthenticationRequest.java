package com.getir.readingisgood.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Username cannot be null or blank!")
    @Size(max = 100, message = "Username is too long!")
    private String username;

    @NotBlank(message = "Username cannot be null or blank!")
    @Size(max = 255, message = "Password is too long!")
    private String password;
}