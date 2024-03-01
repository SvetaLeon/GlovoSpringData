package com.company.glovospringdata.repository;

import com.company.glovospringdata.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    Page<Order> findAll(Pageable pageable);
}
