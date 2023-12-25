package WebProject.WebProject.service;

import WebProject.WebProject.entity.Category;
import WebProject.WebProject.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

	List<Category> getAllCategories();

	Category saveCategory(Category category);

	Page<Category> findAll(Integer pageNo, Integer size);

	Category getAllCategoryById(int id);
}
