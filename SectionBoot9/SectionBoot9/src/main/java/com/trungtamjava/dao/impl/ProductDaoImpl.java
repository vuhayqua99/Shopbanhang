package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.ProductDao;
import com.trungtamjava.entity.Product;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Product> getAllProductHighlight() {
		String jql = "select p from Product p where p.highlight = true order by rand() "; // gruop
																							// by
																							// p.id,c.product.id
		return entityManager.createQuery(jql, Product.class).setMaxResults(9).getResultList();
	}

	@Override
	public List<Product> getAllNewProduct() {
		String jql = "select p from Product p  where p.newProduct = true order by rand() "; // gruop
																							// by
																							// p.id,c.product.id
		return entityManager.createQuery(jql, Product.class).setMaxResults(3).getResultList();
	}

	@Override
	public void addProduct(Product product) {
		entityManager.persist(product);
	}

	@Override
	public void updateProduct(Product product) {
		entityManager.merge(product);
	}

	@Override
	public void deleteProduct(Product product) {
		entityManager.remove(product);
	}

	@Override
	public List<Product> getByName(String name, int start, int lenght) {
		String jql = "select p from Product p join p.category c  where p.name =:name";
		return entityManager.createQuery(jql, Product.class).setParameter("name", name).setFirstResult(start)
				.setMaxResults(lenght).getResultList();
	}

	public List<Product> getByCate(int cateId, int start, int length) {
		String jql = "select p from Product p join p.category c where c.id =:cId";
		return entityManager.createQuery(jql, Product.class).setParameter("cId", cateId).setFirstResult(start)
				.setMaxResults(length).getResultList();
	}

	@Override
	public long countSearch(String name) {
		String jql = "SELECT count(p) FROM Product p WHERE p.name =:name";
		return entityManager.createQuery(jql, Long.class).setParameter("name", name).getSingleResult();
	}

	@Override
	public long countGetAll() {
		String jql = "SELECT count(p) FROM Product p";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}

	@Override
	public Product getProductById(long id) {
		return entityManager.find(Product.class, id);
	}

	@Override
	public List<Product> getAll(int start, int length) {
		String jql = "select p from Product p ";
		return entityManager.createQuery(jql, Product.class).setFirstResult(start).setMaxResults(length)
				.getResultList();
	}

	@Override
	public List<Product> getAllUpcomingProduct() {
		String jql = "select p from Product p  where p.upcoming = true order by rand() ";

		return entityManager.createQuery(jql, Product.class).setMaxResults(3).getResultList();
	}

}
