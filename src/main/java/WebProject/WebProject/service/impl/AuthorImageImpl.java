package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.AuthorImage;
import WebProject.WebProject.repository.AuthorImageRepository;
import WebProject.WebProject.service.AuthorImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorImageImpl implements AuthorImageService {

    @Autowired
    private AuthorImageRepository authorImageRepository;

    @Override
    public List<AuthorImage> getAllAuth(Integer id) {
        return authorImageRepository.findByAuthor_Id(id);
    }

    @Override
    public void save(AuthorImage authorImage) {
        authorImageRepository.save(authorImage);
    }

    @Override
    public void deleteById(int id) {
        authorImageRepository.deleteById(id);
    }
}
