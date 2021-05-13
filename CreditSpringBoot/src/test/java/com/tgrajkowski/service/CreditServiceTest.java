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
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest
class CreditServiceTest {
    @InjectMocks
    private CreditService creditService;
    @Mock
    private CreditMapper creditMapper;
    @Mock
    private CreditRepositoryPaging creditRepositoryPaging;
    @Mock
    private CreditRepository creditRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProductService productService;

    @Test
    void createCredit() {
    }

    @Test
    void getCredit() {
    }

    @Test
    void getCustomer() {
    }


    @Test
    void testCreateCredit() {
    }

    @Test
    void testFindAllOk() {
        CustomerDto[] customerDtos = new CustomerDto[]{
                CustomerDto.builder().id(1).firstName("customer1").build(),
                CustomerDto.builder().id(2).firstName("customer2").build()
        };
        ProductDto[] productDtos = new ProductDto[]{
                ProductDto.builder().productId(1).productName("productName1").value(12).build(),
                ProductDto.builder().productId(2).productName("productName2").value(13).build()
        };
        List<CreditEntity> creditEntities = new ArrayList<>();
        creditEntities.add(CreditEntity.builder().creditId(11).creditName("creditName1").productId(1).customerId(1).build());
        creditEntities.add(CreditEntity.builder().creditId(22).creditName("creditName2").productId(2).customerId(2).build());

        List<CreditDto> creditDtoList = new ArrayList<>();
        creditDtoList.add(CreditDto.builder().creditId(11).creditName("creditName1").build());
        creditDtoList.add(CreditDto.builder().creditId(22).creditName("creditName2").build());

        Map<Integer, ProductDto> idProductDtoMap = new HashMap<>();
        idProductDtoMap.put(1, productDtos[0]);
        idProductDtoMap.put(2, productDtos[1]);

        Map<Integer, CustomerDto> idCustomerDtoMap = new HashMap<>();
        idCustomerDtoMap.put(1, customerDtos[0]);
        idCustomerDtoMap.put(2, customerDtos[1]);

        Mockito.when(customerService.getCustomers()).thenReturn(customerDtos);
        Mockito.when(productService.getProducts()).thenReturn(productDtos);
        Mockito.when(creditRepository.findAll()).thenReturn(creditEntities);
        Mockito.when(creditMapper.mapToCreditDto(creditEntities.get(0))).thenReturn(creditDtoList.get(0));
        Mockito.when(creditMapper.mapToCreditDto(creditEntities.get(1))).thenReturn(creditDtoList.get(1));
        Mockito.when(customerService.groupById(customerDtos)).thenReturn(idCustomerDtoMap);
        Mockito.when(productService.groupById(productDtos)).thenReturn(idProductDtoMap);

        List<CreditCustomerProductDto> all = creditService.findAll();
        Assert.assertEquals(creditEntities.size(), all.size());
        Assert.assertEquals(creditEntities.get(0).getCreditId(), all.get(0).getCreditDto().getCreditId());
        Assert.assertEquals(creditEntities.get(0).getCreditName(), all.get(0).getCreditDto().getCreditName());
        Assert.assertEquals(customerDtos[0].getId(), all.get(0).getCustomerDto().getId());
        Assert.assertEquals(customerDtos[0].getPesel(), all.get(0).getCustomerDto().getPesel());
        Assert.assertEquals(customerDtos[0].getFirstName(), all.get(0).getCustomerDto().getFirstName());
        Assert.assertEquals(customerDtos[0].getSurname(), all.get(0).getCustomerDto().getSurname());
        Assert.assertEquals(productDtos[0].getProductId(), all.get(0).getProductDto().getProductId());
        Assert.assertEquals(productDtos[0].getProductName(), all.get(0).getProductDto().getProductName());
        Assert.assertEquals(productDtos[0].getValue(), all.get(0).getProductDto().getValue());
    }


