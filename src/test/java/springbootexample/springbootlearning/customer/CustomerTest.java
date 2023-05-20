package springbootexample.springbootlearning.customer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
public class CustomerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetAllCustomers() throws Exception {
        Customer customer1 = new Customer(1L, "John", "john@example.com", 12345678);
        Customer customer2 = new Customer(2L, "Jane", "jane@example.com", 98765432);
        List<Customer> customers = Arrays.asList(customer1, customer2);

        Mockito.when(customerService.getAllCustomersAsync())
                .thenReturn(CompletableFuture.completedFuture(customers));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(customers.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(customer1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(customer1.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mobile").value(customer1.getMobile()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(customer2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value(customer2.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mobile").value(customer2.getMobile()));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer(1L, "John", "john@example.com", 12345678);

        Mockito.when(customerService.getCustomerByIdAsync(1L))
                .thenReturn(CompletableFuture.completedFuture(customer));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{id}", 1L))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(customer.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(customer.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(customer.getMobile()));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer(1L, "John", "john@example.com", 12345678);

        Mockito.when(customerService.createCustomerAsync(Mockito.any(Customer.class)))
                .thenReturn(CompletableFuture.completedFuture(customer));

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/customers")
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(customer.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(customer.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(customer.getMobile()));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer(1L, "John", "john@example.com", 12345678);

        Mockito.when(customerService.updateCustomerAsync(Mockito.any(Customer.class)))
                .thenReturn(CompletableFuture.completedFuture(customer));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/{id}", 1L)
                .content(asJsonString(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(customer.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(customer.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(customer.getMobile()));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Mockito.when(customerService.deleteCustomerAsync(1L))
                .thenReturn(CompletableFuture.completedFuture(null));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/{id}", 1L))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
