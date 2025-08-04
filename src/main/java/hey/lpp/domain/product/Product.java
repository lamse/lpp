package hey.lpp.domain.product;

import hey.lpp.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    private User user;

    @NotEmpty
    private String name;

    private String ModelNo;

    @NotEmpty
    private String url;

    private Integer price;

    private String description;

    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductImage> productImages;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
