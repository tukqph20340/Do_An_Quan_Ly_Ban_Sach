package WebProject.WebProject.service;

import WebProject.WebProject.entity.AuthorImage;
import WebProject.WebProject.entity.ProductImage;

public interface AuthorImageService {

    void save(AuthorImage authorImage);

    void deleteById(int id);

}
