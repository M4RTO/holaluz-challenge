package com.example.holaluz.config;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INTERNAL_ERROR_EXTENSION(100, "Error interno, Extencion de archivo incorrecto"),
    INTERNAL_ERROR_XML_PARSER(101, "Error interno, error al intentar parsear archivo XML"),
    INTERNAL_ERROR_FILE_CONVERTER(102, "Error interno, error al intentar convertir el archivo"),

    INTERNAL_ERROR_NOT_FOUND_SUSPICIOUS(103, "Error interno, no hay ninguna sospecha para el cliente dado."),
    INTERNAL_ERROR_CSV_PARSER(104, "Error interno, error al intentar parsear archivo CSV");

    private final int value;
    private final String reasonPhrase;


    ErrorCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

}
