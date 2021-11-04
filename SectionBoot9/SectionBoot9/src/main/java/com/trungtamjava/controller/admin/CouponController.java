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
import com.trungtamjava.model.CouponDTO;
import com.trungtamjava.service.CouponService;

@Controller
public class CouponController extends BaseController2 {

	@Autowired
	CouponService couponService;

	@GetMapping("/admin/coupon/add")
	public String add(Model model, HttpServletRequest request) {
		model.addAttribute("coupon", new CouponDTO());
	
		return "admin/coupon/addCoupon";
	}

	@PostMapping("/admin/coupon/add")
	public String add(HttpServletRequest request, @ModelAttribute(name = "coupon") CouponDTO couponDTO,
			ModelMap modelMap) {
		modelMap.addAttribute("coupon", couponDTO);
		couponService.insert(couponDTO);
		request.setAttribute("sucess", "Thêm thành công!");
		return "admin/coupon/addcoupon";
	}

	@GetMapping(value = "/admin/coupon/update") // ?id
	public String updatecoupon(@RequestParam(name = "id") int id, Model model, HttpServletRequest request) {
		CouponDTO couponDTO = couponService.get(id);
		model.addAttribute("coupon", couponDTO);
		return "admin/coupon/updateCoupon";
	}

	@PostMapping(value = "/admin/coupon/update")
	public String updatecoupon(HttpServletRequest req, @ModelAttribute(name = "coupon") CouponDTO couponDTO,
			ModelMap modelMap) {
		modelMap.addAttribute("coupon", couponDTO);
		couponService.update(couponDTO);
		req.setAttribute("sucess", "Sửa thành công!");
		return "admin/coupon/updatecoupon";
	}

	@GetMapping(value = "/admin/coupon/delete") // ?id
	public String deletecoupon(@RequestParam(name = "id") int id, HttpServletRequest req) {
		CouponDTO couponDTO = couponService.get(id);
		couponService.delete(couponDTO.getId());
		return "redirect:/admin/coupon/search";
	}

	@GetMapping(value = "/admin/coupon/search")
	public String search(HttpServletRequest req, @RequestParam(name = "name", required = false) String name,
			HttpServletRequest request) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));

		if (name != null && !name.equals("null") && !name.equals("")) {
			long count = couponService.countSearch(name);
			double result = Math.ceil((double) count / 6);
			List<CouponDTO> list = couponService.getListByName(name, (page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
			req.setAttribute("couponList", list);
		} else {
			long count = couponService.countAll();
			double result = Math.ceil((double) count / 6);
			List<CouponDTO> list = couponService.getAll((page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("couponList", list);
		}
		return "admin/coupon/listCoupon";
	}

}
