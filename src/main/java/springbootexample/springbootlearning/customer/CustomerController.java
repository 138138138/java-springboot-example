package springbootexample.springbootlearning.customer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Customer>>> getAllCustomers() {
        return customerService.getAllCustomersAsync()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Customer>> getCustomerById(@PathVariable long id) {
        return customerService.getCustomerByIdAsync(id)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Customer>> createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomerAsync(customer)
                .thenApply(createdCustomer -> ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Customer>> updateCustomer(@PathVariable long id,
            @RequestBody Customer customer) {
        customer.setId(id);
        return customerService.updateCustomerAsync(customer)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteCustomer(@PathVariable long id) {
        return customerService.deleteCustomerAsync(id)
                .thenApply(deleted -> ResponseEntity.noContent().build());
    }
}
