package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_cancellation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderCancellation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "reason", columnDefinition = "nvarchar(1111)")
    private String reason;

    @Column(name = "cancellation_date")
    private Date cancellationDate;

    @Column(name = "active_cancel")
    private Boolean activeCancel;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;




}

