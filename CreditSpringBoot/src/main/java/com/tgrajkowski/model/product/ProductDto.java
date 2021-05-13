package com.tgrajkowski.model.product;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDto {
    private Integer productId;
    @NotEmpty
    private String productName;
    @NotNull
    private Integer value;
}
