package WebProject.WebProject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Id;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EnableJpaRepositories
public class OrderDto {
    @Id
    private String tinhTrang;
    private Date ngay;
    private int tien;

}
