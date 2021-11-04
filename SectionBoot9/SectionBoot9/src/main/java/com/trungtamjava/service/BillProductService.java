package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.BillProductDTO;

public interface BillProductService {
	List<BillProductDTO> searchByBillId(int id,int start, int length);

	List<BillProductDTO> searchByName(String name, int start, int length);

	List<BillProductDTO> getAll(int start, int length);

	BillProductDTO get(int id);

	void delete(int id);

	void update(BillProductDTO billProductDTO);

	void insert(BillProductDTO billProductDTO);

	long getCountSearch(String name);

	long getCount();
}
