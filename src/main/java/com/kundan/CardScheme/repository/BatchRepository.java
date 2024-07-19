package com.kundan.CardScheme.repository;

import com.kundan.CardScheme.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepository extends JpaRepository<BatchEntity, Integer> {


    BatchEntity findByBatchName(String batchName);

    List<BatchEntity> findByIsCompletedFalse();

}
