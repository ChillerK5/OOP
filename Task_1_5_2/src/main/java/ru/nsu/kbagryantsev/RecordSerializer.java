package ru.nsu.kbagryantsev;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * Serializer for {@link Record}.
 */
class RecordSerializer implements JsonSerializer<Record> {
    @Override
    public JsonElement serialize(final Record src,
                                 final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        JsonObject record = new JsonObject();

        record.addProperty("title", src.title());
        record.addProperty("text", src.text());
        record.addProperty("date", src.date().getTime());

        return record;
    }
}
