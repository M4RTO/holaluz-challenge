package com.example.holaluz.adapter.jpa.model;

import com.example.holaluz.domain.Reading;
import com.example.holaluz.domain.SuspiciousDomain;
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
    String client;

    @Column(name = "date_month")
    String date_month;

    @Column(name = "suspicious")
    Integer suspicious;

    @Column(name = "median")
    Double median;

    public static List<SuspiciousEntity> toEntity(List<Reading> suspicious, double median) {
        List<SuspiciousEntity> suspiciousEntityList = new ArrayList<>();
        suspicious.forEach( sus -> {
            SuspiciousEntity suspiciousEntity = new SuspiciousEntity();
            suspiciousEntity.setClient(sus.getClientID());
            suspiciousEntity.setDate_month(sus.getPeriod());
            suspiciousEntity.setSuspicious(sus.getReading());
            suspiciousEntity.setMedian(median);
            suspiciousEntityList.add(suspiciousEntity);
        });
        return suspiciousEntityList;
    }

    public static List<SuspiciousDomain> toDomain(List<SuspiciousEntity> suspiciousEntities) {
        List<SuspiciousDomain> suspiciousDomainList = new ArrayList<>();
        suspiciousEntities.forEach( sus -> {
            SuspiciousDomain suspiciousDomain = new SuspiciousDomain();
            suspiciousDomain.setClient(sus.getClient());
            suspiciousDomain.setDate_month(sus.getDate_month());
            suspiciousDomain.setSuspicious(sus.getSuspicious());
            suspiciousDomain.setMedian(suspiciousDomain.getMedian());
            suspiciousDomainList.add(suspiciousDomain);
        });
        return suspiciousDomainList;
    }
}
