package com.example.holaluz.adapter.controller;

import com.example.holaluz.application.port.in.GetReportPortIn;
import com.example.holaluz.application.port.in.UploadFraudFilePortIn;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/frauds")
@Slf4j
public class FraudControllerAdapter {

    private final UploadFraudFilePortIn uploadFraudFilePortIn;

    private final GetReportPortIn getReportPortIn;


    public FraudControllerAdapter(UploadFraudFilePortIn uploadFraudFilePortIn, GetReportPortIn getReportPortIn) {
        this.uploadFraudFilePortIn = uploadFraudFilePortIn;
        this.getReportPortIn = getReportPortIn;
    }

    @PostMapping("")
    public void upload(
            @RequestParam(value = "file") final MultipartFile file
    ) {
        final UploadFraudFilePortIn.Command command = UploadFraudFilePortIn.Command.builder()
                .file(file)
                .build();
        uploadFraudFilePortIn.execute(command);
        log.info("Upload file with electricity readings with this content type: {}", file.getContentType() );
    }

    @GetMapping("/{clientId}")
    public void getReport(
            @PathVariable(value = "clientId") String id,
            HttpServletResponse response
    ) throws JRException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        getReportPortIn.execute(id,response);
    }
}
