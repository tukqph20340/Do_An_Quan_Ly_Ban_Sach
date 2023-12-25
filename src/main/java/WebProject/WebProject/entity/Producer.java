package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "publisher")
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

    @Column(name = "nation", columnDefinition = "nvarchar(11111)")
    private String nation;


}
