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
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.UserService;

@Controller
public class UserController extends BaseController2 {
	@Autowired
	UserService userService;

	@GetMapping("/admin/user/add")
	public String addUser(Model model) {
		model.addAttribute("user", new UserDTO());

		return "admin/user/addUser";
	}

	@PostMapping("/admin/user/add")
	public String addUser(HttpServletRequest request, @ModelAttribute(name = "user") UserDTO userDTO,
			ModelMap modelMap) {
		modelMap.addAttribute("user", userDTO);
		userDTO.setEnabled(true);
		userDTO.setRole("ROLE_MEMBER");
		userService.insert(userDTO);
		request.setAttribute("sucess", "Thêm thành công!");
		return "admin/user/addUser";
	}

	@GetMapping(value = "/admin/user/update") // ?id
	public String updateUser(@RequestParam(name = "id") long id, Model model) {
		UserDTO userDTO = userService.get(id);
		model.addAttribute("user", userDTO);
		return "admin/user/updateUser";
	}

	@PostMapping(value = "/admin/user/update")
	public String updateCategory(HttpServletRequest req, @ModelAttribute(name = "user") UserDTO userDTO,
			ModelMap modelMap) {
		modelMap.addAttribute("user", userDTO);
		userDTO.setEnabled(true);
		userDTO.setRole("ROLE_MEMBER");
		userService.update(userDTO);
		req.setAttribute("sucess", "Sửa thành công!");
		return "admin/user/updateUser";
	}

	@GetMapping(value = "/admin/user/delete") // ?id
	public String deleteCategory(@RequestParam(name = "id") long id, HttpServletRequest req) {
		UserDTO userDTO = userService.get(id);
		userService.delete(userDTO.getId());
		return "redirect:/admin/user/search";
	}

	@GetMapping(value = "/admin/user/search")
	public String search(HttpServletRequest req, @RequestParam(name = "name", required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));

		if (name != null && !name.equals("null") && !name.equals("")) {
			long count = userService.countSearch(name);
			double result = Math.ceil((double) count / 6);
			List<UserDTO> list = userService.search(name, (page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
			req.setAttribute("userList", list);
		} else {
			long count = userService.countGetAll();
			double result = Math.ceil((double) count / 6);
			List<UserDTO> list = userService.getAllUsers((page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("userList", list);
		}
		
		return "admin/user/listUser";
	}

}
