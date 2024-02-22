package ui;

import model.Criminal;
import model.Offence;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// Criminal Record Management System application (GUI)
public class GUI extends JFrame {
    private JButton viewRecord;
    private JFrame frame;
    private JButton addOffence;
    private JButton newCriminal;
    private JButton save;
    private JButton load;
    private JTextField textInput;
    private JButton submit;
    private String input;
    private List<Criminal> records;
    private HashMap<Integer,Criminal> recordMap;
    private static final String JSON_STORE = "./data/records.json";
    private Criminal criminal;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // runs the application
    public GUI() {
        init();
        frame = new JFrame();
        frame.setTitle("Criminal Record Management System");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.white);
        homeScreen1();
        homeScreen2();
    }


    // MODIFIES: this
    // EFFECTS: initializes criminals and the list of criminals.
    public void init() {
        records = new ArrayList<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        criminal = new Criminal(0,null,null,null);
        recordMap = new HashMap<Integer,Criminal>();
    }

    // EFFECTS: displays the options available to the user and calls the respective functions
    private void homeScreen1() {
        ClickListener cl = new ClickListener();
        viewRecord = new JButton("View Criminal Record");
        addOffence = new JButton();
        viewRecord.setBounds(100,30,600,30);
        viewRecord.setFocusable(false);
        viewRecord.addActionListener(cl);
        addOffence.setBounds(100,90,600,30);
        addOffence.setText("Add an offence to an existing criminal");
        addOffence.setFocusable(false);
        addOffence.addActionListener(cl);
        frame.add(viewRecord);
        frame.add(addOffence);
    }

    // EFFECTS: displays the options available to the user and calls the respective functions
    public void homeScreen2() {
        ClickListener cl = new ClickListener();
        newCriminal = new JButton();
        save = new JButton();
        load = new JButton();
        newCriminal.setBounds(100,150,600,30);
        newCriminal.setText("Add new criminal");
        newCriminal.setFocusable(false);
        newCriminal.addActionListener(cl);
        save.setBounds(100,210,600,30);
        save.setText("Save records to file");
        save.setFocusable(false);
        save.addActionListener(cl);
        load.setBounds(100,270,600,30);
        load.setText("Load records from file");
        load.setFocusable(false);
        load.addActionListener(cl);
        frame.add(newCriminal);
        frame.add(save);
        frame.add(load);
    }

    // EFFECTS: called when there's an invalid input from the user and diplays the message "Invalid Input"
    public void invalidInput() {
        JOptionPane.showMessageDialog(frame, "Invalid Input");
    }

    // MODIFIES: this
    // EFFECTS: sets the newly added criminal's details and adds it to the list.
    public void setCriminalDetails(String idS, String name, String dob, String gender, String crime, String rewardS) {
        int id = Integer.parseInt(idS);
        if (criminalExists(id)) {
            JOptionPane.showMessageDialog(frame, "Criminal already exists!");
        } else {
            Criminal c = new Criminal(id, name, dob, gender);
            Offence offence = new Offence(crime);
            c.addOffence(offence);
            int reward = Integer.parseInt(rewardS);
            c.setReward(reward);
            records.add(c);
            recordMap.put(id,c);
            repaintHomeScreen();
            JOptionPane.showMessageDialog(frame, "Criminal added successfully");
        }
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

    // class from action listener
    private class ClickListener implements ActionListener {

        // EFFECTS: processes the app on the basis of which button is pressed
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewRecord) {
                viewRecordImplement();
            } else if (e.getSource() == newCriminal) {
                newCriminalImplement();
            }
            if (e.getSource() == addOffence) {
                addOffenceImplement();;
            }
            if (e.getSource() == save) {
                repaintHomeScreen();
                saveCriminalRecord();
            }

            if (e.getSource() == load) {
                repaintHomeScreen();
                loadCriminalRecord();
            }
        }
    }

    // EFFECTS: sets up the frame for the viewRecord method and takes input from the user.
    public void viewRecordImplement() {
        repaintHomeScreen();
        textInput = new HintTextField("Enter the ID");
        submit = new JButton();
        textInput.setBounds(100,360,600,30);
        submit.setText("Submit");
        submit.setBounds(300,420,200,50);
        frame.add(textInput);
        frame.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = textInput.getText();
                viewRecord(input);
            }
        });
    }

    // EFFECTS: sets up the frame for the newCriminal method and takes input from the user.
    public void newCriminalImplement() {
        repaintHomeScreen();
        JTextField id = new HintTextField("Enter the ID");
        JTextField name = new HintTextField("Enter the name");
        JTextField dob = new HintTextField("Enter the date of birth (MM/DD/YYYY)");
        JTextField gender = new HintTextField("Enter the gender");
        id.setBounds(100,360,600,30);
        name.setBounds(100,400,600,30);
        dob.setBounds(100,440,600,30);
        gender.setBounds(100,480,600,30);
        frame.add(id);
        frame.add(name);
        frame.add(dob);
        frame.add(gender);
        continued(id, name, dob, gender);
    }

    // EFFECTS: sets up the frame for the viewRecord method and takes input from the user (continued).
    public void continued(JTextField id, JTextField name, JTextField dob, JTextField gender) {
        JTextField offence = new HintTextField("Enter the offence");
        JTextField reward = new HintTextField("Enter the reward (in dollars)");
        submit = new JButton();
        offence.setBounds(100,520,600,30);
        reward.setBounds(100,560,600,30);
        submit.setText("Submit");
        submit.setBounds(300,620,200,50);
        frame.add(offence);
        frame.add(reward);
        frame.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = id.getText();
                String n = name.getText();
                String d = dob.getText();
                String gen = gender.getText();
                String crime = offence.getText();
                String prize = reward.getText();
                setCriminalDetails(idString, n, d, gen, crime, prize);
            }
        });
    }

    // EFFECTS: sets up the frame for the addOffence method and takes input from the user.
    public void addOffenceImplement() {
        repaintHomeScreen();
        textInput = new HintTextField("Enter the ID");
        JTextField crime = new HintTextField("Enter the crime committed");
        submit = new JButton();
        textInput.setBounds(100,360,600,30);
        crime.setBounds(100,400,600,30);
        submit.setText("Submit");
        submit.setBounds(300,450,200,50);
        frame.add(textInput);
        frame.add(crime);
        frame.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = textInput.getText();
                String offence = crime.getText();
                addOffence(input, offence);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: loads criminals from file
    private void loadCriminalRecord() {
        try {
            records = jsonReader.read();
            JOptionPane.showMessageDialog(frame, "Loaded from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the criminals to file
    private void saveCriminalRecord() {
        try {
            jsonWriter.open();
            jsonWriter.write(records);
            jsonWriter.close();
            repaintHomeScreen();
            JOptionPane.showMessageDialog(frame, "Saved records");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Unable to write to file");
        }
    }

    // EFFECTS: takes in the id as an input and calls the method to add new offences
    public void addOffence(String id, String offence) {
        Criminal selected = selectCriminal(id);
        if (selected == null) {
            invalidInput();
        } else {
            addNewOffence(selected, offence);
        }
    }

    // EFFECTS: adds new offence to the criminal
    public void addNewOffence(Criminal c, String crime) {
        Offence offence = new Offence(crime);
        c.addOffence(offence);
        repaintHomeScreen();
        JOptionPane.showMessageDialog(frame, "Offence added successfully!");
    }

    // EFFECTS: view the existing records
    public void viewRecord(String id) {
        Criminal selected = selectCriminal(id);
        if (selected == null) {
            invalidInput();
        } else {
            repaintHomeScreen();
            JLabel idL = new JLabel("ID: " + String.valueOf(selected.getId()));
            JLabel nameL = new JLabel("Name: " + selected.getName());
            JLabel dobL = new JLabel("DOB: " + selected.getDob());
            idL.setBounds(100,360,600,30);
            nameL.setBounds(100,400,600,30);
            dobL.setBounds(100,440,600,30);
            frame.add(idL);
            frame.add(nameL);
            frame.add(dobL);
            viewRecord2(selected);
        }
    }

    // EFFECTS: view the existing records
    public void viewRecord2(Criminal selected) {
        JLabel genderL = new JLabel("Gender: " + selected.getGender());
        JLabel offenceL = new JLabel("Offence: " + selected.getOffences());
        JLabel rewardL = new JLabel("Reward: $" + String.valueOf(selected.getReward()));
        genderL.setBounds(100,480,600,30);
        offenceL.setBounds(100,520,600,30);
        rewardL.setBounds(100,560,600,30);
        frame.add(genderL);
        frame.add(offenceL);
        frame.add(rewardL);
    }

    // EFFECTS: displays the home screen and clears everything else in the frame
    public void repaintHomeScreen() {
        frame.getContentPane().removeAll();
        frame.repaint();
        homeScreen1();
        homeScreen2();
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

    // class for displaying hint in the text fields
    class HintTextField extends JTextField implements FocusListener {

        private final String hint;
        private boolean showingHint;

        // constructor, initialises all the variables
        public HintTextField(final String hint) {
            super(hint);
            this.hint = hint;
            this.showingHint = true;
            super.addFocusListener(this);
        }

        // EFFECTS: Displays hint when text field is not selected
        @Override
        public void focusGained(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText("");
                showingHint = false;
            }
        }

        // EFFECTS: Hides hint when text field is selected
        @Override
        public void focusLost(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText(hint);
                showingHint = true;
            }
        }

        // EFFECTS: Shows the hint
        @Override
        public String getText() {
            return showingHint ? "" : super.getText();
        }
    }

}
