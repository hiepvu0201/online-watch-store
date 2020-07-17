package com.group4.onlinewatchstore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Finance")
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private long amount;

    @Column(name = "order_id", nullable = false)
    private long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders")
    private Order order;

    @Column(name = "order_by_id", nullable = false)
    private long orderById;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_by")
    private User orderdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
