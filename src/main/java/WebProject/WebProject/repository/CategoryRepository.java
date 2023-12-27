package WebProject.WebProject.repository;

import WebProject.WebProject.entity.BookCover;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.WebProject.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

	Category findById(int id);
	@Query("SELECT c from Category c WHERE c.category_Name LIKE %?1%")
	Page<Category> findAllByCategory_Name(Pageable p, String name);

}