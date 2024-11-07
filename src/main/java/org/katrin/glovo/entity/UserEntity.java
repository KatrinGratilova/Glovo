package org.katrin.glovo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int id;

    @NotNull
    @Size(min = 3, message = "Name must can't be shorter than 3 characters.")
    @Pattern(regexp = "^[a-zA-Z\\-]*$", message = "Name can contain only english letters and hyphens.")
    @Column(nullable = false, columnDefinition = "VARCHAR CHECK (name ~ '^[a-zA-Z\\-]*$')")
    private String name;

    @NotNull
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,20}", message = "Password must contain at least 1 uppercase and 1 lowercase letter, 1 symbol, and 1 digit.")
    @Column(nullable = false, length = 100, columnDefinition = "VARCHAR CHECK (password ~ '^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,20}$')")
    private String password;

    @NotNull
    @Pattern(regexp = "^\\+\\d{1,3}\\d{10}$", message = "Phone number must be in the format +<country code><10-digit number>.")
    @Column(name = "phone_number", nullable = false, unique = true, columnDefinition = "VARCHAR CHECK (phone_number ~ '^\\+\\d{1,3}\\d{10}$')")
    private String phoneNumber;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "client")
    private List<OrderEntity> orders = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

//@NotNull
//@Email(message = "Invalid email format.")
//@Column(nullable = false, unique = true)
//private String email;
