package com.tgrajkowski.model.credit;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreditDto {
    private Integer creditId;
    private String creditName;
}
