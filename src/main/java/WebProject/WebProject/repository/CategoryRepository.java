package WebProject.WebProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.WebProject.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

	Category findById(int id);

}