package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.BillProductDao;
import com.trungtamjava.entity.BillProduct;

@Repository
@Transactional
public class BillProductDaoImpl implements BillProductDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<BillProduct> searchByBillId(int id, int start, int length) {
		String jql = "select bp from BillProduct bp join bp.bill b where b.id =:bid ";
		return entityManager.createQuery(jql, BillProduct.class).setParameter("bid", id).setFirstResult(start)
				.setMaxResults(length).getResultList();
	}

	@Override
	public List<BillProduct> searchByName(String name, int start, int length) {
		String jql = "select bp from BillProduct bp join bp.bill b join bp.product p where p.name =:name ";
		return entityManager.createQuery(jql, BillProduct.class).setParameter("name", name).setFirstResult(start)
				.setMaxResults(length).getResultList();
	}

	@Override
	public BillProduct get(int id) {
		String jql = "select bp from BillProduct bp join bp.bill b join bp.product p where bp.id =:id ";
		return entityManager.createQuery(jql, BillProduct.class).setParameter("id", id).getSingleResult();
	}

	@Override
	public void delete(BillProduct billProduct) {
		entityManager.remove(billProduct);

	}

	@Override
	public void update(BillProduct billProduct) {
		entityManager.merge(billProduct);

	}

	@Override
	public void insert(BillProduct billProduct) {
		entityManager.persist(billProduct);
	}

	@Override
	public List<BillProduct> getAll(int start, int length) {
		String jql = "select bp from BillProduct bp join bp.bill b join bp.product p  ";
		return entityManager.createQuery(jql, BillProduct.class).setFirstResult(start).setMaxResults(length)
				.getResultList();
	}

	@Override
	public long getCountSearch(String name) {
		String jql = "select count(bp) from BillProduct bp join bp.bill b join bp.product p where p.name =:pname ";
		return entityManager.createQuery(jql, Long.class).setParameter("pname", name).getSingleResult();
	}

	@Override
	public long getCount() {
		String jql = "select count(bp) from BillProduct bp";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}
}
