package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.BillDao;
import com.trungtamjava.entity.Bill;

@Repository
@Transactional
public class BillDaoImpl implements BillDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Bill> searchByBuyerId(int id) {
		String jql = "select b from Bill b join b.buyer u where u.id like =:uid ";
		return entityManager.createQuery(jql, Bill.class).setParameter("uid", id).getResultList();
	}

	@Override
	public List<Bill> searchByBuyerName(String name, int start, int lenght) {
		String jql = "select b from Bill b join b.buyer u where b.nameReceiver like =:uname ";
		return entityManager.createQuery(jql, Bill.class).setParameter("uname", name).setFirstResult(start)
				.setMaxResults(lenght).getResultList();
	}

	@Override
	public Bill get(int id) {
		return entityManager.find(Bill.class, id);
	}

	@Override
	public void delete(Bill bill) {
		entityManager.remove(bill);

	}

	@Override
	public void update(Bill bill) {
		entityManager.merge(bill);
	}

	@Override
	public void insert(Bill bill) {
		entityManager.persist(bill);
	}

	@Override
	public long countAll() {
		String jql = "select count(b) from Bill b";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}

	@Override
	public long countSearch(String name) {
		String jql = "select count(b) from Bill b join b.buyer u where b.nameReceiver like :uname";
		return entityManager.createQuery(jql, Long.class).setParameter("uname", name).getSingleResult();

	}

	@Override
	public List<Bill> getAllbyName(String name, int start, int lenght) {
		String jql = "select b from Bill b join b.buyer u where b.nameReceiver like :uname";
		return entityManager.createQuery(jql, Bill.class).setParameter("uname", name).setFirstResult(start)
				.setMaxResults(lenght).getResultList();
	}

	@Override
	public List<Bill> getAll(int start, int lenght) {
		String jql = "select b from Bill b ";
		return entityManager.createQuery(jql, Bill.class).setFirstResult(start).setMaxResults(lenght).getResultList();
	}

	@Override
	public void setStatus(Bill bill) {
		entityManager.merge(bill);
	}

}
