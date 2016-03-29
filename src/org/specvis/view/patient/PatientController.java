package org.specvis.view.patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.specvis.StartApplication;
import org.specvis.model.*;
import org.specvis.model.procedure.BasicProcedureSettings;
import org.specvis.view.miscellaneous.ExceptionDialogWindow;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
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

public class PatientController implements Initializable {

    private Functions functions;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldName;

    @FXML
    private ComboBox<String> comboBoxTestedEye;

    @FXML
    private TextField textFieldSettings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        functions = StartApplication.getFunctions();

        setPatientIdAndNameAndTestedEye();
        setItemsForComboBoxTestedEye();
    }

    private void setPatientIdAndNameAndTestedEye() {
        Patient patient = StartApplication.getSpecvisData().getPatient();
        if (patient != null) {
            textFieldID.setText(patient.getId());
            textFieldName.setText(patient.getFirstName() + " " + patient.getLastName());
            comboBoxTestedEye.getSelectionModel().select(patient.getTestedEye());
            if (patient.getSettingsName() != null) {
                textFieldSettings.setText(patient.getSettingsName());
            }
        }
    }

    private void setItemsForComboBoxTestedEye() {
        ObservableList<String> observableList = FXCollections.observableArrayList("Both", "Left", "Right");
        comboBoxTestedEye.setItems(observableList);
    }

    @FXML
    private void setTestedEye() {
        if (isPatientSelected()) {
            String testedEye = comboBoxTestedEye.getSelectionModel().getSelectedItem();
            StartApplication.getSpecvisData().getPatient().setTestedEye(testedEye);
        }
    }

    @FXML
    private void setScenePatientNew() throws IOException {
        StartApplication.setScenePatientNew();
    }

    @FXML
    private void setScenePatientExisting() throws IOException {
        StartApplication.setScenePatientExisting();
    }

    @FXML
    private void setScenePatientEdit() throws IOException {
        if (isPatientSelected()) {
            StartApplication.setScenePatientEdit();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must choose patient first.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setScenePatientResults() throws IOException {
        if (isPatientSelected()) {
            StartApplication.setScenePatientResults();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must choose patient first.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setSceneScreenAndLumScale() throws IOException {
        if (isPatientSelected() && isTestedEyeSelected()) {
            StartApplication.setSceneScreenAndLumScale();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must create a new patient or choose an existing one, and choose the tested eye.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setOnActionForLoadButton() {
        if (isPatientSelected()) {
            loadSettings();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must choose patient first.");
            alert.showAndWait();
        }
    }

    private void loadSettings() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load settings");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Specvis settings", "*.sset"));
        File initialDirectory = new File("Settings");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(new File("Settings"));
        }

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            ArrayList<String> settingsFileContent = readSettingsFile(selectedFile);

            // Set settings for Screen and luminance.
            ScreenAndLuminanceScale screenAndLuminanceScale = StartApplication.getSpecvisData().getScreenAndLuminanceScale();
            if (screenAndLuminanceScale == null) {
                screenAndLuminanceScale = new ScreenAndLuminanceScale();
                StartApplication.getSpecvisData().setScreenAndLuminanceScale(screenAndLuminanceScale);
            }

            int screenIndex = Integer.valueOf(settingsFileContent.get(0).split("=")[1]);
            screenAndLuminanceScale.setChosenScreenIndex(screenIndex);

            int screenResolutionX = Integer.valueOf(settingsFileContent.get(1).split("=")[1]);
            screenAndLuminanceScale.setScreenResolutionX(screenResolutionX);

            int screenResolutionY = Integer.valueOf(settingsFileContent.get(2).split("=")[1]);
            screenAndLuminanceScale.setScreenResolutionY(screenResolutionY);

            int screenWidth = Integer.valueOf(settingsFileContent.get(3).split("=")[1]);
            screenAndLuminanceScale.setScreenWidth(screenWidth);

            int screenHeight = Integer.valueOf(settingsFileContent.get(4).split("=")[1]);
            screenAndLuminanceScale.setScreenHeight(screenHeight);

            int patientDistance = Integer.valueOf(settingsFileContent.get(5).split("=")[1]);
            screenAndLuminanceScale.setPatientDistanceFromTheScreen(patientDistance);

            double involvedVisualFieldX = Double.valueOf(settingsFileContent.get(6).split("=")[1]);
            screenAndLuminanceScale.setInvolvedVisualFieldX(involvedVisualFieldX);

            double involvedVisualFieldY = Double.valueOf(settingsFileContent.get(7).split("=")[1]);
            screenAndLuminanceScale.setInvolvedVisualFieldY(involvedVisualFieldY);

            String stimulusScaleID = settingsFileContent.get(8).split("=")[1];
            LuminanceScale stimulusLuminanceScale = functions.findExistingLuminanceScaleByID(stimulusScaleID);
            screenAndLuminanceScale.setStimulusLuminanceScale(stimulusLuminanceScale);

            String backgroundScaleID = settingsFileContent.get(9).split("=")[1];
            LuminanceScale backgroundLumiananceScale = functions.findExistingLuminanceScaleByID(backgroundScaleID);
            screenAndLuminanceScale.setBackgroundLuminanceScale(backgroundLumiananceScale);

            // Set settings for Stimulus and background.
            StimulusAndBackground stimulusAndBackground = StartApplication.getSpecvisData().getStimulusAndBackground();
            if (stimulusAndBackground == null) {
                stimulusAndBackground = new StimulusAndBackground();
                StartApplication.getSpecvisData().setStimulusAndBackground(stimulusAndBackground);
            }

            int stimulusMaxBrightness = Integer.valueOf(settingsFileContent.get(11).split("=")[1]);
            stimulusAndBackground.setStimulusMaxBrightness(stimulusMaxBrightness);

            double stimulusMaxLuminance = Double.valueOf(settingsFileContent.get(12).split("=")[1]);
            stimulusAndBackground.setStimulusMaxLuminance(stimulusMaxLuminance);

            int stimulusMinBrightness = Integer.valueOf(settingsFileContent.get(13).split("=")[1]);
            stimulusAndBackground.setStimulusMinBrightness(stimulusMinBrightness);

            double stimulusMinLuminance = Double.valueOf(settingsFileContent.get(14).split("=")[1]);
            stimulusAndBackground.setStimulusMinLuminance(stimulusMinLuminance);

            String stimulusShape = settingsFileContent.get(15).split("=")[1];
            stimulusAndBackground.setStimulusShape(stimulusShape);

            double stimulusInclination = Double.valueOf(settingsFileContent.get(16).split("=")[1]);
            stimulusAndBackground.setStimulusInclination(stimulusInclination);

            double stimulusSizeX = Double.valueOf(settingsFileContent.get(17).split("=")[1]);
            stimulusAndBackground.setStimulusSizeX(stimulusSizeX);

            double stimulusSizeY = Double.valueOf(settingsFileContent.get(18).split("=")[1]);
            stimulusAndBackground.setStimulusSizeY(stimulusSizeY);

            int stimulusDisplayTime = Integer.valueOf(settingsFileContent.get(19).split("=")[1]);
            stimulusAndBackground.setStimulusDisplayTime(stimulusDisplayTime);

            int interStimuliIntervalConstantPart = Integer.valueOf(settingsFileContent.get(20).split("=")[1]);
            stimulusAndBackground.setConstantPartOfInterval(interStimuliIntervalConstantPart);

            int interStimuliIntervalRandomPart = Integer.valueOf(settingsFileContent.get(21).split("=")[1]);
            stimulusAndBackground.setRandomPartOfInterval(interStimuliIntervalRandomPart);

            int backgroundBrightness = Integer.valueOf(settingsFileContent.get(22).split("=")[1]);
            stimulusAndBackground.setBackgroundBrightness(backgroundBrightness);

            double backgroundLuminance = Double.valueOf(settingsFileContent.get(23).split("=")[1]);
            stimulusAndBackground.setBackgroundLuminance(backgroundLuminance);

            double distanceBetweenStimuliX = Double.valueOf(settingsFileContent.get(24).split("=")[1]);
            stimulusAndBackground.setDistanceBetweenStimuliInDegreesX(distanceBetweenStimuliX);

            double distanceBetweenStimuliY = Double.valueOf(settingsFileContent.get(25).split("=")[1]);
            stimulusAndBackground.setDistanceBetweenStimuliInDegreesY(distanceBetweenStimuliY);

            boolean isCorrectionForSphericitySelected = Boolean.valueOf(settingsFileContent.get(26).split("=")[1]);
            stimulusAndBackground.setIsCorrectionForSphericityCheckBoxChecked(isCorrectionForSphericitySelected);

            // Set settings for Fixation and other.
            FixationAndOther fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();
            if (fixationAndOther == null) {
                fixationAndOther = new FixationAndOther();
                StartApplication.getSpecvisData().setFixationAndOther(fixationAndOther);
            }

            Color fixationPointColor = Color.web(settingsFileContent.get(28).split("=")[1]);
            fixationAndOther.setFixationPointColor(fixationPointColor);

            double fixationPointLuminance = Double.valueOf(settingsFileContent.get(29).split("=")[1]);
            fixationAndOther.setFixationPointLuminance(fixationPointLuminance);

            double fixationPointSizeX = Double.valueOf(settingsFileContent.get(30).split("=")[1]);
            fixationAndOther.setFixationPointSizeX(fixationPointSizeX);

            double fixationPointSizeY = Double.valueOf(settingsFileContent.get(31).split("=")[1]);
            fixationAndOther.setFixationPointSizeY(fixationPointSizeY);

            double fixationPointLocationX = Double.valueOf(settingsFileContent.get(32).split("=")[1]);
            fixationAndOther.setFixationPointLocationX(fixationPointLocationX);

            double fixationPointLocationY = Double.valueOf(settingsFileContent.get(33).split("=")[1]);
            fixationAndOther.setFixationPointLocationY(fixationPointLocationY);

            String answerToStimulusKey = settingsFileContent.get(34).split("=")[1];
            fixationAndOther.setAnswerToStimulus(answerToStimulusKey);

            String pauseProcedureKey = settingsFileContent.get(35).split("=")[1];
            fixationAndOther.setPauseProcedure(pauseProcedureKey);

            String cancelProcedureKey = settingsFileContent.get(36).split("=")[1];
            fixationAndOther.setCancelProcedure(cancelProcedureKey);

            String fixationMonitorTechnique = settingsFileContent.get(38).split("=")[1];
            fixationAndOther.setFixationMonitorTechnique(fixationMonitorTechnique);

            int monitorFixationEveryXStimuli;
            int monitorFixationEveryXToYStimuli_1;
            int monitorFixationEveryXToYStimuli_2;
            boolean isMonitorFixationEveryXStimuliSelected;

            boolean isShowPatientMessageWhenItLosesFixation;
            String textOfTheMessage;
            int fontSize;
            String fontWeight;
            Color fontColor;
            Color messageBackgroundColor;
            double messageBackgroundSizeX;
            double messageBackgroundSizeY;
            double messageDistanceFromFixationPointX;
            double messageDistanceFromFixationPointY;
            int resumeNextStimulusTimeInterval;

            switch (fixationMonitorTechnique) {
                case "Blindspot":

                    FixationAndOtherMonitorSettingsBlindspot fixAndOtherBlindspot = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();

                    if (fixAndOtherBlindspot == null) {
                        fixAndOtherBlindspot = new FixationAndOtherMonitorSettingsBlindspot();
                        StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsBlindspot(fixAndOtherBlindspot);
                    }

                    monitorFixationEveryXStimuli = Integer.valueOf(settingsFileContent.get(39).split("=")[1]);
                    fixAndOtherBlindspot.setMonitorFixationEveryXStimuli(monitorFixationEveryXStimuli);

                    monitorFixationEveryXToYStimuli_1 = Integer.valueOf(settingsFileContent.get(40).split("=")[1]);
                    fixAndOtherBlindspot.setMonitorFixationEveryXYStimuli_1(monitorFixationEveryXToYStimuli_1);

                    monitorFixationEveryXToYStimuli_2 = Integer.valueOf(settingsFileContent.get(41).split("=")[1]);
                    fixAndOtherBlindspot.setMonitorFixationEveryXYStimuli_2(monitorFixationEveryXToYStimuli_2);

                    isMonitorFixationEveryXStimuliSelected = Boolean.valueOf(settingsFileContent.get(42).split("=")[1]);
                    fixAndOtherBlindspot.setIsMonitorFixationEveryXStimuliSelected(isMonitorFixationEveryXStimuliSelected);

                    double monitorStimulusSizeX = Double.valueOf(settingsFileContent.get(43).split("=")[1]);
                    fixAndOtherBlindspot.setFixationMonitorStimulusSizeX(monitorStimulusSizeX);

                    double monitorStimulusSizeY = Double.valueOf(settingsFileContent.get(44).split("=")[1]);
                    fixAndOtherBlindspot.setFixationMonitorStimulusSizeY(monitorStimulusSizeY);

                    int monitorStimulusBrightness = Integer.valueOf(settingsFileContent.get(45).split("=")[1]);
                    fixAndOtherBlindspot.setFixationMonitorStimulusBrightness(monitorStimulusBrightness);

                    double monitorStimulusLuminance = Double.valueOf(settingsFileContent.get(46).split("=")[1]);
                    fixAndOtherBlindspot.setFixationMonitorStimulusLuminance(monitorStimulusLuminance);

                    double blindspotDistanceFromFixationPointX = Double.valueOf(settingsFileContent.get(47).split("=")[1]);
                    fixAndOtherBlindspot.setBlindspotDistanceFromFixPointX(blindspotDistanceFromFixationPointX);

                    double blindspotDistanceFromFixationPointY = Double.valueOf(settingsFileContent.get(48).split("=")[1]);
                    fixAndOtherBlindspot.setBlindspotDistanceFromFixPointY(blindspotDistanceFromFixationPointY);

                    isShowPatientMessageWhenItLosesFixation = Boolean.valueOf(settingsFileContent.get(49).split("=")[1]);
                    fixAndOtherBlindspot.setIsShowPatientMsgSelected(isShowPatientMessageWhenItLosesFixation);

                    textOfTheMessage = settingsFileContent.get(50).split("=")[1];
                    fixAndOtherBlindspot.setTextOfTheMsg(textOfTheMessage);

                    fontSize = Integer.valueOf(settingsFileContent.get(51).split("=")[1]);
                    fixAndOtherBlindspot.setFontSize(fontSize);

                    fontWeight = settingsFileContent.get(52).split("=")[1];
                    fixAndOtherBlindspot.setFontWeight(fontWeight);

                    fontColor = Color.web(settingsFileContent.get(53).split("=")[1]);
                    fixAndOtherBlindspot.setFontColor(fontColor);

                    messageBackgroundColor = Color.web(settingsFileContent.get(54).split("=")[1]);
                    fixAndOtherBlindspot.setMsgBackgroundColor(messageBackgroundColor);

                    messageBackgroundSizeX = Double.valueOf(settingsFileContent.get(55).split("=")[1]);
                    fixAndOtherBlindspot.setMsgBackgroundSizeX(messageBackgroundSizeX);

                    messageBackgroundSizeY = Double.valueOf(settingsFileContent.get(56).split("=")[1]);
                    fixAndOtherBlindspot.setMsgBackgroundSizeY(messageBackgroundSizeY);

                    messageDistanceFromFixationPointX = Double.valueOf(settingsFileContent.get(57).split("=")[1]);
                    fixAndOtherBlindspot.setMsgDistanceFromFixPointX(messageDistanceFromFixationPointX);

                    messageDistanceFromFixationPointY = Double.valueOf(settingsFileContent.get(58).split("=")[1]);
                    fixAndOtherBlindspot.setMsgDistanceFromFixPointY(messageDistanceFromFixationPointY);

                    resumeNextStimulusTimeInterval = Integer.valueOf(settingsFileContent.get(59).split("=")[1]);
                    fixAndOtherBlindspot.setResumeToNextStimulusTimeInterval(resumeNextStimulusTimeInterval);

                    break;
                case "Fixation point change":

                    FixationAndOtherMonitorSettingsFixPointChange fixAndOtherFixPointChange = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();

                    if (fixAndOtherFixPointChange == null) {
                        fixAndOtherFixPointChange = new FixationAndOtherMonitorSettingsFixPointChange();
                        StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsFixPointChange(fixAndOtherFixPointChange);
                    }

                    monitorFixationEveryXStimuli = Integer.valueOf(settingsFileContent.get(39).split("=")[1]);
                    fixAndOtherFixPointChange.setMonitorFixationEveryXStimuli(monitorFixationEveryXStimuli);

                    monitorFixationEveryXToYStimuli_1 = Integer.valueOf(settingsFileContent.get(40).split("=")[1]);
                    fixAndOtherFixPointChange.setMonitorFixationEveryXYStimuli_1(monitorFixationEveryXToYStimuli_1);

                    monitorFixationEveryXToYStimuli_2 = Integer.valueOf(settingsFileContent.get(41).split("=")[1]);
                    fixAndOtherFixPointChange.setMonitorFixationEveryXYStimuli_2(monitorFixationEveryXToYStimuli_2);

                    isMonitorFixationEveryXStimuliSelected = Boolean.valueOf(settingsFileContent.get(42).split("=")[1]);
                    fixAndOtherFixPointChange.setIsMonitorFixationEveryXStimuliSelected(isMonitorFixationEveryXStimuliSelected);

                    double changedFixationPointSizeX = Double.valueOf(settingsFileContent.get(43).split("=")[1]);
                    fixAndOtherFixPointChange.setChangedFixPointSizeX(changedFixationPointSizeX);

                    double changedFixationPointSizeY = Double.valueOf(settingsFileContent.get(44).split("=")[1]);
                    fixAndOtherFixPointChange.setChangedFixPointSizeY(changedFixationPointSizeY);

                    Color changedFixationPointColor = Color.web(settingsFileContent.get(45).split("=")[1]);
                    fixAndOtherFixPointChange.setChangedFixPointColor(changedFixationPointColor);

                    double changedFixationPointLuminance = Double.valueOf(settingsFileContent.get(46).split("=")[1]);
                    fixAndOtherFixPointChange.setChangedFixPointLuminance(changedFixationPointLuminance);

                    isShowPatientMessageWhenItLosesFixation = Boolean.valueOf(settingsFileContent.get(47).split("=")[1]);
                    fixAndOtherFixPointChange.setIsShowPatientMsgSelected(isShowPatientMessageWhenItLosesFixation);

                    textOfTheMessage = settingsFileContent.get(48).split("=")[1];
                    fixAndOtherFixPointChange.setTextOfTheMsg(textOfTheMessage);

                    fontSize = Integer.valueOf(settingsFileContent.get(49).split("=")[1]);
                    fixAndOtherFixPointChange.setFontSize(fontSize);

                    fontWeight = settingsFileContent.get(50).split("=")[1];
                    fixAndOtherFixPointChange.setFontWeight(fontWeight);

                    fontColor = Color.web(settingsFileContent.get(51).split("=")[1]);
                    fixAndOtherFixPointChange.setFontColor(fontColor);

                    messageBackgroundColor = Color.web(settingsFileContent.get(52).split("=")[1]);
                    fixAndOtherFixPointChange.setMsgBackgroundColor(messageBackgroundColor);

                    messageBackgroundSizeX = Double.valueOf(settingsFileContent.get(53).split("=")[1]);
                    fixAndOtherFixPointChange.setMsgBackgroundSizeX(messageBackgroundSizeX);

                    messageBackgroundSizeY = Double.valueOf(settingsFileContent.get(54).split("=")[1]);
                    fixAndOtherFixPointChange.setMsgBackgroundSizeY(messageBackgroundSizeY);

                    messageDistanceFromFixationPointX = Double.valueOf(settingsFileContent.get(55).split("=")[1]);
                    fixAndOtherFixPointChange.setMsgDistanceFromFixPointX(messageDistanceFromFixationPointX);

                    messageDistanceFromFixationPointY = Double.valueOf(settingsFileContent.get(56).split("=")[1]);
                    fixAndOtherFixPointChange.setMsgDistanceFromFixPointY(messageDistanceFromFixationPointY);

                    resumeNextStimulusTimeInterval = Integer.valueOf(settingsFileContent.get(57).split("=")[1]);
                    fixAndOtherFixPointChange.setResumeToNextStimulusTimeInterval(resumeNextStimulusTimeInterval);

                    break;
            }

            int procedureSettingIndex = 0;
            for (int i = 0; i < settingsFileContent.size(); i++) {
                if (settingsFileContent.get(i).split("=")[0].equals("procedure")) {
                    procedureSettingIndex = i;
                    break;
                }
            }

            String procedure = settingsFileContent.get(procedureSettingIndex).split("=")[1];
            fixationAndOther.setProcedure(procedure);

            switch (procedure) {
                case "Basic":

                    BasicProcedureSettings basicProcedureSettings = StartApplication.getSpecvisData().getBasicProcedureSettings();
                    if (basicProcedureSettings == null) {
                        basicProcedureSettings = new BasicProcedureSettings();
                        StartApplication.getSpecvisData().setBasicProcedureSettings(basicProcedureSettings);
                    }

                    boolean spreadValuesLogarithmically = Boolean.valueOf(settingsFileContent.get(procedureSettingIndex + 1).split("=")[1]);
                    basicProcedureSettings.setSpreadValuesLogarithmically(spreadValuesLogarithmically);

                    int lengthOfTheTestedBrightnessVector = Integer.valueOf(settingsFileContent.get(procedureSettingIndex + 2).split("=")[1]);
                    basicProcedureSettings.setVisualFieldTestBrightnessVectorLength(lengthOfTheTestedBrightnessVector);

                    break;
            }

            String settingsName = selectedFile.getName().split("\\.")[0];
            StartApplication.getSpecvisData().getPatient().setSettingsName(settingsName);
            textFieldSettings.setText(settingsName);
        }
    }

    private ArrayList<String> readSettingsFile(File file) {
        ArrayList<String> array = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                array.add(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            ExceptionDialogWindow ed = new ExceptionDialogWindow(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }
        return array;
    }

    private boolean isPatientSelected() {
        return StartApplication.getSpecvisData().getPatient() != null;
    }

    private boolean isTestedEyeSelected() {
        return comboBoxTestedEye.getSelectionModel().getSelectedIndex() != -1;
    }
}