    @Test
    void testFindAllEmptyCustomers() {
        CustomerDto[] customerDtos = new CustomerDto[]{};
        ProductDto[] productDtos = new ProductDto[]{
                ProductDto.builder().productId(1).productName("productName1").value(12).build(),
                ProductDto.builder().productId(2).productName("productName2").value(13).build()
        };
        List<CreditEntity> creditEntities = new ArrayList<>();
        creditEntities.add(CreditEntity.builder().creditId(11).creditName("creditName1").productId(1).customerId(1).build());
        creditEntities.add(CreditEntity.builder().creditId(22).creditName("creditName2").productId(2).customerId(2).build());

        List<CreditDto> creditDtoList = new ArrayList<>();
        creditDtoList.add(CreditDto.builder().creditId(11).creditName("creditName1").build());
        creditDtoList.add(CreditDto.builder().creditId(22).creditName("creditName2").build());

        Map<Integer, ProductDto> idProductDtoMap = new HashMap<>();
        idProductDtoMap.put(1, productDtos[0]);
        idProductDtoMap.put(2, productDtos[1]);

        Map<Integer, CustomerDto> idCustomerDtoMap = new HashMap<>();

        Mockito.when(customerService.getCustomers()).thenReturn(customerDtos);
        Mockito.when(productService.getProducts()).thenReturn(productDtos);
        Mockito.when(creditRepository.findAll()).thenReturn(creditEntities);
        Mockito.when(creditMapper.mapToCreditDto(creditEntities.get(0))).thenReturn(creditDtoList.get(0));
        Mockito.when(creditMapper.mapToCreditDto(creditEntities.get(1))).thenReturn(creditDtoList.get(1));
        Mockito.when(customerService.groupById(customerDtos)).thenReturn(idCustomerDtoMap);
        Mockito.when(productService.groupById(productDtos)).thenReturn(idProductDtoMap);

        List<CreditCustomerProductDto> all = creditService.findAll();
        Assert.assertEquals(creditEntities.size(), all.size());
        Assert.assertEquals(creditEntities.get(0).getCreditId(), all.get(0).getCreditDto().getCreditId());
        Assert.assertEquals(creditEntities.get(0).getCreditName(), all.get(0).getCreditDto().getCreditName());
        Assert.assertNull(all.get(0).getCustomerDto());

        Assert.assertEquals(productDtos[0].getProductId(), all.get(0).getProductDto().getProductId());
        Assert.assertEquals(productDtos[0].getProductName(), all.get(0).getProductDto().getProductName());
        Assert.assertEquals(productDtos[0].getValue(), all.get(0).getProductDto().getValue());
    }


    @Test
    void testFindAllEmptyProducts() {
        CustomerDto[] customerDtos = new CustomerDto[]{
                CustomerDto.builder().id(1).firstName("customer1").build(),
                CustomerDto.builder().id(2).firstName("customer2").build()
        };
        ProductDto[] productDtos = new ProductDto[]{};
        List<CreditEntity> creditEntities = new ArrayList<>();
        creditEntities.add(CreditEntity.builder().creditId(11).creditName("creditName1").productId(1).customerId(1).build());
        creditEntities.add(CreditEntity.builder().creditId(22).creditName("creditName2").productId(2).customerId(2).build());

        List<CreditDto> creditDtoList = new ArrayList<>();
        creditDtoList.add(CreditDto.builder().creditId(11).creditName("creditName1").build());
        creditDtoList.add(CreditDto.builder().creditId(22).creditName("creditName2").build());

        Map<Integer, ProductDto> idProductDtoMap = new HashMap<>();

        Map<Integer, CustomerDto> idCustomerDtoMap = new HashMap<>();
        idCustomerDtoMap.put(1, customerDtos[0]);
        idCustomerDtoMap.put(2, customerDtos[1]);

        Mockito.when(customerService.getCustomers()).thenReturn(customerDtos);
        Mockito.when(productService.getProducts()).thenReturn(productDtos);
        Mockito.when(creditRepository.findAll()).thenReturn(creditEntities);
        Mockito.when(creditMapper.mapToCreditDto(creditEntities.get(0))).thenReturn(creditDtoList.get(0));
        Mockito.when(creditMapper.mapToCreditDto(creditEntities.get(1))).thenReturn(creditDtoList.get(1));
        Mockito.when(customerService.groupById(customerDtos)).thenReturn(idCustomerDtoMap);
        Mockito.when(productService.groupById(productDtos)).thenReturn(idProductDtoMap);

        List<CreditCustomerProductDto> all = creditService.findAll();
        Assert.assertEquals(creditEntities.size(), all.size());
        Assert.assertEquals(creditEntities.get(0).getCreditId(), all.get(0).getCreditDto().getCreditId());
        Assert.assertEquals(creditEntities.get(0).getCreditName(), all.get(0).getCreditDto().getCreditName());
        Assert.assertEquals(customerDtos[0].getId(), all.get(0).getCustomerDto().getId());
        Assert.assertEquals(customerDtos[0].getPesel(), all.get(0).getCustomerDto().getPesel());
        Assert.assertEquals(customerDtos[0].getFirstName(), all.get(0).getCustomerDto().getFirstName());
        Assert.assertEquals(customerDtos[0].getSurname(), all.get(0).getCustomerDto().getSurname());
        Assert.assertNull(all.get(0).getProductDto());
    }


