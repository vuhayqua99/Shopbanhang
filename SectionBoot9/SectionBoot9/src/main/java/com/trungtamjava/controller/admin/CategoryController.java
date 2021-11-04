package com.trungtamjava.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.controller.BaseController2;
import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.service.CategoryService;

@Controller
public class CategoryController extends BaseController2 {
	@Autowired
	CategoryService categoryService;

	@GetMapping("/admin/category/add")
	public String add(Model model, HttpServletRequest request) {
		model.addAttribute("category", new CategoryDTO());
		return "admin/category/addCategory";
	}

	@PostMapping("/admin/category/add")
	public String add(HttpServletRequest request, @ModelAttribute(name = "category") CategoryDTO categoryDTO,
			ModelMap modelMap) {
		modelMap.addAttribute("category", categoryDTO);
		categoryService.addCategory(categoryDTO);
		request.setAttribute("sucess", "Thêm thành công!");
		return "admin/category/addCategory";
	}

	@GetMapping(value = "/admin/category/update") // ?id
	public String updateCategory(@RequestParam(name = "id") int id, Model model, HttpServletRequest request) {
		CategoryDTO categoryDTO = categoryService.getById(id);
		model.addAttribute("category", categoryDTO);

		return "admin/category/updateCategory";
	}

	@PostMapping(value = "/admin/category/update")
	public String updateCategory(HttpServletRequest req, @ModelAttribute(name = "category") CategoryDTO categoryDTO,
			ModelMap modelMap) {
		modelMap.addAttribute("category", categoryDTO);
		categoryService.updateCategory(categoryDTO);
		req.setAttribute("sucess", "Sửa thành công!");
		return "admin/category/updateCategory";
	}

	@GetMapping(value = "/admin/category/delete") // ?id
	public String deleteCategory(@RequestParam(name = "id") int id, HttpServletRequest req) {
		CategoryDTO categoryDTO = categoryService.getById(id);
		if (categoryDTO.getLProductDTOs() == null) {
			categoryService.deleteCategory(categoryDTO.getId());
		} else {
			System.out.println("hello");
			req.setAttribute("warring", "Không thể xóa danh mục vì danh sách có sản phẩm!");
			return "admin/category/notDelete";
		}
		return "redirect:/admin/category/search";
	}

	@GetMapping(value = "/admin/category/search")
	public String search(HttpServletRequest req, @RequestParam(name = "name", required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));

		if (name != null && !name.equals("null") && !name.equals("")) {
			long count = categoryService.countSearch(name);
			double result = Math.ceil((double) count / 4);
			List<CategoryDTO> list = categoryService.getByName(name, (page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
			req.setAttribute("cateList", list);
		} else {
			long count = categoryService.countGetAll();
			double result = Math.ceil((double) count / 6);
			List<CategoryDTO> list = categoryService.getAllCategory((page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("cateList", list);
		}

		return "admin/category/listCategory";
	}

}
