package kkashin.dev.exercise2.repository;

import kkashin.dev.exercise2.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
    select Order o
    where o.user.id = :userId
    and o.orderDate >= :startDate
    and o.orderDate < :endDate
""")
    List<Order> findAllUserOrdersLastMonth(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
            );

    @Query(value = """
    select p.*
    from order_items oi
    join products p on oi.product_id = p.id
    group by p.id
    order by sum(oi.quantity) desc
    limit 5
""", nativeQuery = true)
    List<Product> findTopFiveMostPopularProduct();

    @Query("""
    select p.category as category, sum(oi.quantity * oi.unitPrice) as revenue
    from OrderItem oi
    join oi.product p
    join oi.order o
    where o.status = 'completed'
    group by p.category
""")
    List<CategoryRevenueProjection> getRevenueByCategoryType();

    @Query("""
    select distinct u
    from Order o
    join o.user u
    join o.orderItems oi
    join oi.product p
    where p.category = :category
""")
    List<User> findUsersBoughtCategory(@Param("category") String category);

    @Modifying
    @Query(value = """
    update orders
    set status = 'archived'
    where order_date < current_date - interval '30 days'
""", nativeQuery = true)
    void archiveOldOrders();

    @Query("""
    select year(o.orderDate) as year,
        month(o.orderDate) as month,
        sum(oi.qunatity * oi.unitPrice) as revenue
    from Order o
    join o.orderItems oi
    where o.status = 'completed'
    group by year(o.orderDate), month(o.orderDate)
    order by year(o.orderDate), month(o.orderDate)
""")
    List<MonthlyRevenueProjection> getRevenueByMonths();

    @Query("""
    select p
    from Product p
    where p.stockQuantity < 10
""")
    List<Product> getProductsLeftLessThan10();
}
