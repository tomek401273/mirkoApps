package com.tgrajkowski.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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
