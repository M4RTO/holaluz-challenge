package com.example.holaluz.adapter.jpa;

import com.example.holaluz.adapter.jpa.model.SuspiciousEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SuspiciousJPARepository extends CrudRepository<SuspiciousEntity, Long>{

    List<SuspiciousEntity> findByClient(String id);
}

