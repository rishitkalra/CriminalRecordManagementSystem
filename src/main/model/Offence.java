package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an offence having a crime
public class Offence implements Writable {
    private String crime;

    public Offence(String crime) {
        this.crime = crime;
    }

    public String getCrime() {
        return crime;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Crime", crime);
        return json;
    }
}
