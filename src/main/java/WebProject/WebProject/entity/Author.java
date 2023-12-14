package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;
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
