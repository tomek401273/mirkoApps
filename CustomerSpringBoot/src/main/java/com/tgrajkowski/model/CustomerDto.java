package com.tgrajkowski.model;


import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerDto {

    private Integer id;

    @NotEmpty
    @Size(max = 20)
    @NotNull
    private String firstName;

    @NotEmpty
    @Size(max = 20)
    @NotNull
    @PESEL
    private String pesel;

    @NotEmpty
    @Size(max = 20)
    @NotNull
    private String surname;
}
