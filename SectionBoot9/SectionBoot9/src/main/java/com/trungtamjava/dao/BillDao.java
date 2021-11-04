package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Bill;

public interface BillDao {
	List<Bill> searchByBuyerId(int id);

	List<Bill> searchByBuyerName(String name, int start, int lenght);

	Bill get(int id);

	void setStatus(Bill bill);

	void delete(Bill bill);

	void update(Bill bill);

	void insert(Bill bill);

	long countAll();

	long countSearch(String name);

	List<Bill> getAllbyName(String name, int start, int lenght);

	List<Bill> getAll(int start, int lenght);

}
