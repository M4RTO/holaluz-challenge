package com.example.holaluz.adapter.jpa;

import com.example.holaluz.adapter.jpa.model.SuspiciousEntity;
import com.example.holaluz.application.port.out.SuspiciousPortOut;
import com.example.holaluz.domain.Reading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class SuspiciousAdapter implements SuspiciousPortOut {

    private final SuspiciousJPARepository repository;

    @Autowired
    public SuspiciousAdapter(SuspiciousJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public void uploadSuspicious(List<Reading> suspicious, double median) {
        log.info("save all suspicious");
        List<SuspiciousEntity> entityList = SuspiciousEntity.toEntity(suspicious, median);
        repository.saveAll(entityList);
    }


}
