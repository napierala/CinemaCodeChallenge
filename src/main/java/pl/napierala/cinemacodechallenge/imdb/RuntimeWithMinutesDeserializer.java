package pl.napierala.cinemacodechallenge.imdb;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuntimeWithMinutesDeserializer extends JsonDeserializer<Integer> {

    private static final Pattern PATTERN = Pattern.compile("(\\d+) min");

    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken jsonToken = p.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_STRING) {
            String valueAsString = p.getValueAsString();

            if (valueAsString == null) {
                return null;
            }

            Matcher matcher = PATTERN.matcher(valueAsString);

            if (!matcher.find()) {
                throw new JsonParseException(p, "Could not match string:[" + valueAsString + "] to the pattern.");
            }

            return Integer.parseInt(matcher.group(1));
        }
        throw new JsonParseException(p, "Could not process as it was not a string.");
    }
}