package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.CouponDTO;

public interface CouponService {
	CouponDTO getByName(String name, int start, int lenght);

	CouponDTO get(int id);

	void delete(int id);

	void update(CouponDTO couponDTO);

	void insert(CouponDTO couponDTO);

	List<CouponDTO> getAll(int start, int lenght);

	long countAll();

	long countSearch(String name);

	List<CouponDTO> getListByName(String name, int start, int lenght);
}
