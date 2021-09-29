package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.SearchCategoryDTO;

public interface CategoryService {
	public List<CategoryDTO> getAllCategory(int start, int length);
	
	public void addCategory(CategoryDTO CategoryDTO);

	public void deleteCategory(int id);

	public void updateCategory(CategoryDTO CategoryDTO);
	
	long countGetAll();

	long countSearch(String name);

	List<CategoryDTO> getByName(String name, int start, int lenght);
	
	public CategoryDTO getById(int id);
	
	List<CategoryDTO> find(SearchCategoryDTO searchCategoryDTO);

	Long count(SearchCategoryDTO searchCategoryDTO);

	Long countTotal(SearchCategoryDTO searchCategoryDTO);

}
