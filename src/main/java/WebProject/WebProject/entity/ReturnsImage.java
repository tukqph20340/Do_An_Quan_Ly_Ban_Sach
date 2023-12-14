package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "returns_image")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReturnsImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "url_image", columnDefinition = "nvarchar(1111)")
    private String urlImage;


    @ManyToOne
    @JoinColumn(name = "returns_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Returns returns;
}
