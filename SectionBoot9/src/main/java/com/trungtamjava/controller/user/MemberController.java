
package com.trungtamjava.controller.user;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.controller.BaseController2;
import com.trungtamjava.model.BillDTO;
import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.CouponDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.model.UserPrincipal;
import com.trungtamjava.service.BillProductService;
import com.trungtamjava.service.BillService;
import com.trungtamjava.service.CategoryService;
import com.trungtamjava.service.CouponService;
import com.trungtamjava.service.ProductService;
import com.trungtamjava.service.UserService;

@Controller
public class MemberController extends BaseController2 {
	@Autowired
	private CategoryService _categoryService;
	@Autowired
	private BillService billService;

	@Autowired
	private BillProductService billProductService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService _userService;

	@Autowired
	private CouponService couponService;

//
//	@Autowired
//	private MailService mailService;
	@GetMapping(value = "/member/update") // ?id
	public String updateUser(@RequestParam(name = "id") long id, Model model, HttpServletRequest request) {
		UserDTO userDTO = _userService.get(id);
		model.addAttribute("user", userDTO);
		getCategories(request);
		getProductsUpcoming(request);

		return "member/updateUserByMember";
	}

	@PostMapping(value = "/member/update")
	public String updateUser(HttpServletRequest req, @ModelAttribute(name = "user") UserDTO userDTO,
			ModelMap modelMap) {
		userDTO.setEnabled(true);
		userDTO.setRole("ROLE_MEMBER");
		modelMap.addAttribute("user", userDTO);
		_userService.update(userDTO);
		req.setAttribute("sucess", "Sửa thành công!");

		return "member/updateUserByMember";
	}

	@GetMapping(value = "/member/thong-tin-ca-nhan/{id}")
	public String profile(Model model, @PathVariable("id") long id, HttpServletRequest request) {
		UserDTO userDTO = _userService.get(id);
		System.out.println(userDTO.getPassword());
		if (userDTO.isEnabled()) {
			model.addAttribute("user", userDTO);
		}
		getCategories(request);
		getProductsUpcoming(request);
		return "member/profileMember";
	}

	@GetMapping(value = "/member/enable") // ?id
	public String enableUser(@RequestParam(name = "id") long id, HttpServletRequest req) {
		UserDTO userDTO = _userService.get(id);
		userDTO.setEnabled(false);
		_userService.enable(userDTO);
		req.setAttribute("sucess", "Đã vô hiệu hóa tài khoản!");
		return "member/enabledMember";
	}

