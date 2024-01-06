package com.example.holaluz.adapter.jpa;

import com.example.holaluz.domain.Reading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Test jpa adapter suspicious")
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SuspiciousAdapterTest {

    @Autowired
    private SuspiciousJPARepository repository;

    @MockBean
    private SuspiciousAdapter adapter;

    @Test
    @DisplayName("Test save all suspicious")
    public void testSaveAllSuspicious() {
        // Given
        Reading reading = new Reading();
        List<Reading> readingList = new ArrayList<>();
        readingList.add(reading);
        double medianMock = 2.0;
        // When
        adapter.uploadSuspicious(readingList,medianMock);

        // Then
        Assertions.assertNotNull(repository.findAll());
    }
}
