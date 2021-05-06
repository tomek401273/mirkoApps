package com.tgrajkowski.model.product;

import lombok.*;

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
