package kkashin.dev.exercise2.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Product() {}

    public Product(
            String name,
            String category,
            BigDecimal price,
            Integer stockQuantity
    ) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Product(
            String name,
            String category,
            BigDecimal price,
            Integer stockQuantity,
            List<OrderItem> orderItems
    ) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;

        for (OrderItem item : orderItems) {
            this.addItem(item);
        }
    }

    public void addItem(OrderItem item) {
        this.orderItems.add(item);
        item.setProduct(this);
    }

    public void removeItem(OrderItem item) {
        this.orderItems.remove(item);
        item.setProduct(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
