package com.trungtamjava.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.CategoryDao;
import com.trungtamjava.entity.Category;
import com.trungtamjava.model.SearchCategoryDTO;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Category> getAllCategorys(int start, int length) {
		String jql = "SELECT c from Category c";
		return entityManager.createQuery(jql, Category.class).setFirstResult(start).setMaxResults(length)
				.getResultList();
	}

	@Override
	public void addCategory(Category category) {
		entityManager.persist(category);

	}

	@Override
	public void deleteCategory(Category category) {
		entityManager.remove(category);

	}

	@Override
	public void updateCategory(Category category) {
		entityManager.merge(category);

	}

	@Override
	public long countGetAll() {
		String jql = "select count (c) from Category c";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}

	@Override
	public long countSearch(String name) {
		String jql = "select count(c) from Category c where c.name  =:name";
		return entityManager.createQuery(jql, Long.class).setParameter("name", name).getSingleResult();
	}

	@Override
	public List<Category> getByName(String name, int start, int lenght) {
		String jql = "select c from Category c where c.name  =:name";
		return entityManager.createQuery(jql, Category.class).setParameter("name", name).setFirstResult(start)
				.setMaxResults(lenght).getResultList();

	}

	@Override
	public Category get(int id) {
		return entityManager.find(Category.class, id);
	}

	@Override
	public List<Category> find(SearchCategoryDTO searchCategoryDTO) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
		Root<Category> root = criteriaQuery.from(Category.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchCategoryDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchCategoryDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "id")) {
			if (searchCategoryDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "name")) {
			if (searchCategoryDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
			}
		}

		TypedQuery<Category> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if (searchCategoryDTO.getStart() != null) {
			typedQuery.setFirstResult((searchCategoryDTO.getStart()));
			typedQuery.setMaxResults(searchCategoryDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Category> root = criteriaQuery.from(Category.class);

		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (StringUtils.isNotBlank(searchCategoryDTO.getKeyword())) {
			Predicate predicate1 = builder.like(builder.lower(root.get("name")),
					"%" + searchCategoryDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate1);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Category> root = criteriaQuery.from(Category.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

}
