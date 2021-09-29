package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.SearchUserDTO;
import com.trungtamjava.model.UserDTO;

public interface UserService {
	void insert(UserDTO user);

	void update(UserDTO user);

	void delete(Long id);

	void enable(UserDTO userDTO);

	UserDTO get(Long id);

	UserDTO getByUserName(String userName);

	List<UserDTO> search(String name, int start, int length);

	void changePassword(UserDTO accountDTO);

	void resetPassword(UserDTO accountDTO);

	void setupUserPassword(UserDTO accountDTO);

	void updateProfile(UserDTO userDTO);

	public List<UserDTO> getAllUsers(int start, int length);

	long countGetAll();

	long countSearch(String name);
	
	List<UserDTO> find(SearchUserDTO searchUserDTO);
	
	long count(SearchUserDTO searchUserDTO);

	long countTotal(SearchUserDTO searchUserDTO);
}
