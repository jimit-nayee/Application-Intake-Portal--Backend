package com.example.backend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
	
	List<User> findByEmailAndPassword(String email,String password);
	Optional<User> findByEmail(String email);
	void deleteByEmail(String email);
	

}
