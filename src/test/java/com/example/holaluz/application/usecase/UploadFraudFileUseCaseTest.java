package com.example.holaluz.application.usecase;

import com.example.holaluz.application.port.in.UploadFraudFilePortIn;
import com.example.holaluz.application.port.out.SuspiciousPortOut;
import com.example.holaluz.exception.NotFileExtensionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Tests cases for upload fraud use case")
@ExtendWith(MockitoExtension.class)
public class UploadFraudFileUseCaseTest {


    @Mock
    private SuspiciousPortOut suspiciousPortOut;

    @InjectMocks
    private UploadFraudFileUseCase uploadFraudFileUseCase;

    @BeforeEach
    void setUp() {
        uploadFraudFileUseCase = new UploadFraudFileUseCase(suspiciousPortOut);
    }


    @Test
    void testExecuteWithXMLFileWith0Suspicious() {
        // Arrange
        String xml = "<?xml version='1.0'?>" +
                "<readings>" +
                "<reading clientID='583ef6329df6b' period='2016-01'>37232</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-02'>36537</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-03'>36430</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-04'>36622</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-05'>37776</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-06'>35382</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-07'>37970</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-08'>38463</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-09'>35252</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-10'>35704</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-11'>38220</reading>" +
                "<reading clientID='583ef6329df6b' period='2016-12'>36688</reading>" +
                "</readings>";
        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "application/xml", xml.getBytes());
        final UploadFraudFilePortIn.Command command = UploadFraudFilePortIn.Command.builder()
                .file(file)
                .build();

        // Act
        uploadFraudFileUseCase.execute(command);

        // Assert
        // Add your assertions here
        verify(suspiciousPortOut, times(0)).uploadSuspicious(any(), anyDouble());
    }

    @Test
    void testExecuteWithXMLFileWithSuspicious() {
        // Given
        String xml = "<?xml version='1.0'?>" +
                "<readings>" +
                "<reading clientID='583ef6329e271' period='2016-01'>21921</reading>" +
                "<reading clientID='583ef6329e271' period='2016-02'>21732</reading>" +
                "<reading clientID='583ef6329e271' period='2016-03'>20403</reading>" +
                "<reading clientID='583ef6329e271' period='2016-04'>21068</reading>" +
                "<reading clientID='583ef6329e271' period='2016-05'>20500</reading>" +
                "<reading clientID='583ef6329e271' period='2016-06'>21590</reading>" +
                "<reading clientID='583ef6329e271' period='2016-07'>21852</reading>" +
                "<reading clientID='583ef6329e271' period='2016-08'>21223</reading>" +
                "<reading clientID='583ef6329e271' period='2016-09'>22424</reading>" +
                "<reading clientID='583ef6329e271' period='2016-10'>121208</reading>" +
                "<reading clientID='583ef6329e271' period='2016-11'>21338</reading>" +
                "<reading clientID='583ef6329e271' period='2016-12'>21892</reading>" +
                "</readings>";
        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "application/xml", xml.getBytes());
        final UploadFraudFilePortIn.Command command = UploadFraudFilePortIn.Command.builder()
                .file(file)
                .build();

        // When
        uploadFraudFileUseCase.execute(command);

        // Then
        Mockito.verify(suspiciousPortOut, Mockito.times(1)).uploadSuspicious(anyList(), anyDouble());

    }

    @Test
    void testExecuteWithCSVFileWithSuspicious() {
        // Given

        String csvFile = "client,period,reading\n" +
                "583ef6329d7b9,2016-01,42451\n" +
                "583ef6329d7b9,2016-02,44279\n" +
                "583ef6329d7b9,2016-03,44055\n" +
                "583ef6329d7b9,2016-04,40953\n" +
                "583ef6329d7b9,2016-05,42566\n" +
                "583ef6329d7b9,2016-06,41216\n" +
                "583ef6329d7b9,2016-07,43597\n" +
                "583ef6329d7b9,2016-08,43324\n" +
                "583ef6329d7b9,2016-09,3564\n" +
                "583ef6329d7b9,2016-10,44459\n" +
                "583ef6329d7b9,2016-11,42997\n" +
                "583ef6329d7b9,2016-12,42600";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvFile.getBytes());
        final UploadFraudFilePortIn.Command command = UploadFraudFilePortIn.Command.builder()
                .file(file)
                .build();

        // When
        uploadFraudFileUseCase.execute(command);

        // Then
        Mockito.verify(suspiciousPortOut, Mockito.times(1)).uploadSuspicious(anyList(), anyDouble());
    }

    @Test
    void testExecuteWithCSVFileWithS0uspicious() {
        // Given

        String csvFile = "client,period,reading\n" +
                "583ef6329d81f,2016-01,39760\n" +
                "583ef6329d81f,2016-02,38785\n" +
                "583ef6329d81f,2016-03,37519\n" +
                "583ef6329d81f,2016-04,39028\n" +
                "583ef6329d81f,2016-05,39469\n" +
                "583ef6329d81f,2016-06,37463\n" +
                "583ef6329d81f,2016-07,37152\n" +
                "583ef6329d81f,2016-08,37756\n" +
                "583ef6329d81f,2016-09,37398\n" +
                "583ef6329d81f,2016-10,37770\n" +
                "583ef6329d81f,2016-11,38948\n" +
                "583ef6329d81f,2016-12,37342";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvFile.getBytes());
        final UploadFraudFilePortIn.Command command = UploadFraudFilePortIn.Command.builder()
                .file(file)
                .build();

        // When
        uploadFraudFileUseCase.execute(command);

        // Then
        Mockito.verify(suspiciousPortOut, Mockito.times(0)).uploadSuspicious(anyList(), anyDouble());
    }

    @Test
    void testExecuteWithInvalidFileExtension() {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "invalid content".getBytes());
        final UploadFraudFilePortIn.Command command = UploadFraudFilePortIn.Command.builder()
                .file(file)
                .build();
        // Then
        assertThrows(NotFileExtensionException.class, () -> uploadFraudFileUseCase.execute(command));
    }
}
