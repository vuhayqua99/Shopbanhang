package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Product;

public interface ProductDao {
	List<Product> getByName(String name, int start, int lenght);

	List<Product> getByCate(int cateId, int start, int length);

	List<Product> getAll(int start, int length);

	public List<Product> getAllProductHighlight();

	public List<Product> getAllNewProduct();

	public List<Product> getAllUpcomingProduct();

	public void addProduct(Product product);

	public void updateProduct(Product product);

	public void deleteProduct(Product product);

	public Product getProductById(long id);

	long countSearch(String name);

	long countGetAll();

}