	@GetMapping(value = "/member/add-to-cart")
	public String AddToCart(@RequestParam(name = "pid") int pId, HttpSession session, HttpServletRequest request)
			throws IOException {
		ProductDTO productDTO = productService.getProductById(pId);
		Object object = session.getAttribute("cart");// Lay session neu co,con ko tao 1 session
		if (object == null) {// neu cart rong
			BillProductDTO billProductDTO = new BillProductDTO();// Tao moi 1 billproduct
			billProductDTO.setProductDTO(productDTO);// set product trong billProduct
			billProductDTO.setQuantity(1);// set sl =1
			billProductDTO.setUnitPrice(productDTO.getPrice());// set unitprice bang product price
			Map<Integer, BillProductDTO> map = new HashMap<Integer, BillProductDTO>();// Tao map de nhet billproduct vao
			map.put(pId, billProductDTO);// Vut billproduct vao map su dung pId cua product
			session.setAttribute("cart", map);// set lai session
			session.setAttribute("totalPrice", totalPrice(map));
			session.setAttribute("totalQuantity", totalQuantity(map));
		} else {
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) object;// Lay map trong session ra(ep kieu
																						// doi tuong)
			BillProductDTO billProductDTO = map.get(pId);// Lay billproduct trong map ra
			if (billProductDTO == null) {// Neu chua co billproduct trong map cua session
				billProductDTO = new BillProductDTO();// Tao 1 billproduct moi
				billProductDTO.setProductDTO(productDTO);// set sp trong billproduct moi nay
				billProductDTO.setQuantity(1);
				billProductDTO.setUnitPrice(productDTO.getPrice());
				map.put(pId, billProductDTO);// them billproduct vao map cua session
			} else {// neu co sp trong map roi thi tang sp len 1
				if (billProductDTO.getQuantity() < productDTO.getQuantity()) {
					billProductDTO.setProductDTO(productDTO);
					billProductDTO.setQuantity(billProductDTO.getQuantity() + 1);

				} else {
					billProductDTO.setProductDTO(productDTO);
					billProductDTO.setQuantity(billProductDTO.getQuantity());

				}
				session.setAttribute("totalPrice", totalPrice(map));
				session.setAttribute("totalQuantity", totalQuantity(map));
				session.setAttribute("cart", map);

			}
			session.setAttribute("totalPrice", totalPrice(map));
			session.setAttribute("totalQuantity", totalQuantity(map));
			session.setAttribute("cart", map);
		}
		return "redirect:" + request.getHeader("Referer");
	}

	@GetMapping(value = "/member/cart")
	public String cart(HttpServletRequest req, HttpSession session, Principal principal) {
		List<CategoryDTO> listCategoryDTOs = _categoryService.getAllCategory(0, 100);
		req.setAttribute("listCate", listCategoryDTOs);
		Object object = session.getAttribute("cart");
		long total = 0;
		if (object != null) {
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) object;

			for (Entry<Integer, BillProductDTO> entry : map.entrySet()) {
				BillProductDTO billProduct = entry.getValue();
				total += billProduct.getUnitPrice() * billProduct.getQuantity();
			}
			session.setAttribute("cart", map);
			req.setAttribute("total", total);
			return "member/cart/listCart";
		}
		req.setAttribute("total", total);

		return "member/cart/listCart";
	}

	@PostMapping(value = "/member/cart")
	public String coupons(HttpSession session, HttpServletRequest req) {
		String couponsName = req.getParameter("coupons");
		CouponDTO couponsDTO = couponService.getByName(couponsName, 0, 10);

		if (couponsDTO != null && couponsDTO.getName().equals(couponsName)) {
			session.setAttribute("coupons", couponsDTO);
			System.out.println("Phan Tram Giam Gia:" + couponsDTO.getPersent() + "%");

			return "redirect:/member/cart";
		}
		return "redirect:/member/cart";
	}

	@GetMapping(value = "member/delete-from-cart")
	public String deleteFromCart(@RequestParam(name = "key") int key, HttpSession session) {
		Object object = session.getAttribute("cart");
		if (object == null) {
			Map<Integer, BillProductDTO> map = new HashMap<Integer, BillProductDTO>();
		}
		if (object != null) {
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) object;
			map.remove(key);
			session.setAttribute("totalPrice", totalPrice(map));
			session.setAttribute("totalQuantity", totalQuantity(map));
			session.setAttribute("cart", map);
		}
		return "redirect:/member/cart";
	}

	public int totalQuantity(Map<Integer, BillProductDTO> map) {
		int totalQuantity = 0;
		for (Map.Entry<Integer, BillProductDTO> iteamCart : map.entrySet()) {
			totalQuantity += iteamCart.getValue().getQuantity();
		}

		return totalQuantity;
	}

	public double totalPrice(Map<Integer, BillProductDTO> map) {
		double totalPrice = 0;
		for (Map.Entry<Integer, BillProductDTO> iteamCart : map.entrySet()) {
			BillProductDTO billProduct = iteamCart.getValue();
			totalPrice += billProduct.getUnitPrice() * billProduct.getQuantity();
		}

		return totalPrice;
	}

	@GetMapping(value = "/member/bill/add")
	public String addOrder(HttpServletRequest req, HttpSession session, Model model) {
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDTO user = new UserDTO();
		user.setId(userPrincipal.getId());
		user.setPhone(userPrincipal.getPhone());
		user.setName(userPrincipal.getName());
		user.setAddress(userPrincipal.getAddress());
		Object object = session.getAttribute("cart");

		if (object != null) { // Neu session cart ton tai
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) object;// Khai bao map roi ap kieu map cho
																						// session cua cart
			BillDTO billDTO = new BillDTO();// Tao bill moi

			billDTO.setBuyer(user);// Set nguoi mua cho bill la nguoi dung hien tai
			billDTO.setStatus("NEW");
			billService.insert(billDTO);

			double totalPrice = 0;
			for (Entry<Integer, BillProductDTO> entry : map.entrySet()) {// duyet map de lay billproduct ra
				BillProductDTO billProduct = entry.getValue();

				totalPrice = totalPrice + (billProduct.getQuantity() * billProduct.getUnitPrice());// Tinh
																									// tong
				// gia
				ProductDTO productDTO = productService.getProductById(entry.getValue().getProductDTO().getId());
				productDTO.setQuantity(productDTO.getQuantity() - billProduct.getQuantity());// Tru san pham con
																								// trong
																								// kho khi mua
				productService.updateProduct(productDTO);

				// Xet coupons
				double finalTotalPrice = 0;
				CouponDTO couponsDTO = (CouponDTO) session.getAttribute("coupons");
				if (couponsDTO != null) {

					finalTotalPrice = totalPrice - (totalPrice * couponsDTO.getPersent() / 100);

					billDTO.setPriceTotal(finalTotalPrice + 30000);
					billDTO.setDiscountPercent(couponsDTO.getPersent());
					System.out.println("Phan tram giam :" + couponsDTO.getPersent());
					billDTO.setCouponsName(couponsDTO.getName());
					System.out.println("Ten Coupons :" + couponsDTO.getName());
					billDTO.setPay(String.valueOf(totalPrice));
					System.out.println("FinalTotal Price :" + finalTotalPrice + "+30000");

					billProduct.setBillDTO(billDTO);
					billService.update(billDTO);
					billProductService.insert(billProduct);
					session.setAttribute("bill", billDTO);

				} else {
					billDTO.setPriceTotal(totalPrice + 30000);
					billDTO.setDiscountPercent(0);
					billDTO.setPay(String.valueOf(totalPrice));
					billDTO.setCouponsName("No discount");

					billProduct.setBillDTO(billDTO);
					billService.update(billDTO);
					billProductService.insert(billProduct);
					session.setAttribute("bill", billDTO);
				}

			}

			// update lai bill sau khi da tinh xong
//			new Thread() {
//				public void run() {
//					// send mail
//					try {
//						MailDTO mailDTO = new MailDTO();
//						mailDTO.setMailFrom("nguyentrongvu121199@gmail.com");
//						mailDTO.setMailTo(userPrincipal.getEmail());
//						mailDTO.setMailSubject("Shop Acc!");
//						mailDTO.setMailContent("Ban da dat thanh cong don hang o cua hang  cua Vuhayqua99");
//						mailService.sendEmail(mailDTO);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				};
//			}.start();

			session.removeAttribute("cart");
			session.removeAttribute("coupons");

			return "redirect:/member/check-out";
		}
		return "redirect:/trang-chu";
	}

	@GetMapping(value = "/member/check-out")
	public String checkOut(HttpSession session, HttpServletRequest request, Model model) {
		BillDTO billDTO = (BillDTO) session.getAttribute("bill");
		System.out.println(billDTO.getDiscountPercent());
		if (billDTO != null) {
			model.addAttribute("billDTO", billDTO);
		}

		return "member/cart/checkOut";
	}

	@PostMapping(value = "/member/check-out")
	public String checkOut2(HttpServletRequest request, ModelMap modelMap,
			@ModelAttribute(name = "billDTO") BillDTO billDTO, HttpSession session) {
		modelMap.addAttribute("billDTO", billDTO);
		billService.update(billDTO);
		request.setAttribute("sucess", "Thanh toán thành công!");
		session.removeAttribute("cart");
		session.removeAttribute("coupons");
		session.removeAttribute("bill");
		return "member/cart/checkOut";
	}
}
