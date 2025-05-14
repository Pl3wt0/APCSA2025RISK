package tools;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * A Gson TypeAdapter for serializing and deserializing BufferedImage objects
 * as Base64-encoded PNG strings in JSON.
 */
public class BufferedImageAdapter extends TypeAdapter<BufferedImage> {

    @Override
    public void write(JsonWriter out, BufferedImage value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        // Convert image to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(value, "png", baos);
        baos.flush();

        // Encode byte array to Base64 string
        String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());
        baos.close();

        // Write the Base64 string to JSON
        out.value(base64String);
    }

    @Override
    public BufferedImage read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        // Read the Base64 string from JSON
        String base64String = in.nextString();

        // Decode the Base64 string to a byte array
        byte[] imageBytes = Base64.getDecoder().decode(base64String);

        // Convert byte array back to BufferedImage
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bais);
    }
}
