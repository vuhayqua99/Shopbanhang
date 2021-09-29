package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Coupon;

public interface CouponsDao {
	Coupon getByName(String name, int start, int lenght);

	Coupon get(int id);

	void delete(Coupon coupon);

	void update(Coupon coupon);

	void insert(Coupon coupon);

	List<Coupon> getAll(int start, int lenght);

	long countAll();

	long countSearch(String name);

	List<Coupon> getListByName(String name, int start, int lenght);
}
