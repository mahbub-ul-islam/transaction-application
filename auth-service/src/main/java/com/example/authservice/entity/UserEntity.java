package com.example.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(name = "username", unique = true, nullable = false, updatable = false)
    private String username;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", unique = true, nullable = false, updatable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired = true;
    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked = true;
    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired = true;
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;
}
