package com.samu.todoapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Status status;

    @ManyToOne // Apenas no lado que possui a FK | EAGER por default
    @JoinColumn(name = "owner_id") // Informa coluna para Join
    private User owner;

    @Column(nullable = false)
    private Instant createdAt;
}
