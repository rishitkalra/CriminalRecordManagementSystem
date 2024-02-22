package persistence;

import model.Criminal;
import model.Offence;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// credits: JsonSerializationDemo

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads list of criminals from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Criminal> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseCriminalList(jsonArray);
    }

    // EFFECTS: parses list of criminals from JSON array and returns it
    public List<Criminal> parseCriminalList(JSONArray jsonArray) {
        List<Criminal> cr = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextCriminal = (JSONObject) json;
            cr.add(parseCriminal(nextCriminal));
        }
        return cr;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses criminal from JSON object and returns it
    private Criminal parseCriminal(JSONObject jsonObject) {
        int id = jsonObject.getInt("ID");
        String name = jsonObject.getString("Name");
        String dob = jsonObject.getString("DOB");
        String gender = jsonObject.getString("Gender");
        int reward = jsonObject.getInt("Reward $");
        Criminal cr = new Criminal(id, name, dob, gender);
        cr.setReward(reward);
        addOffences(cr, jsonObject);
        return cr;
    }

    // MODIFIES: cr
    // EFFECTS: parses offences from JSON object and adds them to criminal
    private void addOffences(Criminal cr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Offences");
        for (Object json : jsonArray) {
            JSONObject nextOffence = (JSONObject) json;
            addOffence(cr, nextOffence);
        }
    }

    // MODIFIES: cr
    // EFFECTS: parses offence from JSON object and adds it to criminal
    private void addOffence(Criminal cr, JSONObject jsonObject) {
        String crime = jsonObject.getString("Crime");
        Offence offence = new Offence(crime);
        cr.addOffence(offence);
    }
}
