package com.tgrajkowski.model.credit;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Credit")
public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "credit_id")
    private Integer creditId;

    @Column(name = "credit_name")
    private String creditName;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "productId")
    private Integer productId;

    public CreditEntity(String creditName) {
        this.creditName = creditName;
    }
}
