package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Menu;

public interface MenuDao {
	public List<Menu> getAllMenus();

	public void addMenus(Menu menu);

	public void deleteMenus(Menu menu);

	public void updateMenus(Menu menu);
	
	public Menu getMenus(int id);
}
