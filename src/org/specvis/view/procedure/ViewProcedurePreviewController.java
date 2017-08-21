package org.specvis.view.procedure;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.specvis.StartApplication;
import org.specvis.datastructures.*;
import org.specvis.datastructures.procedures.basic.ProcedureBasicData;
import org.specvis.datastructures.settings.*;
import org.specvis.datastructures.procedures.basic.ProcedureBasicStimulus;
import org.specvis.logic.Functions;
import org.specvis.procedures.basic.ProcedureBasicFixMonitorBlindspot;
import org.specvis.procedures.basic.ProcedureBasicFixMonitorBoth;
import org.specvis.procedures.basic.ProcedureBasicFixMonitorFixPointChange;
import org.specvis.procedures.basic.ProcedureBasicFixMonitorNone;
import org.specvis.view.miscellaneous.ViewExceptionDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Piotr Dzwiniel on 2016-02-08.
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

public class ViewProcedurePreviewController implements Initializable {

    private Functions functions;
    private SpecvisData specvisData;

    @FXML
    private TextArea textArea;

    @FXML
    private Circle circle;

    @FXML
    private Label label;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Button buttonStart;

    @FXML
    private Label labelClock;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        functions = StartApplication.getFunctions();
        specvisData = StartApplication.getSpecvisData();

