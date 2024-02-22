package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// represents a criminal having id, name, dob and gender
public class Criminal implements Writable {

    private int id;
    private String name;
    private String dob;
    private String gender;
    private String offence;
    private int reward;
    private List<Offence> offences;

    public Criminal(int id, String name, String dob, String gender) {

        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        offences = new ArrayList<>();

    }

    // MODIFIES: this
    // EFFECTS: adds offence to this criminal
    public void addOffence(Offence offence) {
        offences.add(offence);
    }

    // EFFECTS: returns an  list of offences in this criminal
    public String getOffences() {
        int a = 0;
        String crime = null;
        for (Offence offence: offences) {
            if (a == 0) {
                crime = offence.getCrime();
            } else {
                crime = crime + ", " + offence.getCrime();
            }
            a++;
        }
        return crime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setOffence(String offence) {
        if (this.offence == null) {
            this.offence = offence;
        } else {
            this.offence = this.offence + ", " + offence;
        }
    }

    public void setReward(int reward) {
        this.reward = this.reward + reward;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getOffence() {
        return offence;
    }

    public int getReward() {
        return reward;
    }



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ID", id);
        json.put("Name", name);
        json.put("DOB", dob);
        json.put("Gender", gender);
        json.put("Offences", offencesToJson());
        json.put("Reward $", reward);
        return json;
    }

    // EFFECTS: returns offences in this criminal object as a JSON array
    private JSONArray offencesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Offence o : offences) {
            jsonArray.put(o.toJson());
        }

        return jsonArray;
    }


}
