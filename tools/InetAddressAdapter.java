package tools;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressAdapter implements JsonSerializer<InetAddress>, JsonDeserializer<InetAddress> {

    @Override
    public JsonElement serialize(InetAddress src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getHostAddress());
    }

    @Override
    public InetAddress deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            return InetAddress.getByName(json.getAsString());
        } catch (UnknownHostException e) {
            throw new JsonParseException("Invalid IP address: " + json.getAsString(), e);
        }
    }
}
