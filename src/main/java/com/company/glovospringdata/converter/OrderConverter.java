package com.company.glovospringdata.converter;

import com.company.glovospringdata.dto.OrderDto;
import com.company.glovospringdata.dto.ProductDto;
import com.company.glovospringdata.model.Order;
import com.company.glovospringdata.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
public class OrderConverter {

    public OrderDto fromModel(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .date(order.getDate())
                .cost(order.getCost())
                .products(productsFromModel(order.getProduct()))
                .build();
    }

    public List<OrderDto> fromModel(Iterable<Order> orders) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orders) {
            orderDtoList.add(fromModel(order));
        }
        return orderDtoList;
    }

    public Order toModel(OrderDto dto) {
        return Order.builder()
                .date(dto.getDate())
                .cost(dto.getCost())
                .product(productsToModel(dto.getProducts()))
                .build();
    }

    public Order toModel(Order order, OrderDto dto) {
        order.setDate(dto.getDate());
        order.setCost(dto.getCost());
        order.setProduct(productsToModel(dto.getProducts()));
        return order;
    }

    private List<ProductDto> productsFromModel(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        if (isNotEmpty(products)) {
            for (Product product : products) {
                ProductDto dto = ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .cost(product.getCost())
                        .build();
                productDtoList.add(dto);
            }
        }
        return productDtoList;
    }

    private List<Product> productsToModel(List<ProductDto> dtos) {
        List<Product> products = new ArrayList<>();
        if (isNotEmpty(dtos)) {
            for (ProductDto dto : dtos) {
                Product product = Product.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .cost(dto.getCost())
                        .build();
                products.add(product);
            }
        }
        return products;
    }
}
