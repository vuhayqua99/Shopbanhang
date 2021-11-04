package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.ProductDTO;

public interface ProductService {
	public List<ProductDTO> getAllProductHighlight();

	public List<ProductDTO> getAllNewProduct();
	
	public List<ProductDTO> getAllUpcomingProduct();
	
	List<ProductDTO> getAll(int start, int length);

	public void addProduct(ProductDTO productDTO);

	public void updateProduct(ProductDTO productDTO);

	public void deleteProduct(long id);

	public ProductDTO getProductById(long id);

	List<ProductDTO> searchByCateId(int cateId, int start, int length);

	long countSearch(String name);

	long countGetAll();

	List<ProductDTO> search(String name, int start, int lenght);
	
	
	

}
