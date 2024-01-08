package com.example.holaluz.application.usecase;

import com.example.holaluz.application.port.out.SuspiciousPortOut;
import com.example.holaluz.exception.NotFoundSuspiciousException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetReportUseCaseTest {

    @Mock
    private SuspiciousPortOut suspiciousPortOut;

    @InjectMocks
    private GetReportUseCase getReportUseCase;


    @Test()
    public void testExecuteNoSuspicious()  {
        String clientId = "nonExistentClientId";
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        when(suspiciousPortOut.getSuspiciousById(clientId)).thenReturn(Collections.emptyList());

        assertThrows(NotFoundSuspiciousException.class, () -> getReportUseCase.execute(clientId, mockResponse));

    }
}

