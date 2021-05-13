package com.tgrajkowski.model;

import com.tgrajkowski.model.credit.CreditDto;
import com.tgrajkowski.model.customer.CustomerDto;
import com.tgrajkowski.model.product.ProductDto;
import lombok.*;

import javax.validation.Valid;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreditCustomerProductDto {
    @Valid
    private CreditDto creditDto;
    @Valid
    private CustomerDto customerDto;
    @Valid
    private ProductDto productDto;
}
