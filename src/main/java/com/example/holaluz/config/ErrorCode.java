package com.example.holaluz.config;

public enum ErrorCode {

    INTERNAL_ERROR_EXTENSION(100, "Error interno, Extencion de archivo incorrecto"),
    INTERNAL_ERROR_XML_PARSER(101, "Error interno, error al intentar parsear archivo XML"),
    INTERNAL_ERROR_FILE_CONVERTER(102, "Error interno, error al intentar convertir el archivo");

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
