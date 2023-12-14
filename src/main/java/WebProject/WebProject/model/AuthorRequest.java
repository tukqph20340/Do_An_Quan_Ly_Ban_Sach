package WebProject.WebProject.model;


import WebProject.WebProject.entity.AuthorImage;
import WebProject.WebProject.entity.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AuthorRequest {

    @NotNull(message = "Tên Không Được Nhập Trống")
    private String nameAuthor;

    @NotNull(message = "Địa Chỉ Không Được Nhập Trống")
    private String address;

    @NotNull(message = "Miêu Tả Không Được Nhập Trống")
    private String description;


    private List<AuthorImage> authorImage;


    private List<Product> products;
}
