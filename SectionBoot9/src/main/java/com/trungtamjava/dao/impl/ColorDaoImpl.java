package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.ColorDao;
import com.trungtamjava.entity.Color;

@Repository
@Transactional
public class ColorDaoImpl implements ColorDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Color> getAllColor(int start, int length) {
		String jql = "select c from Color c";
		return entityManager.createQuery(jql, Color.class).setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public List<Color> getAllColorByName(String name, int start, int length) {
		String jql = "select c from Color c where c.name =: name";
		return entityManager.createQuery(jql, Color.class).setParameter("name", name).setFirstResult(start)
				.setMaxResults(length).getResultList();
	}

	@Override
	public void addColor(Color color) {
		entityManager.persist(color);
	}

	@Override
	public void deleteColor(Color color) {
		entityManager.remove(color);
	}

	@Override
	public void updateColor(Color color) {
		entityManager.merge(color);
	}

	@Override
	public Color getColor(int id) {
		String jql = "select c from Color c where c.id =: id";
		return entityManager.createQuery(jql, Color.class).setParameter("id", id).getSingleResult();
	}

	@Override
	public long countGetAll() {
		String jql = "select  count(c) from Color c ";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}

	@Override
	public long countSearch(String name) {
		String jql = "select  count(c) from Color c where c.name =: name ";

		return entityManager.createQuery(jql, Long.class).setParameter("name", name).getSingleResult();
	}

}
