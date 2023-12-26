package WebProject.WebProject.repository;

import WebProject.WebProject.entity.AuthorImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorImageRepository extends JpaRepository<AuthorImage, Integer> {

    void deleteById(int id);
    List<AuthorImage> findByAuthor_Id(Integer id);

}
