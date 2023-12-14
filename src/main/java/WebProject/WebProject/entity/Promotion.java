package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "promotion") // Mapping với bảng "promotion"
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data

public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Mapping với cột "id"
    private Integer id;

    @Column(name = "coupon_code") // Mapping với cột "coupon_code"
    private String couponCode;

    @Column(name = "created_at") // Mapping với cột "created_at"
    private Date createdAt;

    @Column(name = "discount_type") // Mapping với cột "discount_type"
    private Integer discountType;

    @Column(name = "discount_value") // Mapping với cột "discount_value"
    private Long discountValue;

    @Column(name = "expired_at") // Mapping với cột "expired_at"
    private Date expiredAt;

    @Column(name = "is_active") // Mapping với cột "is_active"
    private Boolean isActive;

    @Column(name = "is_public") // Mapping với cột "is_public"
    private Boolean isPublic;

    @Column(name = "maximum_discount_value") // Mapping với cột "maximum_discount_value"
    private Long maximumDiscountValue;

    @Column(name = "name") // Mapping với cột "name"
    private String name;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
    private List<Order> order;

    // Constructors, getters, and setters
}
