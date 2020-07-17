package com.group4.onlinewatchstore.entities;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "unit_price", nullable = false, columnDefinition="Decimal(19,4)")
    private double unitPrice;

    @Column(name = "unit_in_stock", nullable = false)
    private long unitInStock;

    @Type(type = "json")
    @Column(name = "product_images", columnDefinition = "json")
    private ArrayList<String> productImages;

    @Column(name = "is_disabled", columnDefinition = "boolean default false")
    private boolean isDisabled;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "brand_id", nullable = false)
    private long brandId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand")
    private Brand brand;

    @Column(name = "category_id", nullable = false)
    private long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;
}
