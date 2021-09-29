package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.ColorDTO;

public interface ColorService {
	public List<ColorDTO> getAllColor(int start, int length);

	public List<ColorDTO> getAllColorByName(String name, int start, int length);

	public void addColor(ColorDTO colorDTO);

	public void deleteColor(int id);

	public void updateColor(ColorDTO colorDTO);

	public ColorDTO getColor(int id);

	long countGetAll();

	long countSearch(String name);
}
