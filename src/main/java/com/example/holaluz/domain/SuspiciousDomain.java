package com.example.holaluz.domain;

import com.example.holaluz.adapter.jpa.model.SuspiciousEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SuspiciousDomain {


    Long id;

    String client;

    String date_month;

    Integer suspicious;

    Double median;


}
