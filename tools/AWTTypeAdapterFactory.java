package tools;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class AWTTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<?> rawType = typeToken.getRawType();

        // Check if it belongs to java.awt package
        if (rawType.getPackage() != null && rawType.getPackage().getName().startsWith("java.awt")) {

            // Handle known problematic classes like Robot
            if (Robot.class.isAssignableFrom(rawType)) {
                return (TypeAdapter<T>) new RobotTypeAdapter();
            }

            // Default handler for other java.awt classes
            return (TypeAdapter<T>) new TypeAdapter<Object>() {
                @Override
                public void write(JsonWriter out, Object value) throws IOException {
                    if (value == null) {
                        out.nullValue();
                        return;
                    }

                    // Default to stringifying class name and hashCode
                    out.beginObject();
                    out.name("class").value(value.getClass().getName());
                    out.name("toString").value(value.toString());
                    out.endObject();
                }

                @Override
                public Object read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }

                    // Skip deserialization for unknown AWT types
                    in.beginObject();
                    while (in.hasNext()) {
                        in.nextName();
                        in.skipValue();
                    }
                    in.endObject();

                    return null; // Returning null since restoring arbitrary AWT objects isn't safe
                }
            };
        }

        // Not a java.awt type — let Gson handle it normally
        return null;
    }
}
