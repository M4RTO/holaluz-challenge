package com.example.holaluz.application.port.in;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFraudFilePortIn {

     void execute(Command command);


     @Value
     @Builder
     class Command {
          MultipartFile file;
     }
}
