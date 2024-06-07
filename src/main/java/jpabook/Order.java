package jpabook;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @ManyToOne // 주문 : 회원 = N : 1
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(Long id, Member member, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.id = id;
        this.member = member;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
