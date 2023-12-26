package WebProject.WebProject.service;

import WebProject.WebProject.entity.AuthorImage;

import java.util.List;

public interface AuthorImageService {

    List<AuthorImage> getAllAuth(Integer id);

    void save(AuthorImage authorImage);

    void deleteById(int id);

}
