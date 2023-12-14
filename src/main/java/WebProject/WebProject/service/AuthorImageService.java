package WebProject.WebProject.service;

import WebProject.WebProject.entity.AuthorImage;

public interface AuthorImageService {

    void save(AuthorImage authorImage);

    void deleteById(int id);

}
