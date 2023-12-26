package WebProject.WebProject.service;

import WebProject.WebProject.entity.BookCover;
import WebProject.WebProject.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCoverService {

    List<BookCover> getAllBookCover();

    BookCover saveBook(BookCover bookCover);

    Page<BookCover> findAll(Integer pageNo, Integer size);

    BookCover getAllBookCoverById(int id);

    List<BookCover> seach(String name);
}
