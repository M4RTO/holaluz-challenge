package com.example.holaluz.domain;

import com.opencsv.bean.CsvBindByName;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@XmlRootElement(name = "reading")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reading {

    @XmlAttribute(name = "clientID")
    @CsvBindByName(column = "client")
    String clientID;

    @XmlAttribute
    @CsvBindByName(column = "period")
    String period;

    @XmlValue
    @CsvBindByName(column = "reading")
    int reading;

}
