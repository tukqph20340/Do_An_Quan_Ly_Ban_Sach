package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.AuthorImage;
import WebProject.WebProject.repository.AuthorImageRepository;
import WebProject.WebProject.service.AuthorImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorImageImpl implements AuthorImageService {

    @Autowired
    private AuthorImageRepository authorImageRepository;

    @Override
    public void save(AuthorImage authorImage) {
        authorImageRepository.save(authorImage);
    }

    @Override
    public void deleteById(int id) {
        authorImageRepository.deleteById(id);
    }
}
