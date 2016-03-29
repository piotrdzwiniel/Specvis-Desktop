package org.specvis.view.patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.specvis.StartApplication;
import org.specvis.model.ExistingPatient;
import org.specvis.model.Patient;
import org.specvis.view.miscellaneous.CustomTableView;
import org.specvis.view.miscellaneous.ExceptionDialogWindow;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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

public class PatientExistingController implements Initializable{

    @FXML
    private VBox vBox;

    @FXML
    private TableView tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableView();
    }

    private void setTableView() {
        ObservableList<ExistingPatient> tableData = getExistingPatientsData();
        String[] tableColumnsNames = new String[] {"ID", "First name", "Last name", "Gender", "Date of birth", "Age", "Phone", "Email", "City"};
        String[] tableFieldsNames = ExistingPatient.getFieldsNames();
        tableView = new CustomTableView<>(tableColumnsNames, tableFieldsNames, tableData);
        vBox.getChildren().clear();
        vBox.getChildren().add(tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
    }

    private ObservableList<ExistingPatient> getExistingPatientsData() {
        ObservableList<ExistingPatient> existingPatientsData = FXCollections.observableArrayList();
        try {
            // 1.
            ArrayList<String> array = new ArrayList<>();
            File file = new File("patients.s");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                array.add(line);
            }
            bufferedReader.close();

            // 2.
            if (array.size() > 0) {
                ExistingPatient[] existingPatients = new ExistingPatient[array.size()];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                for (int i = 0; i < array.size(); i++) {
                    String[] str = array.get(i).split("\t");
                    ExistingPatient patient = new ExistingPatient(
                            str[0],
                            str[1],
                            str[2],
                            str[3],
                            LocalDate.parse(str[4], formatter),
                            Integer.valueOf(str[5]),
                            str[6],
                            str[7],
                            str[8]
                    );
                    existingPatients[i] = patient;
                }
                existingPatientsData = FXCollections.observableArrayList(existingPatients);
            }
        } catch (Exception ex) {
            ExceptionDialogWindow ed = new ExceptionDialogWindow(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }
        return existingPatientsData;
    }

    @FXML
    private void deleteExistingPatient() throws IOException {

        if (tableView.getSelectionModel().getSelectedItem() != null) {

            // Load chosen patient.
            ExistingPatient existingPatient = (ExistingPatient) tableView.getSelectionModel().getSelectedItem();
            String patientID = existingPatient.getId();

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Are you sure you want to delete selected patient?");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.get() == ButtonType.OK) {

                deleteSelectedPatient(patientID);
                setTableView();
            }

            // Show information dialog.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Selected patient was removed.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must select an existing patient first.");
            alert.showAndWait();
        }
    }

    private void deleteSelectedPatient(String patientID) {
        try {
            // 1.
            ArrayList<String> array = new ArrayList<>();
            File file = new File("patients.s");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                array.add(line);
            }
            bufferedReader.close();

            // 2.
            for (int i = 0; i < array.size(); i++) {
                String[] str = array.get(i).split("\t");
                if (str[0].equals(patientID)) {
                    array.remove(i);
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
                        for (String anArray : array) {
                            bufferedWriter.write(anArray + "\n");
                        }
                        bufferedWriter.close();
                    } catch (IOException ex) {
                        ExceptionDialogWindow ed = new ExceptionDialogWindow(Alert.AlertType.ERROR, ex);
                        ed.setTitle("Exception");
                        ed.setHeaderText(ex.getClass().getName());
                        ed.showAndWait();
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            ExceptionDialogWindow ed = new ExceptionDialogWindow(Alert.AlertType.ERROR, ex);
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
    private void chooseExistingPatient() throws IOException {

        if (tableView.getSelectionModel().getSelectedItem() != null) {

            // Load chosen existing patient.
            ExistingPatient existingPatient = (ExistingPatient) tableView.getSelectionModel().getSelectedItem();
            String patientID = existingPatient.getId();
            Patient patient = findExistingPatientByID(patientID);
            StartApplication.getSpecvisData().setPatient(patient);

            // Set scene patient.
            setScenePatient();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must select an existing patient first.");
            alert.showAndWait();
        }
    }

    private Patient findExistingPatientByID(String patientID) {
        Patient patient = new Patient();
        try {
            // 1.
            ArrayList<String> array = new ArrayList<>();
            File file = new File("patients.s");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                array.add(line);
            }
            bufferedReader.close();

            // 2.
            for (String anArray : array) {
                String[] str = anArray.split("\t");
                if (str[0].equals(patientID)) {
                    patient.setId(str[0]);
                    patient.setFirstName(str[1]);
                    patient.setLastName(str[2]);
                    patient.setGender(str[3]);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    patient.setDateOfBirth(LocalDate.parse(str[4], formatter));
                    patient.setAge(Integer.valueOf(str[5]));
                    patient.setPhone(str[6]);
                    patient.setEmail(str[7]);
                    patient.setCity(str[8]);
                    patient.setPostalCode(str[9]);
                    patient.setVisualAcuityL(str[10]);
                    patient.setVisualAcuityR(str[11]);
                    patient.setAdditionalInformation(str[12].replaceAll("#n%", "\n"));
                }
            }
        } catch (Exception ex) {
            ExceptionDialogWindow ed = new ExceptionDialogWindow(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }
        return patient;
    }
}
