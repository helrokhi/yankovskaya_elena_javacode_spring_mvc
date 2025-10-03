package ru.pro.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.pro.model.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.NONE;
import static ru.pro.model.enums.UserRole.USER;

@Entity
@Table(name = "user_access")
@Getter
@Setter
public class UserAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String login;

    @Enumerated(EnumType.STRING)
    private UserRole role = USER;

    @Column(nullable = false)
    private boolean isAccountNonLocked = true;

    @CreationTimestamp
    @Column(updatable = false)
    @Setter(NONE)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Setter(NONE)
    private LocalDateTime updatedAt;
}
