package com.example.order.rest.impl.integration;

import com.example.order.data.dao.OrderRepository;
import com.example.order.rest.OrderController;
import com.example.order.rest.configuration.constant.Headers;
import com.example.order.rest.impl.OrderControllerImpl;
import com.example.order.service.OrderService;
import com.example.order.service.model.OrderDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for {@link OrderController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 27.7.2022
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OrderService.class, OrderRepository.class})
@WebMvcTest(OrderControllerImpl.class)
@ComponentScan(basePackages = {"com.example.order"})
public class OrderControllerIntegrationTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    private static OrderDto order;

    @BeforeAll
    public static void setUp() {

        order = new OrderDto();
        order.setAmount(123D);
        order.setCustomerId(123L);
        order.setBookIdList(Collections.singletonList(5678L));
        order.setStatus("OK");
    }


    //@Test
    void testSave() throws Exception {

        String content = "{\n" +
                "    \"customerId\": 6692698889932193484,\n" +
                "    \"bookIdList\": [5385818005803788950],\n" +
                "    \"status\": \"OK\",\n" +
                "    \"amount\": 150,\n" +
                "    \"processDate\": \"2022-07-07\"\n" +
                "}";

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/save")
                .header(Headers.AUTHORIZATION, "token123")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL_VALUE);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }
}
