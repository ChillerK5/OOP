package ru.nsu.kbagryantsev;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * Deserializer for {@link Record}.
 */
class RecordDeserializer implements JsonDeserializer<Record> {
    @Override
    public Record deserialize(final JsonElement json,
                              final Type typeOfT,
                              final JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String title = jsonObject.getAsJsonPrimitive("title").getAsString();
        String text = jsonObject.getAsJsonPrimitive("text").getAsString();
        long rawDate = jsonObject.getAsJsonPrimitive("date").getAsLong();
        Date date = new Date(rawDate);

        return new Record(title, text, date);
    }
}
