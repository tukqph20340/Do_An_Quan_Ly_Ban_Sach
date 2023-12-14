package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.BookCover;
import WebProject.WebProject.repository.BookCoverRepository;
import WebProject.WebProject.service.BookCoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCoverServiceImpl implements BookCoverService {

    @Autowired
    private BookCoverRepository bookCoverRepository;

    @Override
    public List<BookCover> getAllBookCover() {
        return bookCoverRepository.findAll();
    }

    @Override
    public BookCover saveBookCover(BookCover bookCover) {
        return bookCoverRepository.save(bookCover);
    }

    @Override
    public Page<BookCover> findAll(Pageable pageable) {
        return bookCoverRepository.findAll(pageable);
    }

    @Override
    public BookCover getAllBookCoverById(int id) {
        return bookCoverRepository.findById(id);
    }
}