        initTextForTextArea();
        initStyleForLabel();
        initProgressIndicator();
    }

    private void initTextForTextArea() {

        textArea.appendText("BASIC INFORMATION" + "\n\n");

        textArea.appendText("DEPRECATED_Procedure: " + specvisData.getUiSettingsFixationAndOther().getProcedure().toLowerCase() + "\n\n");

        Date date = new Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        textArea.appendText("Date of the test: " + modifiedDate + "\n");
        textArea.appendText("Patient name: " + specvisData.getPatient().getFirstName() + " " + specvisData.getPatient().getLastName() + "\n");
        textArea.appendText("Tested eye: " + specvisData.getPatient().getTestedEye().toLowerCase() + "\n\n");

        textArea.appendText("Screen size (mm): " + specvisData.getUiSettingsScreenAndLuminanceScale().getScreenWidth() + "/" + specvisData.getUiSettingsScreenAndLuminanceScale().getScreenHeight() + "\n");
        textArea.appendText("Patient distance (mm): " + specvisData.getUiSettingsScreenAndLuminanceScale().getPatientDistanceFromTheScreen() + "\n");
        textArea.appendText("Involved visual field (\u00b0): " + specvisData.getUiSettingsScreenAndLuminanceScale().getInvolvedVisualFieldX() + "/" + specvisData.getUiSettingsScreenAndLuminanceScale().getInvolvedVisualFieldY() + "\n\n");

        textArea.appendText("Stimulus scale name: " + specvisData.getUiSettingsScreenAndLuminanceScale().getStimulusLuminanceScale().getName() + "\n");
        textArea.appendText("Stimulus max brightness (%): " + specvisData.getUiSettingsStimulusAndBackground().getStimulusMaxBrightness() + "\n");
        textArea.appendText("Stimulus max luminance (cd/m2): " + specvisData.getUiSettingsStimulusAndBackground().getStimulusMaxLuminance() + "\n");
        textArea.appendText("Stimulus min brightness (%): " + specvisData.getUiSettingsStimulusAndBackground().getStimulusMinBrightness() + "\n");
        textArea.appendText("Stimulus min luminance (cd/m2): " + specvisData.getUiSettingsStimulusAndBackground().getStimulusMinLuminance() + "\n");
        textArea.appendText("Stimulus shape: " + specvisData.getUiSettingsStimulusAndBackground().getStimulusShape().toLowerCase() + "\n");
        textArea.appendText("Stimulus inclination (\u00b0): " + specvisData.getUiSettingsStimulusAndBackground().getStimulusInclination() + "\n");
        textArea.appendText("Stimulus size (\u00b0): " + specvisData.getUiSettingsStimulusAndBackground().getStimulusSizeX() + "/" + specvisData.getUiSettingsStimulusAndBackground().getStimulusSizeY() + "\n");
        textArea.appendText("Stimulus display time (ms): " + specvisData.getUiSettingsStimulusAndBackground().getStimulusDisplayTime() + "\n");
        textArea.appendText("Inter-stimuli interval (ms): " + specvisData.getUiSettingsStimulusAndBackground().getConstantPartOfInterval() + "/" + specvisData.getUiSettingsStimulusAndBackground().getRandomPartOfInterval() + "\n");
        textArea.appendText("Distance between stimuli (\u00b0): " + specvisData.getUiSettingsStimulusAndBackground().getDistanceBetweenStimuliInDegreesX() + "/" + specvisData.getUiSettingsStimulusAndBackground().getDistanceBetweenStimuliInDegreesY() + "\n\n");

        String doCorrection;
        if (specvisData.getUiSettingsStimulusAndBackground().isCorrectionForSphericityCheckBoxChecked()) {
            doCorrection = "yes";
        } else {
            doCorrection = "no";
        }
        textArea.appendText("Sphericity correction: " + doCorrection + "\n\n");

        textArea.appendText("Background scale name: " + specvisData.getUiSettingsScreenAndLuminanceScale().getBackgroundLuminanceScale().getName() + "\n");
        textArea.appendText("Background brightness (%): " + specvisData.getUiSettingsStimulusAndBackground().getBackgroundBrightness() + "\n");
        textArea.appendText("Background luminance (cd/m2): " + specvisData.getUiSettingsStimulusAndBackground().getBackgroundLuminance() + "\n\n");

        double minDB = functions.decibelsValue(specvisData.getUiSettingsStimulusAndBackground().getStimulusMaxLuminance(), specvisData.getUiSettingsStimulusAndBackground().getStimulusMaxLuminance(), specvisData.getUiSettingsStimulusAndBackground().getBackgroundLuminance(), 2);
        double maxDB = functions.decibelsValue(specvisData.getUiSettingsStimulusAndBackground().getStimulusMaxLuminance(), specvisData.getUiSettingsStimulusAndBackground().getStimulusMinLuminance(), specvisData.getUiSettingsStimulusAndBackground().getBackgroundLuminance(), 2);
        textArea.appendText("Decibel range (dB): " + minDB + "/" + maxDB + "\n\n");

        textArea.appendText("Fixation point location (\u00b0): " + specvisData.getUiSettingsFixationAndOther().getFixationPointLocationX() + "/" + specvisData.getUiSettingsFixationAndOther().getFixationPointLocationY() + "\n");
        textArea.appendText("Fixation point color: " + StartApplication.getFunctions().toHexCode(specvisData.getUiSettingsFixationAndOther().getFixationPointColor()) + "\n");
        textArea.appendText("Fixation point luminance (cd/m2): " + specvisData.getUiSettingsFixationAndOther().getFixationPointLuminance() + "\n");
        textArea.appendText("Fixation point size (\u00b0): " + specvisData.getUiSettingsFixationAndOther().getFixationPointSizeX() + "/" + specvisData.getUiSettingsFixationAndOther().getFixationPointSizeY() + "\n\n");

        switch (specvisData.getUiSettingsFixationAndOther().getFixationMonitorTechnique()) {
            case "Blindspot":
                textArea.appendText("Fixation monitor technique: blindspot" + "\n");
                if (specvisData.getUiSettingsFixMonitorBlindspot().isMonitorFixationEveryXStimuliSelected()) {
                    textArea.appendText("Monitor fixation every " + specvisData.getUiSettingsFixMonitorBlindspot().getMonitorFixationEveryXStimuli() + " stimuli" + "\n");
                } else {
                    textArea.appendText("Monitor fixation every " + specvisData.getUiSettingsFixMonitorBlindspot().getMonitorFixationEveryXYStimuli_1() + " to " + specvisData.getUiSettingsFixMonitorBlindspot().getMonitorFixationEveryXYStimuli_2() + " stimuli" + "\n");
                }
                textArea.appendText("Monitor stimulus size (\u00b0): " + specvisData.getUiSettingsFixMonitorBlindspot().getFixationMonitorStimulusSizeX() + "/" + specvisData.getUiSettingsFixMonitorBlindspot().getFixationMonitorStimulusSizeY() + "\n");
                textArea.appendText("Monitor stimulus brightness (%): " + specvisData.getUiSettingsFixMonitorBlindspot().getFixationMonitorStimulusBrightness() + "\n");
                textArea.appendText("Monitor stimulus luminance (cd/m2): " + StartApplication.getFunctions().round(specvisData.getUiSettingsFixMonitorBlindspot().getFixationMonitorStimulusLuminance(), 2) + "\n");
                textArea.appendText("Blindspot distance from fixation point (\u00b0): " + specvisData.getUiSettingsFixMonitorBlindspot().getBlindspotDistanceFromFixPointX() + "/" + specvisData.getUiSettingsFixMonitorBlindspot().getBlindspotDistanceFromFixPointY() + "\n");
                String showMsgBlindspot;
                if (specvisData.getUiSettingsFixMonitorBlindspot().isShowPatientMsgSelected()) {
                    showMsgBlindspot = "yes";
                } else {
                    showMsgBlindspot = "no";
                }
                textArea.appendText("Show the patient a message when it loses fixation: " + showMsgBlindspot + "\n\n");
                break;
            case "Fixation point change":


                textArea.appendText("Fixation monitor technique: fixation point change" + "\n");
                if (specvisData.getUiSettingsProcedureBasicFixPointChange().isMonitorFixationEveryXStimuliSelected()) {
                    textArea.appendText("Monitor fixation every " + specvisData.getUiSettingsProcedureBasicFixPointChange().getMonitorFixationEveryXStimuli() + " stimuli" + "\n");
                } else {
                    textArea.appendText("Monitor fixation every " + specvisData.getUiSettingsProcedureBasicFixPointChange().getMonitorFixationEveryXYStimuli_1() + " to " + specvisData.getUiSettingsProcedureBasicFixPointChange().getMonitorFixationEveryXYStimuli_2() + " stimuli" + "\n");
                }
                textArea.appendText("Changed fixation point size (\u00b0): " + specvisData.getUiSettingsProcedureBasicFixPointChange().getChangedFixPointSizeX() + "/" + specvisData.getUiSettingsProcedureBasicFixPointChange().getChangedFixPointSizeY() + "\n");
                textArea.appendText("Changed fixation point color: " + StartApplication.getFunctions().toHexCode(specvisData.getUiSettingsProcedureBasicFixPointChange().getChangedFixPointColor()) + "\n");
                textArea.appendText("Changed fixation point luminance (cd/m2): " + StartApplication.getFunctions().round(specvisData.getUiSettingsProcedureBasicFixPointChange().getChangedFixPointLuminance(), 2) + "\n");
                String showMsgFixPointChange;
                if (specvisData.getUiSettingsProcedureBasicFixPointChange().isShowPatientMsgSelected()) {
                    showMsgFixPointChange = "yes";
                } else {
                    showMsgFixPointChange = "no";
                }
                textArea.appendText("Show the patient a message when it loses fixation: " + showMsgFixPointChange + "\n\n");
                break;
            default:
                textArea.appendText("Fixation monitor technique: none" + "\n\n");
                break;
        }
    }

    private void initStyleForLabel() {
        label.setStyle("-fx-text-fill: white;");
    }

    private void initProgressIndicator() {
        progressIndicator.setProgress(0.0);
    }

    public void addTextToTextArea(String text) {
        textArea.appendText(text);
    }

    public void setColorForCircle(Color color) {
        circle.setFill(color);
    }

    public void setTextForLabel(String text) {
        label.setText(text);
    }

    public void setStyleForLabel(String style) {
        label.setStyle(style);
    }

    public void setProgressForProgressIndicator(double progress) {
        progressIndicator.setProgress(progress);
    }

    public void setButtonStartDisable(boolean value) {
        buttonStart.setDisable(value);
    }

    @FXML
    private void setOnActionShowResultsButton() throws IOException {
        if (label.getText().equals("Finished")) {

            StartApplication.setSceneProcedureShowResults();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must finish procedure first.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setOnActionSaveResultsButton() {

        if (label.getText().equals("Finished")) {

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Are you sure you want to save results?");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.get() == ButtonType.OK) {

                try {

                    SpecvisData specvisData = StartApplication.getSpecvisData();

                    // Create a folder "Results" if it does not exist.
                    File dirResults = new File("Results");
                    if (!dirResults.exists()) {
                        dirResults.mkdir();
                    }

                    // Create a folder "Results/<Patient ID>" if it does not exist.
                    File dirResultsPatientID = new File("Results/" + specvisData.getPatient().getId());
                    if (!dirResultsPatientID.exists()) {
                        dirResultsPatientID.mkdir();
                    }

                    // Create a folder "Results/<Patient ID>/<Results ID>".
                    String resultsID = "R_" + functions.createIndividualID(functions.getCurrentDateYYYYmmDD(), 4);
                    File dirResultsPatientIDResultsID = new File("Results/" + specvisData.getPatient().getId() + "/" + resultsID);
                    if (!dirResultsPatientIDResultsID.exists()) {
                        dirResultsPatientIDResultsID.mkdir();
                    }

                    // Save data to file "Results/<Patient ID>/<Results ID>/session_info.txt".
                    File fileSessionInfo = new File("Results/" + specvisData.getPatient().getId() + "/" + resultsID + "/session_info.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileSessionInfo));
                    ObservableList<CharSequence> paragraph = textArea.getParagraphs();
                    Iterator<CharSequence> iterator = paragraph.iterator();
                    while (iterator.hasNext()) {
                        CharSequence charSequence = iterator.next();
                        bufferedWriter.append(charSequence);
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    // Save data to file "Results/<Patient ID>/<Results ID>/session_data.txt".
                    ProcedureBasicData procedureBasicData = StartApplication.getSpecvisData().getProcedureBasicData();
                    ArrayList<ProcedureBasicStimulus> procedureBasicStimulusArrayList = procedureBasicData.getArrayListProcedureBasicStimulus();
                    Collections.sort(procedureBasicStimulusArrayList, (t1, t2) -> t1.getIndex() - t2.getIndex());

                    File fileSessionData = new File("Results/" + specvisData.getPatient().getId() + "/" + resultsID + "/session_data.txt");
                    bufferedWriter = new BufferedWriter(new FileWriter(fileSessionData));

                    for (int i = 0; i < procedureBasicStimulusArrayList.size(); i++) {
                        ProcedureBasicStimulus stimulus = procedureBasicStimulusArrayList.get(i);
                        bufferedWriter.write(
                                stimulus.getIndex() + "\t" +
                                        functions.round(stimulus.getPositionOnTheScreenInPixelsX(), 2) + "\t" +
                                        functions.round(stimulus.getPositionOnTheScreenInPixelsY(), 2) + "\t" +
                                        functions.round(stimulus.getDistanceFromFixPointOnTheFieldOfViewInDegreesX(), 2) + "\t" +
                                        functions.round(stimulus.getDistanceFromFixPointOnTheFieldOfViewInDegreesY(), 2) + "\t" +
                                        stimulus.getBrightnessThreshold() + "\t" +
                                        stimulus.getLuminanceThreshold() + "\t" +
                                        stimulus.getDecibelThreshold() + "\n"
                        );
                    }
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    // Show information dialog.
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("The results have been saved in " + resultsID + ".");
                    alert.showAndWait();

                } catch (Exception ex) {
                    ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
                    ed.setTitle("Exception");
                    ed.setHeaderText(ex.getClass().getName());
                    ed.showAndWait();
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must finish procedure first.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setOnActionSaveSettingsButton() {

        // Open dialog for asking about the settings name.
        TextInputDialog dialog = new TextInputDialog("settings_name");
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Save settings");
        dialog.setContentText("Please enter settings name");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {

            // Create a folder "Settings" if it does not exist.
            File dirSettings = new File("Settings");
            if (!dirSettings.exists()) {
                dirSettings.mkdir();
            }

            File settingsFile = new File("Settings/" + result.get() + ".sset");

            boolean shouldIWriteThisFile = false;

            if (!settingsFile.exists()) {
                shouldIWriteThisFile = true;
            } else {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmation");
                confirmation.setHeaderText("File already exists.");
                confirmation.setContentText("Do you want to overwrite it?");
                Optional<ButtonType> answer = confirmation.showAndWait();

                if (answer.get() == ButtonType.OK) {
                    shouldIWriteThisFile = true;
                }
            }

            if (shouldIWriteThisFile) {
                try {

                    BufferedWriter bf = new BufferedWriter(new FileWriter(settingsFile));

                    UISettingsScreenAndLuminanceScale screenAndLum = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale();
                    UISettingsStimulusAndBackground stimAndBg = StartApplication.getSpecvisData().getUiSettingsStimulusAndBackground();
                    UISettingsFixationAndOther fixAndOther = StartApplication.getSpecvisData().getUiSettingsFixationAndOther();

                    // Write settings from Screen and luminance scene.
                    bf.write("screenIndex=" + screenAndLum.getChosenScreenIndex() + "\n");
                    bf.write("screenResolutionX=" + screenAndLum.getScreenResolutionX() + "\n");
                    bf.write("screenResolutionY=" + screenAndLum.getScreenResolutionY() + "\n");
                    bf.write("screenWidth=" + screenAndLum.getScreenWidth() + "\n");
                    bf.write("screenHeight=" + screenAndLum.getScreenHeight() + "\n");
                    bf.write("patientDistance=" + screenAndLum.getPatientDistanceFromTheScreen() + "\n");
                    bf.write("involvedVisualFieldX=" + screenAndLum.getInvolvedVisualFieldX() + "\n");
                    bf.write("involvedVisualFieldY=" + screenAndLum.getInvolvedVisualFieldY() + "\n");
                    bf.write("stimulusScaleID=" + screenAndLum.getStimulusLuminanceScale().getId() + "\n");
                    bf.write("backgroundScaleID=" + screenAndLum.getBackgroundLuminanceScale().getId() + "\n\n");

                    // Write settings from Stimulus and background scene.
                    bf.write("stimulusMaxBrightness=" + stimAndBg.getStimulusMaxBrightness() + "\n");
                    bf.write("stimulusMaxLuminance=" + stimAndBg.getStimulusMaxLuminance() + "\n");
                    bf.write("stimulusMinBrightness=" + stimAndBg.getStimulusMinBrightness() + "\n");
                    bf.write("stimulusMinLuminance=" + stimAndBg.getStimulusMinLuminance() + "\n");
                    bf.write("stimulusShape=" + stimAndBg.getStimulusShape() + "\n");
                    bf.write("stimulusInclination=" + stimAndBg.getStimulusInclination() + "\n");
                    bf.write("stimulusSizeX=" + stimAndBg.getStimulusSizeX() + "\n");
                    bf.write("stimulusSizeY=" + stimAndBg.getStimulusSizeY() + "\n");
                    bf.write("stimulusDisplayTime=" + stimAndBg.getStimulusDisplayTime() + "\n");
                    bf.write("interStimuliIntervalConstantPart=" + stimAndBg.getConstantPartOfInterval() + "\n");
                    bf.write("interStimuliIntervalRandomPart=" + stimAndBg.getRandomPartOfInterval() + "\n");
                    bf.write("backgroundBrightness=" + stimAndBg.getBackgroundBrightness() + "\n");
                    bf.write("backgroundLuminance=" + stimAndBg.getBackgroundLuminance() + "\n");
                    bf.write("distanceBetweenStimuliX=" + stimAndBg.getDistanceBetweenStimuliInDegreesX() + "\n");
                    bf.write("distanceBetweenStimuliY=" + stimAndBg.getDistanceBetweenStimuliInDegreesY() + "\n");
                    bf.write("isCorrectionForSphericitySelected=" + stimAndBg.isCorrectionForSphericityCheckBoxChecked() + "\n\n");

                    // Write settings from Fixation and other scene.
                    bf.write("fixationPointColor=" + functions.toHexCode(fixAndOther.getFixationPointColor()) + "\n");
                    bf.write("fixationPointLuminance=" + fixAndOther.getFixationPointLuminance() + "\n");
                    bf.write("fixationPointSizeX=" + fixAndOther.getFixationPointSizeX() + "\n");
                    bf.write("fixationPointSizeY=" + fixAndOther.getFixationPointSizeY() + "\n");
                    bf.write("fixationPointLocationX=" + fixAndOther.getFixationPointLocationX() + "\n");
                    bf.write("fixationPointLocationY=" + fixAndOther.getFixationPointLocationY() + "\n");
                    bf.write("answerToStimulusKey=" + fixAndOther.getAnswerToStimulus() + "\n");
                    bf.write("pauseProcedureKey=" + fixAndOther.getPauseProcedure() + "\n");
                    bf.write("cancelProcedureKey=" + fixAndOther.getCancelProcedure() + "\n\n");

                    // Write settings from Fixation and other scene -> Monitor fixation technique.
                    switch (fixAndOther.getFixationMonitorTechnique()) {
                        case "None":

                            bf.write("fixationMonitorTechnique=" + fixAndOther.getFixationMonitorTechnique() + "\n\n");

                            break;
                        case "Blindspot":

                            UISettingsFixMonitorBlindspot fixAndOtherBlindspot = StartApplication.getSpecvisData().getUiSettingsFixMonitorBlindspot();

                            bf.write("fixationMonitorTechnique=" + fixAndOther.getFixationMonitorTechnique() + "\n");
                            bf.write("monitorFixationEveryXStimuli=" + fixAndOtherBlindspot.getMonitorFixationEveryXStimuli() + "\n");
                            bf.write("monitorFixationEveryXToYStimuli_1=" + fixAndOtherBlindspot.getMonitorFixationEveryXYStimuli_1() + "\n");
                            bf.write("monitorFixationEveryXToYStimuli_2=" + fixAndOtherBlindspot.getMonitorFixationEveryXYStimuli_2() + "\n");
                            bf.write("isMonitorFixationEveryXStimuliSelected=" + fixAndOtherBlindspot.isMonitorFixationEveryXStimuliSelected() + "\n");
                            bf.write("monitorStimulusSizeX=" + fixAndOtherBlindspot.getFixationMonitorStimulusSizeX() + "\n");
                            bf.write("monitorStimulusSizeY=" + fixAndOtherBlindspot.getFixationMonitorStimulusSizeY() + "\n");
                            bf.write("monitorStimulusBrightness=" + fixAndOtherBlindspot.getFixationMonitorStimulusBrightness() + "\n");
                            bf.write("monitorStimulusLuminance=" + fixAndOtherBlindspot.getFixationMonitorStimulusLuminance() + "\n");
                            bf.write("blindspotDistanceFromFixationPointX=" + fixAndOtherBlindspot.getBlindspotDistanceFromFixPointX() + "\n");
                            bf.write("blindspotDistanceFromFixationPointY=" + fixAndOtherBlindspot.getBlindspotDistanceFromFixPointY() + "\n");

                            bf.write("isShowPatientMessageWhenItLosesFixation=" + fixAndOtherBlindspot.isShowPatientMsgSelected() + "\n");
                            bf.write("textOfTheMessage=" + fixAndOtherBlindspot.getTextOfTheMsg() + "\n");
                            bf.write("fontSize=" + fixAndOtherBlindspot.getFontSize() + "\n");
                            bf.write("fontWeight=" + fixAndOtherBlindspot.getFontWeight() + "\n");
                            bf.write("fontColor=" + functions.toHexCode(fixAndOtherBlindspot.getFontColor()) + "\n");
                            bf.write("messageBackgroundColor=" + functions.toHexCode(fixAndOtherBlindspot.getMsgBackgroundColor()) + "\n");
                            bf.write("messageBackgroundSizeX=" + fixAndOtherBlindspot.getMsgBackgroundSizeX() + "\n");
                            bf.write("messageBackgroundSizeY=" + fixAndOtherBlindspot.getMsgBackgroundSizeY() + "\n");
                            bf.write("messageDistanceFromFixationPointX=" + fixAndOtherBlindspot.getMsgDistanceFromFixPointX() + "\n");
                            bf.write("messageDistanceFromFixationPointY=" + fixAndOtherBlindspot.getMsgDistanceFromFixPointY() + "\n");
                            bf.write("resume->NextStimulusTimeInterval=" + fixAndOtherBlindspot.getResumeToNextStimulusTimeInterval() + "\n\n");

                            break;
                        case "Fixation point change":

                            UISettingsFixMonitorFixPointChange fixAndOtherFixPointChange = StartApplication.getSpecvisData().getUiSettingsProcedureBasicFixPointChange();

                            bf.write("fixationMonitorTechnique=" + fixAndOther.getFixationMonitorTechnique() + "\n");
                            bf.write("monitorFixationEveryXStimuli=" + fixAndOtherFixPointChange.getMonitorFixationEveryXStimuli() + "\n");
                            bf.write("monitorFixationEveryXToYStimuli_1=" + fixAndOtherFixPointChange.getMonitorFixationEveryXYStimuli_1() + "\n");
                            bf.write("monitorFixationEveryXToYStimuli_2=" + fixAndOtherFixPointChange.getMonitorFixationEveryXYStimuli_2() + "\n");
                            bf.write("isMonitorFixationEveryXStimuliSelected=" + fixAndOtherFixPointChange.isMonitorFixationEveryXStimuliSelected() + "\n");
                            bf.write("changedFixationPointSizeX=" + fixAndOtherFixPointChange.getChangedFixPointSizeX() + "\n");
                            bf.write("changedFixationPointSizeY=" + fixAndOtherFixPointChange.getChangedFixPointSizeY() + "\n");
                            bf.write("changedFixationPointColor=" + functions.toHexCode(fixAndOtherFixPointChange.getChangedFixPointColor()) + "\n");
                            bf.write("changedFixationPointLuminance=" + fixAndOtherFixPointChange.getChangedFixPointLuminance() + "\n");

                            bf.write("isShowPatientMessageWhenItLosesFixation=" + fixAndOtherFixPointChange.isShowPatientMsgSelected() + "\n");
                            bf.write("textOfTheMessage=" + fixAndOtherFixPointChange.getTextOfTheMsg() + "\n");
                            bf.write("fontSize=" + fixAndOtherFixPointChange.getFontSize() + "\n");
                            bf.write("fontWeight=" + fixAndOtherFixPointChange.getFontWeight() + "\n");
                            bf.write("fontColor=" + functions.toHexCode(fixAndOtherFixPointChange.getFontColor()) + "\n");
                            bf.write("messageBackgroundColor=" + functions.toHexCode(fixAndOtherFixPointChange.getMsgBackgroundColor()) + "\n");
                            bf.write("messageBackgroundSizeX=" + fixAndOtherFixPointChange.getMsgBackgroundSizeX() + "\n");
                            bf.write("messageBackgroundSizeY=" + fixAndOtherFixPointChange.getMsgBackgroundSizeY() + "\n");
                            bf.write("messageDistanceFromFixationPointX=" + fixAndOtherFixPointChange.getMsgDistanceFromFixPointX() + "\n");
                            bf.write("messageDistanceFromFixationPointY=" + fixAndOtherFixPointChange.getMsgDistanceFromFixPointY() + "\n");
                            bf.write("resume->NextStimulusTimeInterval=" + fixAndOtherFixPointChange.getResumeToNextStimulusTimeInterval() + "\n\n");

                            break;

                        case "Both":

                            UISettingsFixMonitorBoth uiSettingsFixMonitorBoth = StartApplication.getSpecvisData().getUiSettingsFixMonitorBoth();

                            bf.write("fixationMonitorTechnique=" + fixAndOther.getFixationMonitorTechnique() + "\n");

                            bf.write("monitorFixationEveryXStimuli=" + uiSettingsFixMonitorBoth.getMonitorFixationEveryXStimuli() + "\n");
                            bf.write("monitorFixationEveryXToYStimuli_1=" + uiSettingsFixMonitorBoth.getMonitorFixationEveryXYStimuli_1() + "\n");
                            bf.write("monitorFixationEveryXToYStimuli_2=" + uiSettingsFixMonitorBoth.getMonitorFixationEveryXYStimuli_2() + "\n");
                            bf.write("isMonitorFixationEveryXStimuliSelected=" + uiSettingsFixMonitorBoth.isMonitorFixationEveryXStimuliSelected() + "\n");

                            bf.write("monitorStimulusSizeX=" + uiSettingsFixMonitorBoth.getFixMonitorStimulusSizeInDgX() + "\n");
                            bf.write("monitorStimulusSizeY=" + uiSettingsFixMonitorBoth.getFixMonitorStimulusSizeInDgY() + "\n");
                            bf.write("monitorStimulusBrightness=" + uiSettingsFixMonitorBoth.getFixMonitorStimulusBrightness() + "\n");
                            bf.write("monitorStimulusLuminance=" + uiSettingsFixMonitorBoth.getFixMonitorStimulusLuminance() + "\n");
                            bf.write("blindspotDistanceFromFixationPointX=" + uiSettingsFixMonitorBoth.getFixMonitorStimulusDistanceFromFixPointInDgX() + "\n");
                            bf.write("blindspotDistanceFromFixationPointY=" + uiSettingsFixMonitorBoth.getFixMonitorStimulusDistanceFromFixPointInDgY() + "\n");

                            bf.write("changedFixationPointSizeX=" + uiSettingsFixMonitorBoth.getChangedFixPointSizeInDgX() + "\n");
                            bf.write("changedFixationPointSizeY=" + uiSettingsFixMonitorBoth.getChangedFixPointSizeInDgY() + "\n");
                            bf.write("changedFixationPointColor=" + functions.toHexCode(uiSettingsFixMonitorBoth.getChangedFixPointColor()) + "\n");
                            bf.write("changedFixationPointLuminance=" + uiSettingsFixMonitorBoth.getChangedFixPointColorLuminance() + "\n");

                            bf.write("isShowPatientMessageWhenItLosesFixation=" + uiSettingsFixMonitorBoth.isShowPatientMsgSelected() + "\n");
                            bf.write("showMessageForXMs=" + uiSettingsFixMonitorBoth.getResumeProcedureAutomaticallyAfterXMs() + "\n");
                            bf.write("showNextStimulusFollowingMessageAfterXMs=" + uiSettingsFixMonitorBoth.getShowNextStimAfterMsgAfterXMs() + "\n");

                            bf.write("textOfTheMessage=" + uiSettingsFixMonitorBoth.getTextOfTheMsg() + "\n");
                            bf.write("fontSize=" + uiSettingsFixMonitorBoth.getFontSize() + "\n");
                            bf.write("fontWeight=" + uiSettingsFixMonitorBoth.getFontWeight() + "\n");
                            bf.write("fontColor=" + functions.toHexCode(uiSettingsFixMonitorBoth.getFontColor()) + "\n");
                            bf.write("messageBackgroundColor=" + functions.toHexCode(uiSettingsFixMonitorBoth.getMsgBackgroundColor()) + "\n");
                            bf.write("messageBackgroundSizeX=" + uiSettingsFixMonitorBoth.getMsgBackgroundSizeInDgX() + "\n");
                            bf.write("messageBackgroundSizeY=" + uiSettingsFixMonitorBoth.getMsgBackgroundSizeInDgY() + "\n");
                            bf.write("messageDistanceFromFixationPointX=" + uiSettingsFixMonitorBoth.getMsgDistanceFromFixPointInDgX() + "\n");
                            bf.write("messageDistanceFromFixationPointY=" + uiSettingsFixMonitorBoth.getMsgDistanceFromFixPointInDgY() + "\n\n");

                            break;
                    }

                    // Write settings from Fixation and other scene -> DEPRECATED_Procedure.
                    switch (fixAndOther.getProcedure()) {
                        case "Basic":

                            UISettingsProcedureBasic basicProcSet = StartApplication.getSpecvisData().getUiSettingsProcedureBasic();

                            bf.write("procedure=" + fixAndOther.getProcedure() + "\n");
                            bf.write("spreadValuesLogarithmically=" + basicProcSet.isSpreadValuesLogarithmically() + "\n");
                            bf.write("lengthOfTheTestedBrightnessVector=" + basicProcSet.getVisualFieldTestBrightnessVectorLength() + "\n");

                            break;
                    }
                    bf.close();

                    // Show information dialog.
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("The settings have been saved.");
                    alert.showAndWait();
                } catch (IOException ex) {
                    ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
                    ed.setTitle("Exception");
                    ed.setHeaderText(ex.getClass().getName());
                    ed.showAndWait();
                }
            }
        }
    }

    @FXML
    private void setOnActionDiscardButton() throws IOException {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Are you sure you want to discard this procedure (all progress and not saved data will be lost)?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.get() == ButtonType.OK) {

            StartApplication.setSceneFixationAndOther();
        }
    }

    @FXML
    private void setOnActionStartButton() throws IOException {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Are you sure you want to start procedure?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.get() == ButtonType.OK) {

            circle.setFill(Color.web("#202020"));
            initStyleForLabel();
            label.setText("Waiting...");
            progressIndicator.setProgress(0.0);

            // Commented: old version 1.0.0.
//            switch (specvisData.getUiSettingsFixationAndOther().getProcedure()) {
//                case "Basic":
//                    DEPRECATED_Procedure procedure = new DEPRECATED_Procedure();
//                    procedure.show();
//                    break;
//            }

            switch (specvisData.getUiSettingsFixationAndOther().getProcedure()) {
                case "Basic":
                    switch (specvisData.getUiSettingsFixationAndOther().getFixationMonitorTechnique()) {
                        case "Blindspot":

                            UISettingsFixMonitorBlindspot uiSettingsBlindspot = StartApplication.getSpecvisData().getUiSettingsFixMonitorBlindspot();
                            if (uiSettingsBlindspot == null) {
                                uiSettingsBlindspot = new UISettingsFixMonitorBlindspot();
                                uiSettingsBlindspot.setDefaultValues();
                            }
                            StartApplication.getSpecvisData().setUiSettingsFixMonitorBlindspot(uiSettingsBlindspot);

                            ProcedureBasicFixMonitorBlindspot procedureBasicFixMonitorBlindspot = new ProcedureBasicFixMonitorBlindspot();
                            procedureBasicFixMonitorBlindspot.show();
                            break;
                        case "Fixation point change":

                            UISettingsFixMonitorFixPointChange uiSettingsFixPoint = StartApplication.getSpecvisData().getUiSettingsProcedureBasicFixPointChange();
                            if (uiSettingsFixPoint == null) {
                                uiSettingsFixPoint = new UISettingsFixMonitorFixPointChange();
                                uiSettingsFixPoint.setDefaultValues();
                            }
                            StartApplication.getSpecvisData().setUiSettingsProcedureBasicFixPointChange(uiSettingsFixPoint);

                            ProcedureBasicFixMonitorFixPointChange procedureBasicFixMonitorFixPointChange = new ProcedureBasicFixMonitorFixPointChange();
                            procedureBasicFixMonitorFixPointChange.show();
                            break;
                        case "Both":

                            UISettingsFixMonitorBoth uiSettingsBoth = StartApplication.getSpecvisData().getUiSettingsFixMonitorBoth();
                            if (uiSettingsBoth == null) {
                                uiSettingsBoth = new UISettingsFixMonitorBoth();
                                uiSettingsBoth.setDefaultValues();
                            }
                            StartApplication.getSpecvisData().setUiSettingsFixMonitorBoth(uiSettingsBoth);

                            ProcedureBasicFixMonitorBoth procedureBasicFixMonitorBoth = new ProcedureBasicFixMonitorBoth();
                            procedureBasicFixMonitorBoth.show();
                            break;
                        default:
                            ProcedureBasicFixMonitorNone procedureBasicFixMonitorNone = new ProcedureBasicFixMonitorNone();
                            procedureBasicFixMonitorNone.show();
                            break;
                    }
            }
        }
    }

    public void setTextForStopwatch(long start, long end) {
        labelClock.setText(functions.totalTime(start, end));
    }
}
