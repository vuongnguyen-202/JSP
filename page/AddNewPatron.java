package a2_2001040230.page;

import a2_2001040230.common.DateUtils;
import a2_2001040230.common.PatronType;
import a2_2001040230.dao.PatronDao;
import a2_2001040230.model.Patron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Page New Module
 */
public class AddNewPatron extends JPanel implements ActionListener {

    private PatronDao patronDao;
    private JTextField textFieldName, textFieldDob, textFieldEmail, textFieldPhone;
    private JLabel labelError, labelSuccess;
    private JComboBox<PatronType> comboBox;

    public AddNewPatron() {
        patronDao = new PatronDao();

        setSize(600, 400);
        setLayout(null);

        // Name
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(50, 60, 130, 16);
        add(nameLabel);

        textFieldName = new JTextField();
        textFieldName.setBounds(200, 55, 350, 26);
        add(textFieldName);

        // Dob
        JLabel dobLabel = new JLabel("Dob (format: DD/MM/YY)");
        dobLabel.setBounds(50, 100, 150, 16);
        add(dobLabel);

        textFieldDob = new JTextField();
        textFieldDob.setBounds(200, 95, 350, 26);
        add(textFieldDob);

        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 140, 150, 16);
        add(emailLabel);

        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(200, 135, 350, 26);
        add(textFieldEmail);

        // Phone
        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(50, 180, 150, 16);
        add(phoneLabel);

        textFieldPhone = new JTextField();
        textFieldPhone.setBounds(200, 175, 350, 26);
        add(textFieldPhone);

        // Patron Type
        JLabel patronTypeLabel = new JLabel("Patron Type");
        patronTypeLabel.setBounds(50, 220, 150, 16);
        add(patronTypeLabel);

        comboBox = new JComboBox<>(PatronType.values());
        comboBox.setSelectedIndex(0);
        comboBox.setBounds(200, 215, 150, 27);
        add(comboBox);

        // Label Result
        // Error
        labelError = new JLabel("");
        labelError.setForeground(Color.RED);
        labelError.setHorizontalAlignment(SwingConstants.CENTER);
        labelError.setBounds(0, 260, 600, 16);
        add(labelError);

        // Success
        labelSuccess = new JLabel("");
        labelSuccess.setForeground(Color.GREEN);
        labelSuccess.setHorizontalAlignment(SwingConstants.CENTER);
        labelSuccess.setBounds(0, 260, 600, 16);
        add(labelSuccess);

        // Button Save
        JButton btnAdd = new JButton("Add Patron");
        btnAdd.setBounds(240, 300, 120, 29);
        btnAdd.addActionListener(this);
        add(btnAdd);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Patron")) {
            addPatron();
        }
    }

    private void addPatron() {
        labelError.setText("");
        labelSuccess.setText("");

        String name = textFieldName.getText();
        String dobStr = textFieldDob.getText();
        String email = textFieldEmail.getText();
        String phone = textFieldPhone.getText();

        PatronType patronType = (PatronType) comboBox.getSelectedItem();

        try {
            Date dob = DateUtils.convertDateFormat(dobStr);
            Patron newPatron = new Patron(name, dob, email, phone, patronType);
            patronDao.add(newPatron);

            textFieldName.setText("");
            textFieldDob.setText("");
            textFieldEmail.setText("");
            textFieldPhone.setText("");

            labelSuccess.setText("New Patron Added");
        } catch (ParseException | SQLException exception) {
            labelError.setText(exception.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}