package com.tgrajkowski.service;

import com.tgrajkowski.model.CreditCustomerProductDto;
import com.tgrajkowski.model.credit.CreditDto;
import com.tgrajkowski.model.credit.CreditEntity;
import com.tgrajkowski.model.credit.CreditRepository;
import com.tgrajkowski.model.credit.CreditRepositoryPaging;
import com.tgrajkowski.model.customer.CustomerDto;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.service.customer.CustomerService;
import com.tgrajkowski.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CreditService {
    private final CreditMapper creditMapper;
    private final CreditRepositoryPaging creditRepositoryPaging;
    private final CreditRepository creditRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Autowired
    public CreditService(CreditMapper creditMapper, CreditRepositoryPaging creditRepositoryPaging, CreditRepository creditRepository, CustomerService customerService, ProductService productService) {
        this.creditMapper = creditMapper;
        this.creditRepositoryPaging = creditRepositoryPaging;
        this.creditRepository = creditRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public Integer createCredit(CreditCustomerProductDto creditCustomerProductDto) {
        Optional<CreditEntity> creditEntityOptional =
                creditRepositoryPaging.findCreditEntityByCreditName(creditCustomerProductDto.getCreditDto().getCreditName());
        if (creditEntityOptional.isEmpty()) {

            Integer customerId = customerService.postCustomer(creditCustomerProductDto.getCustomerDto());
            Integer productId = productService.postProduct(creditCustomerProductDto.getProductDto());
            if (customerId==null)
                throw  new RuntimeException("customer id Not present");

            if (productId==null)
                throw  new RuntimeException("product id not present ");

            CreditEntity creditEntity = creditMapper
                    .mapToCreditEntity(creditCustomerProductDto.getCreditDto(), customerId, productId);

            creditEntity =creditRepositoryPaging.save(creditEntity);
            return creditEntity.getCreditId();
        } else {
            return creditEntityOptional.get().getCreditId();
        }
    }


    public CreditDto getCredit(Integer creditId) {
        Optional<CreditEntity> creditEntityOptional = creditRepositoryPaging.findById(creditId);
        return creditEntityOptional
                .map(creditMapper::mapToCreditDto)
                .orElse(null);
    }


    public List<CreditCustomerProductDto> findAll() {

        CustomerDto[] customers = customerService.getCustomers();
        Map<Integer, CustomerDto> idCustomerDtoMap =customerService.groupById(customers);

        ProductDto[] productDtos = productService.getProducts();
        Map<Integer, ProductDto> idProductDtoMap = productService.groupById(productDtos);

        List<CreditEntity> creditEntities = creditRepository.findAll();

        List<CreditCustomerProductDto> creditCustomerProductDtos = new LinkedList<>();

        for (CreditEntity creditEntity : creditEntities) {
            creditCustomerProductDtos.add(
                    new CreditCustomerProductDto(
                            creditMapper.mapToCreditDto(creditEntity),
                            idCustomerDtoMap.get(creditEntity.getCustomerId()),
                            idProductDtoMap.get(creditEntity.getProductId())
                    ));
        }

        return creditCustomerProductDtos;
    }
}
