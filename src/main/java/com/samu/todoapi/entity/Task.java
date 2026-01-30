package com.samu.todoapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY) // Apenas no lado que possui a FK | EAGER por default
    @JoinColumn(name = "owner_id", nullable = false) // Informa coluna para Join
    private User owner;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = Instant.now();
    }
}
