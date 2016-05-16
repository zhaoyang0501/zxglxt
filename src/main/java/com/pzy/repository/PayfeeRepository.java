package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Payfee;
public interface PayfeeRepository extends PagingAndSortingRepository<Payfee, Long>,JpaSpecificationExecutor<Payfee>{
}

