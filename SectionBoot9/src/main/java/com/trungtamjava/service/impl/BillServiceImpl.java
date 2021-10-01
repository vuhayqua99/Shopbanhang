package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.BillDao;
import com.trungtamjava.entity.Bill;
import com.trungtamjava.entity.User;
import com.trungtamjava.model.BillDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.BillService;

@Service
@Transactional
public class BillServiceImpl implements BillService {
	@Autowired
	BillDao billDao;

	@Override
	public List<BillDTO> searchByBuyerId(int id) {
		List<Bill> bills = billDao.searchByBuyerId(id);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		bills.forEach(bill -> {
			billDTOs.add(convertBill(bill));
		});
		return billDTOs;
	}

	@Override
	public List<BillDTO> searchByBuyerName(String name, int start, int lenght) {
		List<Bill> bills = billDao.searchByBuyerName(name, start, lenght);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		bills.forEach(bill -> {
			billDTOs.add(convertBill(bill));
		});
		return billDTOs;
	}

	@Override
	public BillDTO get(int id) {
		Bill bill = billDao.get(id);

		return convertBill(bill);
	}

	@Override
	public void delete(int id) {
		Bill bill = billDao.get(id);
		if (bill != null) {
			billDao.delete(bill);
		}

	}

	@Override
	public void update(BillDTO billDTO) {
		Bill bill = billDao.get(billDTO.getId());
		if (bill != null) {
			bill.setId(billDTO.getId());
			bill.setBuyDate(new Date());
			bill.setCouponsName(billDTO.couponsName);
			bill.setDiscountPercent(billDTO.getDiscountPercent());
			bill.setPay(billDTO.getPay());
			bill.setPriceTotal(billDTO.getPriceTotal());
			bill.setStatus(billDTO.getStatus());
			bill.setAddressReceiver(billDTO.getAddressReceiver());
			bill.setNameReceiver(billDTO.getNameReceiver());
			bill.setPhoneReceiver(billDTO.getPhoneReceiver());
			bill.setNote(billDTO.getNote());

			billDao.update(bill);

		}

	}

	@Override
	public void insert(BillDTO billDTO) {
		Bill bill = new Bill();
		bill.setBuyDate(billDTO.getBuyDate());
		bill.setCouponsName(billDTO.getCouponsName());
		bill.setBuyDate(new Date());
		bill.setDiscountPercent(0);
		bill.setPay(billDTO.getPay());
		bill.setPriceTotal(billDTO.getPriceTotal());
		bill.setStatus(billDTO.getStatus());
		bill.setAddressReceiver(billDTO.getAddressReceiver());
		bill.setNameReceiver(billDTO.getNameReceiver());
		bill.setPhoneReceiver(billDTO.getPhoneReceiver());
		bill.setNote(billDTO.getNote());

		User user = new User();
		user.setAddress(billDTO.getBuyer().getAddress());
		user.setId(billDTO.getBuyer().getId());
		user.setEmail(billDTO.getBuyer().getEmail());
		user.setEnabled(billDTO.getBuyer().isEnabled());
		user.setGender(billDTO.getBuyer().getGender());
		user.setName(billDTO.getBuyer().getName());
		user.setPassword(billDTO.getBuyer().getPassword());
		user.setPhone(billDTO.getBuyer().getPhone());
		user.setRole(billDTO.getBuyer().getRole());
		user.setUsername(billDTO.getBuyer().getUsername());

		bill.setUser(user);

		billDao.insert(bill);
		billDTO.setId(bill.getId());

	}

	@Override
	public long countAll() {
		long count = billDao.countAll();
		return count;
	}

	@Override
	public long countSearch(String name) {
		long count = billDao.countSearch(name);
		return count;
	}

	@Override
	public List<BillDTO> getAllBillByName(String name, int start, int lenght) {
		List<Bill> bills = billDao.getAllbyName(name, start, lenght);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		bills.forEach(bill -> {
			billDTOs.add(convertBill(bill));
		});

		return billDTOs;
	}

	@Override
	public List<BillDTO> getAll(int start, int lenght) {
		List<Bill> bills = billDao.getAll(start, lenght);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		bills.forEach(bill -> {
			billDTOs.add(convertBill(bill));
		});

		return billDTOs;
	}

	@Override
	public void setStatus(BillDTO billDTO) {
		Bill bill = billDao.get(billDTO.getId());
		if (bill != null) {
			bill.setStatus(billDTO.getStatus());
			billDao.setStatus(bill);
		}
	}

	@SuppressWarnings("unused")
	private BillDTO convertBill(Bill b) {
		BillDTO billDTO = new BillDTO();
		billDTO.setId(b.getId());
		billDTO.setBuyDate(b.getBuyDate());
		billDTO.setCouponsName(b.getCouponsName());
		billDTO.setDiscountPercent(b.getDiscountPercent());
		billDTO.setPay(b.getPay());
		billDTO.setPriceTotal(b.getPriceTotal());
		billDTO.setStatus(b.getStatus());
		billDTO.setAddressReceiver(b.getAddressReceiver());
		billDTO.setNameReceiver(b.getNameReceiver());
		billDTO.setNote(b.getNote());
		billDTO.setPhoneReceiver(b.getPhoneReceiver());

		UserDTO userDTO = new UserDTO();
		userDTO.setAddress(b.getUser().getAddress());
		userDTO.setId(b.getUser().getId());
		userDTO.setEmail(b.getUser().getEmail());
		userDTO.setEnabled(b.getUser().isEnabled());
		userDTO.setGender(b.getUser().getGender());
		userDTO.setName(b.getUser().getName());
		userDTO.setPassword(b.getUser().getPassword());
		userDTO.setPhone(b.getUser().getPhone());
		userDTO.setRole(b.getUser().getRole());
		userDTO.setUsername(b.getUser().getUsername());

		billDTO.setBuyer(userDTO);
		return billDTO;
	}
}
