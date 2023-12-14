package WebProject.WebProject.repository;

import WebProject.WebProject.entity.BookCover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCoverRepository extends JpaRepository<BookCover, Integer> {

    BookCover findById(int id);

}
