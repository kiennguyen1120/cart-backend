package com.kienkhongngu.justashopapp.service.cart;

import com.kienkhongngu.justashopapp.entity.Cart;
import com.kienkhongngu.justashopapp.entity.CartItem;
import com.kienkhongngu.justashopapp.entity.Product;
import com.kienkhongngu.justashopapp.exception.ResourceNotFoundException;
import com.kienkhongngu.justashopapp.repository.CartItemRepository;
import com.kienkhongngu.justashopapp.repository.CartRepository;
import com.kienkhongngu.justashopapp.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);

        // Check if the item already exists in the cart
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> createNewCartItem(cart, product, quantity));

        // If item already exists, just update the quantity
        if (cartItem.getId() != null) {
            updateItemQuantity(cartItem, cartItem.getQuantity() + quantity);
        }

        saveCartItem(cart, cartItem);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {

        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        CartItem item = getCartItem(cartId, productId);
        updateItemQuantity(item, quantity);
        updateCartTotalAmount(cart);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return  cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

    // Helper method to create a new CartItem
    private CartItem createNewCartItem(Cart cart, Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice();
        return cartItem;
    }

    // Helper method to update item quantity and total price
    private void updateItemQuantity(CartItem item, int quantity) {
        item.setQuantity(quantity);
        item.setTotalPrice();
        cartItemRepository.save(item);
    }

    // Helper method to save cart and update total amount
    private void saveCartItem(Cart cart, CartItem cartItem) {
        cart.addItem(cartItem);
        updateCartTotalAmount(cart);
        cartRepository.save(cart);
    }

    // Helper method to calculate total amount of the cart
    private void updateCartTotalAmount(Cart cart) {
        BigDecimal totalAmount = cart.getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
    }
}
