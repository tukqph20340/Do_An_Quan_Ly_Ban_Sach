package WebProject.WebProject.service;

import WebProject.WebProject.entity.Category;

import org.springframework.data.domain.Page;


import java.util.List;

public interface CategoryService {

	List<Category> getAllCategories();

	Category saveCategory(Category category);

	Page<Category> findAll(Integer pageNo, Integer size);

	Category getAllCategoryById(int id);

	Page<Category> teh(Integer pageNo, Integer size,String ten);
}
