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
import org.specvis.datastructures.patient.PatientResults;
import org.specvis.datastructures.procedures.basic.ProcedureBasicStimulus;
import org.specvis.logic.CustomTableView;
import org.specvis.view.miscellaneous.ViewExceptionDialog;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Piotr Dzwiniel on 2016-02-29.
 */

/*
 * Copyright from 2014 till now - Piotr Dzwiniel
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

public class ViewPatientResultsController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private TableView tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableView();
    }

    private void setTableView() {

        ObservableList<PatientResults> tableData = getPatientResultsData();
        String[] tableColumnsNames = new String[] {"ID", "Date", "Eye", "Visual field", "Procedure", "Fixation monitor", "Test duration"};
        String[] tableFieldsNames = PatientResults.getFieldsNames();
        tableView = new CustomTableView<>(tableColumnsNames, tableFieldsNames, tableData);
        vBox.getChildren().clear();
        vBox.getChildren().add(tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
    }

    private ObservableList<PatientResults> getPatientResultsData() {
        ObservableList<PatientResults> patientResultsData = FXCollections.observableArrayList();

        File patientResultsMainDirectory = new File("Results/" + StartApplication.getSpecvisData().getPatient().getId());

        if (patientResultsMainDirectory.exists()) {

            String[] patientResultsDirectories = patientResultsMainDirectory.list();

            for (int i = 0; i < patientResultsDirectories.length; i++) {
                PatientResults patientResults = getPatientResults(new File("Results/" + StartApplication.getSpecvisData().getPatient().getId() + "/" + patientResultsDirectories[i]));
                patientResultsData.add(patientResults);
            }
        }

        return patientResultsData;
    }

    private PatientResults getPatientResults(File directory) {

        PatientResults patientResults = new PatientResults();

        try {

            // 1.
            ArrayList<String> arrayList = new ArrayList<>();
            File file = new File(directory.getPath() + "/" + "session_info.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                arrayList.add(line);
            }
            bufferedReader.close();

            // 2.
            String id = directory.getName();
            String date = arrayList.get(4).split(":")[1].replaceAll("\\s+", "");
            String eye = arrayList.get(6).split(":")[1].replaceAll("\\s+", "");
            String visualField = arrayList.get(10).split(":")[1].replaceAll("\\s+", "");
            String procedure = arrayList.get(2).split(":")[1].replaceAll("\\s+", "");
            String fixationMonitor = arrayList.get(37).split(": ")[1];
            String testDuration = arrayList.get(arrayList.size() - 2).split(": ")[1];

            patientResults = new PatientResults(id, date, eye, visualField, procedure, fixationMonitor, testDuration);

        } catch (IOException ex) {
            ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }

        return patientResults;
    }

    private ArrayList<String> getPatientResultsInfo(File file) {

        ArrayList<String> arrayList = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                arrayList.add(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }

        return  arrayList;
    }

    private double[][] getPatientResultsData(File file) {

        // Read file.
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                arrayList.add(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }

        // Create 2D array of ProcedureBasicStimulus.
        ArrayList<ProcedureBasicStimulus> data = new ArrayList<>();


        for (int i = 0; i < arrayList.size(); i++) {
            String[] str = arrayList.get(i).split("\t");

            ProcedureBasicStimulus procedureBasicStimulus = new ProcedureBasicStimulus();
            procedureBasicStimulus.setIndex(Integer.valueOf(str[0]));
            procedureBasicStimulus.setPositionOnTheScreenInPixelsX(Double.valueOf(str[1]));
            procedureBasicStimulus.setPositionOnTheScreenInPixelsY(Double.valueOf(str[2]));
            procedureBasicStimulus.setDecibelThreshold(Double.valueOf(str[7]));

            data.add(procedureBasicStimulus);
        }

        Collections.sort(data, ((o1, o2) -> Double.compare(o1.getPositionOnTheScreenInPixelsY(), o2.getPositionOnTheScreenInPixelsY())));

        ArrayList<ArrayList<ProcedureBasicStimulus>> sortedData = new ArrayList<>();

        double temp = 0;
        for (int i = 0; i < data.size(); i++) {

            if (temp != data.get(i).getPositionOnTheScreenInPixelsY()) {
                ArrayList<ProcedureBasicStimulus> row = getAllStimuliWithSpecifiYPosition(
                        data.get(i).getPositionOnTheScreenInPixelsY(), data
                );
                Collections.sort(row, (o1, o2) -> Double.compare(o1.getPositionOnTheScreenInPixelsX(), o2.getPositionOnTheScreenInPixelsX()));
                sortedData.add(row);
            }

            temp = data.get(i).getPositionOnTheScreenInPixelsY();
        }

        // Convert sorted array data to double[][] decibel matrix.
        double[][] decibelMatrix = new double[sortedData.size()][sortedData.get(0).size()];

        for (int i = 0; i < decibelMatrix.length; i++) {
            for (int j = 0; j < decibelMatrix[i].length; j++) {
                decibelMatrix[i][j] = sortedData.get(i).get(j).getDecibelThreshold();
            }
        }

        return decibelMatrix;
    }

    private ArrayList<ProcedureBasicStimulus> getAllStimuliWithSpecifiYPosition(double positionY, ArrayList<ProcedureBasicStimulus> listOfAllStimuli) {
        ArrayList<ProcedureBasicStimulus> arrayList = new ArrayList<>();
        for (int i = 0; i < listOfAllStimuli.size(); i++) {
            if (positionY == listOfAllStimuli.get(i).getPositionOnTheScreenInPixelsY()) {
                arrayList.add(listOfAllStimuli.get(i));
            }
        }
        return arrayList;
    }

    @FXML
    private void setOnActionDeleteButton() {

        if (tableView.getSelectionModel().getSelectedItem() != null) {

            // Load chosen patient results.
            PatientResults patientResults = (PatientResults) tableView.getSelectionModel().getSelectedItem();
            String patientResultsID = patientResults.getId();

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Are you sure you want to delete selected patient results?");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.get() == ButtonType.OK) {

                deleteSelectedPatientResults(patientResultsID);
                setTableView();
            }

            // Show information dialog.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Selected patient results was removed.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must select a patient results first.");
            alert.showAndWait();
        }
    }

    private void deleteSelectedPatientResults(String patientResultsID) {

        File directoryPath = new File("Results/" + StartApplication.getSpecvisData().getPatient().getId() + "/" + patientResultsID);
        StartApplication.getFunctions().deleteDir(directoryPath);
    }

    @FXML
    private void setOnActionCancelButton() throws IOException {
        StartApplication.setScenePatient();
    }

    @FXML
    private void setOnActionInfoButton() throws Exception {

        if (tableView.getSelectionModel().getSelectedItem() != null) {
            PatientResults patientResults = (PatientResults) tableView.getSelectionModel().getSelectedItem();
            String id = patientResults.getId();

            File file = new File("Results/" + StartApplication.getSpecvisData().getPatient().getId() + "/" + id + "/session_info.txt");
            ArrayList<String> sessionInfoFileContent = getPatientResultsInfo(file);

            StartApplication.getSpecvisData().getPatient().setResultsInfo(sessionInfoFileContent);

            StartApplication.setScenePatientResultsInfo();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must select a patient results first.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setOnActionMapButton() throws Exception {

        if (tableView.getSelectionModel().getSelectedItem() != null) {

            PatientResults patientResults = (PatientResults) tableView.getSelectionModel().getSelectedItem();
            String id = patientResults.getId();

            StartApplication.getSpecvisData().getPatient().setResultsInfo(getPatientResultsInfo(new File("Results/" + StartApplication.getSpecvisData().getPatient().getId() + "/" + id + "/session_info.txt")));

            File file = new File("Results/" + StartApplication.getSpecvisData().getPatient().getId() + "/" + id + "/session_data.txt");
            double[][] sessionDataFileContent = getPatientResultsData(file);

            StartApplication.getSpecvisData().getPatient().setResultsData(sessionDataFileContent);
            StartApplication.getSpecvisData().getPatient().setPatientResults(patientResults);

            StartApplication.setScenePatientResultsMap();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must select a patient results first.");
            alert.showAndWait();
        }
    }
}
