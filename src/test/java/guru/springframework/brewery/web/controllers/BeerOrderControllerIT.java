/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.services.BeerOrderService;
import guru.springframework.brewery.web.model.BeerOrderDto;
import guru.springframework.brewery.web.model.BeerOrderPagedList;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author miron.maksymiuk
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeerOrderControllerIT {
    
    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private BeerOrderService beerOrderService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of listOrders method, of class BeerOrderController.
     */
    @Test
    public void testListOrders() {
        List<BeerOrderDto> beerOrders = new ArrayList<>();
        beerOrders.add(BeerOrderDto.builder().id(UUID.randomUUID())
                .version(1)
                .customerId(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build());
        beerOrders.add(BeerOrderDto.builder().id(UUID.randomUUID())
                .version(2)
                .customerId(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build());
        BeerOrderPagedList beerPagedList = new BeerOrderPagedList(
                beerOrders, PageRequest.of(1, 1), 2L);
        
        given(beerOrderService.listOrders(
                any(UUID.class), any(PageRequest.class)))
                .willReturn(beerPagedList);
        
        BeerOrderPagedList beerOrderPagedList = restTemplate.getForObject(
                "/api/v1/customers/{customerId}/orders", 
                BeerOrderPagedList.class,
                UUID.randomUUID());

        assertThat(beerOrderPagedList.getContent()).hasSize(2);
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
