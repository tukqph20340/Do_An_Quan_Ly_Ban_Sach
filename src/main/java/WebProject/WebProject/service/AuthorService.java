package WebProject.WebProject.service;

import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthor();

    Author saveAuthor(Author author);

    Page<Author> findAll(Pageable pageable);

    Author getAllAuthorById(int id);
}
