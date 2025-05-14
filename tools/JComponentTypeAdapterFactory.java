package tools;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import javax.swing.*;
import java.lang.reflect.Type;

/**
 * A TypeAdapterFactory for serializing and deserializing JComponent subclasses.
 */
public class JComponentTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!JComponent.class.isAssignableFrom(type.getRawType())) {
            return null;
        }

        return (TypeAdapter<T>) new TypeAdapter<JComponent>() {
            @Override
            public void write(JsonWriter out, JComponent value) throws java.io.IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }

                out.beginObject();
                out.name("class").value(value.getClass().getName());
                out.name("alignmentX").value(value.getAlignmentX());
                out.endObject();
            }

            @Override
            public JComponent read(JsonReader in) throws java.io.IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }

                String className = null;
                float alignmentX = 0.5f;

                in.beginObject();
                while (in.hasNext()) {
                    String name = in.nextName();
                    switch (name) {
                        case "class":
                            className = in.nextString();
                            break;
                        case "alignmentX":
                            alignmentX = (float) in.nextDouble();
                            break;
                    }
                }
                in.endObject();

                if (className == null) {
                    throw new JsonParseException("Missing class name for JComponent deserialization.");
                }

                try {
                    Class<?> clazz = Class.forName(className);
                    if (!JComponent.class.isAssignableFrom(clazz)) {
                        throw new JsonParseException("Class is not a JComponent: " + className);
                    }

                    JComponent component = (JComponent) clazz.getDeclaredConstructor().newInstance();

                    component.setAlignmentX(alignmentX);

                    return component;

                } catch (Exception e) {
                    throw new JsonParseException("Failed to deserialize JComponent: " + className, e);
                }
            }
        };
    }
}
