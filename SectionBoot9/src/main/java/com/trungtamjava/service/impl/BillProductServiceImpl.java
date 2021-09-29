package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.BillProductDao;
import com.trungtamjava.entity.Bill;
import com.trungtamjava.entity.BillProduct;
import com.trungtamjava.entity.Product;
import com.trungtamjava.model.BillDTO;
import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.service.BillProductService;

@Service
@Transactional
public class BillProductServiceImpl implements BillProductService {
	@Autowired
	BillProductDao billProductDao;

	@Override
	public List<BillProductDTO> searchByBillId(int id, int start, int length) {
		List<BillProduct> billProducts = billProductDao.searchByBillId(id, start, length);
		List<BillProductDTO> billProductDTOs = new ArrayList<BillProductDTO>();
		for (BillProduct bp : billProducts) {
			BillProductDTO billProductDTO = new BillProductDTO();
			billProductDTO.setId(bp.getId());
			billProductDTO.setQuantity(bp.getQuantity());
			billProductDTO.setUnitPrice(bp.getUnitPrice());

			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(bp.getProduct().getName());
			productDTO.setId(bp.getProduct().getId());
			productDTO.setPrice(bp.getProduct().getPrice());

			BillDTO billDTO = new BillDTO();
			billDTO.setId(bp.getBill().getId());

			billProductDTO.setProductDTO(productDTO);
			billProductDTO.setBillDTO(billDTO);

			billProductDTOs.add(billProductDTO);

		}

		return billProductDTOs;
	}

	@Override
	public List<BillProductDTO> searchByName(String name, int start, int length) {
		List<BillProduct> billProducts = billProductDao.searchByName(name, start, length);
		List<BillProductDTO> billProductDTOs = new ArrayList<BillProductDTO>();
		for (BillProduct bp : billProducts) {
			BillProductDTO billProductDTO = new BillProductDTO();
			billProductDTO.setId(bp.getId());
			billProductDTO.setQuantity(bp.getQuantity());
			billProductDTO.setUnitPrice(bp.getUnitPrice());

			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(bp.getProduct().getName());
			productDTO.setId(bp.getProduct().getId());
			productDTO.setPrice(bp.getProduct().getPrice());

			BillDTO billDTO = new BillDTO();
			billDTO.setId(bp.getBill().getId());

			billProductDTO.setProductDTO(productDTO);
			billProductDTO.setBillDTO(billDTO);

			billProductDTOs.add(billProductDTO);

		}

		return billProductDTOs;
	}

	@Override
	public BillProductDTO get(int id) {
		BillProduct billProduct = billProductDao.get(id);
		BillProductDTO billProductDTO = new BillProductDTO();
		billProductDTO.setId(billProduct.getId());
		billProductDTO.setQuantity(billProduct.getQuantity());
		billProductDTO.setUnitPrice(billProduct.getUnitPrice());

		ProductDTO productDTO = new ProductDTO();
		productDTO.setName(billProduct.getProduct().getName());
		productDTO.setId(billProduct.getProduct().getId());
		productDTO.setPrice(billProduct.getProduct().getPrice());

		BillDTO billDTO = new BillDTO();
		billDTO.setId(billProduct.getBill().getId());

		billProductDTO.setProductDTO(productDTO);
		billProductDTO.setBillDTO(billDTO);

		return billProductDTO;
	}

	@Override
	public void delete(int id) {
		BillProduct billProduct = billProductDao.get(id);
		if (billProduct != null) {
			billProductDao.delete(billProduct);
		}

	}

	@Override
	public void update(BillProductDTO billProductDTO) {
		BillProduct billProduct = billProductDao.get(billProductDTO.getId());
		if (billProduct != null) {
			billProduct.setQuantity(billProductDTO.getQuantity());
			billProduct.setUnitPrice(billProductDTO.getUnitPrice());

			Product product = new Product();
			product.setId(billProductDTO.getProductDTO().getId());

			Bill bill = new Bill();
			bill.setId(billProductDTO.getBillDTO().getId());

			billProduct.setBill(bill);
			billProduct.setProduct(product);

		}
		billProductDao.update(billProduct);

	}

	@Override
	public void insert(BillProductDTO billProductDTO) {

		BillProduct billProduct = new BillProduct();
		billProduct.setQuantity(billProductDTO.getQuantity());
		billProduct.setUnitPrice(billProductDTO.getUnitPrice());

		Product product = new Product();
		product.setId(billProductDTO.getProductDTO().getId());
		product.setName(billProductDTO.getProductDTO().getName());
		product.setImg(billProductDTO.getProductDTO().getImg());

		Bill bill = new Bill();
		bill.setId(billProductDTO.getBillDTO().getId());

		billProduct.setBill(bill);
		billProduct.setProduct(product);

		billProductDao.insert(billProduct);

	}

	@Override
	public List<BillProductDTO> getAll(int start, int length) {
		List<BillProduct> billProducts = billProductDao.getAll(start, length);
		List<BillProductDTO> billProductDTOs = new ArrayList<BillProductDTO>();
		for (BillProduct bp : billProducts) {
			BillProductDTO billProductDTO = new BillProductDTO();
			billProductDTO.setId(bp.getId());
			billProductDTO.setQuantity(bp.getQuantity());
			billProductDTO.setUnitPrice(bp.getUnitPrice());

			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(bp.getProduct().getName());
			productDTO.setId(bp.getProduct().getId());
			productDTO.setPrice(bp.getProduct().getPrice());

			BillDTO billDTO = new BillDTO();
			billDTO.setId(bp.getBill().getId());

			billProductDTO.setProductDTO(productDTO);
			billProductDTO.setBillDTO(billDTO);

			billProductDTOs.add(billProductDTO);

		}

		return billProductDTOs;

	}

	@Override
	public long getCountSearch(String name) {
		long count = billProductDao.getCountSearch(name);
		return count;
	}

	@Override
	public long getCount() {
		long count = billProductDao.getCount();
		return count;
	}
}
