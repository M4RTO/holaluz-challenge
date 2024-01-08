package com.example.holaluz.application.usecase;

import com.example.holaluz.application.port.in.UploadFraudFilePortIn;
import com.example.holaluz.application.port.out.SuspiciousPortOut;
import com.example.holaluz.domain.Reading;
import com.example.holaluz.exception.NotFileExtensionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.holaluz.config.ErrorCode.INTERNAL_ERROR_EXTENSION;
import static com.example.holaluz.util.Parser.parseCSVtoDomain;
import static com.example.holaluz.util.Parser.parseXMLtoDomain;

@Component
@Slf4j
public class UploadFraudFileUseCase implements UploadFraudFilePortIn {
    private static final String TEXT_CSV_VALUE = "text/csv";

    private final SuspiciousPortOut suspiciousPortOut;

    @Autowired
    public UploadFraudFileUseCase(SuspiciousPortOut suspiciousPortOut) {
        this.suspiciousPortOut = suspiciousPortOut;
    }

    @Override
    public void execute(Command command) {
        MultipartFile file = command.getFile();
        List<Reading> readings;
        if (MediaType.APPLICATION_XML_VALUE.equals(file.getContentType())) {
            readings = parseXMLtoDomain(file);
            log.info("those are the readings: {}", readings);
        } else if (TEXT_CSV_VALUE.equals(file.getContentType())) {
            readings = parseCSVtoDomain(file);
            log.info("those are the readings: {}", readings);
        } else {
            throw new NotFileExtensionException(INTERNAL_ERROR_EXTENSION);
        }

        Map<String, List<Reading>> groupByClient = readings.stream()
                .collect(Collectors.groupingBy(Reading::getClientID));

        groupByClient.forEach((key, value) -> {
            log.info("Calculating median of client : {} ", key);
            double median = calculateMedian(value);
            log.info("The median of client {} is: {}",key, median);
            List<Reading> suspicious = calculateSuspicious(value, median);
            if (!suspicious.isEmpty()) {
                log.info("These are the suspicions found : {}", suspicious);
                suspiciousPortOut.uploadSuspicious(suspicious, median);
            }
        });
    }

    private List<Reading> calculateSuspicious(List<Reading> readings, double median) {
        double minor50Percent = median - (median * 0.50);
        double plus50Percent = median + (median * 0.50);

        return readings.stream()
                .filter(value -> value.getReading() < minor50Percent || value.getReading() > plus50Percent)
                .toList();
    }

    private double calculateMedian(List<Reading> readings) {
        readings.sort(Comparator.comparing(Reading::getReading));
        int size = readings.size();
        int firstNumber = readings.get((size / 2) - 1).getReading();
        int secondNumber = readings.get(size / 2).getReading();
        return (double) (firstNumber + secondNumber) / 2;
    }
}

