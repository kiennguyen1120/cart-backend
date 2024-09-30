package com.kienkhongngu.justashopapp.service.order;

import com.kienkhongngu.justashopapp.dto.OrderDto;
import com.kienkhongngu.justashopapp.entity.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
