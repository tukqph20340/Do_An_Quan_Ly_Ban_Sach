package WebProject.WebProject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

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
