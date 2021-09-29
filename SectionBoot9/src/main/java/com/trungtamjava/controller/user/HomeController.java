package com.trungtamjava.controller.user;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends com.trungtamjava.controller.BaseController2 {

	@GetMapping(value = { "/", "/trang-chu" })
	public String listSlides(HttpServletRequest request) {
		getSlides(request);

		getCategories(request);

		getMenus(request);

		getProductsHighlight(request);

		getProductsNewProduct(request);

		getProductsUpcoming(request);

		return "client/index";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadfile(HttpServletRequest request, HttpServletResponse response) {
		String image = request.getParameter("image");
		final String UPLOAD_FOLDER = "D:\\git_clone\\class-spring08\\SectionBoot9\\src\\main\\resources\\static\\assets\\user\\img\\";
		File file = new File(UPLOAD_FOLDER + File.separator + image);
		if (file.exists()) {
			try {
				FileUtils.copyFile(file, response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
