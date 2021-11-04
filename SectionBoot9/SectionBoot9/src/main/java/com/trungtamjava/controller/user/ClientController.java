package com.trungtamjava.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.controller.BaseController2;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.CategoryService;
import com.trungtamjava.service.CouponService;
import com.trungtamjava.service.MenuService;
import com.trungtamjava.service.ProductService;
import com.trungtamjava.service.UserService;

@Controller
public class ClientController extends BaseController2 {
	@Autowired
	UserService _userService;
	@Autowired
	MenuService _menuService;

	@Autowired
	CategoryService _categoryService;

	@Autowired
	ProductService _productService;

	@Autowired
	CouponService couponService;

	@GetMapping(value = "/register")
	public String register(Model model, HttpServletRequest request) {
		model.addAttribute("user", new UserDTO());
		getCategories(request);
		getProductsUpcoming(request);

		return "client/register";
	}

	@PostMapping(value = "/register")
	public String register(HttpServletRequest req, @ModelAttribute(name = "user") UserDTO userDTO, ModelMap modelMap) {
		userDTO.setEnabled(true);
		userDTO.setRole("ROLE_MEMBER");
		modelMap.addAttribute("user", userDTO);
		_userService.insert(userDTO);
		req.setAttribute("sucess", "Đăng ký thành công!");
		System.out.println(userDTO.getGender());
		return "client/register";
	}

	@GetMapping(value = { "/chi-tiet-san-pham/{id}" })
	public String ProductDetail(HttpServletRequest request, @PathVariable("id") long id) {

		ProductDTO productDTO = _productService.getProductById(id);

		List<ProductDTO> productDTOs = _productService.searchByCateId(productDTO.getCategoryDTO().getId(), 0, 6);

		getMenus(request);

		getCategories(request);

		getProductsUpcoming(request);

		request.setAttribute("product", productDTO);

		request.setAttribute("products", productDTOs);

		return "client/products/productDetail";

	}

	@GetMapping(value = "/search-san-pham")
	public String searchName(HttpServletRequest request, @RequestParam(name = "name", required = false) String keyword,
			Model model) {

		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
		if (keyword != null && !keyword.equals("") && !keyword.equals("null")) {
//				long count = productService.countGetAll();

			List<ProductDTO> listProductDTOsss = _productService.search(keyword, 0, 1000);
			int i = listProductDTOsss.size();
			double result = Math.ceil((double) i / 6);/// láy ra số trang nhiều nhaats có the
			List<ProductDTO> listProductDTOs = _productService.search(keyword, (page - 1) * 6, 6);// lây 6 sp theo trang
																									// hienj tai, start:

			request.setAttribute("productList", listProductDTOs);
			request.setAttribute("currentPage", page);
			request.setAttribute("result", result);
			request.setAttribute("keyword", keyword);
		} else {
			long count = _productService.countGetAll();
			double result = Math.ceil((double) count / 6);
			List<ProductDTO> listProductDTOs = _productService.getAll((page - 1) * 6, 6);
			request.setAttribute("productList", listProductDTOs);
			request.setAttribute("currentPage", page);
			request.setAttribute("result", result);
			request.setAttribute("keyword", keyword);
		}
		getMenus(request);
		return "client/products/searchProduct";

	}

	@GetMapping(value = "/san-pham")
	public String searchByCateId(HttpServletRequest request,
			@RequestParam(name = "cId", required = true) String cateId) {

		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

//			long count = productService.countGetAll();

		List<ProductDTO> listProductDTOsss = _productService.searchByCateId(Integer.parseInt(cateId), 0, 1000);
		int i = listProductDTOsss.size();
		double result = Math.ceil((double) i / 6);/// láy ra số trang nhiều nhaats có the
		List<ProductDTO> listProductDTOs = _productService.searchByCateId(Integer.parseInt(cateId), (page - 1) * 6, 6);// lây
																														// 6
																														// sp
																														// theo
																														// trang
																														// hienj
																														// tai,
		getMenus(request); // start:

		request.setAttribute("productList", listProductDTOs);
		request.setAttribute("currentPage", page);
		request.setAttribute("result", result);
		request.setAttribute("cateId", cateId);

		return "client/products/category";

	}
}
