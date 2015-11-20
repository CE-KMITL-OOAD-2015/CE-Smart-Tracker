package com.kaleido.cesmarttracker;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pirushprechathavanich on 11/9/15.
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        long timeInMilliseconds = Long.parseLong(jp.getText());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliseconds);
        return calendar.getTime();
    }

//    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        String s = json.getAsJsonPrimitive().getAsString();
//        long l = Long.parseLong(s.substring(6, s.length()-2));
//        Date d = new Date(l);
//        return d;
//    }
}
