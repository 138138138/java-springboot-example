package springbootexample.springbootlearning.customer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Async
    public CompletableFuture<List<Customer>> getAllCustomersAsync() {
        return CompletableFuture.completedFuture(customerRepository.findAll());
    }

    @Async
    public CompletableFuture<Customer> getCustomerByIdAsync(long id) {
        return CompletableFuture.completedFuture(customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id)));
    }

    @Async
    public CompletableFuture<Customer> createCustomerAsync(Customer customer) {
        return CompletableFuture.completedFuture(customerRepository.save(customer));
    }

    @Async
    public CompletableFuture<Customer> updateCustomerAsync(Customer customer) {
        return CompletableFuture.completedFuture(customerRepository.save(customer));
    }

    @Async
    public CompletableFuture<Void> deleteCustomerAsync(long id) {
        customerRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }
}
