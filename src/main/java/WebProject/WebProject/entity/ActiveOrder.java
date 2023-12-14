package WebProject.WebProject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
