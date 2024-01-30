package com.example.demo.Services;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.Models.Customer;
import com.example.demo.Repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer>getAllCustomers() {
        return customerRepository.findAll();
    }
    public Optional<Customer> getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer;
    }

    public ResponseEntity<ApiResponse<List<Customer>>> addNewCustomer (Customer customer) {
        List<Customer> customers = new ArrayList<>();
        try {

            Customer customer1 = customerRepository.save(customer);
            customers.add(customer1);

            return ResponseEntity.ok(new ApiResponse<>(201, "Customer added", customers));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "server error", customers));
        }
    }

    public ResponseEntity<ApiResponse<List<Customer>>> updateCustomer (Customer updateCustomer, Long id) {
        try {
            Optional<Customer> existingCustomerOptional = customerRepository.findById(id);
            if (existingCustomerOptional.isPresent()){
                Customer existingCustomer = existingCustomerOptional.get();

                existingCustomer.setName(updateCustomer.getName());
                existingCustomer.setAddress(updateCustomer.getAddress());
                existingCustomer.setContactNumber(updateCustomer.getContactNumber());
                existingCustomer.setEmail(updateCustomer.getEmail());

                customerRepository.save(existingCustomer);
                return ResponseEntity.ok(new ApiResponse<>(200, "Customer updated successfully", new ArrayList<>()));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Customer not found", new ArrayList<>()));
            }
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "server error", new ArrayList<>()));
        }
    }

    public ResponseEntity<ApiResponse<List<Void>>> deleteCustomer (Long id) {
        try {
            Optional<Customer> exisitingCustomerOptional = customerRepository.findById(id);
            if (exisitingCustomerOptional.isPresent()) {
                Customer customer = exisitingCustomerOptional.get();
                customerRepository.deleteById(id);
                return ResponseEntity.ok(new ApiResponse<>(200, "Customer deleted successfully", new ArrayList<>()));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Customer not found", new ArrayList<>()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "An error occured while deleting the customer", new ArrayList<>()));
        }
    }
    private boolean isCustomerDuplicate(int contactNumber){
        return customerRepository.existsByContactNumber(contactNumber);
    }
}