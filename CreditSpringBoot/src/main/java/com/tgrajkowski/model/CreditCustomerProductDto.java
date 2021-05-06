package com.tgrajkowski.model;

import com.tgrajkowski.model.credit.CreditDto;
import com.tgrajkowski.model.customer.CustomerDto;
import com.tgrajkowski.model.product.ProductDto;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreditCustomerProductDto {
    private CreditDto creditDto;
    private CustomerDto customerDto;
    private ProductDto productDto;
}
