package com.tgrajkowski.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "CreditDb", name = "Credit")
public class CreditEntity {
    @Id
    @Column(name = "credit_id")
    private Integer creditId;

    @Column(name = "creditName")
    private String creditName;
}
