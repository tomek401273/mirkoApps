package com.tgrajkowski.controller;

import com.tgrajkowski.exception.ImproperSampleFormatException;
import com.tgrajkowski.model.CreditCustomerProductDto;
import com.tgrajkowski.model.credit.CreditDto;
import com.tgrajkowski.service.CreditService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/credit")
public class CreditController {
    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping
    public Integer createCredit(@Valid @RequestBody CreditCustomerProductDto creditCustomerProductDto) {
        return creditService.createCredit(creditCustomerProductDto);
    }

    @GetMapping("{creditId}")
    public CreditDto getCredit(@PathVariable("creditId") Integer creditId) {
        return creditService.getCredit(creditId);
    }

    @GetMapping
    public List<CreditCustomerProductDto> getAllCredit() {
        return creditService.findAll();
    }

    @ExceptionHandler(ImproperSampleFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleImproperSampleFormatException(ImproperSampleFormatException e) {
        return "Sorry one of microservices is down. Retry after a while. "+e.getMessage();
    }
}
