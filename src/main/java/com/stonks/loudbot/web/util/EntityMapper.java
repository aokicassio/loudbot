package com.stonks.loudbot.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stonks.loudbot.model.Crypto;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityMapper {

    private static final Logger LOGGER = Logger.getLogger(EntityMapper.class.getName());

    public static Crypto parseCryptoFromJsonString(String json)  {
        Crypto crypto = null;
        try {
            crypto = (Crypto) parse(json.toLowerCase(), Crypto.class);
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "Failed to parse json string");
        }

        return crypto;
    }

    protected static Object parse(String json, Class clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json.toLowerCase(), clazz);
    }

    private EntityMapper(){
    }
}