    @Test
    void testFindAllEmptyCredits() {
        CustomerDto[] customerDtos = new CustomerDto[]{
                CustomerDto.builder().id(1).firstName("customer1").build(),
                CustomerDto.builder().id(2).firstName("customer2").build()
        };
        ProductDto[] productDtos = new ProductDto[]{
                ProductDto.builder().productId(1).productName("productName1").value(12).build(),
                ProductDto.builder().productId(2).productName("productName2").value(13).build()
        };
        List<CreditEntity> creditEntities = new ArrayList<>();

        Map<Integer, ProductDto> idProductDtoMap = new HashMap<>();
        idProductDtoMap.put(1, productDtos[0]);
        idProductDtoMap.put(2, productDtos[1]);

        Map<Integer, CustomerDto> idCustomerDtoMap = new HashMap<>();
        idCustomerDtoMap.put(1, customerDtos[0]);
        idCustomerDtoMap.put(2, customerDtos[1]);

        Mockito.when(customerService.getCustomers()).thenReturn(customerDtos);
        Mockito.when(productService.getProducts()).thenReturn(productDtos);
        Mockito.when(creditRepository.findAll()).thenReturn(creditEntities);
        Mockito.when(customerService.groupById(customerDtos)).thenReturn(idCustomerDtoMap);
        Mockito.when(productService.groupById(productDtos)).thenReturn(idProductDtoMap);

        List<CreditCustomerProductDto> all = creditService.findAll();
        Assert.assertEquals(creditEntities.size(), all.size());
    }

    @Test
    public void createCreditTest() {
        CreditCustomerProductDto creditCustomerProductDto = CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditName("creditName1").build())
                .productDto(ProductDto.builder().productName("productName1").value(12).build())
                .customerDto(CustomerDto.builder().id(1).firstName("customer1").build())
                .build();
        Optional<CreditEntity> creditEntityOptional = Optional.empty();
        Integer customerId = 11;
        Integer productId = 13;
        Integer creditId = 123;
        CreditEntity creditEntity = CreditEntity.builder().creditName("creditName1").productId(1).customerId(1).build();
        CreditEntity creditEntitySaved = CreditEntity.builder().creditId(creditId).creditName("creditName1").productId(1).customerId(1).build();

        Mockito.when(creditRepositoryPaging.findCreditEntityByCreditName(creditCustomerProductDto.getCreditDto().getCreditName()))
                .thenReturn(creditEntityOptional);
        Mockito.when(customerService.postCustomer(creditCustomerProductDto.getCustomerDto())).thenReturn(customerId);
        Mockito.when(productService.postProduct(creditCustomerProductDto.getProductDto())).thenReturn(productId);

        Mockito.when(creditMapper.mapToCreditEntity(creditCustomerProductDto.getCreditDto(), customerId, productId))
                .thenReturn(creditEntity);
        Mockito.when(creditRepositoryPaging.save(Mockito.any())).thenReturn(creditEntitySaved);

