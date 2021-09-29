package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.BillProduct;

public interface BillProductDao {
	List<BillProduct> searchByBillId(int id,int start, int length);

	List<BillProduct> searchByName(String name, int start, int length);

	List<BillProduct> getAll(int start, int length);

	BillProduct get(int id);

	long getCountSearch(String name);

	long getCount();

	void delete(BillProduct billProduct);

	void update(BillProduct billProduct);

	void insert(BillProduct billProduct);
}
