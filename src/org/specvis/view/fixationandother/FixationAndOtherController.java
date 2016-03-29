package org.specvis.view.fixationandother;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.specvis.StartApplication;
import org.specvis.model.*;
import org.specvis.model.procedure.BasicProcedureSettings;
import org.specvis.view.miscellaneous.PreviewStimuliDistributionWindow;

import java.io.IOException;
import java.net.URL;
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

public class FixationAndOtherController implements Initializable {

    private Functions functions;
    private ScreenAndLuminanceScale screenAndLuminanceScale;
    private LuminanceScale luminanceScaleForBackground;
    private StimulusAndBackground stimulusAndBackground;

    @FXML
    private ColorPicker colorPickerFixationPointColor;

    @FXML
    private TextField textFieldFixationPointLuminance;

    @FXML
    private Spinner<Double> spinnerFixationPointSizeX;

    @FXML
    private Spinner<Double> spinnerFixationPointSizeY;

    @FXML
    private ComboBox<String> comboBoxFixationMonitorTechnique;

    @FXML
    private Spinner<Double> spinnerFixationPointLocationX;

    @FXML
    private Spinner<Double> spinnerFixationPointLocationY;

    @FXML
    private Pane panePreview;

    @FXML
    private ComboBox<String> comboBoxAnswerToStimulus;

    @FXML
    private ComboBox<String> comboBoxPauseProcedure;

    @FXML
    private ComboBox<String> comboBoxCancelProcedure;

    @FXML
    private ComboBox<String> comboBoxProcedure;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        functions = StartApplication.getFunctions();

        screenAndLuminanceScale = StartApplication.getSpecvisData().getScreenAndLuminanceScale();

        luminanceScaleForBackground = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getBackgroundLuminanceScale();

        stimulusAndBackground = StartApplication.getSpecvisData().getStimulusAndBackground();

        setInitialValueForTextFieldFixationPointLuminance();
        setItemsForComboBoxFixationMonitorTechnique();
        setItemsForComboBoxesWithKeys();
        setItemsForComboBoxProcedure();

        initValuesForFields();

