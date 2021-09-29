package com.trungtamjava.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.MenuDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.SlidesDTO;
import com.trungtamjava.service.CategoryService;
import com.trungtamjava.service.MenuService;
import com.trungtamjava.service.ProductService;
import com.trungtamjava.service.SlidesService;
import com.trungtamjava.service.UserService;

@Controller
public class BaseController2 {

	@Autowired
	MenuService _menuService;

	@Autowired
	SlidesService _slidesService;

	@Autowired
	CategoryService _categoryService;

	@Autowired
	ProductService _productService;

	@Autowired
	UserService _userService;

	
	public void getSlides(HttpServletRequest request) {

		List<SlidesDTO> slidesDTOs = _slidesService.getAllSlides();
		request.setAttribute("slides", slidesDTOs);

	}

	public void getCategories(HttpServletRequest request) {

		List<CategoryDTO> categoryDTOs = _categoryService.getAllCategory(0, 100);
		request.setAttribute("categories", categoryDTOs);

	}

	public void getMenus(HttpServletRequest request) {

		List<MenuDTO> menuDTOs = _menuService.getAllMenus();
		request.setAttribute("menus", menuDTOs);

	}

	public void getProductsHighlight(HttpServletRequest request) {

		List<ProductDTO> productDTOs = _productService.getAllProductHighlight();
		request.setAttribute("products", productDTOs);

	}

	public void getProductsNewProduct(HttpServletRequest request) {

		List<ProductDTO> productDTOs2 = _productService.getAllNewProduct();
		request.setAttribute("products2", productDTOs2);

	}

	public void getProductsUpcoming(HttpServletRequest request) {

		List<ProductDTO> productDTOs3 = _productService.getAllUpcomingProduct();
		request.setAttribute("products3", productDTOs3);
		System.out.println(productDTOs3.size());
	}

	public void getAllProduct(HttpServletRequest request) {
		List<ProductDTO> productDTOs4 = _productService.getAll(0, 100);
		request.setAttribute("products4", productDTOs4);

	}

}
