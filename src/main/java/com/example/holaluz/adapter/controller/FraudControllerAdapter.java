package com.example.holaluz.adapter.controller;

import com.example.holaluz.application.port.in.UploadFraudFilePortIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/frauds")
@Slf4j
public class FraudControllerAdapter {

    private final UploadFraudFilePortIn uploadFraudFilePortIn;

    public FraudControllerAdapter(UploadFraudFilePortIn uploadFraudFilePortIn) {
        this.uploadFraudFilePortIn = uploadFraudFilePortIn;
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
}
