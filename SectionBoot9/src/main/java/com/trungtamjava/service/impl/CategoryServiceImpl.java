package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.CategoryDao;
import com.trungtamjava.entity.Category;
import com.trungtamjava.entity.Product;
import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.SearchCategoryDTO;
import com.trungtamjava.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao categoryDao;

	@Override
	public List<CategoryDTO> getAllCategory(int start, int length) {
		List<Category> categories = categoryDao.getAllCategorys(start, length);
		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();

		for (Category cate : categories) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setName(cate.getName());
			categoryDTO.setDescription(cate.getDescription());
			categoryDTO.setId(cate.getId());
			categoryDTOs.add(categoryDTO);
		}

		return categoryDTOs;
	}

	@Override
	public void addCategory(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());
		category.setDescription(categoryDTO.getDescription());

		categoryDao.addCategory(category);
		categoryDTO.setId(category.getId());
	}

	@Override
	public void deleteCategory(int id) {
		Category category = categoryDao.get(id);
		if (category != null) {
			categoryDao.deleteCategory(category);
		}

	}

	@Override
	public void updateCategory(CategoryDTO categoryDTO) {
		Category category = categoryDao.get(categoryDTO.getId());
		if (category != null) {
			category.setName(categoryDTO.getName());
			category.setDescription(categoryDTO.getDescription());
			categoryDao.updateCategory(category);
		}

	}

	@Override
	public long countGetAll() {
		long count = categoryDao.countGetAll();
		return count;
	}

	@Override
	public long countSearch(String name) {
		long count = categoryDao.countSearch(name);
		return count;
	}

	@Override
	public List<CategoryDTO> getByName(String name, int start, int lenght) {
		List<Category> categories = categoryDao.getByName(name, start, lenght);
		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();

		for (Category cate : categories) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setName(cate.getName());
			categoryDTO.setDescription(cate.getDescription());
			categoryDTO.setId(cate.getId());

			categoryDTOs.add(categoryDTO);
		}

		return categoryDTOs;
	}

	@Override
	public CategoryDTO getById(int id) {
		Category category = categoryDao.get(id);
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setDescription(category.getDescription());
		categoryDTO.setName(category.getName());

		List<ProductDTO> liProductDTOs = new ArrayList<ProductDTO>();
		List<Product> liProducts = category.getProducts();
		for (Product p : liProducts) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(p.getName());
			productDTO.setImg(p.getImg());
			productDTO.setId(p.getId());

			liProductDTOs.add(productDTO);
		}
		categoryDTO.setLProductDTOs(liProductDTOs);
		return categoryDTO;
	}

	@Override
	public List<CategoryDTO> find(SearchCategoryDTO searchCategoryDTO) {
		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
		List<Category> categories = categoryDao.find(searchCategoryDTO);
		for (Category category : categories) {
			categoryDTOs.add(convertToDTO(category));
		}

		return categoryDTOs;
	}

	private CategoryDTO convertToDTO(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		categoryDTO.setDescription(category.getDescription());
		
		
		return categoryDTO;
	}

	@Override
	public Long count(SearchCategoryDTO searchCategoryDTO) {
		return categoryDao.count(searchCategoryDTO);
	}

	@Override
	public Long countTotal(SearchCategoryDTO searchCategoryDTO) {
		return categoryDao.countTotal(searchCategoryDTO);
	}

}
