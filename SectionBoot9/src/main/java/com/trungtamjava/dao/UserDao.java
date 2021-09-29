package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.User;
import com.trungtamjava.model.SearchUserDTO;

public interface UserDao {
	void insert(User user);

	void update(User user);

	void delete(User user);

	void enable(User user);

	User get(Long id);

	User getByUserName(String userName);

	List<User> search(String findName, int start, int length);

	public List<User> getAllUsers(int start, int length);

	long countGetAll();

	long countSearch(String name);
	
	List<User> find(SearchUserDTO searchUserDTO);

	long count(SearchUserDTO searchUserDTO);

	long countTotal(SearchUserDTO searchUserDTO);

}
