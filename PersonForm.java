import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;



public class PersonForm extends JFrame {

    private JTextField firstNameField, lastNameField, ageField, cityField, countryField;

    public PersonForm() {
        super("Person Form");

        // Initialize fields
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        ageField = new JTextField(5);
        cityField = new JTextField(15);
        countryField = new JTextField(15);

        // Create labels
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel cityLabel = new JLabel("City:");
        JLabel countryLabel = new JLabel("Country:");

        // Create buttons
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToJson();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Create layout
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(countryLabel);
        panel.add(countryField);
        panel.add(saveButton);
        panel.add(cancelButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void saveDataToJson() {
        JSONObject person = new JSONObject();
        person.put("First Name", firstNameField.getText());
        person.put("Last Name", lastNameField.getText());
        person.put("Age", ageField.getText());
        person.put("City", cityField.getText());
        person.put("Country", countryField.getText());

        JSONArray peopleArray;
        // Try to read existing data from file
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("people.json")) {
            peopleArray = (JSONArray) jsonParser.parse(reader);
        } catch (Exception e) {
            // If file doesn't exist or is empty, create a new array
            peopleArray = new JSONArray();
        }

        // Add the new person to the array
        peopleArray.add(person);

        try (FileWriter file = new FileWriter("people.json")) {
            // Write the updated array back to the file
            file.write(peopleArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear the form fields after saving
        clearFormFields();
    }


    private void clearFormFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        cityField.setText("");
        countryField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PersonForm().setVisible(true);
            }
        });
    }
}
