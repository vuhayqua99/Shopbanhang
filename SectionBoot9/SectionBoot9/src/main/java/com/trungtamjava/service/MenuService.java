package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.entity.Menu;
import com.trungtamjava.model.MenuDTO;

public interface MenuService {
	public List<MenuDTO> getAllMenus();

	public void addMenus(MenuDTO menuDTO);

	public void deleteMenus(int id);

	public void updateMenus(MenuDTO menuDTO);
	
	public MenuDTO getMenus(int id);
}
