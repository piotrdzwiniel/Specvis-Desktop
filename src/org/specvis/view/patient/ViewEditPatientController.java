package org.specvis.view.patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.specvis.StartApplication;
import org.specvis.logic.Functions;
import org.specvis.datastructures.patient.Patient;
import org.specvis.view.miscellaneous.ViewExceptionDialog;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Piotr Dzwiniel on 2016-02-08.
 */

/*
 * Copyright 2014-2016 Piotr Dzwiniel
 *
 * This file is part of Specvis.
 *
 * Specvis is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * Specvis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Specvis; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

public class ViewEditPatientController implements Initializable {

    private Functions functions;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private ComboBox<String> comboBoxGender;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textFieldAge;

    @FXML
    private Button buttonCalculateAge;

    @FXML
    private TextField textFieldPhone;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldCity;

    @FXML
    private TextField textFieldPostalCode;

    @FXML
    private TextField textFieldVisualAcuityL;

    @FXML
    private TextField textFieldVisualAcuityR;

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        functions = StartApplication.getFunctions();

        setDataForAllFields();
        setItemsForComboBoxGender();
        setDatePickerProperties();
    }

    private void setDataForAllFields() {
        Patient patient = StartApplication.getSpecvisData().getPatient();

        textFieldID.setText(patient.getId());
        textFieldFirstName.setText(patient.getFirstName());
        textFieldLastName.setText(patient.getLastName());
        comboBoxGender.getSelectionModel().select(patient.getGender());
        datePicker.setValue(patient.getDateOfBirth());
        textFieldAge.setText(String.valueOf(patient.getAge()));
        textFieldPhone.setText(patient.getPhone());
        textFieldEmail.setText(patient.getEmail());
        textFieldCity.setText(patient.getCity());
        textFieldPostalCode.setText(patient.getPostalCode());
        textFieldVisualAcuityL.setText(patient.getVisualAcuityL());
        textFieldVisualAcuityR.setText(patient.getVisualAcuityR());
        textArea.setText(patient.getAdditionalInformation());
    }

    private void setItemsForComboBoxGender() {
        ObservableList<String> observableList = FXCollections.observableArrayList("Male", "Female", "Other");
        comboBoxGender.setItems(observableList);
    }

    private void setDatePickerProperties() {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateTimeFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        });
        datePicker.setPromptText("yyyy-MM-dd");
    }

    @FXML
    private void calculatePatientAge() {
        try {
            String currentDate = functions.getCurrentDateYYYYmmDD();
            String patientDateOfBirth = datePicker.getValue().toString();

            String format = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date cd = null;
            Date dob = null;
            try {
                cd = sdf.parse(currentDate);
                dob = sdf.parse(patientDateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long diff = cd.getTime() - dob.getTime();
            int diffDays = (int) (diff / (24 * 1000 * 60 * 60));
            int patientAge = diffDays / 365;

            textFieldAge.setText(String.valueOf(patientAge));

        } catch (Exception ex) {
            ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }
    }

    @FXML
    private void setScenePatient() throws IOException {
        StartApplication.setScenePatient();
    }

    @FXML
    private void saveEditedPatient() throws IOException {
        if (keyFieldsAreFilled()) {
            try {
                // Set text of empty fields to "#".
                manageEmptyFields();

                // Click buttonCalculateAge.
                if (textFieldAge.getText().equals("")) {
                    buttonCalculateAge.fire();
                }

                // Create new patient.
                Patient patient = new Patient(
                        textFieldID.getText(),
                        textFieldFirstName.getText(),
                        textFieldLastName.getText(),
                        comboBoxGender.getSelectionModel().getSelectedItem(),
                        datePicker.getValue(),
                        Integer.valueOf(textFieldAge.getText()),
                        textFieldPhone.getText(),
                        textFieldEmail.getText(),
                        textFieldCity.getText(),
                        textFieldPostalCode.getText(),
                        textFieldVisualAcuityL.getText(),
                        textFieldVisualAcuityR.getText(),
                        textArea.getText()
                );
                patient.setTestedEye(StartApplication.getSpecvisData().getPatient().getTestedEye());

                // Edited patient String line for writing in file.
                String editedPatient = patient.getId() + "\t" +
                        patient.getFirstName() + "\t" +
                        patient.getLastName() + "\t" +
                        patient.getGender() + "\t" +
                        patient.getDateOfBirth().toString() + "\t" +
                        patient.getAge() + "\t" +
                        patient.getPhone() + "\t" +
                        patient.getEmail() + "\t" +
                        patient.getCity() + "\t" +
                        patient.getPostalCode() + "\t" +
                        patient.getVisualAcuityL() + "\t" +
                        patient.getVisualAcuityR() + "\t" +
                        patient.getAdditionalInformation().replaceAll("\\n", "#n%");

                // Read patients.s file.
                ArrayList<String> array = new ArrayList<>();
                File file = new File("patients.s");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    array.add(line);
                }
                bufferedReader.close();

                // Replace data for edited patient.
                for (int i = 0; i < array.size(); i++) {
                    String[] str = array.get(i).split("\t");
                    if (str[0].equals(patient.getId())) {
                        array.set(i, editedPatient);
                        break;
                    }
                }

                // Overwrite patients.s file.
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
                    for (String anArray : array) {
                        bufferedWriter.write(anArray + "\n");
                    }
                    bufferedWriter.close();
                } catch (IOException ex) {
                    ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
                    ed.setTitle("Exception");
                    ed.setHeaderText(ex.getClass().getName());
                    ed.showAndWait();
                }


                // Add edited patient to SpecvisData.
                StartApplication.getSpecvisData().setPatient(patient);

                // Show information dialog.
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Patient has been edited.");
                alert.showAndWait();

                // Set scene patient.
                setScenePatient();

            } catch (Exception ex) {
                ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
                ed.setTitle("Exception");
                ed.setHeaderText(ex.getClass().getName());
                ed.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must provide at least patient's first name, last name, gender and date of birth.");
            alert.showAndWait();
        }
    }

    private boolean keyFieldsAreFilled() {
        boolean a = !textFieldFirstName.getText().equals("");
        boolean b = !textFieldLastName.getText().equals("");
        boolean c = datePicker.getValue() != null;
        boolean d = comboBoxGender.getSelectionModel().getSelectedIndex() != -1;

        return a && b && c && d;
    }

    private void manageEmptyFields() {
        String str = "#";
        setTextForEmptyTextField(textFieldPhone, str);
        setTextForEmptyTextField(textFieldEmail, str);
        setTextForEmptyTextField(textFieldCity, str);
        setTextForEmptyTextField(textFieldPostalCode, str);
        setTextForEmptyTextField(textFieldVisualAcuityL, str);
        setTextForEmptyTextField(textFieldVisualAcuityR, str);

        if (textArea.getText().equals("")) {
            textArea.setText(str);
        }
    }

    public void setTextForEmptyTextField(TextField textField, String text) {
        if (textField.getText().equals("")) {
            textField.setText(text);
        }
    }
}
