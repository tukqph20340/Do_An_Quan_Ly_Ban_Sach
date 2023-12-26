package WebProject.WebProject.service;

import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthor();

    Author saveAuthor(Author author);

    Author ten(String author);
    Page<Author> timKiemTen(Integer pageNo, Integer size, String ten);

    Page<Author> findAll(Integer pageNo, Integer size);

    Author getAllAuthorById(int id);
}
