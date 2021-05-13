package com.tgrajkowski.model.credit;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreditDto {
    private Integer creditId;
    @NotEmpty
    private String creditName;
}
