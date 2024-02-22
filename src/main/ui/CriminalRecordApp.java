package ui;

import model.Criminal;
import model.Offence;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Criminal Record Management System application
public class CriminalRecordApp {
    private List<Criminal> records;
    private static final String JSON_STORE = "./data/records.json";
    private Criminal criminal;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private GUI gui;


    // EFFECTS: runs the criminal record application
    public CriminalRecordApp() {
        gui = new GUI();
        //runCriminalRecordApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runCriminalRecordApp() {
        boolean keepGoing = true;

        init();

        while (keepGoing) {
            Scanner sc = new Scanner(System.in);
            displayMenu();
            String command = sc.nextLine();

            if (command.equals("8")) {
                System.out.println("Thank you for using the criminal record management system app \nHave a good day!");
                keepGoing = false;
            } else {
                //processCommand(command);
            }
        }

    }


    // MODIFIES: this
    // EFFECTS: initializes criminals and the list of criminals.
    public void init() {
        records = new ArrayList<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        criminal = new Criminal(0,null,null,null);
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        /*System.out.println("\nSelect one of the following options: ");
        System.out.println("\t1 -> View the existing records");
        System.out.println("\t2 -> Add an offence to an existing criminal record");
        System.out.println("\t3 -> Change the reward on an existing criminal");
        System.out.println("\t4 -> Add a new criminal to the records");
        System.out.println("\t5 -> View the number of existing criminals");
        System.out.println("\t6 -> Save records to file");
        System.out.println("\t7 -> Load records from file");
        System.out.println("\t8 -> Quit");*/
    }


    /*// EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "1":
                viewRecord();
                break;
            case "2":
                addOffence();
                break;
            case "3":
                changeReward();
                break;
            case "4":
                addCriminal();
                break;
            default:
                processCommand2(command);
                break;
        }
    }

    private void processCommand2(String command) {
        switch (command) {
            case "5":
                recordsLength();
                break;
            case "6":
                saveCriminalRecord();
                break;
            case "7":
                loadCriminalRecord();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }*/


    // MODIFIES: this
    // EFFECTS: adds a criminal by creating its object
    public void addCriminal() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the criminal id: ");
        String idString = sc.nextLine();
        int id = Integer.parseInt(idString);
        if (criminalExists(id)) {
            System.out.println("Criminal with the given ID already exists.");
        } else {
            System.out.println("Please enter the criminal's name: ");
            String name = sc.nextLine();
            System.out.println("Please enter the date of birth (mm/dd/yyyy) : ");
            String dob = sc.nextLine();
            System.out.println("Please enter the criminal's gender: ");
            String gender = sc.nextLine();
            setCriminalDetails(id, name, dob, gender);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the newly added criminal's details and adds it to the list.
    public void setCriminalDetails(int id, String name, String dob, String gender) {
        Criminal c = new Criminal(id, name, dob, gender);
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the crime committed: ");
        String crime = sc.nextLine();
        Offence offence = new Offence(crime);
        c.addOffence(offence);
        System.out.println("Please enter the reward: $");
        String rewardString = sc.nextLine();
        int reward = Integer.parseInt(rewardString);
        c.setReward(reward);
        records.add(c);
        System.out.println("Criminal added successfully.");
    }

    // EFFECTS: view the existing records
    public void viewRecord(String id) {
        Criminal selected = selectCriminal(id);
        if (selected == null) {
            System.out.println("Invalid Input");
        } else {
            System.out.println("\n ID: " + selected.getId());
            System.out.println("\n Name: " + selected.getName());
            System.out.println("\n Date of birth (MM/DD/YYYY) : " + selected.getDob());
            System.out.println("\n Gender: " + selected.getGender());
            System.out.println("\n Offence: ");
            selected.getOffences();
            System.out.println("\n Reward: $" + selected.getReward());
        }
    }

    // EFFECTS: takes in the id as an input and calls the method to add new offences
    public void addOffence(String id) {
        Criminal selected = selectCriminal(id);
        if (selected == null) {
            System.out.println("Invalid Input");
        } else {
            addNewOffence(selected);
        }

    }

    // EFFECTS: adds new offence to the criminal
    public void addNewOffence(Criminal c) {

        System.out.println("Please enter the crime committed: ");
        Scanner sc = new Scanner(System.in);
        String crime = sc.nextLine();
        Offence offence = new Offence(crime);
        c.addOffence(offence);
    }




    // MODIFIES: this
    // EFFECTS: updates the reward of an existing criminal record
    public void changeReward(String id) {
        Criminal selected = selectCriminal(id);
        if (selected == null) {
            System.out.println("Invalid Input");
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the new reward on the criminal: $");
            String rewardString = sc.nextLine();
            int reward = Integer.parseInt(rewardString);
            selected.setReward(reward);
        }
    }

    // EFFECTS: tells the user how many criminals are there in the records.
    public void recordsLength() {
        int size = records.size();
        System.out.println("There are " + String.format("%d", size) + " criminals in the records.");
    }

    // EFFECTS: prompts user to select one of the existing records and returns it
    private Criminal selectCriminal(String id) {
        int selection = Integer.parseInt(id);
        for (Criminal criminal : records) {
            if (selection == criminal.getId()) {
                return criminal;
            }
        }
        return null;
    }

    // EFFECTS: returns true of the criminal already exists, else returns false.
    public Boolean criminalExists(int id) {
        for (Criminal criminal : records) {
            if (id == criminal.getId()) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: saves the criminals to file
    private void saveCriminalRecord() {
        try {
            jsonWriter.open();
            jsonWriter.write(records);
            jsonWriter.close();
            System.out.println("Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads criminals from file
    private void loadCriminalRecord() {
        try {
            records = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
