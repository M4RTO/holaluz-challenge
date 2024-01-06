package com.example.holaluz.application.port.out;

import com.example.holaluz.domain.Reading;

import java.util.List;

public interface SuspiciousPortOut {

    void uploadSuspicious(List<Reading> suspicious, double median);

}
