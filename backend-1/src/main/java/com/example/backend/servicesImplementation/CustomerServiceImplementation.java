package com.example.backend.servicesImplementation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
//import com.example.backend.EmailService.EmailService;
import com.example.backend.constatns.JWTConstants;
import com.example.backend.dto.CustomerForApprovementDto;
import com.example.backend.dto.JwtPayload;
import com.example.backend.model.Customer;
import com.example.backend.model.CustomerForApprovement;
import com.example.backend.repo.CustomerForApprovementRepo;
import com.example.backend.repo.CustomerRepo;
import com.example.backend.service.CustomerService;
import com.example.backend.service.S3Service;
import com.example.backend.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.amazonaws.util.IOUtils;

@Service
public class CustomerServiceImplementation implements CustomerService {
	@Autowired
	private AmazonS3 s3;
	@Autowired
	private S3ServiceImplementation s3Service;;

	@Autowired
	CustomerRepo custRepo;


	@Autowired
	CustomerForApprovement customerForApprovement;

	@Autowired
	CustomerForApprovementRepo customerForApprovementRepo;

	@Override
	public Binary downloadFile(String bucketName,@RequestParam String username) throws IOException {
	    return s3Service.downloadFile(bucketName,username);

	}

	@Override
	public Map<String, String> validateCustomer(@RequestBody Map<String, Object> requestParams) {
		HashMap<String, String> map = new HashMap<>();

		String email = (String) requestParams.get("email");
		System.out.println(email);
		System.out.println("method called");
		String fname = (String) requestParams.get("fname");
		String lname = (String) requestParams.get("lname");
		String city = (String) requestParams.get("city");
		String state = (String) requestParams.get("state");

		if (custRepo.findByEmail(email).size() > 0) {
			
			Customer customer = custRepo.findByEmail(email).get(0);
			System.out.println("customer found "+customer);
			Boolean validFname = false;
			if (fname != null)
				validFname = customer.getFname().equalsIgnoreCase(fname.trim());
			Boolean validLname = false;
			if (lname != null)
				validLname = customer.getLname().equalsIgnoreCase(lname.trim());
			Boolean validCity = false;
			if (city != null)
			{
				System.out.println(customer.getCity());
				validCity = customer.getCity().trim().equalsIgnoreCase(city.trim());
				System.out.println(validCity);
			}
			Boolean validState = false;
			if (state != null)
				validState = customer.getState().equals(state.trim());
			map.put("fname", Boolean.toString(validFname));
			map.put("lname", Boolean.toString(validLname));
			map.put("city", Boolean.toString(validCity));
			map.put("state", Boolean.toString(validState));
			map.put("result", "true");

		} else
			map.put("result", "Customer not found");
		return map;

	}

	@Override
//	@Transactional
	public Map<String, String> registerCustomer(CustomerForApprovementDto customer, String jwtToken)
			throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException,Exception {
//		 jwtToken = req.getHeader(JWTConstants.JWT_HEADER);
		JwtPayload payload = JsonUtils.parseJson(jwtToken);

		HashMap<String, String> map = new HashMap<>();
		String approvementStatus = "2";

		String email = (String) customer.getEmail();
		String fname = (String) customer.getFname();
		String lname = (String) customer.getLname();
		String city = (String) customer.getCity();
		String address = (String) customer.getAddress();
		String state = (String) customer.getState();
		MultipartFile pdf = customer.getPdf();
//		
//
//		byte[] binaryData = Base64.getDecoder().decode(pdf);
//		Binary binary = new Binary(binaryData);
		customerForApprovement.setApprovementStatus(approvementStatus);
		customerForApprovement.setFname(fname);
		customerForApprovement.setLname(lname);
		customerForApprovement.setCity(city);
		customerForApprovement.setEmail(email);
		customerForApprovement.setAddress(address);
		customerForApprovement.setState(state);
		customerForApprovement.setApprovementStatus("2");
		String addedBy = payload.getSub();
		customerForApprovement.setAddedBy(addedBy);
		customerForApprovement.setPdfName(email);
		List<CustomerForApprovement> existingRecord = customerForApprovementRepo.findByEmail(email);

		if (existingRecord.size() > 0) {

//			 CustomerForApprovement record = customerForApprovementRepo.findByEmail(email).get(0);
//			 Query query = new Query(Criteria.where("email").is(email));
//			 Update update = new Update().set("pdf", binary);
//			 mongoTemplate.updateFirst(query, update, "customerForApprovement");
		} else {
			try {
			
			
				System.out.println("Saving file to S3 Bucket");
				s3Service.saveFile(pdf, email,"customerforapprovement");
				customerForApprovementRepo.save(customerForApprovement);
				System.out.println("Saved file to s3 bucket");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("failed to save file");
				e.printStackTrace();
				throw new Exception("Failed to save file", e);
			}
		}
//		 EmailService.sendmail(customerForApprovement);
		map.put("result", "submitted");
		return map;
	}

	@Override
	public List<Customer> getCustomerList() {
		// TODO Auto-generated method stub
		return custRepo.findAll();
	}

	@Override
	public List<CustomerForApprovement> getCustomerForApprovement() {
		// TODO Auto-generated method stub
		return customerForApprovementRepo.findAll();
	}

	@Override
	public void addCustomer(Customer customer) {
		custRepo.save(customer);

	}

	@Override
	public void deleteCustomer(String email) {
		custRepo.deleteByEmail(email);

	}

//	@Override
//	public void updateCustomer(Customer customer) {
//		// TODO Auto-generated method stub
//		custRepo.(customer);
//		
//		Query q= new Query().addCriteria(Criteria.where("email").is(customer.getEmail()));
//		Update update = mongoTemplate.updateMulti(q, UpdateDefinition, Customer.class); 
//	}

	public List<CustomerForApprovement> getCustomerListForAgent(String email) {
		// TODO Auto-generated method stub
		return customerForApprovementRepo.findByAddedBy(email);
	}

	public Customer updateCustomer(Customer customer) {
		return custRepo.save(customer);
		// TODO Auto-generated method stub

	}

	@Override
	public List<CustomerForApprovement> getListOfCutomerForReview() {
		// TODO Auto-generated method stub
		return customerForApprovementRepo.findAllWihoutPdf();
	}

	@Override
	public void reviewDoneBy(String customerEmail, String reviewerEmail) {
		// TODO Auto-generated method stub

		customerForApprovementRepo.reviewDoneBy(customerEmail, reviewerEmail);
	}

	@Override
	public void approveCustomer(String customerEmail, MultipartFile pdf, String jwtToken) throws Exception {
		// TODO Auto-generated method stub
		
		JwtPayload payload = JsonUtils.parseJson(jwtToken);
		String reviewedBy = payload.getSub();
		System.out.println(reviewedBy);
		s3Service.saveFile(pdf, customerEmail,"approvedcustomers");
		customerForApprovementRepo.approveCustomer(customerEmail, reviewedBy, customerEmail);
		

	}

	@Override
	public void reviewDoneBy(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> registerCustomer(CustomerForApprovement customer, String jwtToken)
			throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return null;
	}

}
