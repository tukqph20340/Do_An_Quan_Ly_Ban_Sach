package WebProject.WebProject.repository;

import WebProject.WebProject.entity.BookCover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCoverRepository extends JpaRepository<BookCover, Integer> {

    BookCover findById(int id);

    @Query("SELECT c from BookCover c WHERE c.cover_type LIKE %?1%")
    List<BookCover> seach(String name);

}
