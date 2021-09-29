package com.trungtamjava.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.trungtamjava.controller.BaseController2;

@Controller
public class HomeAdminController extends BaseController2 {

	@GetMapping("/admin")
	public String indexAdmin(HttpServletRequest request) {
		
		return "admin/indexAdmin";
	}
}
