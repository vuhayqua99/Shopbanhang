package com.trungtamjava.controller.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.trungtamjava.controller.BaseController2;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.service.ProductService;

@Controller
public class ProductController extends BaseController2 {
	@Autowired
	ProductService productService;

	@GetMapping("/admin/product/add")
	public String addProduct(Model model, HttpServletRequest request) {
		model.addAttribute("product", new ProductDTO());

		getCategories(request);
		return "admin/product/addProduct";
	}

	@PostMapping("/admin/product/add")
	public String addProduct(HttpServletRequest request, @ModelAttribute(name = "product") ProductDTO productDTO,
			ModelMap modelMap, @RequestParam(name = "imageFile") MultipartFile imagefile) {
		modelMap.addAttribute("product", productDTO);

		String originalFilename = imagefile.getOriginalFilename();
		int lastIndex = originalFilename.lastIndexOf(".");
		String ext = originalFilename.substring(lastIndex);

		String avatarFilename = System.currentTimeMillis() + ext;
		File newfile = new File(
				"D:\\git_clone\\class-spring08\\SectionBoot9\\src\\main\\resources\\static\\assets\\user\\img\\"
						+ avatarFilename);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(newfile);
			fileOutputStream.write(imagefile.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		productDTO.setImg(avatarFilename);

		productService.addProduct(productDTO);

		request.setAttribute("sucess", "Thêm thành công!");
		return "admin/product/addProduct";
	}

	@GetMapping(value = "/admin/product/search")
	public String search(HttpServletRequest req, @RequestParam(name = "name", required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));
		if (name != null && !name.equals("null") && !name.equals("")) {
			long count = productService.countSearch(name);
			double result = Math.ceil((double) count / 5);
			List<ProductDTO> listProductDTOs = productService.search(name, (page - 1) * 6, 6);
			req.setAttribute("productList", listProductDTOs);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
		} else {
			long count = productService.countGetAll();
			double result = Math.ceil((double) count / 5);
			List<ProductDTO> listProductDTOs = productService.getAll((page - 1) * 6, 6);
			req.setAttribute("productList", listProductDTOs);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			for (ProductDTO p : listProductDTOs) {
				System.out.println(p.getName());
			}
		}

		return "admin/product/listProduct";
	}

	@GetMapping(value = "/admin/product/update") // ?id
	public String updateProduct(@RequestParam(name = "id") long id, Model model, HttpServletRequest request) {
		ProductDTO productDTO = productService.getProductById(id);
		getCategories(request);
		model.addAttribute("product", productDTO);
		return "admin/product/updateProduct";
	}

	@PostMapping(value = "/admin/product/update")
	public String updateCategory(HttpServletRequest req, @ModelAttribute(name = "product") ProductDTO productDTO,
			ModelMap modelMap, @RequestParam(name = "imageFile") MultipartFile imagefile) {
		modelMap.addAttribute("product", productDTO);

		String originalFilename = imagefile.getOriginalFilename();
		int lastIndex = originalFilename.lastIndexOf(".");
		String ext = originalFilename.substring(lastIndex);

		String avatarFilename = System.currentTimeMillis() + ext;
		File newfile = new File(
				"D:\\git_clone\\class-spring08\\SectionBoot9\\src\\main\\resources\\static\\assets\\user\\img\\"
						+ avatarFilename);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(newfile);
			fileOutputStream.write(imagefile.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		productDTO.setImg(avatarFilename);
		productService.updateProduct(productDTO);
		req.setAttribute("sucess", "Sửa thành công!");
		return "admin/product/updateProduct";
	}

	@GetMapping(value = "/admin/product/delete") // ?id
	public String deleteCategory(@RequestParam(name = "id") long id, HttpServletRequest req) {
		ProductDTO productDTO = productService.getProductById(id);
		if (productDTO.getListColorDTO() == null && productDTO.getLBillProductDTOs() == null) {
			productService.deleteProduct(productDTO.getId());
		} else {
			req.setAttribute("warring", "Không thể xóa sản phẩm!");
			return "admin/product/notDelete";
		}
		return "redirect:/admin/product/search";
	}

}
