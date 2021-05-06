package com.tgrajkowski.model.credit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<CreditEntity, Integer> {
    Optional<CreditEntity> findCreditEntityByCreditName(String creditName);

//    List<CreditEntity> findAll(Pageable pageable);
}
