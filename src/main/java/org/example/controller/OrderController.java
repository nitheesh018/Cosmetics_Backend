package org.example.controller;

import org.example.model.Order;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> orderData) {
        try {
            String name = (String) orderData.get("name");
            String address = (String) orderData.get("address");
            String phone = (String) orderData.get("phoneNumber");
            String cardNumber = (String) orderData.get("cardNumber");
            String cardExpiry = (String) orderData.get("cardExpiry");
            String cvv = (String) orderData.get("cvv");
            double total = Double.parseDouble(orderData.get("total").toString());
            String email = (String) orderData.get("email");

            List<String> productNames = new ArrayList<>();
            List<?> rawList = (List<?>) orderData.get("productNames");
            for (Object item : rawList) {
                if (item instanceof String) {
                    productNames.add((String) item);
                }
            }

            Order order = orderService.placeOrder(
                    name, address, phone, cardNumber, cardExpiry, cvv,
                    total, productNames, email
            );

            return ResponseEntity.ok(order);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error placing order: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getOrdersForUser(@RequestParam String email) {
        try {
            List<Order> orders = orderService.getOrdersByUser(email);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error fetching orders: " + e.getMessage());
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error fetching all orders: " + e.getMessage());
        }
    }
}
