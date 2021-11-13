package pl.napierala.cinemacodechallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    public static final String REGULAR_USER_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @NotEmpty
    @Size(min = 1)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_and_roles", joinColumns = @JoinColumn(name = "users_id"),
            foreignKey = @ForeignKey(name = "users_and_roles_users_id_fkey"))
    private Set<String> roles;

    @PrePersist
    private void onCreate() {
        createTime = LocalDateTime.now();
    }
}