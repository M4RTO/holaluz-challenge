package com.example.holaluz.util;

import com.example.holaluz.domain.Reading;
import com.example.holaluz.domain.Readings;
import com.example.holaluz.exception.FileConverterException;
import com.example.holaluz.exception.ParserCSVException;
import com.example.holaluz.exception.ParserXMLException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;

import static com.example.holaluz.config.ErrorCode.*;

@Slf4j
public class Parser {

    public static List<Reading> parseXMLtoDomain(MultipartFile file) {
        JAXBContext jaxbContext;
        log.info("Convert multipart file into file");
        File data = convert(file);
        try {
            jaxbContext = JAXBContext.newInstance(Readings.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            log.info("Convert XML file into java object");
            Readings allReadings = (Readings) jaxbUnmarshaller.unmarshal(data);
            log.info("result of parseXML: {} ", allReadings.getReadings());
            return allReadings.getReadings();
        } catch (JAXBException e) {
            throw new ParserXMLException(INTERNAL_ERROR_XML_PARSER);
        }
    }

    public static List<Reading> parseCSVtoDomain(MultipartFile file) {
        List<Reading> readings;
        try  {
            Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CsvToBean<?> csvToBean = new CsvToBeanBuilder<Reading>(reader)
                    .withType(Reading.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();
            readings = (List<Reading>) csvToBean.parse();
        } catch (Exception ex ) {
            log.error("error parsing csv file {} ", ex);
            throw new ParserCSVException(INTERNAL_ERROR_CSV_PARSER);
        }
        return readings;
    }

    public static File convert(MultipartFile file) {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new FileConverterException(INTERNAL_ERROR_FILE_CONVERTER);
        }

        return convFile;
    }
}
