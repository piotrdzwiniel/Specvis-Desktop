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
import org.specvis.datastructures.luminancescale.LuminanceScale;
import org.specvis.datastructures.settings.*;
import org.specvis.logic.Functions;
import org.specvis.view.miscellaneous.ViewStimuliDistribution;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class ViewFixationAndOtherController implements Initializable {

    private Functions functions;
    private UISettingsScreenAndLuminanceScale uiSettingsScreenAndLuminanceScale;
    private LuminanceScale luminanceScaleForBackground;
    private UISettingsStimulusAndBackground uiSettingsStimulusAndBackground;

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

        uiSettingsScreenAndLuminanceScale = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale();

        luminanceScaleForBackground = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().getBackgroundLuminanceScale();

        uiSettingsStimulusAndBackground = StartApplication.getSpecvisData().getUiSettingsStimulusAndBackground();

        setInitialValueForTextFieldFixationPointLuminance();
        setItemsForComboBoxFixationMonitorTechnique();
        setItemsForComboBoxesWithKeys();
        setItemsForComboBoxProcedure();

        initValuesForFields();

        setLookForPanePreview();
    }

    private void initValuesForFields() {
        UISettingsFixationAndOther uiSettingsFixationAndOther = StartApplication.getSpecvisData().getUiSettingsFixationAndOther();
        if (uiSettingsFixationAndOther != null) {

            colorPickerFixationPointColor.setValue(uiSettingsFixationAndOther.getFixationPointColor());
            textFieldFixationPointLuminance.setText(String.valueOf(uiSettingsFixationAndOther.getFixationPointLuminance()));
            spinnerFixationPointSizeX.getValueFactory().setValue(uiSettingsFixationAndOther.getFixationPointSizeX());
            spinnerFixationPointSizeY.getValueFactory().setValue(uiSettingsFixationAndOther.getFixationPointSizeY());
            if (StartApplication.getSpecvisData().getPatient().getTestedEye().equals("Both") && uiSettingsFixationAndOther.getFixationMonitorTechnique().equals("Blindspot")) {
                comboBoxFixationMonitorTechnique.getSelectionModel().select(0);
            } else {
                comboBoxFixationMonitorTechnique.getSelectionModel().select(uiSettingsFixationAndOther.getFixationMonitorTechnique());
            }
            spinnerFixationPointLocationX.getValueFactory().setValue(uiSettingsFixationAndOther.getFixationPointLocationX());
            spinnerFixationPointLocationY.getValueFactory().setValue(uiSettingsFixationAndOther.getFixationPointLocationY());
            comboBoxAnswerToStimulus.getSelectionModel().select(uiSettingsFixationAndOther.getAnswerToStimulus());
            comboBoxPauseProcedure.getSelectionModel().select(uiSettingsFixationAndOther.getPauseProcedure());
            comboBoxCancelProcedure.getSelectionModel().select(uiSettingsFixationAndOther.getCancelProcedure());
            comboBoxProcedure.getSelectionModel().select(uiSettingsFixationAndOther.getProcedure());
        }
    }

    private void setInitialValueForTextFieldFixationPointLuminance() {
        textFieldFixationPointLuminance.setText("0.0");
    }

    public void updateValueForTextFieldFixationPointLuminance() {
        UISettingsFixationAndOther uiSettingsFixationAndOther = StartApplication.getSpecvisData().getUiSettingsFixationAndOther();
        textFieldFixationPointLuminance.setText(String.valueOf(uiSettingsFixationAndOther.getFixationPointLuminance()));
    }

    private void setItemsForComboBoxFixationMonitorTechnique() {
        ObservableList<String> observableList = FXCollections.observableArrayList("None", "Blindspot", "Fixation point change", "Both");
        comboBoxFixationMonitorTechnique.setItems(observableList);
        comboBoxFixationMonitorTechnique.getSelectionModel().select(0);

        // If both eyes are tested at the same time, only "Fixation point change" technique
        // will be available.
        if (StartApplication.getSpecvisData().getPatient().getTestedEye().equals("Both")) {
            comboBoxFixationMonitorTechnique.getItems().remove(1);
            comboBoxFixationMonitorTechnique.getItems().remove(2);
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
        int brightnessForBackground = uiSettingsStimulusAndBackground.getBackgroundBrightness();
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
        double screenResolutionX = uiSettingsScreenAndLuminanceScale.getScreenResolutionX();
        double screenResolutionY = uiSettingsScreenAndLuminanceScale.getScreenResolutionY();

        // 2. Get involved visual field.
        double involvedVisualFieldX = uiSettingsScreenAndLuminanceScale.getInvolvedVisualFieldX();
        double involvedVisualFieldY = uiSettingsScreenAndLuminanceScale.getInvolvedVisualFieldY();

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
        UISettingsFixationAndOther uiSettingsFixationAndOther = StartApplication.getSpecvisData().getUiSettingsFixationAndOther();
        if (uiSettingsFixationAndOther == null) {
            uiSettingsFixationAndOther = new UISettingsFixationAndOther();
            StartApplication.getSpecvisData().setUiSettingsFixationAndOther(uiSettingsFixationAndOther);
        }

        // Set values for uiSettingsFixationAndOther.
        uiSettingsFixationAndOther.setFixationPointColor(colorPickerFixationPointColor.getValue());
        uiSettingsFixationAndOther.setFixationPointLuminance(Double.valueOf(textFieldFixationPointLuminance.getText()));
        uiSettingsFixationAndOther.setFixationPointSizeX(Double.valueOf(spinnerFixationPointSizeX.getValue().toString()));
        uiSettingsFixationAndOther.setFixationPointSizeY(Double.valueOf(spinnerFixationPointSizeY.getValue().toString()));
        uiSettingsFixationAndOther.setFixationMonitorTechnique(comboBoxFixationMonitorTechnique.getSelectionModel().getSelectedItem());
        uiSettingsFixationAndOther.setFixationPointLocationX(Double.valueOf(spinnerFixationPointLocationX.getValue().toString()));
        uiSettingsFixationAndOther.setFixationPointLocationY(Double.valueOf(spinnerFixationPointLocationY.getValue().toString()));
        uiSettingsFixationAndOther.setAnswerToStimulus(comboBoxAnswerToStimulus.getSelectionModel().getSelectedItem());
        uiSettingsFixationAndOther.setPauseProcedure(comboBoxPauseProcedure.getSelectionModel().getSelectedItem());
        uiSettingsFixationAndOther.setCancelProcedure(comboBoxCancelProcedure.getSelectionModel().getSelectedItem());
        uiSettingsFixationAndOther.setProcedure(comboBoxProcedure.getSelectionModel().getSelectedItem());
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

        Parent root = FXMLLoader.load(ViewFixationAndOtherController.class.getResource("ViewFixPointLuminance.fxml"));
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

                UISettingsFixMonitorBlindspot uiSettingsFixMonitorBlindspot = StartApplication.getSpecvisData().getUiSettingsFixMonitorBlindspot();

                if (uiSettingsFixMonitorBlindspot == null) {
                    uiSettingsFixMonitorBlindspot = new UISettingsFixMonitorBlindspot();
                    uiSettingsFixMonitorBlindspot.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsFixMonitorBlindspot(uiSettingsFixMonitorBlindspot);
                }

                setValuesForFixationAndOtherFields();
                StartApplication.setSceneFixationAndOtherMonitorSettingsBlindspot();
                break;
            case "Fixation point change":

                UISettingsFixMonitorFixPointChange uiSettingsProcedureBasicFixPointChange = StartApplication.getSpecvisData().getUiSettingsProcedureBasicFixPointChange();

                if (uiSettingsProcedureBasicFixPointChange == null) {
                    uiSettingsProcedureBasicFixPointChange = new UISettingsFixMonitorFixPointChange();
                    uiSettingsProcedureBasicFixPointChange.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsProcedureBasicFixPointChange(uiSettingsProcedureBasicFixPointChange);
                }

                setValuesForFixationAndOtherFields();
                StartApplication.setSceneFixationAndOtherMonitorSettingsFixPointChange();
                break;
            case "Both":

                UISettingsFixMonitorBoth uiSettingsFixMonitorBoth = StartApplication.getSpecvisData().getUiSettingsFixMonitorBoth();

                if (uiSettingsFixMonitorBoth == null) {
                    uiSettingsFixMonitorBoth = new UISettingsFixMonitorBoth();
                    uiSettingsFixMonitorBoth.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsFixMonitorBoth(uiSettingsFixMonitorBoth);
                }

                setValuesForFixationAndOtherFields();
                StartApplication.setSceneFixationAndOtherMonitorSettingsBoth();
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

                UISettingsFixMonitorBlindspot uiSettingsFixMonitorBlindspot = StartApplication.getSpecvisData().getUiSettingsFixMonitorBlindspot();
                if (uiSettingsFixMonitorBlindspot == null) {
                    uiSettingsFixMonitorBlindspot = new UISettingsFixMonitorBlindspot();
                    uiSettingsFixMonitorBlindspot.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsFixMonitorBlindspot(uiSettingsFixMonitorBlindspot);
                }

                setValuesForFixationAndOtherFields();
                break;
            case "Fixation point change":

                UISettingsFixMonitorFixPointChange uiSettingsProcedureBasicFixPointChange = StartApplication.getSpecvisData().getUiSettingsProcedureBasicFixPointChange();

                if (uiSettingsProcedureBasicFixPointChange == null) {
                    uiSettingsProcedureBasicFixPointChange = new UISettingsFixMonitorFixPointChange();
                    uiSettingsProcedureBasicFixPointChange.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsProcedureBasicFixPointChange(uiSettingsProcedureBasicFixPointChange);
                }

                setValuesForFixationAndOtherFields();
                break;
            case "Both":

                UISettingsFixMonitorBoth uiSettingsFixMonitorBoth = StartApplication.getSpecvisData().getUiSettingsFixMonitorBoth();

                if (uiSettingsFixMonitorBoth == null) {
                    uiSettingsFixMonitorBoth = new UISettingsFixMonitorBoth();
                    uiSettingsFixMonitorBoth.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsFixMonitorBoth(uiSettingsFixMonitorBoth);
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
                UISettingsProcedureBasic uiSettingsProcedureBasic = StartApplication.getSpecvisData().getUiSettingsProcedureBasic();
                if (uiSettingsProcedureBasic == null) {
                    uiSettingsProcedureBasic = new UISettingsProcedureBasic();
                    uiSettingsProcedureBasic.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsProcedureBasic(uiSettingsProcedureBasic);
                }
                break;
        }
    }

    @FXML
    private void previewStimuliDistribution() {

        setValuesForFixationAndOtherFields();

        ViewStimuliDistribution viewStimuliDistribution;

        switch (comboBoxFixationMonitorTechnique.getSelectionModel().getSelectedItem()) {

            case "Blindspot":

                // Show ViewStimuliDistribution with respect to Blindspot settings.
                UISettingsFixMonitorBlindspot uiSettingsFixMonitorBlindspot = StartApplication.getSpecvisData().getUiSettingsFixMonitorBlindspot();

                if (uiSettingsFixMonitorBlindspot == null) {
                    uiSettingsFixMonitorBlindspot = new UISettingsFixMonitorBlindspot();
                    uiSettingsFixMonitorBlindspot.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsFixMonitorBlindspot(uiSettingsFixMonitorBlindspot);
                }

                viewStimuliDistribution = new ViewStimuliDistribution(
                        "Blindspot",
                        Double.valueOf(spinnerFixationPointLocationX.getValue().toString()),
                        Double.valueOf(spinnerFixationPointLocationY.getValue().toString()),
                        uiSettingsFixMonitorBlindspot.getBlindspotDistanceFromFixPointX(),
                        uiSettingsFixMonitorBlindspot.getBlindspotDistanceFromFixPointY(),
                        uiSettingsFixMonitorBlindspot.isShowPatientMsgSelected(),
                        false
                );
                viewStimuliDistribution.show();

                break;
            case "Fixation point change":

                // Show ViewStimuliDistribution with respect to Fixation point change settings.
                UISettingsFixMonitorFixPointChange uiSettingsProcedureBasicFixPointChange = StartApplication.getSpecvisData().getUiSettingsProcedureBasicFixPointChange();

                if (uiSettingsProcedureBasicFixPointChange == null) {
                    uiSettingsProcedureBasicFixPointChange = new UISettingsFixMonitorFixPointChange();
                    uiSettingsProcedureBasicFixPointChange.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsProcedureBasicFixPointChange(uiSettingsProcedureBasicFixPointChange);
                }

                viewStimuliDistribution = new ViewStimuliDistribution(
                        "Fixation point change",
                        Double.valueOf(spinnerFixationPointLocationX.getValue().toString()),
                        Double.valueOf(spinnerFixationPointLocationY.getValue().toString()),
                        0.0,
                        0.0,
                        uiSettingsProcedureBasicFixPointChange.isShowPatientMsgSelected(),
                        false
                );
                viewStimuliDistribution.show();

                break;
            case "Both":

                UISettingsFixMonitorBoth uiSettings = StartApplication.getSpecvisData().getUiSettingsFixMonitorBoth();

                if (uiSettings == null) {
                    uiSettings = new UISettingsFixMonitorBoth();
                    uiSettings.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsFixMonitorBoth(uiSettings);
                }

                viewStimuliDistribution = new ViewStimuliDistribution(
                        "Both",
                        Double.valueOf(spinnerFixationPointLocationX.getValue().toString()),
                        Double.valueOf(spinnerFixationPointLocationY.getValue().toString()),
                        uiSettings.getFixMonitorStimulusDistanceFromFixPointInDgX(),
                        uiSettings.getFixMonitorStimulusDistanceFromFixPointInDgY(),
                        uiSettings.isShowPatientMsgSelected(),
                        false
                );
                viewStimuliDistribution.show();

                break;
            default:

                // Show ViewStimuliDistribution with respect to None settings.
                viewStimuliDistribution = new ViewStimuliDistribution(
                        "None",
                        Double.valueOf(spinnerFixationPointLocationX.getValue().toString()),
                        Double.valueOf(spinnerFixationPointLocationY.getValue().toString()),
                        0.0,
                        0.0,
                        false,
                        false
                );
                viewStimuliDistribution.show();

                break;
        }
    }

    @FXML
    private void openProcedureSettings() throws IOException {
        switch (comboBoxProcedure.getSelectionModel().getSelectedItem()) {
            case "Basic":

                UISettingsProcedureBasic uiSettingsProcedureBasic = StartApplication.getSpecvisData().getUiSettingsProcedureBasic();
                if (uiSettingsProcedureBasic == null) {
                    uiSettingsProcedureBasic = new UISettingsProcedureBasic();
                    uiSettingsProcedureBasic.setDefaultValues();
                    StartApplication.getSpecvisData().setUiSettingsProcedureBasic(uiSettingsProcedureBasic);
                }

                StartApplication.setSceneBasicProcedureSettings();
                break;
        }
    }
}
