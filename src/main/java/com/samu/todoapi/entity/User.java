package com.samu.todoapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
//Entity deve ter construtor sem argumentos public/protected
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING) // Persiste String não números
    @NotBlank
    private Role roles;

    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "owner") // LAZY por default
    private List<Task> tasks;

    @PrePersist
    private void prePersist() {
        this.createdAt = Instant.now();
        this.enabled = true;
    }
}
