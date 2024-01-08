package com.example.holaluz.application.usecase;

import com.example.holaluz.adapter.jpa.model.SuspiciousEntity;
import com.example.holaluz.application.port.in.GetReportPortIn;
import com.example.holaluz.application.port.out.SuspiciousPortOut;
import com.example.holaluz.domain.SuspiciousDomain;
import com.example.holaluz.exception.NotFoundSuspiciousException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.holaluz.config.ErrorCode.INTERNAL_ERROR_NOT_FOUND_SUSPICIOUS;

@Component
@Slf4j
public class GetReportUseCase implements GetReportPortIn {

    private final SuspiciousPortOut suspiciousPortOut;

    private ResourceLoader resourceLoader;

    @Autowired
    public GetReportUseCase(SuspiciousPortOut suspiciousPortOut, ResourceLoader resourceLoader) {
        this.suspiciousPortOut = suspiciousPortOut;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void execute(String id, HttpServletResponse response) throws IOException, JRException {
        List<SuspiciousDomain> suspicious = suspiciousPortOut.getSuspiciousById(id);
        if(!suspicious.isEmpty()) {
            Resource resource = resourceLoader.getResource("classpath:report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(suspicious);
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
        } else {
            throw new NotFoundSuspiciousException(INTERNAL_ERROR_NOT_FOUND_SUSPICIOUS);
        }

    }
}

