package com.trungtamjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Long id;
	private String name;
	private int age;
	private String role;
	private boolean enabled;
	private String username;
	private String password;
	private String address;
	private String gender;
	private String phone;
	private String email;
	

}
