/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.repositories.CustomerRepository;
import guru.springframework.brewery.web.model.BeerOrderPagedList;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 *
 * @author miron.maksymiuk
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeerOrderControllerIT {
    
    @Autowired
    private TestRestTemplate testRestTemplate;
     @Autowired
    private CustomerRepository customerRepository;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = customerRepository.findAll().get(0);
    }
    
    /*
        By John Thompson
    */
    @Test
    void testListOrders() {
        String url = "/api/v1/customers/" + customer.getId().toString() + " /orders";

        BeerOrderPagedList pagedList = testRestTemplate.getForObject(url, BeerOrderPagedList.class);

        assertThat(pagedList.getContent()).hasSize(1);
    }

    /**
     * Test of placeOrder method, of class BeerOrderController.
     */
    @Test
    @Disabled
    public void testPlaceOrder() {
    }

    /**
     * Test of getOrder method, of class BeerOrderController.
     */
    @Test
    @Disabled
    public void testGetOrder() {
    }

    /**
     * Test of pickupOrder method, of class BeerOrderController.
     */
    @Test
    @Disabled
    public void testPickupOrder() {
    }
    
}
