package WebProject.WebProject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

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
