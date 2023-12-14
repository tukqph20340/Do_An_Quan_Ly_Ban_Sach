package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "`returns`")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Returns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "reason", columnDefinition = "nvarchar(1111)")
    private String reason;

    @Column(name = "returns_date")
    private Date returnsDate;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;

    @OneToMany(mappedBy = "returns")
    private List<ReturnsImage> returnsImages ;
}
