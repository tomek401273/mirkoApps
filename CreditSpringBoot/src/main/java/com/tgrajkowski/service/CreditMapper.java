package com.tgrajkowski.service;

import com.tgrajkowski.model.credit.CreditDto;
import com.tgrajkowski.model.credit.CreditEntity;
import org.springframework.stereotype.Component;

@Component
public class CreditMapper {
    public CreditDto mapToCreditDto(CreditEntity creditEntity) {
        return CreditDto.builder()
                .creditId(creditEntity.getCreditId())
                .creditName(creditEntity.getCreditName())
                .build();
    }

    public CreditEntity mapToCreditEntity(CreditDto creditDto, Integer customerId, Integer productId) {
        return CreditEntity
                .builder()
                .creditName(creditDto.getCreditName())
                .customerId(customerId)
                .productId(productId)
                .build();
    }
}
