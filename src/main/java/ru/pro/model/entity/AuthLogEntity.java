package ru.pro.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.pro.model.enums.AuthLogAction;
import ru.pro.model.enums.AuthLogStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.NONE;

@Entity
@Table(name = "auth_logs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String login;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthLogAction action;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthLogStatus status;

    @Column
    private String details;

    @CreationTimestamp
    @Column(updatable = false)
    @Setter(NONE)
    private LocalDateTime createdAt;
}
