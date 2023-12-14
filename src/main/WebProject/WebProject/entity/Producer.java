package WebProject.WebProject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.security.SecureRandom;
import java.util.List;

@Entity
@Table(name = "producer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_producer", columnDefinition = "nvarchar(1111)")
    private String nameProducer;

    @Column(name = "phone", columnDefinition = "nvarchar(10)")
    private String phone;

    @Column(name = "email",columnDefinition = "nvarchar(1111)")
    private String email;

    @Column(name = "address", columnDefinition = "nvarchar(1111)")
    private String address;

    @Column(name = "description", columnDefinition = "nvarchar(11111)")
    private String description;

    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL)
    private List<Product> products;

}
