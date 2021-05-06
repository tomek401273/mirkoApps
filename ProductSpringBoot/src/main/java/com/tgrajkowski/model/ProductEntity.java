package com.tgrajkowski.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product")
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "value")
    private Integer value;
}
