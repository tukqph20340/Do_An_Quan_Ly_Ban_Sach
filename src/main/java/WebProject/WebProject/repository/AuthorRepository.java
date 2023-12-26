package WebProject.WebProject.repository;

import WebProject.WebProject.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findById(int id);
    Author findByNameAuthor(String ten);
    @Query("SELECT c from Author c WHERE c.nameAuthor LIKE %?1%")
    Page<Author> findAllByNameAuthor(Pageable p, String ten);

}
