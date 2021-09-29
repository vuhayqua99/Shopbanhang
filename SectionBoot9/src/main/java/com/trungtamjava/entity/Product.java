package com.trungtamjava.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Table(name = "products1")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Lob
	private String details;

	private int quantity;

	private String img;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private boolean highlight;

	private String name;

	@Column(name = "new_product")
	private boolean newProduct;

	private boolean upcoming;

	private double price;

	private int sale;

	private String sizes;

	private String title;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	// bi-directional many-to-one association to Color
	@OneToMany(mappedBy = "product")
	private List<Color> colors;

	@OneToMany(mappedBy = "product")
	private List<BillProduct> billProducts;

	// bi-directional many-to-one association to Category
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_category")
	private Category category;

	public Product() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isUpcoming() {
		return upcoming;
	}

	public void setUpcoming(boolean upcoming) {
		this.upcoming = upcoming;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BillProduct> getBillProducts() {
		return billProducts;
	}

	public void setBillProducts(List<BillProduct> billProducts) {
		this.billProducts = billProducts;
	}

	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	public boolean isNewProduct() {
		return newProduct;
	}

	public void setNewProduct(boolean newProduct) {
		this.newProduct = newProduct;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSale() {
		return this.sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

	public String getSizes() {
		return this.sizes;
	}

	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Color> getColors() {
		return this.colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

	public Color addColor(Color color) {
		getColors().add(color);
		color.setProduct(this);

		return color;
	}

	public Color removeColor(Color color) {
		getColors().remove(color);
		color.setProduct(null);

		return color;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}