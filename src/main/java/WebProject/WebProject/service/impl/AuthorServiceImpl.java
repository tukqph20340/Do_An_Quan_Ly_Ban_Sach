package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.Author;
import WebProject.WebProject.repository.AuthorRepository;
import WebProject.WebProject.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author ten(String author) {
        return authorRepository.findByNameAuthor(author);
    }

    @Override
    public Page<Author> timKiemTen(Integer pageNo, Integer size, String ten) {
        Pageable pageable = PageRequest.of(pageNo,size);
        return authorRepository.findAllByNameAuthor(pageable,ten);
    }

    @Override
    public Page<Author> findAll(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo,size);
        return authorRepository.findAll(pageable);
    }



    @Override
    public Author getAllAuthorById(int id) {
        return authorRepository.findById(id);
    }
}
