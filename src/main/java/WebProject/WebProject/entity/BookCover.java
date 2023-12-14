package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book_cover")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class BookCover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cover_type", columnDefinition = "nvarchar(1111)")
    private String coverType;

    @Column(name = "desciption", columnDefinition = "nvarchar(11111)")
    private String description;

    @OneToMany(mappedBy = "bookCover", cascade = CascadeType.ALL)
    private List<Product> products;

}
