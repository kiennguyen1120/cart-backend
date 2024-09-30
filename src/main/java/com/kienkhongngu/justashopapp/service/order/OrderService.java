package com.kienkhongngu.justashopapp.service.order;


import com.kienkhongngu.justashopapp.dto.OrderDto;
import com.kienkhongngu.justashopapp.entity.*;
import com.kienkhongngu.justashopapp.enums.OrderStatus;
import com.kienkhongngu.justashopapp.exception.ResourceNotFoundException;
import com.kienkhongngu.justashopapp.repository.OrderRepository;
import com.kienkhongngu.justashopapp.repository.ProductRepository;
import com.kienkhongngu.justashopapp.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrderFromCart(cart);
        cartService.clearCart(cart.getId());
        return orderRepository.save(order);
    }

    private Order createOrderFromCart(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> createOrderItemFromCartItem(order, cartItem))
                .toList();

        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        return order;
    }

    private OrderItem createOrderItemFromCartItem(Order order, CartItem cartItem) {
        Product product = cartItem.getProduct();
        product.setQuantity(product.getQuantity() - cartItem.getQuantity());
        productRepository.save(product);

        return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .toList();
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}

