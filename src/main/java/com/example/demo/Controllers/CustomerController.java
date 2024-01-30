package com.example.demo.Controllers;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.Models.Customer;
import com.example.demo.Repositories.CustomerRepository;
import com.example.demo.Services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(new ApiResponse<>(200, "Customer retrieved", customers));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the customers", new ArrayList<>()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<Optional<Customer>>>> getCustomerById(Long id) {
        List<Optional<Customer>> customerList = new ArrayList<>();
        try {
            Optional<Customer> customers = customerService.getCustomerById(id);
            customerList.add(customers);
            return ResponseEntity.ok(new ApiResponse<>(200, "Customer retrieved", customerList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the customer", new ArrayList<>()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<Customer>>> createCustomer(@RequestBody Customer customer) {
        try {
            return customerService.addNewCustomer(customer);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the customer", new ArrayList<>()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<List<Customer>>> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        try {
            return customerService.updateCustomer(customer, id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the customer", new ArrayList<>()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<List<Void>>> deleteCustomer(@PathVariable Long id) {
        try {
            return customerService.deleteCustomer(id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occured while retrieving the customer", new ArrayList<>()));
        }
    }

}
