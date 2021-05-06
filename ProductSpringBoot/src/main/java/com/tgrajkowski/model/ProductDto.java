package com.tgrajkowski.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDto {
    private Integer productId;
    private String productName;
    private Integer value;
}
