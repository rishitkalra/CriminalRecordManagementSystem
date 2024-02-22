package persistence;

import org.json.JSONObject;

// credits: JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
