package com.trungtamjava.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.ResponseDTO;
import com.trungtamjava.model.SearchCategoryDTO;
import com.trungtamjava.service.CategoryService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class CategoryAPIController {
	@Autowired
	CategoryService categoryService;

	@PostMapping("/admin/category/add")
	public CategoryDTO addUser(@RequestBody CategoryDTO categoryDTO) {
		categoryService.addCategory(categoryDTO);
		return categoryDTO;
	}

	@GetMapping(value = "/category/search")
	public List<CategoryDTO> listUser() {
		List<CategoryDTO> list = categoryService.getAllCategory(0, 1000);
		return list;
	}

	@GetMapping(value = "/category/get/{id}")
	public CategoryDTO get(@PathVariable(name = "id") int id) {
		return categoryService.getById(id);
	}

	@PutMapping(value = "/admin/category/update")
	public void updateUser(@RequestBody CategoryDTO categoryDTO) {
		categoryService.updateCategory(categoryDTO);
	}

	@DeleteMapping(value = "admin/category/delete")
	public void deleteUser(@RequestParam(name = "id") int id) {
		categoryService.deleteCategory(id);

	}

	@PostMapping(value = "/category/tim")
	public ResponseDTO<CategoryDTO> find(@RequestBody SearchCategoryDTO searchCategoryDTO) {
		ResponseDTO<CategoryDTO> responseDTO = new ResponseDTO<CategoryDTO>();
		responseDTO.setData(categoryService.find(searchCategoryDTO));
		responseDTO.setRecordsFiltered(categoryService.count(searchCategoryDTO));
		responseDTO.setRecordsTotal(categoryService.countTotal(searchCategoryDTO));
		return responseDTO;
	}
}
