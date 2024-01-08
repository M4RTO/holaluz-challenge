package com.example.holaluz.application.port.out;

import com.example.holaluz.adapter.jpa.model.SuspiciousEntity;
import com.example.holaluz.domain.Reading;
import com.example.holaluz.domain.SuspiciousDomain;

import java.util.List;

public interface SuspiciousPortOut {

    void uploadSuspicious(List<Reading> suspicious, double median);

    List<SuspiciousDomain> getSuspiciousById(String id);

}
