package com.ips.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ips.dto.UsersDto;
import com.ips.entity.User;
import com.ips.repository.UserRepository;
import com.ips.util.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class UserImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	@Override
	public List<UsersDto> findAll(){
	List<UsersDto> dto = new ArrayList<>();
		Iterable<User> users = this.userRepository.findAll();
		for (User user : users) {
			UsersDto usersDto = Helpers.modelMapper().map(user, UsersDto.class);
			
			dto.add(usersDto);
		}
		return dto;
	}

	@Override
	public UsersDto findByUsername(String username) {
		// TODO Auto-generated method stub
		Optional<User> users = this.userRepository.findByUsername(username);

		if (!users.isPresent()) {
			return null;
		}
		return Helpers.modelMapper().map(users.get(), UsersDto.class);
	}

	@Override
	public UsersDto findByUserId(int userId) {
		// TODO Auto-generated method stub
		Optional<User> users = this.userRepository.findById((long) userId);

		if (!users.isPresent()) {
			return null;
		}
		return Helpers.modelMapper().map(users.get(), UsersDto.class);
	}

	@Override
	public void save(UsersDto user) {
		// TODO Auto-generated method stub
		User users = Helpers.modelMapper().map(user, User.class);
		
		User usrAux = new User();
		
		String passEnc = passEncoder.encode(user.getPassword());
		
		usrAux.setApellido1(user.getApellido1());
		usrAux.setApellido2(user.getApellido2());
		usrAux.setNombre1(user.getNombre1());
		usrAux.setNombre2(user.getNombre2());
		usrAux.setEnabled(true);
		usrAux.setUsername(user.getUsername());
		usrAux.setPassword(passEnc);
		usrAux.setNumeroDocumento(user.getNumeroDocumento());
		usrAux.setTipoDocumento(user.getTipoDocumento());
		
		switch (user.getTipoUsuario()) {
		case "PAC":
			usrAux.setTipoUsuario("user");
			break;
		case "MED":
			usrAux.setTipoUsuario("med");
			break;
		case "ADM":
			usrAux.setTipoUsuario("admin");
			break;
		default:
			break;
		}	
		
		this.userRepository.save(usrAux);

	}

	@Override
	public void saveAll(List<UsersDto> users) {
		// TODO Auto-generated method stub
		List<User> u = new ArrayList<>();
		
		for(UsersDto user : users) {
			User us = Helpers.modelMapper().map(user, User.class);
			
			u.add(us);
		}
		
		this.userRepository.saveAll(u);

	}

	@Override
	public void deleteById(int userId) {
		// TODO Auto-generated method stub
		this.userRepository.deleteById((long) userId);

	}

	private UsersDto convertToUsersDto(final User user) {

		return Helpers.modelMapper().map(user, UsersDto.class);
	}

}