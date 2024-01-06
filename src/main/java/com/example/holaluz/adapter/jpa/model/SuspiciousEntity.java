package com.example.holaluz.adapter.jpa.model;

import com.example.holaluz.domain.Reading;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "suspicious")
@Data
public class SuspiciousEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "client")
    String clientId;

    @Column(name = "date_month")
    String month;

    @Column(name = "suspicious")
    Integer suspicious;

    @Column(name = "median")
    Double median;

    public static List<SuspiciousEntity> toEntity(List<Reading> suspicious, double median) {
        List<SuspiciousEntity> suspiciousEntityList = new ArrayList<>();
        suspicious.forEach( sus -> {
            SuspiciousEntity suspiciousEntity = new SuspiciousEntity();
            suspiciousEntity.setClientId(sus.getClientID());
            suspiciousEntity.setMonth(sus.getPeriod());
            suspiciousEntity.setSuspicious(sus.getReading());
            suspiciousEntity.setMedian(median);
            suspiciousEntityList.add(suspiciousEntity);
        });
        return suspiciousEntityList;
    }

}
