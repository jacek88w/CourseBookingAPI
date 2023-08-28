package pl.jacek.coursebookingresolving.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.jacek.coursebookingresolving.enums.Role;
import pl.jacek.coursebookingresolving.validation.annotations.ValidDateOfBirth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(
        name = "tbl_user",
        uniqueConstraints = @UniqueConstraint(
                name = "email_address_unique",
                columnNames = "email_address"
        )
)
public class User implements UserDetails {

    public User(String firstName, String lastName, String email, List<Course> courses, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.courses = courses;
        this.enabled = enabled;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Length(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Length(min = 3, max = 50)
    private String lastName;

    @NotNull
    @ValidDateOfBirth
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Min(0)
    @Max(150)
    private Integer age;

    @Email
    @Column(name = "email_address")
    @NotNull
    private String email;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private List<Course> courses;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    private String password;

    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
