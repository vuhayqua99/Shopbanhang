package com.trungtamjava.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the bill database table.
 * 
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bills1")
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@CreatedDate
	@Temporal(TemporalType.DATE)
	@Column(name = "buy_date")
	private Date buyDate;

	@Column(name = "coupons_name")
	private String couponsName;

	private int discountPercent;

	private String pay;

	public String getAddressReceiver() {
		return addressReceiver;
	}

	public void setAddressReceiver(String addressReceiver) {
		this.addressReceiver = addressReceiver;
	}

	public String getNameReceiver() {
		return nameReceiver;
	}

	public void setNameReceiver(String nameReceiver) {
		this.nameReceiver = nameReceiver;
	}

	public String getPhoneReceiver() {
		return phoneReceiver;
	}

	public void setPhoneReceiver(String phoneReceiver) {
		this.phoneReceiver = phoneReceiver;
	}

	@Column(name = "price_total")
	private double priceTotal;

	private String status;

	@Column(name = "address_receiver")
	private String addressReceiver;

	@Column(name = "name_receiver")
	private String nameReceiver;

	@Column(name = "phone_receiver")
	private String phoneReceiver;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	private String note;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private User buyer;

	// bi-directional many-to-one association to BillProduct
	@OneToMany(mappedBy = "bill", cascade = { CascadeType.MERGE })
	private List<BillProduct> billProducts;

	public Bill() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBuyDate() {
		return this.buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public String getCouponsName() {
		return this.couponsName;
	}

	public void setCouponsName(String couponsName) {
		this.couponsName = couponsName;
	}

	public int getDiscountPercent() {
		return this.discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public String getPay() {
		return this.pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public double getPriceTotal() {
		return this.priceTotal;
	}

	public void setPriceTotal(double priceTotal) {
		this.priceTotal = priceTotal;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return this.buyer;
	}

	public void setUser(User buyer) {
		this.buyer = buyer;
	}

	public List<BillProduct> getBillProducts() {
		return this.billProducts;
	}

	public void setBillProducts(List<BillProduct> billProducts) {
		this.billProducts = billProducts;
	}

	public BillProduct addBillProduct(BillProduct billProduct) {
		getBillProducts().add(billProduct);
		billProduct.setBill(this);

		return billProduct;
	}

	public BillProduct removeBillProduct(BillProduct billProduct) {
		getBillProducts().remove(billProduct);
		billProduct.setBill(null);

		return billProduct;
	}

}