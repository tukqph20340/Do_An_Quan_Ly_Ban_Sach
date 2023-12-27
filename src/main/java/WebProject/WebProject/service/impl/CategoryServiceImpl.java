package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.Category;
import WebProject.WebProject.repository.CategoryRepository;
import WebProject.WebProject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;


	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	@Override
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}


	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Page<Category> findAll(Integer pageNo, Integer size) {
		Pageable pageable = PageRequest.of(pageNo, size);
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Category getAllCategoryById(int id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Page<Category> fillByName(Integer pageNo, Integer size, String ten) {
		Pageable pageable = PageRequest.of(pageNo, size);
		return categoryRepository.findAllByCategory_Name(pageable,ten);
	}


}