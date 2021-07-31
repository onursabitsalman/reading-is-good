package com.getir.readingisgood.entity;

import com.getir.readingisgood.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
    @SequenceGenerator(name = "user_id", sequenceName = "user_id_sequence", allocationSize = 100)
    private Long id;

    @Column(unique = true)
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

    @Column(unique = true)
    @NotNull(message = "Email cannot be null!")
    @Email(message = "Email should be valid!")
    private String email;

    @NotNull(message = "Role cannot be null!")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Order> orders;

}
