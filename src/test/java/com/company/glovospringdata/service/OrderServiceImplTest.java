package com.company.glovospringdata.service;

import com.company.glovospringdata.converter.OrderConverter;
import com.company.glovospringdata.dto.OrderDto;
import com.company.glovospringdata.model.Order;
import com.company.glovospringdata.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private static final int ORDER_ID = 1;
    private static final Pageable PAGEABLE = Pageable.unpaged();

    @InjectMocks
    private OrderServiceImpl testInstance;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderConverter orderConverter;

    @Mock
    private Page<Order> page;

    @Mock
    private List<Order> orders;

    @Mock
    private List<OrderDto> orderDtoList;

    @Mock
    private Order order;

    private OrderDto dto;

    @BeforeEach
    public void init() {
        dto = new OrderDto();
        dto.setId(ORDER_ID);
        dto.setDate(LocalDate.now());
        dto.setCost(10.55);
        dto.setProducts(null);
    }

    @Test
    void shouldReturnOrders() {
        when(orderRepository.findAll(PAGEABLE)).thenReturn(page);
        when(page.getContent()).thenReturn(orders);
        when(orderConverter.fromModel(orders)).thenReturn(orderDtoList);

        List<OrderDto> result = testInstance.getOrders(PAGEABLE);

        verify(orderRepository, times(1)).findAll(PAGEABLE);
        verify(orderConverter, times(1)).fromModel(orders);
        assertNotNull(result);
        assertEquals(orderDtoList, result);
    }

    @Test
    void shouldReturnOrderById() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(orderConverter.fromModel(order)).thenReturn(dto);

        OrderDto result = testInstance.getOrderById(ORDER_ID);

        verify(orderRepository, times(1)).findById(ORDER_ID);
        verify(orderConverter, times(1)).fromModel(order);
        assertNotNull(result);
        assertEquals(ORDER_ID, result.getId());
    }

    @Test
    void shouldNotReturnOrderById() {
        assertThrows(NoSuchElementException.class, () -> {
            testInstance.getOrderById(ORDER_ID);
        });
    }

    @Test
    void shouldCreateNewOrder() {
        when(orderConverter.toModel(dto)).thenReturn(order);

        testInstance.saveNewOrder(dto);

        verify(orderConverter, times(1)).toModel(dto);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldUpdateOrder() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(orderConverter.toModel(order, dto)).thenReturn(order);

        testInstance.updateOrder(ORDER_ID, dto);

        verify(orderRepository, times(1)).findById(ORDER_ID);
        verify(orderConverter, times(1)).toModel(order, dto);
        verify(orderRepository, times(1)).save(order);
        assertNotNull(order);
        assertNotNull(order.getId());
    }

    @Test
    void shouldNotUpdateOrder() {
        assertThrows(NoSuchElementException.class, () -> {
            testInstance.updateOrder(ORDER_ID, dto);
        });
    }

    @Test
    void shouldDeleteOrder() {
        testInstance.deleteOrder(ORDER_ID);

        verify(orderRepository, times(1)).deleteById(ORDER_ID);
    }
}