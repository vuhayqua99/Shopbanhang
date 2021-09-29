package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Category;
import com.trungtamjava.model.SearchCategoryDTO;

public interface CategoryDao {

	public void addCategory(Category category);

	public void deleteCategory(Category category);

	public void updateCategory(Category category);

	public List<Category> getAllCategorys(int start, int length);

	long countGetAll();

	long countSearch(String name);

	List<Category> getByName(String name, int start, int lenght);

	public Category get(int id);

	List<Category> find(SearchCategoryDTO searchCategoryDTO);
	
	Long count(SearchCategoryDTO searchCategoryDTO);

	Long countTotal(SearchCategoryDTO searchCategoryDTO);

}
