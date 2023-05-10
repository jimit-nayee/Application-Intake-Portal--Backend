package com.example.backend.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.backend.model.Customer;
import com.example.backend.model.CustomerForApprovement;

public interface CustomerForApprovementRepo extends MongoRepository<CustomerForApprovement, String> {
	 
	

	 List<CustomerForApprovement> findByEmail(String email);
	 
	 @Query(value = "{}", fields = "{ 'pdf' : 0 }")
	 List<CustomerForApprovement> findAllWihoutPdf();  
  
	 @Query(value="{ 'addedBy' : ?0 }", fields="{ 'pdf' : 0}")
	List<CustomerForApprovement> findByAddedBy(String email);

	 //we know that we have to update
		
	@Query("{ 'email' : ?0  }")
	@Update("{ '$set' : { 'is_customer_approved' :?1} }")
	void reviewDoneBy(String email);
	 
	
}
 

