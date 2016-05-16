package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Tran;
public interface TranRepository extends PagingAndSortingRepository<Tran, Long>,JpaSpecificationExecutor<Tran>{
}

