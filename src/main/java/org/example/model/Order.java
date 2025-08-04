package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"user"}) // ✅ Prevent infinite loop during JSON serialization
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String shippingAddress;

    private String phoneNumber;

    private String cardNumber;     // For demo: should be encrypted or masked in real apps
    private String cardExpiry;
    private String cvv;

    private double totalAmount;

    @ElementCollection
    private List<String> productNames;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // === Getters and Setters ===

    public Long getId() { return id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String name) { this.customerName = name; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String address) { this.shippingAddress = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardExpiry() { return cardExpiry; }
    public void setCardExpiry(String cardExpiry) { this.cardExpiry = cardExpiry; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double amount) { this.totalAmount = amount; }

    public List<String> getProductNames() { return productNames; }
    public void setProductNames(List<String> productNames) { this.productNames = productNames; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
