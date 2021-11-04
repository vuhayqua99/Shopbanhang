package com.trungtamjava.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
	public int id;
	public UserDTO buyer;
	public String status;
	public Date buyDate;
	public double priceTotal;
	public Integer discountPercent;
	public String pay;
	public String couponsName;
	private String addressReceiver;
	private String note;
	private String nameReceiver;
	private String phoneReceiver;

}
