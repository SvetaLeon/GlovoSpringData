package com.company.glovospringdata.controller;

import com.company.glovospringdata.controller.response.ApiResponse;
import com.company.glovospringdata.dto.OrderDto;
import com.company.glovospringdata.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping()
    public ApiResponse<List<OrderDto>> getOrders(@RequestParam(value = "pageNum", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                 @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                 @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        ApiResponse<List<OrderDto>> response = new ApiResponse<>();
        List<OrderDto> result = orderService.getOrders(pageable);
        if (result != null && !result.isEmpty()) {
            response.setSuccess(true);
            response.setData(result);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderDto> getOrderById(@PathVariable Integer id) {
        ApiResponse<OrderDto> response = new ApiResponse<>();
        OrderDto orderDto = orderService.getOrderById(id);
        if (orderDto != null) {
            response.setSuccess(true);
            response.setData(orderDto);
        }
        return response;
    }

    @PostMapping()
    public ApiResponse<OrderDto> createNewOrder(@RequestBody OrderDto dto) {
        ApiResponse<OrderDto> response = new ApiResponse<>();
        try {
            orderService.saveNewOrder(dto);
            response.setSuccess(true);
            response.setMessages(Collections.singletonList("Order created"));
        } catch (Exception e) {
            response.setSuccess(false);
            response.addMessage("Failed to create order");
        }
        return response;
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderDto> updateOrderById(@PathVariable Integer id, @RequestBody OrderDto dto) {
        ApiResponse<OrderDto> response = new ApiResponse<>();
        if (dto != null) {
            try {
                orderService.updateOrder(id, dto);
                response.setSuccess(true);
                response.setMessages(Collections.singletonList("Order updated"));
            } catch (Exception e) {
                response.setSuccess(false);
                response.addMessage("Failed to update order");
            }
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<OrderDto> deleteOrderById(@PathVariable Integer id) {
        ApiResponse<OrderDto> response = new ApiResponse<>();
        if (id != null) {
            try {
                orderService.deleteOrder(id);
                response.setSuccess(true);
                response.setMessages(Collections.singletonList("Order deleted"));
            } catch (Exception e) {
                response.setSuccess(false);
                response.addMessage("Failed to delete order");
            }
        }
        return response;
    }
}
