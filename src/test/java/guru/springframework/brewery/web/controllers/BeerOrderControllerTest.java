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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author miron.maksymiuk
 */
@WebMvcTest(BeerOrderController.class)
public class BeerOrderControllerTest {
    
    @MockBean
    private BeerOrderService beerOrderService;
    @Autowired
    private MockMvc mockMvc;
    private BeerOrderDto beerOrder;
    private UUID custUUID;
    
    @BeforeEach
    public void setUp() {
        custUUID = UUID.randomUUID();
        beerOrder = BeerOrderDto.builder().id(UUID.randomUUID())
                .version(1)
                .customerId(custUUID)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
    }

    @AfterEach
    public void resetMocks() {
        reset(beerOrderService);
    }
    
    /**
     * Test of listOrders method, of class BeerOrderController.
     */
    @Test
    public void testListOrders() throws Exception {
        List<BeerOrderDto> beerOrders = new ArrayList<>();
        beerOrders.add(beerOrder);
        custUUID = UUID.randomUUID();
        beerOrders.add(BeerOrderDto.builder().id(UUID.randomUUID())
                .version(2)
                .customerId(custUUID)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build());
        BeerOrderPagedList beerPagedList = new BeerOrderPagedList(
                beerOrders, PageRequest.of(1, 1), 2L);

        given(beerOrderService.listOrders(any(UUID.class), any(PageRequest.class)))
            .willReturn(beerPagedList);
        
        mockMvc.perform(get("/api/v1/customers/{customerId}/orders", custUUID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.content[0].id", is(beerOrder.getId().toString())));
        
        then(beerOrderService).should().listOrders(any(UUID.class), any(PageRequest.class));
    }

    /**
     * Test of placeOrder method, of class BeerOrderController.
     */
    @Test
    @Disabled("not tested yet")
    public void testPlaceOrder() {
    }

    /**
     * Test of getOrder method, of class BeerOrderController.
     */
    @Test
    public void testGetOrder() throws Exception {
        given(beerOrderService.getOrderById(any(UUID.class), any(UUID.class)))
                .willReturn(beerOrder);
        mockMvc.perform(
                get("/api/v1/customers/{customerId}/orders/{orderId}", 
                        beerOrder.getCustomerId(), beerOrder.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(beerOrder.getId().toString())))
                .andExpect(jsonPath("$.customerId", is(custUUID.toString())))
                ;
        then(beerOrderService).should().getOrderById(any(UUID.class), any(UUID.class));
    }

    /**
     * Test of pickupOrder method, of class BeerOrderController.
     */
    @Test
    @Disabled("not tested yet")
    public void testPickupOrder() {
    }
    
}
