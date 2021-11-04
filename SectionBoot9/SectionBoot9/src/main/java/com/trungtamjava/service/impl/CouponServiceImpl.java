package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.CouponsDao;
import com.trungtamjava.entity.Coupon;
import com.trungtamjava.model.CouponDTO;
import com.trungtamjava.service.CouponService;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

	@Autowired
	CouponsDao couponsDao;

	@Override
	public CouponDTO getByName(String name, int start, int lenght) {
		Coupon coupon = couponsDao.getByName(name, start, lenght);
		CouponDTO couponDTO = new CouponDTO();
		couponDTO.setId(coupon.getId());
		couponDTO.setName(coupon.getName());
		couponDTO.setPersent(coupon.getPersent());

		return couponDTO;
	}

	@Override
	public CouponDTO get(int id) {
		Coupon coupon = couponsDao.get(id);
		CouponDTO couponDTO = new CouponDTO();
		couponDTO.setId(coupon.getId());
		couponDTO.setName(coupon.getName());
		couponDTO.setPersent(coupon.getPersent());

		return couponDTO;
	}

	@Override
	public void delete(int id) {
		Coupon coupon = couponsDao.get(id);
		if (coupon != null) {
			couponsDao.delete(coupon);
		}

	}

	@Override
	public void update(CouponDTO couponDTO) {
		Coupon coupon = couponsDao.get(couponDTO.getId());
		if (coupon != null) {
			coupon.setId(couponDTO.getId());
			coupon.setName(couponDTO.getName());
			coupon.setPersent(couponDTO.getPersent());

			couponsDao.update(coupon);

		}

	}

	@Override
	public void insert(CouponDTO couponDTO) {
		Coupon coupon = new Coupon();
		coupon.setId(couponDTO.getId());
		coupon.setName(couponDTO.getName());
		coupon.setPersent(couponDTO.getPersent());

		couponsDao.insert(coupon);

	}

	@Override
	public List<CouponDTO> getAll(int start, int lenght) {
		List<Coupon> coupons = couponsDao.getAll(start, lenght);
		List<CouponDTO> couponDTOs = new ArrayList<CouponDTO>();
		for (Coupon c : coupons) {
			CouponDTO couponDTO = new CouponDTO();
			couponDTO.setId(c.getId());
			couponDTO.setName(c.getName());
			couponDTO.setPersent(c.getPersent());

			couponDTOs.add(couponDTO);
		}

		return couponDTOs;
	}

	@Override
	public long countAll() {
		long count = couponsDao.countAll();
		return count;
	}

	@Override
	public long countSearch(String name) {
		long count = couponsDao.countSearch(name);
		return count;
	}

	@Override
	public List<CouponDTO> getListByName(String name, int start, int lenght) {
		List<Coupon> coupons = couponsDao.getListByName(name, start, lenght);
		List<CouponDTO> couponDTOs = new ArrayList<CouponDTO>();
		for (Coupon c : coupons) {
			CouponDTO couponDTO = new CouponDTO();
			couponDTO.setId(c.getId());
			couponDTO.setName(c.getName());
			couponDTO.setPersent(c.getPersent());

			couponDTOs.add(couponDTO);
		}

		return couponDTOs;
	}

}
