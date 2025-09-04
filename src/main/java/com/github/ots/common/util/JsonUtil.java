package com.github.ots.common.util;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {

    public final static ObjectMapper objectMapper;
    public final static ObjectWriter objectWriter;
    public final static ObjectReader objectReader;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // Register the custom deserializerSimpleModule
        SimpleModule customModule = new SimpleModule();
        objectMapper.registerModule(customModule);

        objectMapper.configure(DeserializationFeature.WRAP_EXCEPTIONS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        objectReader = objectMapper.reader();
    }

}
