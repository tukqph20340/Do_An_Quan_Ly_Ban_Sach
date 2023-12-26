package WebProject.WebProject.repository;

import WebProject.WebProject.entity.BookCover;
import WebProject.WebProject.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCoverRepository extends JpaRepository<BookCover, Integer> {

    BookCover findById(int id);

    @Query("SELECT c from BookCover c WHERE c.cover_type LIKE %?1%")
    Page<BookCover> findAllByCover_type(Pageable p, String name);

}