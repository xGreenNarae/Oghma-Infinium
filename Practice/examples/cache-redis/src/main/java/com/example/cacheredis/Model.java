package com.example.cacheredis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Where(clause = "removed_at is NULL")
@SQLDelete(sql = "UPDATE model SET removed_at = NOW() WHERE id=?")
@NoArgsConstructor
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant removedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Instant.now();
    }

    @Builder
    public Model(String name) {
        this.name = name;
    }
}
