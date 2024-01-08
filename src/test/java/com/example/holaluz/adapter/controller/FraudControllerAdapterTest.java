package com.example.holaluz.adapter.controller;

import com.example.holaluz.application.port.in.GetReportPortIn;
import com.example.holaluz.application.port.in.UploadFraudFilePortIn;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("FraudController Adapter Test")
@WebMvcTest(FraudControllerAdapter.class)
@AutoConfigureWebClient(registerRestTemplate = true)
public class FraudControllerAdapterTest {

    private static final String URL_FRAUD = "/api/v1/frauds";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UploadFraudFilePortIn uploadFraudFilePortIn;


    @MockBean
    private GetReportPortIn getReportPortIn;

    @Test
    @DisplayName("when upload controller is called, the adapter should return ok")
    void uploadOk() throws Exception {
        final var fileMock = new MockMultipartFile("file", "2016-readings.csv", "text/csv","X0213".getBytes());

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .multipart(HttpMethod.POST, URL_FRAUD)
                                .file(fileMock)
                        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(uploadFraudFilePortIn, times(1)).execute(UploadFraudFilePortIn.Command.builder().file(fileMock).build());
    }

    @Test
    @DisplayName("when upload controller is called, the adapter should return not ok")
    void uploadWithError() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .multipart(HttpMethod.POST, URL_FRAUD))
                .andDo(print())
        .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetReport() throws Exception {
        String clientId = "yourClientId";

        doNothing().when(getReportPortIn).execute(eq(clientId), any(HttpServletResponse.class));

        mockMvc.perform(get(URL_FRAUD + "/" + clientId))
                .andExpect(status().isOk());

        verify(getReportPortIn, times(1)).execute(eq(clientId), any(HttpServletResponse.class));
    }
}

