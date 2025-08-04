package org.example.service;

import org.example.model.Order;
import org.example.model.User;
import org.example.repository.OrderRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Place order with phone & payment details
    public Order placeOrder(
            String name,
            String address,
            String phone,
            String cardNumber,
            String cardExpiry,
            String cvv,
            double totalAmount,
            List<String> productNames,
            String email
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // 🎓 Apply 20% discount for @csu.edu users
        if (email.toLowerCase().endsWith("@csu.edu")) {
            totalAmount *= 0.8;
        }

        Order order = new Order();
        order.setCustomerName(name);
        order.setShippingAddress(address);
        order.setPhoneNumber(phone);
        order.setCardNumber(cardNumber);
        order.setCardExpiry(cardExpiry);
        order.setCvv(cvv);
        order.setTotalAmount(totalAmount);
        order.setProductNames(productNames);
        order.setUser(user);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUserId(user.getId());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
