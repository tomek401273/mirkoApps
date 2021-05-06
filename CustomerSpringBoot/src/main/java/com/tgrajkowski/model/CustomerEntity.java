package com.tgrajkowski.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "pesel")
    private String pesel;
    @Column(name = "surname")
    private String surname;
}
