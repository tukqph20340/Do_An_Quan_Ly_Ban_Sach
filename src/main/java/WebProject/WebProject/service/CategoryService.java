package WebProject.WebProject.service;

import java.util.List;

import WebProject.WebProject.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
	
	Category saveCategory(Category category);

	Category getCategoryById(int id);

	Category updateCategory(Category category);
	
	List<Category> findAll();
	Page<Category> finadALL(Pageable pageable);

	void deleteCategoryId(int id);
}
