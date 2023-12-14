package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "active_order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ActiveOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "code_active")
    private String codeActive;

    @OneToMany(mappedBy = "activeOrder", cascade = CascadeType.ALL)
    private List<Order> order;
}
