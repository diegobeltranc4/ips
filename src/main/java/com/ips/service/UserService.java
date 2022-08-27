package com.ips.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ips.dto.UsersDto;

@Service
public interface UserService {
	
	List<UsersDto> findAll();
	
	UsersDto findByUsername(String username);
	
	UsersDto findByUserId(int userId);
	
	void save(UsersDto user);
	
	void saveAll(List<UsersDto> users);
	
	void deleteById(int userId);
}
