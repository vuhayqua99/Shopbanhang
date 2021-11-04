package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Color;

public interface ColorDao {
	public List<Color> getAllColor(int start, int length);

	public List<Color> getAllColorByName(String name, int start, int length);

	public void addColor(Color color);

	public void deleteColor(Color color);

	public void updateColor(Color color);

	public Color getColor(int id);

	long countGetAll();

	long countSearch(String name);

}
