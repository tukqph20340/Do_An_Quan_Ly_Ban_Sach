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
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_author", columnDefinition = "nvarchar(1111)")
    private String nameAuthor;

    @Column(name = "address", columnDefinition = "varchar(1111)")
    private String address;

    @Column(name = "description ", columnDefinition = "nvarchar(11111)")
    private String description;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<AuthorImage> authorImage;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Product> products;
}
