package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.MenuDao;
import com.trungtamjava.entity.Menu;
import com.trungtamjava.model.MenuDTO;
import com.trungtamjava.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	@Autowired
	MenuDao menuDao;

	@Override
	public List<MenuDTO> getAllMenus() {
		List<Menu> menus = menuDao.getAllMenus();
		List<MenuDTO> menuDTOs = new ArrayList<MenuDTO>();
		for (Menu me : menus) {
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setName(me.getName());
			menuDTO.setUrl(me.getUrl());

			menuDTOs.add(menuDTO);
		}

		return menuDTOs;
	}

	@Override
	public void addMenus(MenuDTO menuDTO) {
		Menu menu = new Menu();
		menu.setName(menuDTO.getName());
		menu.setUrl(menuDTO.getUrl());

		menuDao.addMenus(menu);

	}

	@Override
	public void deleteMenus(int id) {
		Menu menu = menuDao.getMenus(id);
		if (menu != null) {
			menuDao.deleteMenus(menu);

		}

	}

	@Override
	public void updateMenus(MenuDTO menuDTO) {
		Menu menu = menuDao.getMenus(menuDTO.getId());
		if (menu != null) {
			menuDao.updateMenus(menu);
		}
	}

	@Override
	public MenuDTO getMenus(int id) {
		Menu menu = menuDao.getMenus(id);
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setName(menu.getName());
		menuDTO.setUrl(menu.getUrl());

		return menuDTO;
	}

}
