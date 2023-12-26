package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.BookCover;
import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.repository.BookCoverRepository;
import WebProject.WebProject.repository.ProducerRepository;
import WebProject.WebProject.service.BookCoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public BookCover saveBook(BookCover bookCover) {
        return bookCoverRepository.save(bookCover);
    }

    @Override
    public Page<BookCover> findAll(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return bookCoverRepository.findAll(pageable);
    }



    @Override
    public BookCover getAllBookCoverById(int id) {
        return bookCoverRepository.findById(id);
    }

    @Override
    public Page<BookCover> fillByName(Integer pageNo, Integer size, String ten) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return bookCoverRepository.findAllByCover_type(pageable,ten);
    }


}
