package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.ProductDao;
import com.trungtamjava.entity.BillProduct;
import com.trungtamjava.entity.Category;
import com.trungtamjava.entity.Color;
import com.trungtamjava.entity.Product;
import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.ColorDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	@Override
	public List<ProductDTO> getAllProductHighlight() {
		List<Product> products = productDao.getAllProductHighlight();
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product pp : products) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(pp.getName());
			productDTO.setCreated_at(pp.getCreatedAt());
			productDTO.setUpdated_at(pp.getUpdatedAt());
			productDTO.setDetails(pp.getDetails());
			productDTO.setHighlight(pp.isHighlight());
			productDTO.setNew_product(pp.isNewProduct());
			productDTO.setId(pp.getId());
			productDTO.setPrice(pp.getPrice());
			productDTO.setQuantity(pp.getQuantity());
			productDTO.setImg(pp.getImg());
			// get img
			List<ColorDTO> lisColorDTOs = new ArrayList<ColorDTO>();
			List<Color> colors = pp.getColors();
			for (Color cc : colors) {
				ColorDTO colorDTO = new ColorDTO();
				colorDTO.setName(cc.getName());
				colorDTO.setCode(cc.getCode());

				lisColorDTOs.add(colorDTO);
			}
			productDTO.setListColorDTO(lisColorDTOs);

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(categoryDTO.getId());
			categoryDTO.setName(categoryDTO.getName());
			categoryDTO.setDescription(categoryDTO.getDescription());
			productDTO.setCategoryDTO(categoryDTO);

			productDTOs.add(productDTO);

		}

		return productDTOs;
	}

	@Override
	public List<ProductDTO> getAllNewProduct() {
		List<Product> products2 = productDao.getAllNewProduct();
		List<ProductDTO> productDTOs2 = new ArrayList<ProductDTO>();
		for (Product pp : products2) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(pp.getName());
			productDTO.setCreated_at(pp.getCreatedAt());
			productDTO.setUpdated_at(pp.getUpdatedAt());
			productDTO.setDetails(pp.getDetails());
			productDTO.setHighlight(pp.isHighlight());
			productDTO.setNew_product(pp.isNewProduct());
			productDTO.setId(pp.getId());
			productDTO.setPrice(pp.getPrice());
			productDTO.setSizes(pp.getSizes());
			productDTO.setQuantity(pp.getQuantity());
			productDTO.setImg(pp.getImg());
			// get img
			List<ColorDTO> lisColorDTOs = new ArrayList<ColorDTO>();
			List<Color> colors = pp.getColors();
			for (Color cc : colors) {
				ColorDTO colorDTO = new ColorDTO();

				colorDTO.setName(cc.getName());
				colorDTO.setCode(cc.getCode());

				lisColorDTOs.add(colorDTO);
			}
			productDTO.setListColorDTO(lisColorDTOs);

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(categoryDTO.getId());
			categoryDTO.setName(categoryDTO.getName());
			categoryDTO.setDescription(categoryDTO.getDescription());

			productDTO.setCategoryDTO(categoryDTO);

			productDTOs2.add(productDTO);

		}

		return productDTOs2;
	}

	@Override
	public void addProduct(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setCreatedAt(productDTO.getCreated_at());
		product.setUpdatedAt(productDTO.getUpdated_at());
		product.setDetails(productDTO.getDetails());
		product.setPrice(productDTO.getPrice());
		product.setNewProduct(productDTO.isNew_product());
		product.setUpcoming(productDTO.isUpcoming());
		product.setHighlight(productDTO.isHighlight());
		product.setTitle(productDTO.getTitle());
		product.setSizes(productDTO.getSizes());
		product.setQuantity(productDTO.getQuantity());
		product.setImg(productDTO.getImg());

		Category category = new Category();
		category.setId(productDTO.getCategoryDTO().getId());
		category.setName(productDTO.getCategoryDTO().getName());
		category.setDescription(productDTO.getCategoryDTO().getDescription());

		product.setCategory(category);

		productDao.addProduct(product);

	}

	@Override
	public void updateProduct(ProductDTO productDTO) {
		Product product = productDao.getProductById(productDTO.getId());
		if (product != null) {
			product.setName(productDTO.getName());
			product.setCreatedAt(productDTO.getCreated_at());
			product.setUpdatedAt(productDTO.getUpdated_at());
			product.setDetails(productDTO.getDetails());
			product.setPrice(productDTO.getPrice());
			product.setNewProduct(productDTO.isNew_product());
			product.setHighlight(productDTO.isHighlight());
			product.setTitle(productDTO.getTitle());
			product.setSizes(productDTO.getSizes());
			product.setQuantity(productDTO.getQuantity());
			product.setImg(productDTO.getImg());
			product.setUpcoming(productDTO.isUpcoming());

			Category category = new Category();
			category.setId(productDTO.getCategoryDTO().getId());
			category.setName(productDTO.getCategoryDTO().getName());
			category.setDescription(productDTO.getCategoryDTO().getDescription());

			product.setCategory(category);

			productDao.updateProduct(product);

		}

	}

	@Override
	public void deleteProduct(long id) {
		Product product = productDao.getProductById(id);
		List<Color> colors = product.getColors();
		colors.removeAll(colors);
		if (product != null) {
			productDao.deleteProduct(product);
		}

	}

	@Override
	public ProductDTO getProductById(long id) {
		Product pp = productDao.getProductById(id);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName(pp.getName());
		productDTO.setCreated_at(pp.getCreatedAt());
		productDTO.setUpdated_at(pp.getUpdatedAt());
		productDTO.setDetails(pp.getDetails());
		productDTO.setHighlight(pp.isHighlight());
		productDTO.setNew_product(pp.isNewProduct());
		productDTO.setId(pp.getId());
		productDTO.setPrice(pp.getPrice());
		productDTO.setSizes(pp.getSizes());
		productDTO.setTitle(pp.getTitle());
		productDTO.setQuantity(pp.getQuantity());
		productDTO.setImg(pp.getImg());

		List<ColorDTO> lisColorDTOs = new ArrayList<ColorDTO>();
		List<Color> colors = pp.getColors();
		for (Color cc : colors) {
			ColorDTO colorDTO = new ColorDTO();

			colorDTO.setName(cc.getName());
			colorDTO.setCode(cc.getCode());

			lisColorDTOs.add(colorDTO);
		}
		productDTO.setListColorDTO(lisColorDTOs);

		CategoryDTO categoryDTO = new CategoryDTO();

		categoryDTO.setId(pp.getCategory().getId());
		categoryDTO.setName(pp.getCategory().getName());
		categoryDTO.setDescription(pp.getCategory().getDescription());

		productDTO.setCategoryDTO(categoryDTO);

		List<BillProductDTO> billProductDTOs = new ArrayList<BillProductDTO>();
		List<BillProduct> billProducts = pp.getBillProducts();
		for (BillProduct bi : billProducts) {
			BillProductDTO billProductDTO = new BillProductDTO();
			billProductDTO.setQuantity(bi.getQuantity());
			billProductDTO.setUnitPrice(bi.getUnitPrice());
			billProductDTOs.add(billProductDTO);
		}
		productDTO.setLBillProductDTOs(billProductDTOs);
		return productDTO;
	}

	@Override
	public List<ProductDTO> searchByCateId(int cateId, int start, int length) {
		List<Product> products = productDao.getByCate(cateId, start, length);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product pp : products) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(pp.getName());
			productDTO.setCreated_at(pp.getCreatedAt());
			productDTO.setUpdated_at(pp.getUpdatedAt());
			productDTO.setDetails(pp.getDetails());
			productDTO.setHighlight(pp.isHighlight());
			productDTO.setNew_product(pp.isNewProduct());
			productDTO.setId(pp.getId());
			productDTO.setPrice(pp.getPrice());
			productDTO.setSizes(pp.getSizes());
			productDTO.setTitle(pp.getTitle());
			productDTO.setQuantity(pp.getQuantity());
			productDTO.setImg(pp.getImg());

			List<ColorDTO> lisColorDTOs = new ArrayList<ColorDTO>();
			List<Color> colors = pp.getColors();
			for (Color cc : colors) {
				ColorDTO colorDTO = new ColorDTO();
				colorDTO.setName(cc.getName());
				colorDTO.setCode(cc.getCode());

				lisColorDTOs.add(colorDTO);
			}
			productDTO.setListColorDTO(lisColorDTOs);

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(pp.getCategory().getId());
			categoryDTO.setName(pp.getCategory().getName());
			categoryDTO.setDescription(pp.getCategory().getDescription());

			productDTO.setCategoryDTO(categoryDTO);

			productDTOs.add(productDTO);

		}

		return productDTOs;
	}

	@Override
	public long countSearch(String name) {
		long count = productDao.countSearch(name);
		return count;
	}

	@Override
	public long countGetAll() {
		long count = productDao.countGetAll();
		return count;
	}

	@Override
	public List<ProductDTO> search(String name, int start, int lenght) {
		List<Product> products = productDao.getByName(name, start, lenght);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product pp : products) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(pp.getName());
			productDTO.setCreated_at(pp.getCreatedAt());
			productDTO.setUpdated_at(pp.getUpdatedAt());
			productDTO.setDetails(pp.getDetails());
			productDTO.setHighlight(pp.isHighlight());
			productDTO.setNew_product(pp.isNewProduct());
			productDTO.setId(pp.getId());
			productDTO.setPrice(pp.getPrice());
			productDTO.setSizes(pp.getSizes());
			productDTO.setQuantity(pp.getQuantity());
			productDTO.setImg(pp.getImg());

			List<ColorDTO> lisColorDTOs = new ArrayList<ColorDTO>();
			List<Color> colors = pp.getColors();
			for (Color cc : colors) {
				ColorDTO colorDTO = new ColorDTO();
				colorDTO.setName(cc.getName());
				colorDTO.setCode(cc.getCode());

				lisColorDTOs.add(colorDTO);
			}
			productDTO.setListColorDTO(lisColorDTOs);

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(pp.getCategory().getId());
			categoryDTO.setName(pp.getCategory().getName());
			categoryDTO.setDescription(pp.getCategory().getDescription());

			productDTO.setCategoryDTO(categoryDTO);

			productDTOs.add(productDTO);

		}
		return productDTOs;

	}

	@Override
	public List<ProductDTO> getAll(int start, int length) {
		List<Product> products = productDao.getAll(start, length);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product pp : products) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(pp.getName());
			productDTO.setCreated_at(pp.getCreatedAt());
			productDTO.setUpdated_at(pp.getUpdatedAt());
			productDTO.setDetails(pp.getDetails());
			productDTO.setHighlight(pp.isHighlight());
			productDTO.setNew_product(pp.isNewProduct());
			productDTO.setUpcoming(pp.isUpcoming());
			productDTO.setId(pp.getId());
			productDTO.setPrice(pp.getPrice());
			productDTO.setSizes(pp.getSizes());
			productDTO.setQuantity(pp.getQuantity());
			productDTO.setImg(pp.getImg());

			List<ColorDTO> lisColorDTOs = new ArrayList<ColorDTO>();
			List<Color> colors = pp.getColors();
			for (Color cc : colors) {
				ColorDTO colorDTO = new ColorDTO();
				colorDTO.setName(cc.getName());
				colorDTO.setCode(cc.getCode());

				lisColorDTOs.add(colorDTO);
			}
			productDTO.setListColorDTO(lisColorDTOs);

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(pp.getCategory().getId());
			categoryDTO.setName(pp.getCategory().getName());
			categoryDTO.setDescription(pp.getCategory().getDescription());

			productDTO.setCategoryDTO(categoryDTO);

			productDTOs.add(productDTO);

		}
		return productDTOs;

	}

	@Override
	public List<ProductDTO> getAllUpcomingProduct() {
		List<Product> products = productDao.getAllUpcomingProduct();
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product pp : products) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(pp.getName());
			productDTO.setCreated_at(pp.getCreatedAt());
			productDTO.setUpdated_at(pp.getUpdatedAt());
			productDTO.setDetails(pp.getDetails());
			productDTO.setHighlight(pp.isHighlight());
			productDTO.setNew_product(pp.isNewProduct());
			productDTO.setUpcoming(pp.isUpcoming());
			productDTO.setId(pp.getId());
			productDTO.setPrice(pp.getPrice());
			productDTO.setQuantity(pp.getQuantity());
			productDTO.setImg(pp.getImg());

			List<ColorDTO> lisColorDTOs = new ArrayList<ColorDTO>();
			List<Color> colors = pp.getColors();
			for (Color cc : colors) {
				ColorDTO colorDTO = new ColorDTO();
				colorDTO.setName(cc.getName());
				colorDTO.setCode(cc.getCode());
				lisColorDTOs.add(colorDTO);
			}
			productDTO.setListColorDTO(lisColorDTOs);

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(categoryDTO.getId());
			categoryDTO.setName(categoryDTO.getName());
			categoryDTO.setDescription(categoryDTO.getDescription());
			productDTO.setCategoryDTO(categoryDTO);

			productDTOs.add(productDTO);

		}
		return productDTOs;
	}
}
