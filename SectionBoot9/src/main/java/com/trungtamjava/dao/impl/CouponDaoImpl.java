package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.CouponsDao;
import com.trungtamjava.entity.Coupon;

@Repository
@Transactional
public class CouponDaoImpl implements CouponsDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Coupon getByName(String name, int start, int lenght) {
		String jql = "SELECT c FROM Coupon c where c.name = :name";
		return entityManager.createQuery(jql, Coupon.class).setParameter("name", name).setFirstResult(start)
				.setMaxResults(lenght).getSingleResult();
	}

	@Override
	public Coupon get(int id) {
		String jql = "select c from Coupon c where c.id = :id";
		return entityManager.createQuery(jql, Coupon.class).setParameter("id", id).getSingleResult();
	}

	@Override
	public void delete(Coupon coupon) {
		entityManager.remove(coupon);

	}

	@Override
	public void update(Coupon coupon) {
		entityManager.merge(coupon);
	}

	@Override
	public void insert(Coupon coupon) {
		entityManager.persist(coupon);
	}

	@Override
	public List<Coupon> getAll(int start, int lenght) {
		String jql = "select c from Coupon c";
		return entityManager.createQuery(jql, Coupon.class).setFirstResult(start).setMaxResults(lenght).getResultList();

	}

	@Override
	public long countAll() {
		String jql = "select count(c) from Coupon c";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}

	@Override
	public long countSearch(String name) {

		String jql = "select count(c) from Coupon c where c.name = :name";
		return entityManager.createQuery(jql, Long.class).setParameter("name", name).getSingleResult();
	}

	@Override
	public List<Coupon> getListByName(String name, int start, int lenght) {
		String jql = "select c from Coupon c where c.name =:name";
		return entityManager.createQuery(jql, Coupon.class).setParameter("name", name).setFirstResult(start)
				.setMaxResults(lenght).getResultList();
	}

}
