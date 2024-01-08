package com.example.holaluz.application.port.in;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Value;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface GetReportPortIn {

     void execute(String id, HttpServletResponse response) throws IOException, JRException;
}
