package tools;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.awt.Robot;
import java.io.IOException;
import java.lang.reflect.Field;

public class RobotTypeAdapter extends TypeAdapter<Robot> {

    @Override
    public void write(JsonWriter out, Robot robot) throws IOException {
        if (robot == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("class").value(robot.getClass().getName());

        // Try to access 'peer' reflectively
        try {
            Field peerField = Robot.class.getDeclaredField("peer");
            peerField.setAccessible(true); // this is where your error happened before

            Object peer = peerField.get(robot);
            if (peer != null) {
                out.name("peerClass").value(peer.getClass().getName());
                out.name("peerToString").value(peer.toString());
            } else {
                out.name("peer").nullValue();
            }
        } catch (Exception e) {
            out.name("peer").value("inaccessible: " + e.getMessage());
        }

        out.endObject();
    }

    @Override
    public Robot read(JsonReader in) throws IOException {
        // No safe way to deserialize Robot
        in.beginObject();
        while (in.hasNext()) {
            in.nextName();
            in.skipValue();
        }
        in.endObject();
        return null;
    }
}
