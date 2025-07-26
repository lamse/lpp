package hey.lpp.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public User() {
    }

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @CreationTimestamp
    // @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    // @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}