package com.tgrajkowski.service;

import com.tgrajkowski.configuration.ApplicationConfig;
import com.tgrajkowski.model.CreditCustomerProductDto;
import com.tgrajkowski.model.credit.CreditDto;
import com.tgrajkowski.model.credit.CreditEntity;
import com.tgrajkowski.model.credit.CreditRepository;
import com.tgrajkowski.model.credit.CreditRepositoryPaging;
import com.tgrajkowski.model.customer.CustomerDto;
import com.tgrajkowski.model.product.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class CreditService {
    private final CreditMapper creditMapper;
    private final CreditRepositoryPaging creditRepositoryPaging;
    private final RetrieveDataFromApi retrieveDataFromApi;
    private final ApplicationConfig applicationConfig;
    private final CreditRepository creditRepository;

    public CreditService(CreditMapper creditMapper, CreditRepositoryPaging creditRepositoryPaging, RetrieveDataFromApi retrieveDataFromApi, ApplicationConfig applicationConfig, CreditRepository creditRepository) {
        this.creditMapper = creditMapper;
        this.creditRepositoryPaging = creditRepositoryPaging;
        this.retrieveDataFromApi = retrieveDataFromApi;
        this.applicationConfig = applicationConfig;
        this.creditRepository = creditRepository;
    }

    @Transactional
    public Integer createCredit(CreditCustomerProductDto creditCustomerProductDto) {
        Optional<CreditEntity> creditEntityOptional =
                creditRepositoryPaging.findCreditEntityByCreditName(creditCustomerProductDto.getCreditDto().getCreditName());
        if (creditEntityOptional.isEmpty()) {

            Integer customerId = postCustomer(creditCustomerProductDto.getCustomerDto());
            Integer productId = postProduct(creditCustomerProductDto.getProductDto());

            CreditEntity creditEntity = creditMapper
                    .mapToCreditEntity(creditCustomerProductDto.getCreditDto(), customerId, productId);

            creditRepositoryPaging.save(creditEntity);
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

    public CustomerDto[] getCustomers() {
        URI uri = createCustomerUri("");
        CustomerDto[] customerDtos = retrieveDataFromApi.getCustomersData(uri);
        return customerDtos;
    }

    public ProductDto[] getProducts() {
        URI uri = createProductUri("");
        ProductDto[] customerDtos = retrieveDataFromApi.getProductsData(uri);
        return customerDtos;
    }

    public Integer postProduct(ProductDto productDto) {
        URI uri = createProductUri("");
        return retrieveDataFromApi.postData(uri, productDto);
    }

    public URI createProductUri(String productId) {
        return UriComponentsBuilder.fromHttpUrl(applicationConfig.getProductApiLink() + "/product/" + productId)
                .build().encode().toUri();
    }

    public Integer postCustomer(CustomerDto customerDto) {
        URI uri = createCustomerUri("");
        return retrieveDataFromApi.postData(uri, customerDto);
    }

    public URI createCustomerUri(String customerId) {
        return UriComponentsBuilder.fromHttpUrl(applicationConfig.getCustomerApiLink() + "/customer/" + customerId)
                .build().encode().toUri();
    }


    public List<CreditCustomerProductDto> findAll() {
        List<CreditEntity> creditEntities = creditRepository.findAll();
        CustomerDto[] customers = getCustomers();
        ProductDto[] productDtos = getProducts();
        Map<Integer, CustomerDto> integerCustomerDtoMap = new HashMap<>();
        for (CustomerDto customerDto : customers
        ) {
            integerCustomerDtoMap.put(customerDto.getId(), customerDto);
        }

        Map<Integer, ProductDto> integerProductDtoMap = new HashMap<>();
        for (ProductDto productDto : productDtos
        ) {
            integerProductDtoMap.put(productDto.getProductId(), productDto);
        }

        List<CreditCustomerProductDto> creditCustomerProductDtos = new ArrayList<>();


        for (CreditEntity creditEntity : creditEntities
        ) {
            creditCustomerProductDtos.add(
                    new CreditCustomerProductDto(
                            creditMapper.mapToCreditDto(creditEntity),
                            integerCustomerDtoMap.get(creditEntity.getCustomerId()),
                            integerProductDtoMap.get(creditEntity.getProductId())
                    ));
        }

        return creditCustomerProductDtos;
    }
}
