package com.trungtamjava.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the coupons database table.
 * 
 */
@Entity
@Table(name = "coupons1")
@NamedQuery(name = "Coupon.findAll", query = "SELECT c FROM Coupon c")
public class Coupon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private int persent;

	public Coupon() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPersent() {
		return this.persent;
	}

	public void setPersent(int persent) {
		this.persent = persent;
	}

}