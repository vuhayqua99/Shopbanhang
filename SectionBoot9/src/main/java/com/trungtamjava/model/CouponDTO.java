package com.trungtamjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {
	private int id;
	private String name;
	private int persent;

}
