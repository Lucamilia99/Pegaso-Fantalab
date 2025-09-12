package com.pegaso.PEGASO.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T parseResponse(String body, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(body, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Errore nella deserializzazione JSON", e);
        }
    }
}