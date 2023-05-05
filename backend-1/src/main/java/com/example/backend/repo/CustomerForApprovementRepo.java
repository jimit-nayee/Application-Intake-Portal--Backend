package com.example.backend.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.backend.model.Customer;
import com.example.backend.model.CustomerForApprovement;

public interface CustomerForApprovementRepo extends MongoRepository<CustomerForApprovement, String> {
	
	 List<CustomerForApprovement> findByEmail(String email);

	 @Query(value="{ 'addedBy' : ?0 }", fields="{ 'pdf' : 0}")
	List<CustomerForApprovement> findByAddedBy(String email);
}
 

