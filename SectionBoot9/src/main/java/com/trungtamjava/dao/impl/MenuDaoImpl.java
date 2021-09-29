package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.MenuDao;
import com.trungtamjava.entity.Menu;

@Transactional
@Repository // dung cho lop dao
public class MenuDaoImpl implements MenuDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Menu> getAllMenus() {
		String jql = "SELECT E FROM Menu E ";
		return entityManager.createQuery(jql, Menu.class).getResultList();
	}

	@Override
	public void addMenus(Menu menu) {
		entityManager.persist(menu);
	}

	@Override
	public void deleteMenus(Menu menu) {
		entityManager.remove(menu);
	}

	@Override
	public void updateMenus(Menu menu) {
		entityManager.merge(menu);
	}

	@Override
	public Menu getMenus(int id) {
		return entityManager.find(Menu.class, id);
	}

}
