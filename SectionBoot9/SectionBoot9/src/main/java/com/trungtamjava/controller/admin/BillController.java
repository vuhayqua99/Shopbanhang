package com.trungtamjava.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.controller.BaseController2;
import com.trungtamjava.model.BillDTO;
import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.service.BillProductService;
import com.trungtamjava.service.BillService;

@Controller
public class BillController extends BaseController2 {
	@Autowired
	BillService billService;

	@Autowired
	BillProductService billProductService;

	@GetMapping("/admin/bill/set-status") // ?id
	public String setStatus(Model model, HttpServletRequest req, @RequestParam(name = "id") int id) {
		BillDTO billDTO = billService.get(id);
		billDTO.setStatus("Đã giao hàng thành công!");
		billService.setStatus(billDTO);
		req.setAttribute("sucess", " Set thành công!");

		return "admin/bill/setStatus";
	}

	@GetMapping(value = "/admin/bill/delete") // ?id
	public String deletebill(@RequestParam(name = "id") int id, HttpServletRequest req) {
		BillDTO billDTO = billService.get(id);
		billService.delete(billDTO.getId());
		return "redirect:/admin/bill/search";
	}

	@GetMapping(value = "/admin/bill/search")
	public String search(HttpServletRequest req, @RequestParam(name = "name", required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));

		if (name != null && !name.equals("null") && !name.equals("")) {
			long count = billService.countSearch(name);
			double result = Math.ceil((double) count / 6);
			List<BillDTO> list = billService.getAllBillByName(name, (page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
			req.setAttribute("billList", list);
		} else {
			long count = billService.countAll();
			double result = Math.ceil((double) count / 6);
			List<BillDTO> list = billService.getAll((page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("billList", list);
		}
		return "admin/bill/listBill";
	}

	@GetMapping(value = "/admin/bill-product/delete") // ?billProId
	public String deleteBillPro(@RequestParam(name = "billProId") int id, HttpServletRequest req) {
		BillProductDTO billProductDTO = billProductService.get(id);
		if (billProductDTO != null) {
			billProductService.delete(billProductDTO.getId());
		}
		return "redirect:/admin/bill-product/search";
	}

	@GetMapping(value = "/admin/bill-product/search-by-bill-id")
	public String searchBillProductByBillId(HttpServletRequest req,
			@RequestParam(name = "billId", required = false) String id) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));
		if (id != null && !id.equals("null") && !id.equals("")) {
			List<BillProductDTO> listBillProductDTOss = billProductService.searchByBillId(Integer.valueOf(id),
					(page - 1) * 6, 6);
			long count = listBillProductDTOss.size();
			double result = Math.ceil((double) count / 6);
			List<BillProductDTO> listBillProductDTOs = billProductService.searchByBillId(Integer.valueOf(id),
					(page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("id", id);
			req.setAttribute("billProList", listBillProductDTOs);
		} else {
			long count = billProductService.getCount();
			double result = Math.ceil((double) count / 6);
			List<BillProductDTO> listBillProductDTOs = billProductService.getAll((page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("billProList", listBillProductDTOs);
		}
		return "admin/billproduct/listBillProductByBillid";
	}

	@GetMapping(value = "/admin/bill-product/search")
	public String searchBillProduct(HttpServletRequest req,
			@RequestParam(name = "name", required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));

		if (name != null && !name.equals("null") && !name.equals("")) {
			long count = billProductService.getCountSearch(name);
			System.out.println(count);
			double result = Math.ceil((double) count / 6);
			List<BillProductDTO> list = billProductService.searchByName(name, (page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
			req.setAttribute("billProList", list);
		} else {
			long count = billProductService.getCount();
			double result = Math.ceil((double) count / 6);
			List<BillProductDTO> list = billProductService.getAll((page - 1) * 6, 6);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("billProList", list);
		}
		return "admin/billproduct/listBillProduct";
	}
}
