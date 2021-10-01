package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.ColorDao;
import com.trungtamjava.entity.Color;
import com.trungtamjava.entity.Product;
import com.trungtamjava.model.ColorDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.service.ColorService;

@Service
@Transactional
public class ColorServiceImpl implements ColorService {
	@Autowired
	ColorDao colorDao;

	@Override
	public List<ColorDTO> getAllColor(int start, int length) {
		List<Color> colors = colorDao.getAllColor(start, length);
		List<ColorDTO> colorDTOs = new ArrayList<ColorDTO>();
		colors.forEach(color -> {
			colorDTOs.add(convertColorDTO(color));
		});

		return colorDTOs;
	}

	@Override
	public List<ColorDTO> getAllColorByName(String name, int start, int length) {
		List<Color> colors = colorDao.getAllColorByName(name, start, length);
		List<ColorDTO> colorDTOs = new ArrayList<ColorDTO>();
		colors.forEach(color -> {
			colorDTOs.add(convertColorDTO(color));
		});

		return colorDTOs;
	}

	@Override
	public void addColor(ColorDTO colorDTO) {
		Color color = new Color();
		color.setName(colorDTO.getName());
		color.setCode(colorDTO.getCode());

		Product product = new Product();
		product.setId(colorDTO.getProductDTO().getId());
		product.setName(colorDTO.getProductDTO().getName());
		product.setHighlight(colorDTO.getProductDTO().isHighlight());
		product.setNewProduct(colorDTO.getProductDTO().isNew_product());
		product.setUpcoming(colorDTO.getProductDTO().isUpcoming());

		color.setProduct(product);

		colorDao.addColor(color);

	}

	@Override
	public void deleteColor(int id) {
		Color color = colorDao.getColor(id);
		if (color != null) {
			colorDao.deleteColor(color);
		}
	}

	@Override
	public void updateColor(ColorDTO colorDTO) {
		Color color = colorDao.getColor(colorDTO.getId());
		if (color != null) {
			color.setName(colorDTO.getName());
			color.setCode(colorDTO.getCode());
			colorDao.updateColor(color);
		}
	}

	@Override
	public ColorDTO getColor(int id) {
		Color c = colorDao.getColor(id);
		return convertColorDTO(c);
	}

	@Override
	public long countGetAll() {
		long count = colorDao.countGetAll();
		return count;
	}

	@Override
	public long countSearch(String name) {
		long count = colorDao.countSearch(name);

		return count;
	}

	@SuppressWarnings("unused")
	private ColorDTO convertColorDTO(Color c) {
		ColorDTO colorDTO = new ColorDTO();
		colorDTO.setId(c.getId());
		colorDTO.setCode(c.getCode());
		colorDTO.setName(c.getName());

		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(c.getProduct().getId());
		productDTO.setName(c.getProduct().getName());
		productDTO.setQuantity(c.getProduct().getQuantity());
		productDTO.setHighlight(c.getProduct().isHighlight());
		productDTO.setNew_product(c.getProduct().isNewProduct());
		productDTO.setUpcoming(c.getProduct().isUpcoming());
		productDTO.setImg(c.getProduct().getImg());
		colorDTO.setProductDTO(productDTO);

		return colorDTO;
	}

}