        Integer result = creditService.createCredit(creditCustomerProductDto);
        Assert.assertEquals(creditId, result);
    }

    @Test
    public void createCreditAlreadyExistTest() {
        CreditCustomerProductDto creditCustomerProductDto = CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditName("creditName1").build())
                .productDto(ProductDto.builder().productName("productName1").value(12).build())
                .customerDto(CustomerDto.builder().id(1).firstName("customer1").build())
                .build();
        Integer creditId = 123;
        Optional<CreditEntity> creditEntityOptional = Optional.of(CreditEntity.builder().creditId(creditId).creditName("creditName1").productId(1).customerId(1).build());


        Mockito.when(creditRepositoryPaging.findCreditEntityByCreditName(creditCustomerProductDto.getCreditDto().getCreditName()))
                .thenReturn(creditEntityOptional);
        Integer result = creditService.createCredit(creditCustomerProductDto);
        Assert.assertEquals(creditId, result);
    }


    @Test
    public void createCreditCustomerIdIsNullTest() {
        CreditCustomerProductDto creditCustomerProductDto = CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditName("creditName1").build())
                .productDto(ProductDto.builder().productName("productName1").value(12).build())
                .customerDto(CustomerDto.builder().id(1).firstName("customer1").build())
                .build();
        Optional<CreditEntity> creditEntityOptional = Optional.empty();
        Integer customerId = null;
        Integer productId = 13;
        Integer creditId = 123;
        CreditEntity creditEntity = CreditEntity.builder().creditName("creditName1").productId(1).customerId(1).build();
        CreditEntity creditEntitySaved = CreditEntity.builder().creditId(creditId).creditName("creditName1").productId(1).customerId(1).build();

        Mockito.when(creditRepositoryPaging.findCreditEntityByCreditName(creditCustomerProductDto.getCreditDto().getCreditName()))
                .thenReturn(creditEntityOptional);
        Mockito.when(customerService.postCustomer(creditCustomerProductDto.getCustomerDto())).thenReturn(customerId);
        Mockito.when(productService.postProduct(creditCustomerProductDto.getProductDto())).thenReturn(productId);
        Mockito.when(creditMapper.mapToCreditEntity(creditCustomerProductDto.getCreditDto(), customerId, productId))
                .thenReturn(creditEntity);
        Mockito.when(creditRepositoryPaging.save(Mockito.any())).thenReturn(creditEntitySaved);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> creditService.createCredit(creditCustomerProductDto));
        Assertions.assertEquals("CustomerService didn't respond correctly.", runtimeException.getMessage());
    }


    @Test
    public void createCreditProductIdIsNullTest() {
        CreditCustomerProductDto creditCustomerProductDto = CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditName("creditName1").build())
                .productDto(ProductDto.builder().productName("productName1").value(12).build())
                .customerDto(CustomerDto.builder().id(1).firstName("customer1").build())
                .build();
        Optional<CreditEntity> creditEntityOptional = Optional.empty();
        Integer customerId = 155;
        Integer productId = null;
        Integer creditId = 123;
        CreditEntity creditEntity = CreditEntity.builder().creditName("creditName1").productId(1).customerId(1).build();
        CreditEntity creditEntitySaved = CreditEntity.builder().creditId(creditId).creditName("creditName1").productId(1).customerId(1).build();

        Mockito.when(creditRepositoryPaging.findCreditEntityByCreditName(creditCustomerProductDto.getCreditDto().getCreditName()))
                .thenReturn(creditEntityOptional);
        Mockito.when(customerService.postCustomer(creditCustomerProductDto.getCustomerDto())).thenReturn(customerId);
        Mockito.when(productService.postProduct(creditCustomerProductDto.getProductDto())).thenReturn(productId);
        Mockito.when(creditMapper.mapToCreditEntity(creditCustomerProductDto.getCreditDto(), customerId, productId))
                .thenReturn(creditEntity);
        Mockito.when(creditRepositoryPaging.save(Mockito.any())).thenReturn(creditEntitySaved);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> creditService.createCredit(creditCustomerProductDto));
        Assertions.assertEquals("ProductService didn't respond correctly.", runtimeException.getMessage());
    }


}
