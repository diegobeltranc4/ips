package com.ips.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ips.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>  {
	
	@Transactional(readOnly=true)
    public Optional<User> findByUsername(String username);
	     
}