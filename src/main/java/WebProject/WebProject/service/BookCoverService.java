package WebProject.WebProject.service;

import WebProject.WebProject.entity.BookCover;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCoverService {

    List<BookCover> getAllBookCover();

    BookCover saveBookCover(BookCover bookCover);

    Page<BookCover> findAll(Pageable pageable);

    BookCover getAllBookCoverById(int id);
}
