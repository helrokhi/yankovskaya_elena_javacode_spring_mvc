package ru.pro.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.NONE;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private UserAccess userAccess;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String contactNumber;

    @CreationTimestamp
    @Column(updatable = false)
    @Setter(NONE)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Setter(NONE)
    private LocalDateTime updatedAt;
}
