package kkashin.dev.exercise2.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itd;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    private LocalDateTime orderDate;
    private String status;

    protected Order() {}

    public Order(User user, String status) {
        this.user = user;
        this.status = status;
        this.orderDate = LocalDateTime.now();
    }

    public Order(User user, List<OrderItem> items, String status) {
        this.user = user;
        this.status = status;
        this.orderDate = LocalDateTime.now();

        for (OrderItem item : items) {
            addItem(item);
        }
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
