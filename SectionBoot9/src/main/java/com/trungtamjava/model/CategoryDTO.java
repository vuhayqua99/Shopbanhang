package com.trungtamjava.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	private int id;
	private String name;
	private String description;
	private List<ProductDTO> lProductDTOs;

}
