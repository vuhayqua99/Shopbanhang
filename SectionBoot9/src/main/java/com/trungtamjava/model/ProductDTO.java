package com.trungtamjava.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private long id;
	private CategoryDTO categoryDTO;
	private String sizes;
	private String name;
	private double price;
	private int sale;
	private String title;
	private boolean highlight;
	private boolean new_product;
	private String details;
	private java.util.Date created_at;
	private java.util.Date updated_at;
	private String img;
	private List<ColorDTO> listColorDTO;
	private List<BillProductDTO> lBillProductDTOs;
	private boolean upcoming;
	private int quantity;
	private MultipartFile file;// dung ipa
}
