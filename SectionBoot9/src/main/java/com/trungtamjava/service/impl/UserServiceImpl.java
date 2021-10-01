package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.UserDao;
import com.trungtamjava.entity.User;
import com.trungtamjava.model.SearchUserDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.model.UserPrincipal;
import com.trungtamjava.service.UserService;
import com.trungtamjava.utils.PasswordGenerator;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	UserDao userDao;

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

	@Override
	public void delete(Long id) {
		User user = userDao.get(id);
		if (user != null) {
			userDao.delete(user);
		}

	}

	@Override
	public UserDTO get(Long id) {
		User user = userDao.get(id);
		return convertToDTO(user);
	}

	@Override
	public UserDTO getByUserName(String userName) {
		User user = userDao.getByUserName(userName);
		return convertToDTO(user);
	}

	@Override
	public List<UserDTO> search(String name, int start, int length) {
		List<User> lUserrs = userDao.search(name, start, length);
		List<UserDTO> lDtos = new ArrayList<UserDTO>();
		lUserrs.forEach(user -> {
			lDtos.add(convertToDTO(user));
		});

		return lDtos;
	}

	@Override
	public void changePassword(UserDTO accountDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetPassword(UserDTO accountDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupUserPassword(UserDTO userDTO) {
		User user = userDao.get(userDTO.getId());
		if (user != null) {
			user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
		}

	}

	@Override
	public void updateProfile(UserDTO userDTO) {

	}

	@Override
	public void insert(UserDTO userDTO) {
		userDao.insert(convertUser(userDTO));

	}

	@Override
	public void update(UserDTO userDTO) {
		User user = userDao.get(userDTO.getId());
		if (user != null) {
			user.setName(userDTO.getName());
			user.setAge(userDTO.getAge());
			user.setUsername(userDTO.getUsername());
			user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
			user.setGender(userDTO.getGender());
			user.setAddress(userDTO.getAddress());
			user.setRole(userDTO.getRole());
			user.setPhone(userDTO.getPhone());
			user.setEmail(userDTO.getEmail());
			user.setEnabled(userDTO.isEnabled());
			userDao.update(user);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		UserPrincipal userPrincipal = new UserPrincipal(user.getUsername(), user.getPassword(), user.isEnabled(), true,
				true, true, authorities);

		userPrincipal.setId(user.getId());
		userPrincipal.setName(user.getName());
		userPrincipal.setRole(user.getRole());
		userPrincipal.setPhone(user.getPhone());
		userPrincipal.setEmail(user.getEmail());

		return userPrincipal;
	}

	@Override
	public List<UserDTO> getAllUsers(int start, int length) {
		List<User> lUserrs = userDao.getAllUsers(start, length);
		List<UserDTO> lDtos = new ArrayList<UserDTO>();
		lUserrs.forEach(user -> {
			lDtos.add(convertToDTO(user));
		});
		return lDtos;
	}

	@Override
	public long countGetAll() {
		long count = userDao.countGetAll();
		return count;
	}

	@Override
	public long countSearch(String name) {
		long count = userDao.countSearch(name);
		return count;
	}

	@Override
	public void enable(UserDTO userDTO) {
		User user = userDao.get(userDTO.getId());
		if (user != null) {
			user.setEnabled(userDTO.isEnabled());
			userDao.enable(user);
		}

	}

	@Override
	public List<UserDTO> find(SearchUserDTO searchUserDTO) {
		List<User> users = userDao.find(searchUserDTO);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		users.forEach(user -> {
			userDTOs.add(convertToDTO(user));
		});

		return userDTOs;
	}

	@Override
	public long count(SearchUserDTO searchUserDTO) {
		return userDao.count(searchUserDTO);
	}

	@Override
	public long countTotal(SearchUserDTO searchUserDTO) {
		return userDao.countTotal(searchUserDTO);
	}

	private UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setUsername(user.getUsername());
		userDTO.setPassword(user.getPassword());
		userDTO.setAddress(user.getAddress());
		userDTO.setGender(user.getGender());
		userDTO.setPhone(user.getPhone());
		userDTO.setEmail(user.getEmail());
		userDTO.setRole(user.getRole());
		userDTO.setEnabled(user.isEnabled());
		userDTO.setAge(user.getAge());
//		if (user.getPassword() != null) {
//			userDTO.setPassword(user.getPassword());
//		}
//		if (user.getCreatedDate() != null) {
		// userDTO.setCreatedDate(DateTimeUtils.formatDate(user.getCreatedDate(),
		// DateTimeUtils.DD_MM_YYYY_HH_MM));
//		}
//		if (user.getCreatedBy() != null) {
//			userDTO.setCreatedBy(user.getCreatedBy().getName());
//		}

		return userDTO;
	}

	private User convertUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setAge(userDTO.getAge());
		user.setUsername(userDTO.getUsername());
		user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
		user.setGender(userDTO.getGender());
		user.setAddress(userDTO.getAddress());
		user.setRole(userDTO.getRole());
		user.setPhone(userDTO.getPhone());
		user.setEmail(userDTO.getEmail());
		user.setEnabled(userDTO.isEnabled());

		return user;
	}

}
