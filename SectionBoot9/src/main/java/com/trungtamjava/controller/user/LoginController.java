package com.trungtamjava.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.controller.BaseController2;
import com.trungtamjava.model.UserPrincipal;

@Controller
public class LoginController extends BaseController2 {

	@GetMapping(value = "/login")
	public String login(HttpServletRequest request, @RequestParam(name = "e", required = false) String error) {
		if (error != null) {
			request.setAttribute("e", error);
		}
		getCategories(request);
		getMenus(request);
		getProductsUpcoming(request);
		return "client/login";

	}

}