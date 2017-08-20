package org.specvis.view.patient;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.specvis.StartApplication;
import org.specvis.datastructures.patient.PatientResults;
import org.specvis.datastructures.procedures.basic.ProcedureBasicStimulus;
import org.specvis.logic.CustomTableView;
import org.specvis.view.miscellaneous.ViewExceptionDialog;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by piotr_dzwiniel on 20.08.2017.
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

public class ViewComparePatientResultsTableController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private TableView tableView;

    @FXML
    private Label labelLoading;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<PatientResults> tableData = getPatientResultsList();
        String[] tableColumnsNames = new String[] {"ID", "Date", "Eye", "Visual field", "Procedure", "Fixation monitor", "Test duration"};
        String[] tableFieldsNames = PatientResults.getFieldsNames();
        tableView = new CustomTableView<>(tableColumnsNames, tableFieldsNames, tableData);
        vBox.getChildren().clear();
        vBox.getChildren().add(tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
    }

    /**
     * Get consistent patient results data that can be compared.
     * @return
     */
    private ObservableList<PatientResults> getPatientResultsList() {
        ObservableList<PatientResults> patientResultsData = FXCollections.observableArrayList();

        /* PREPARE MAP WITH INFORMATION OF INITIAL RESULTS */
        // Information include: 1) tested eye; 2) involved visual field; 3) distance between stimuli;
        // 4) fixation point locaiton.

        /* Initialize map with initial results information */
        Map<String, String> mapWithInitialResultsInfo = new HashMap<>();

        /* Get patient ID and patient initial results ID */
        String patientID = StartApplication.getSpecvisData().getPatient().getId();
        String patientResultsID = StartApplication.getSpecvisData().getPatient().getPatientResults().getId();

        System.out.println(patientID);
        System.out.println(patientResultsID);

        /* Read session_info.txt file with initial results information */
        File file = new File("Results/" + patientID + "/" + patientResultsID + "/session_info.txt");
        ArrayList<String> fileContent = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.add(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String testedEye = fileContent.get(6).split(":")[1].replaceAll("\\s+", "");
        String involvedVisualField = fileContent.get(10).split(":")[1].replaceAll("\\s+", "");
        String distanceBetweenStimuli = fileContent.get(22).split(":")[1].replaceAll("\\s+", "");
        String fixationPointLocation = fileContent.get(32).split(":")[1].replaceAll("\\s+", "");

        /* Populate map with initial results information */
        mapWithInitialResultsInfo.put("Tested eye", testedEye);
        mapWithInitialResultsInfo.put("Involved visual field", involvedVisualField);
        mapWithInitialResultsInfo.put("Distance between stimuli", distanceBetweenStimuli);
        mapWithInitialResultsInfo.put("Fixation point location", fixationPointLocation);

        /* FIND ALL RESULTS FOR THE PATIENT THAT ARE CONSISTENT WITH INITIAL RESULTS INFORMATION */
        /* Get list of all results in patient folder */
        String[] patientResultsIDs = new File("Results/" + patientID).list();

        /* Test all results for consistency with initial results information */
        ArrayList<String> consistedPatientResultsIDs = new ArrayList<>();
        for (int i = 0; i < patientResultsIDs.length; i++) {
            if (testPatientResultsConsistenty(mapWithInitialResultsInfo, patientResultsIDs[i])) {
                consistedPatientResultsIDs.add(patientResultsIDs[i]);
            }
        }

        /* Get patient results data for all valid results IDs */
        if (consistedPatientResultsIDs.size() > 0) {
            for (String resultsID : consistedPatientResultsIDs) {
                PatientResults patientResults = getPatientResults(new File("Results/" + patientID + "/" + resultsID));
                patientResultsData.add(patientResults);
            }
        }

        return patientResultsData;
    }

    /**
     * Test consistency between patient results to compare.
     * @param mapWithInitialResultsInfo
     * @param resultsIDForTest
     * @return true if results are consistent.
     */
    private boolean testPatientResultsConsistenty(Map<String, String> mapWithInitialResultsInfo, String resultsIDForTest) {

        /* Initialize map with initial results information */
        Map<String, String> mapWithInfoToCompare = new HashMap<>();

        /* Get patient ID and patient initial results ID */
        String patientID = StartApplication.getSpecvisData().getPatient().getId();
        String patientResultsID = resultsIDForTest;

        /* Read session_info.txt file with initial results information */
        File file = new File("Results/" + patientID + "/" + patientResultsID + "/session_info.txt");
        ArrayList<String> fileContent = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.add(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String testedEye = fileContent.get(6).split(":")[1].replaceAll("\\s+", "");
        String involvedVisualField = fileContent.get(10).split(":")[1].replaceAll("\\s+", "");
        String distanceBetweenStimuli = fileContent.get(22).split(":")[1].replaceAll("\\s+", "");
        String fixationPointLocation = fileContent.get(32).split(":")[1].replaceAll("\\s+", "");

        /* Populate map with initial results information */
        mapWithInfoToCompare.put("Tested eye", testedEye);
        mapWithInfoToCompare.put("Involved visual field", involvedVisualField);
        mapWithInfoToCompare.put("Distance between stimuli", distanceBetweenStimuli);
        mapWithInfoToCompare.put("Fixation point location", fixationPointLocation);

        return mapWithInitialResultsInfo.equals(mapWithInfoToCompare) ? true : false;
    }

    /**
     * Get patient results from given directory.
     * @param directory
     * @return
     */
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

    @FXML
    private void setOnActionCancelButton() throws Exception {

        StartApplication.setScenePatientResultsMap();
    }

    @FXML
    private void setOnActionCompareButton() {

        if (tableView.getSelectionModel().getSelectedItem() != null) {

            Timeline timelineEvents = new Timeline(
                    new KeyFrame(Duration.millis(0), event -> {

                        /* Set text for loading label */
                        labelLoading.setText("Specvis is computing. Please wait...");
                    }),
                    new KeyFrame(Duration.millis(100), event -> {
                        computeComparison();
                    })
            );
            timelineEvents.setCycleCount(1);
            timelineEvents.play();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must select a patient results first.");
            alert.showAndWait();
        }
    }

    private void computeComparison() {

        /* Get and set patient results to compare */
        PatientResults patientResults = (PatientResults) tableView.getSelectionModel().getSelectedItem();
        StartApplication.getSpecvisData().getPatient().setPatientResultsToCompare(patientResults);

        /* Get and set session info to compare */
        ArrayList<String> resultsInfoToCompare = getPatientResultsInfo(new File("Results/" + StartApplication.getSpecvisData().getPatient().getId() + "/" + patientResults.getId() + "/session_info.txt"));
        StartApplication.getSpecvisData().getPatient().setResultsInfoToCompare(resultsInfoToCompare);

        /* Get and set session data to compare */
        double[][] resultsDataToCompare = getPatientResultsData(new File("Results/" + StartApplication.getSpecvisData().getPatient().getId() + "/" + patientResults.getId() + "/session_data.txt"));
        StartApplication.getSpecvisData().getPatient().setResultsDataToCompare(resultsDataToCompare);

        /* Open comparison window */
        try {
            StartApplication.setSceneComparePatientResultsMaps();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
