package com.trungtamjava.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamjava.exception.JwtCustomException;
import com.trungtamjava.model.ResponseDTO;
import com.trungtamjava.model.SearchUserDTO;
import com.trungtamjava.model.TokenDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.model.UserPrincipal;
import com.trungtamjava.security.JwtTokenProvider;
import com.trungtamjava.service.UserService;
import com.trungtamjava.utils.RoleEnum;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
public class UserAPIController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	// login me
	@PostMapping("/login")
	public TokenDTO login(@RequestParam(required = true, name = "username") String username,
			@RequestParam(required = true, name = "password") String password,
			@RequestParam(name = "deviceToken", required = false) String token) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username);
		} catch (AuthenticationException e) {
			throw new JwtCustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping(value = "/member/me")
	private UserDTO me() {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userService.get(currentUser.getId());
	}

	// register
	@PostMapping("/user/register")
	public UserDTO register(@RequestBody UserDTO userDTO) {
		userDTO.setEnabled(true);
		userDTO.setRole("ROLE_" + RoleEnum.MEMBER.name());
		userService.insert(userDTO);
		return userDTO;
	}

	@PostMapping("/admin/user/add")
	public UserDTO addUser(@RequestBody UserDTO userDTO) {
		userDTO.setEnabled(true);
		userService.insert(userDTO);
		return userDTO;
	}

	@PutMapping(value = "/admin/user/update")
	public void updateUser(@RequestBody UserDTO userDTO) {
		userService.update(userDTO);
	}

	@GetMapping(value = "/admin/user/{id}")
	public UserDTO get(@PathVariable(name = "id") Long id) {
		return userService.get(id);
	}

	@DeleteMapping(value = "/admin/user/delete")
	public void delete(@RequestParam(name = "id") Long id) {
		userService.delete(id);
	}

	@DeleteMapping(value = "/admin/user/delete-multi")
	public void delete(@RequestParam(name = "ids") List<Long> ids) {
		for (Long id : ids) {
			try {
				userService.delete(id);
			} catch (Exception e) {

			}
		}
	}

	@PostMapping(value = "/admin/accounts")
	public ResponseDTO<UserDTO> findCustomers(@RequestBody SearchUserDTO searchUserDTO) {
		ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
		responseDTO.setData(userService.find(searchUserDTO));
		responseDTO.setRecordsFiltered(userService.count(searchUserDTO));
		responseDTO.setRecordsTotal(userService.countTotal(searchUserDTO));
		return responseDTO;
	}

}
