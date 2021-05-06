package com.tgrajkowski.model;

import com.tgrajkowski.model.credit.CreditEntity;
import com.tgrajkowski.model.credit.CreditRepositoryPaging;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CreditRepositoryPagingTest {
    @Autowired
    private CreditRepositoryPaging creditRepositoryPaging;

    @Test
    void findCreditEntityByCreditName() {
        List<CreditEntity> creditEntityList= new ArrayList<>();
        creditEntityList.add(new CreditEntity("creditName0"));
        creditEntityList.add(new CreditEntity("creditName1"));
        creditEntityList.add(new CreditEntity("creditName2"));
        creditEntityList.add(new CreditEntity("creditName3"));
        creditEntityList.add(new CreditEntity("creditName4"));
        creditEntityList.add(new CreditEntity("creditName5"));
        creditEntityList.add(new CreditEntity("creditName6"));
        creditEntityList.add(new CreditEntity("creditName7"));
        creditEntityList.add(new CreditEntity("creditName8"));
        creditRepositoryPaging.saveAll(creditEntityList);

        Pageable
                pageable = PageRequest.of(0, 2);
        Page<CreditEntity>
                creditEntities = creditRepositoryPaging.findAll(pageable);
        Assert.assertEquals(creditEntityList.get(0).getCreditName(), creditEntities.getContent().get(0).getCreditName());
        Assert.assertEquals(creditEntityList.get(1).getCreditName(), creditEntities.getContent().get(1).getCreditName());

        pageable = PageRequest.of(0, 4);
        creditEntities = creditRepositoryPaging.findAll(pageable);
        Assert.assertEquals(creditEntityList.get(2).getCreditName(), creditEntities.getContent().get(2).getCreditName());
        Assert.assertEquals(creditEntityList.get(3).getCreditName(), creditEntities.getContent().get(3).getCreditName());


        pageable = PageRequest.of(0, 6);
        creditEntities = creditRepositoryPaging.findAll(pageable);
        Assert.assertEquals(creditEntityList.get(4).getCreditName(), creditEntities.getContent().get(4).getCreditName());
        Assert.assertEquals(creditEntityList.get(5).getCreditName(), creditEntities.getContent().get(5).getCreditName());


    }
}
