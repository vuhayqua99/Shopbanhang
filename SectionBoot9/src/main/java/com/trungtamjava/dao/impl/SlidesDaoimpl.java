package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.SlidesDao;
import com.trungtamjava.entity.Slide;

@Transactional
@Repository // dung cho lop dao
public class SlidesDaoimpl implements SlidesDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Slide> getAllSlides() {
		String jql = "SELECT e from Slide e";
		return entityManager.createQuery(jql, Slide.class).getResultList();
	}

	@Override
	public void addSlides(Slide slide) {
		entityManager.persist(slide);
	}

	@Override
	public void deleteSlides(Slide slide) {
		entityManager.remove(slide);
	}

	@Override
	public void updateSlides(Slide slide) {
		entityManager.merge(slide);
	}

	@Override
	public Slide getSlides(int id) {

		return entityManager.find(Slide.class, id);

	}

}
