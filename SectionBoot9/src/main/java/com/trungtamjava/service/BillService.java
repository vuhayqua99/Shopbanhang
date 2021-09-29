package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.BillDTO;

public interface BillService {
	List<BillDTO> searchByBuyerId(int id);

	List<BillDTO> searchByBuyerName(String name, int start, int lenght);

	BillDTO get(int id);

	void delete(int id);

	void update(BillDTO bill);

	void insert(BillDTO bill);

	long countAll();

	long countSearch(String name);

	List<BillDTO> getAllBillByName(String name, int start, int lenght);

	List<BillDTO> getAll(int start, int lenght);

	public void setStatus(BillDTO billDTO);

}