        setLookForPanePreview();
    }

    private void initValuesForFields() {
        FixationAndOther fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();
        if (fixationAndOther != null) {

            colorPickerFixationPointColor.setValue(fixationAndOther.getFixationPointColor());
            textFieldFixationPointLuminance.setText(String.valueOf(fixationAndOther.getFixationPointLuminance()));
            spinnerFixationPointSizeX.getValueFactory().setValue(fixationAndOther.getFixationPointSizeX());
            spinnerFixationPointSizeY.getValueFactory().setValue(fixationAndOther.getFixationPointSizeY());
            if (StartApplication.getSpecvisData().getPatient().getTestedEye().equals("Both") && fixationAndOther.getFixationMonitorTechnique().equals("Blindspot")) {
                comboBoxFixationMonitorTechnique.getSelectionModel().select(0);
            } else {
                comboBoxFixationMonitorTechnique.getSelectionModel().select(fixationAndOther.getFixationMonitorTechnique());
            }
            spinnerFixationPointLocationX.getValueFactory().setValue(fixationAndOther.getFixationPointLocationX());
            spinnerFixationPointLocationY.getValueFactory().setValue(fixationAndOther.getFixationPointLocationY());
            comboBoxAnswerToStimulus.getSelectionModel().select(fixationAndOther.getAnswerToStimulus());
            comboBoxPauseProcedure.getSelectionModel().select(fixationAndOther.getPauseProcedure());
            comboBoxCancelProcedure.getSelectionModel().select(fixationAndOther.getCancelProcedure());
            comboBoxProcedure.getSelectionModel().select(fixationAndOther.getProcedure());
        }
    }

    private void setInitialValueForTextFieldFixationPointLuminance() {
        textFieldFixationPointLuminance.setText("0.0");
    }

    public void updateValueForTextFieldFixationPointLuminance() {
        FixationAndOther fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();
        textFieldFixationPointLuminance.setText(String.valueOf(fixationAndOther.getFixationPointLuminance()));
    }

    private void setItemsForComboBoxFixationMonitorTechnique() {
        ObservableList<String> observableList = FXCollections.observableArrayList("None", "Blindspot", "Fixation point change");
        comboBoxFixationMonitorTechnique.setItems(observableList);
        comboBoxFixationMonitorTechnique.getSelectionModel().select(0);

        if (StartApplication.getSpecvisData().getPatient().getTestedEye().equals("Both")) {
            comboBoxFixationMonitorTechnique.getItems().remove(1);
        }
    }

    private void setItemsForComboBoxesWithKeys() {
        ObservableList<String> observableList = FXCollections.observableArrayList(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "DIGIT0", "DIGIT1", "DIGIT2", "DIGIT3", "DIGIT4", "DIGIT5",
                "DIGIT6", "DIGIT7", "DIGIT8", "DIGIT9", "NUMPAD0", "NUMPAD1",
                "NUMPAD2", "NUMPAD3", "NUMPAD4", "NUMPAD5", "NUMPAD6", "NUMPAD7",
                "NUMPAD8", "NUMPAD9", "ALT", "CONTROL", "SHIFT", "ENTER", "SPACE",
                "ESCAPE", "UP", "DOWN", "LEFT", "RIGHT");

        comboBoxAnswerToStimulus.setItems(observableList);
        comboBoxAnswerToStimulus.getSelectionModel().select("SPACE");

        comboBoxPauseProcedure.setItems(observableList);
        comboBoxPauseProcedure.getSelectionModel().select("P");

        comboBoxCancelProcedure.setItems(observableList);
        comboBoxCancelProcedure.getSelectionModel().select("ESCAPE");
    }

    private void setItemsForComboBoxProcedure() {
        ObservableList<String> observableList = FXCollections.observableArrayList("Basic");

        comboBoxProcedure.setItems(observableList);
        comboBoxProcedure.getSelectionModel().select("Basic");
    }

    private void setLookForPanePreview() {

            // 1. Set background color.
        int hueForBackground = (int) functions.round(luminanceScaleForBackground.getHue(), 0);
        int saturationForBackground = (int) functions.round(luminanceScaleForBackground.getSaturation(), 0);
        int brightnessForBackground = stimulusAndBackground.getBackgroundBrightness();
        String colorForBackground = "-fx-background-color: hsb(" + hueForBackground + ", " + saturationForBackground + "%, " + brightnessForBackground + "%);";

        // 2. Create fixation point.
        Shape fixationPoint = createEllipseFixationPoint();

        // 3. Set panePreview.
        if (panePreview.getChildren().size() > 0) {
            panePreview.getChildren().remove(panePreview.getChildren().size() - 1);
        }
        panePreview.setStyle(colorForBackground);
        panePreview.getChildren().add(fixationPoint);
    }

    private Ellipse createEllipseFixationPoint() {

        // 1. Get screen resolution.
        double screenResolutionX = screenAndLuminanceScale.getScreenResolutionX();
        double screenResolutionY = screenAndLuminanceScale.getScreenResolutionY();

        // 2. Get involved visual field.
        double involvedVisualFieldX = screenAndLuminanceScale.getInvolvedVisualFieldX();
        double involvedVisualFieldY = screenAndLuminanceScale.getInvolvedVisualFieldY();

        // 3. Calculate pixels per one degree.
        double pixelsForOneDegreeX = screenResolutionX / involvedVisualFieldX;
        double pixelsForOneDegreeY = screenResolutionY / involvedVisualFieldY;

        // 4. Get stimulus size in degrees.
        double stimulusSizeInDegreesX = Double.valueOf(spinnerFixationPointSizeX.getValue().toString());
        double stimulusSizeInDegreesY = Double.valueOf(spinnerFixationPointSizeY.getValue().toString());

        // 5. Calculate fixation point radius in pixels.
        double stimulusRadiusInPixelsX = (stimulusSizeInDegreesX / 2) * pixelsForOneDegreeX;
        double stimulusRadiusInPixelsY = (stimulusSizeInDegreesY / 2) * pixelsForOneDegreeY;

        // 6. Set color for fixation point.
        Color color = colorPickerFixationPointColor.getValue();

        // 7. Create fixation point preview shape.
        Ellipse ellipse;
        ellipse = new Ellipse(panePreview.getPrefWidth() / 2, panePreview.getPrefHeight() / 2, stimulusRadiusInPixelsX, stimulusRadiusInPixelsY);
        ellipse.setFill(color);
        ellipse.setStroke(color);

        return ellipse;
    }

    private void setValuesForFixationAndOtherFields() {
        FixationAndOther fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();
        if (fixationAndOther == null) {
            fixationAndOther = new FixationAndOther();
            StartApplication.getSpecvisData().setFixationAndOther(fixationAndOther);
        }

        // Set values for fixationAndOther.
        fixationAndOther.setFixationPointColor(colorPickerFixationPointColor.getValue());
        fixationAndOther.setFixationPointLuminance(Double.valueOf(textFieldFixationPointLuminance.getText()));
        fixationAndOther.setFixationPointSizeX(Double.valueOf(spinnerFixationPointSizeX.getValue().toString()));
        fixationAndOther.setFixationPointSizeY(Double.valueOf(spinnerFixationPointSizeY.getValue().toString()));
        fixationAndOther.setFixationMonitorTechnique(comboBoxFixationMonitorTechnique.getSelectionModel().getSelectedItem());
        fixationAndOther.setFixationPointLocationX(Double.valueOf(spinnerFixationPointLocationX.getValue().toString()));
        fixationAndOther.setFixationPointLocationY(Double.valueOf(spinnerFixationPointLocationY.getValue().toString()));
        fixationAndOther.setAnswerToStimulus(comboBoxAnswerToStimulus.getSelectionModel().getSelectedItem());
        fixationAndOther.setPauseProcedure(comboBoxPauseProcedure.getSelectionModel().getSelectedItem());
        fixationAndOther.setCancelProcedure(comboBoxCancelProcedure.getSelectionModel().getSelectedItem());
        fixationAndOther.setProcedure(comboBoxProcedure.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void setActionForColorPickerFixationPointColor() {
        setLookForPanePreview();
    }

    @FXML
    private void setActionForSpinnersFixationPointSize() {
        setLookForPanePreview();
    }

    @FXML
    private void measureFixationPointLuminance() throws IOException {

        setValuesForFixationAndOtherFields();

        Stage stage = new Stage();

        Parent root = FXMLLoader.load(FixationAndOtherController.class.getResource("FixationAndOtherMeasureLum.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Specvis");
        stage.getIcons().add(new Image("/org/specvis/graphics/SpecvisIcon.png"));
        stage.show();
    }

    @FXML
    private void openFixationMonitorSettings() throws IOException {

        switch (comboBoxFixationMonitorTechnique.getSelectionModel().getSelectedItem()) {
            case "Blindspot":

                FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();

                if (fixationAndOtherMonitorSettingsBlindspot == null) {
                    fixationAndOtherMonitorSettingsBlindspot = new FixationAndOtherMonitorSettingsBlindspot();
                    fixationAndOtherMonitorSettingsBlindspot.setDefaultValues();
                    StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsBlindspot(fixationAndOtherMonitorSettingsBlindspot);
                }

                setValuesForFixationAndOtherFields();
                StartApplication.setSceneFixationAndOtherMonitorSettingsBlindspot();
                break;
            case "Fixation point change":

                FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();

                if (fixationAndOtherMonitorSettingsFixPointChange == null) {
                    fixationAndOtherMonitorSettingsFixPointChange = new FixationAndOtherMonitorSettingsFixPointChange();
                    fixationAndOtherMonitorSettingsFixPointChange.setDefaultValues();
                    StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsFixPointChange(fixationAndOtherMonitorSettingsFixPointChange);
                }

                setValuesForFixationAndOtherFields();
                StartApplication.setSceneFixationAndOtherMonitorSettingsFixPointChange();
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Lack of key information.");
                alert.setContentText("You must choose fixation monitor technique first.");
                alert.showAndWait();
                break;
        }
    }

    @FXML
    private void setSceneStimulusAndBackground() throws IOException {
        setValuesForFixationAndOtherFields();
        StartApplication.setSceneStimulusAndBackground();
    }

    @FXML
    private void setSceneProcedure() throws IOException {

        switch (comboBoxFixationMonitorTechnique.getSelectionModel().getSelectedItem()) {
            case "Blindspot":

                FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();
                if (fixationAndOtherMonitorSettingsBlindspot == null) {
                    fixationAndOtherMonitorSettingsBlindspot = new FixationAndOtherMonitorSettingsBlindspot();
                    fixationAndOtherMonitorSettingsBlindspot.setDefaultValues();
                    StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsBlindspot(fixationAndOtherMonitorSettingsBlindspot);
                }

                setValuesForFixationAndOtherFields();
                break;
            case "Fixation point change":

                FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();

                if (fixationAndOtherMonitorSettingsFixPointChange == null) {
                    fixationAndOtherMonitorSettingsFixPointChange = new FixationAndOtherMonitorSettingsFixPointChange();
                    fixationAndOtherMonitorSettingsFixPointChange.setDefaultValues();
                    StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsFixPointChange(fixationAndOtherMonitorSettingsFixPointChange);
                }

                setValuesForFixationAndOtherFields();
                break;
            default:
                setValuesForFixationAndOtherFields();
                break;
        }

        setProcedureSettingsDependingOnProcedureChoice();
        StartApplication.setSceneProcedure();
    }

    private void setProcedureSettingsDependingOnProcedureChoice() {
        switch (comboBoxProcedure.getSelectionModel().getSelectedItem()) {
            case "Basic":
                BasicProcedureSettings basicProcedureSettings = StartApplication.getSpecvisData().getBasicProcedureSettings();
                if (basicProcedureSettings == null) {
                    basicProcedureSettings = new BasicProcedureSettings();
                    basicProcedureSettings.setDefaultValues();
                    StartApplication.getSpecvisData().setBasicProcedureSettings(basicProcedureSettings);
                }
                break;
        }
    }

    @FXML
    private void previewStimuliDistribution() {

        setValuesForFixationAndOtherFields();

        PreviewStimuliDistributionWindow previewStimuliDistributionWindow;

        switch (comboBoxFixationMonitorTechnique.getSelectionModel().getSelectedItem()) {

            case "Blindspot":

                // Show PreviewStimuliDistributionWindow with respect to Blindspot settings.
                FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();

                if (fixationAndOtherMonitorSettingsBlindspot == null) {
                    fixationAndOtherMonitorSettingsBlindspot = new FixationAndOtherMonitorSettingsBlindspot();
                    fixationAndOtherMonitorSettingsBlindspot.setDefaultValues();
                    StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsBlindspot(fixationAndOtherMonitorSettingsBlindspot);
                }

                previewStimuliDistributionWindow = new PreviewStimuliDistributionWindow(
                        "Blindspot",
                        Double.valueOf(spinnerFixationPointLocationX.getValue().toString()),
                        Double.valueOf(spinnerFixationPointLocationY.getValue().toString()),
                        fixationAndOtherMonitorSettingsBlindspot.getBlindspotDistanceFromFixPointX(),
                        fixationAndOtherMonitorSettingsBlindspot.getBlindspotDistanceFromFixPointY(),
                        fixationAndOtherMonitorSettingsBlindspot.isShowPatientMsgSelected(),
                        false
                );
                previewStimuliDistributionWindow.show();

                break;
            case "Fixation point change":

                // Show PreviewStimuliDistributionWindow with respect to Fixation point change settings.
                FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();

                if (fixationAndOtherMonitorSettingsFixPointChange == null) {
                    fixationAndOtherMonitorSettingsFixPointChange = new FixationAndOtherMonitorSettingsFixPointChange();
                    fixationAndOtherMonitorSettingsFixPointChange.setDefaultValues();
                    StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsFixPointChange(fixationAndOtherMonitorSettingsFixPointChange);
                }

                previewStimuliDistributionWindow = new PreviewStimuliDistributionWindow(
                        "Fixation point change",
                        Double.valueOf(spinnerFixationPointLocationX.getValue().toString()),
                        Double.valueOf(spinnerFixationPointLocationY.getValue().toString()),
                        0.0,
                        0.0,
                        fixationAndOtherMonitorSettingsFixPointChange.isShowPatientMsgSelected(),
                        false
                );
                previewStimuliDistributionWindow.show();

                break;
            default:

                // Show PreviewStimuliDistributionWindow with respect to None settings.
                previewStimuliDistributionWindow = new PreviewStimuliDistributionWindow(
                        "None",
                        Double.valueOf(spinnerFixationPointLocationX.getValue().toString()),
                        Double.valueOf(spinnerFixationPointLocationY.getValue().toString()),
                        0.0,
                        0.0,
                        false,
                        false
                );
                previewStimuliDistributionWindow.show();

                break;
        }
    }

    @FXML
    private void openProcedureSettings() throws IOException {
        switch (comboBoxProcedure.getSelectionModel().getSelectedItem()) {
            case "Basic":

                BasicProcedureSettings basicProcedureSettings = StartApplication.getSpecvisData().getBasicProcedureSettings();
                if (basicProcedureSettings == null) {
                    basicProcedureSettings = new BasicProcedureSettings();
                    basicProcedureSettings.setDefaultValues();
                    StartApplication.getSpecvisData().setBasicProcedureSettings(basicProcedureSettings);
                }

                StartApplication.setSceneBasicProcedureSettings();
                break;
        }
    }
}
