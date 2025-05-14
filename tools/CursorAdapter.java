package tools;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.awt.Cursor;
import java.io.IOException;

/**
 * A Gson TypeAdapter for serializing and deserializing java.awt.Cursor objects
 * by storing their 'type' integer.
 */
public class CursorAdapter extends TypeAdapter<Cursor> {

    @Override
    public void write(JsonWriter out, Cursor value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        // Serialize only the cursor type as an integer
        out.value(value.getType());
    }

    @Override
    public Cursor read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        // Read the cursor type integer and create a new Cursor instance
        int type = in.nextInt();
        return new Cursor(type);
    }
}
