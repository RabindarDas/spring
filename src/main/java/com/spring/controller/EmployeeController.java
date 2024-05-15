package com.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.Employee;
import com.spring.repository.EmployeeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	// get all employees
		@GetMapping("/employees")
		public List<Employee> getAllEmployees(){
			return employeeRepository.findAll();
		}	
	@PostMapping("/employees")
	@CrossOrigin(origins = "http://localhost:4200")
	public Employee createEmployee( @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employeess/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Employee> getEmployeesById(@PathVariable("id") long id) throws Exception {
		System.out.println(id);
		Optional<Employee> emp=employeeRepository.findById(id);
		if (emp.isPresent()) {
            return new ResponseEntity<>(emp.get(), HttpStatus.OK);
        } else {
            throw new Exception();
        }
		//return ResponseEntity.ok().body(emp);
	}
//	@PutMapping("/employees/{id}")
//	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employees) throws Exception {
//		 employeeRepository.findById(id).
//				orElseThrow(()->{return new Exception("no value present for the employee"+id);
//				 
//				});
//		 
//		
//			
//		 employeeRepository.save(employees);
//		
//		
//		return 	ResponseEntity.ok().body(employees);
//				
//				
//			
//		
//		}
	@PutMapping("/employees/{id}")
	 public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employeeDetails){
		Employee updateEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
		updateEmployee.setUsername(employeeDetails.getUsername());
		updateEmployee.setEmail(employeeDetails.getEmail());
		updateEmployee.setPassword(employeeDetails.getPassword());
		updateEmployee.setConfirmPassword(employeeDetails.getConfirmPassword());
		updateEmployee.setPhone(employeeDetails.getPhone());
		employeeRepository.save(updateEmployee);
		 return ResponseEntity.ok(updateEmployee);
		
		
	}
	
	
	
	
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id){
		employeeRepository.deleteById(id);
		return ResponseEntity.ok("Employee deleted successfully!.");
		 
	}

}
