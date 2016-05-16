package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Task;
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>,JpaSpecificationExecutor<Task>{
}

