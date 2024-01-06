package com.example.holaluz.domain;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

import java.util.List;
@Getter
@XmlRootElement(name = "readings")
@XmlAccessorType(XmlAccessType.FIELD)
public class Readings {

    @XmlElement(name = "reading")
    private List<Reading> readings;


}
