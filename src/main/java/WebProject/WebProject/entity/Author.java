package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
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

    @Column(name = "date_of_birth", columnDefinition = "varchar(1111)")
    private Date date_of_birth;

    @Column(name = "nation", columnDefinition = "varchar(1111)")
    private String nation;

    @Column(name = "description ", columnDefinition = "nvarchar(11111)")
    private String description;

}
