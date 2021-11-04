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
import com.trungtamjava.model.ColorDTO;
import com.trungtamjava.service.ColorService;

@Controller
public class ColorController extends BaseController2 {

	@Autowired
	ColorService colorService;

	@GetMapping("/admin/color/add")
	public String add(Model model, HttpServletRequest request) {
		model.addAttribute("color", new ColorDTO());
		getAllProduct(request);
		return "admin/color/addColor";
	}

	@PostMapping("/admin/color/add")
	public String add(HttpServletRequest request, @ModelAttribute(name = "color") ColorDTO colorDTO,
			ModelMap modelMap) {
		modelMap.addAttribute("color", colorDTO);

		colorService.addColor(colorDTO);

		request.setAttribute("sucess", "Thêm thành công!");
		return "admin/color/addColor";
	}

	@GetMapping(value = "/admin/color/update") // ?id
	public String updatecolor(@RequestParam(name = "id") int id, Model model, HttpServletRequest request) {
		ColorDTO colorDTO = colorService.getColor(id);
		model.addAttribute("color", colorDTO);
		getAllProduct(request);
		
		return "admin/color/updateColor";
	}

	@PostMapping(value = "/admin/color/update")
	public String updatecolor(HttpServletRequest req, @ModelAttribute(name = "color") ColorDTO colorDTO,
			ModelMap modelMap) {
		modelMap.addAttribute("color", colorDTO);
		colorService.updateColor(colorDTO);
		req.setAttribute("sucess", "Sửa thành công!");
		return "admin/color/updateColor";
	}

	@GetMapping(value = "/admin/color/delete") // ?id
	public String deletecolor(@RequestParam(name = "id") int id, HttpServletRequest req) {
		ColorDTO colorDTO = colorService.getColor(id);
		colorService.deleteColor(colorDTO.getId());
		return "redirect:/admin/color/search";
	}

	@GetMapping(value = "/admin/color/search")
	public String search(HttpServletRequest req, @RequestParam(name = "name", required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));

		if (name != null && !name.equals("null") && !name.equals("")) {
			long count = colorService.countSearch(name);
			double result = Math.ceil((double) count / 6);
			List<ColorDTO> list = colorService.getAllColorByName(name, (page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
			req.setAttribute("colorList", list);
		} else {
			long count = colorService.countGetAll();
			double result = Math.ceil((double) count / 6);
			List<ColorDTO> list = colorService.getAllColor((page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("colorList", list);
		}
		return "admin/color/listColor";
	}

}
