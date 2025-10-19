package com.example.store.controller;

import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.mapper.CustomerMapper;
import com.example.store.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    /**
     * Get all customers or search by name with pagination.
     *
     * @param name optional name substring to search for (case-insensitive)
     * @param page page number (default: 0)
     * @param size page size (default: 20)
     * @param sort sort criteria (default: id,desc)
     * @return paginated list of customers
     */
    @GetMapping
    public Page<CustomerDTO> getAllCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        Sort.Direction direction = sort.length > 1 && sort[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        // If name search is requested
        if (name != null && !name.trim().isEmpty()) {
            Page<Customer> customerPage = customerService.searchCustomersByName(name, pageable);
            return customerPage.map(customerMapper::customerToCustomerDTO);
        }

        // Return all customers with pagination
        Page<Customer> customerPage = customerService.getAllCustomersPaginated(pageable);
        return customerPage.map(customerMapper::customerToCustomerDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody Customer customer) {
        return customerMapper.customerToCustomerDTO(customerService.createCustomer(customer));
    }
}
