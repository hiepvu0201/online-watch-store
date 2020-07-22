package com.group4.onlinewatchstore.entities;

import com.group4.onlinewatchstore.entities.factory.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "status")
    private int status;

    @Lob
    @Column(name = "note")
    private String note;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_disabled", columnDefinition = "boolean default false")
    private boolean isDisabled;

    @Column(name = "created_by_id", nullable = false)
    private long createdById;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "modified_by_id")
    private long modifiedById;

    @ManyToOne
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems;
}
