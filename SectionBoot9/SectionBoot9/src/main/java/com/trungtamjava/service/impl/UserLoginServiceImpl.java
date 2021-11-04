//package com.trungtamjava.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.trungtamjava.dao.UserDao;
//import com.trungtamjava.entity.User;
//
//@Service
//@Transactional
//public class UserLoginServiceImpl implements UserDetailsService {
//	@Autowired
//	UserDao userDao;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userDao.getByUserName(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("no user");
//		}
//		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
//		authorities.add(new SimpleGrantedAuthority(user.getRole()));
//		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
//				user.getPassword(), true, true, true, true, authorities);
//		return userDetails;
//	}
//
//}
