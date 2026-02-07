package com.samu.todoapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Builder
@Entity
//Entity deve ter construtor sem argumentos public/protected
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id // Define PK
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Define geração da PK
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @NotNull
    private boolean enabled;

    @Setter(AccessLevel.NONE) // Avoid setter for role
    @Enumerated(EnumType.STRING) // Persiste String não números
    @Column(nullable = false)
    private Authority authority = Authority.USER;

    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "owner") // LAZY por default
    private List<Task> tasks;

    @PrePersist
    private void prePersist() {
        this.createdAt = Instant.now();
        this.enabled = true;
        this.authority = Authority.USER;
    }
}
