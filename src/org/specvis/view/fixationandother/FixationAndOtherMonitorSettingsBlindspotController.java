package org.specvis.view.fixationandother;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import org.specvis.StartApplication;
import org.specvis.model.*;
import org.specvis.view.miscellaneous.PreviewStimuliDistributionWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Piotr Dzwiniel on 2016-02-29.
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

public class FixationAndOtherMonitorSettingsBlindspotController implements Initializable {

    private Functions functions;
    private ScreenAndLuminanceScale screenAndLuminanceScale;
    private LuminanceScale luminanceScaleForStimulus;
    private LuminanceScale luminanceScaleForBackground;

    // 1. Tab "Settings".
    @FXML
    private RadioButton radioButtonMonitorFixationEveryXStimuli;

    @FXML
    private RadioButton radioButtonMonitorFixationEveryXToYStimuli;

    @FXML
    private Spinner<Integer> spinnerMonitorFixationEveryXStimuli;

    @FXML
    private Spinner<Integer> spinnerMonitorFixationEveryXToYStimuli_1;

    @FXML
    private Spinner<Integer> spinnerMonitorFixationEveryXToYStimuli_2;

    @FXML
    private Pane panePreview;

    @FXML
    private Spinner<Double> spinnerFixationMonitorStimulusSizeX;

    @FXML
    private Spinner<Double> spinnerFixationMonitorStimulusSizeY;

    @FXML
    private Spinner<Integer> spinnerFixationMonitorStimulusBrightness;

    @FXML
    private TextField textFieldFixationMonitorStimulusLuminance;

    @FXML
    private Spinner<Double> spinnerBlindspotDistanceFromFixPointX;

    @FXML
    private Spinner<Double> spinnerBlindspotDistanceFromFixPointY;

    // 2. Tab "Message after loss of fixation".
    @FXML
    private CheckBox checkBoxShowMessageForPatient;

    @FXML
    private TextField textFieldTextOfTheMessage;

    @FXML
    private Spinner<Integer> spinnerFontSize;

    @FXML
    private ComboBox<String> comboBoxFontWeight;

    @FXML
    private ColorPicker colorPickerFontColor;

    @FXML
    private ColorPicker colorPickerMsgBackgroundColor;

    @FXML
    private Spinner<Double> spinnerMsgBackgroundSizeX;

    @FXML
    private Spinner<Double> spinnerMsgBackgroundSizeY;

    @FXML
    private Spinner<Double> spinnerMsgDistanceFromFixPointX;

    @FXML
    private Spinner<Double> spinnerMsgDistanceFromFixPointY;

    @FXML
    private Spinner<Integer> spinnerResumeToNextStimulusTimeInterval;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        functions = StartApplication.getFunctions();

        screenAndLuminanceScale = StartApplication.getSpecvisData().getScreenAndLuminanceScale();

        luminanceScaleForStimulus = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getStimulusLuminanceScale();
        luminanceScaleForBackground = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getBackgroundLuminanceScale();

        setValueForTextFieldFixationMonitorStimulusLuminance();
        setValuesForComboBoxFontWeight();

        initValuesForFields();

        setLookForPanePreview();
    }

    private void initValuesForFields() {
        FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();
        if (fixationAndOtherMonitorSettingsBlindspot != null) {

            // 1.
            if (fixationAndOtherMonitorSettingsBlindspot.isMonitorFixationEveryXStimuliSelected()) {
                radioButtonMonitorFixationEveryXStimuli.setSelected(true);
                radioButtonMonitorFixationEveryXToYStimuli.setSelected(false);
            } else {
                radioButtonMonitorFixationEveryXStimuli.setSelected(false);
                radioButtonMonitorFixationEveryXToYStimuli.setSelected(true);
            }
            spinnerMonitorFixationEveryXStimuli.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getMonitorFixationEveryXStimuli());
            spinnerMonitorFixationEveryXToYStimuli_1.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getMonitorFixationEveryXYStimuli_1());
            spinnerMonitorFixationEveryXToYStimuli_2.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getMonitorFixationEveryXYStimuli_2());
            spinnerFixationMonitorStimulusSizeX.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getFixationMonitorStimulusSizeX());
            spinnerFixationMonitorStimulusSizeY.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getFixationMonitorStimulusSizeY());
            spinnerFixationMonitorStimulusBrightness.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getFixationMonitorStimulusBrightness());
            spinnerBlindspotDistanceFromFixPointX.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getBlindspotDistanceFromFixPointX());
            spinnerBlindspotDistanceFromFixPointY.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getBlindspotDistanceFromFixPointY());

            // 2.
            checkBoxShowMessageForPatient.setSelected(fixationAndOtherMonitorSettingsBlindspot.isShowPatientMsgSelected());
            textFieldTextOfTheMessage.setText(fixationAndOtherMonitorSettingsBlindspot.getTextOfTheMsg());
            spinnerFontSize.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getFontSize());
            comboBoxFontWeight.getSelectionModel().select(fixationAndOtherMonitorSettingsBlindspot.getFontWeight());
            colorPickerFontColor.setValue(fixationAndOtherMonitorSettingsBlindspot.getFontColor());
            colorPickerMsgBackgroundColor.setValue(fixationAndOtherMonitorSettingsBlindspot.getMsgBackgroundColor());
            spinnerMsgBackgroundSizeX.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getMsgBackgroundSizeX());
            spinnerMsgBackgroundSizeY.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getMsgBackgroundSizeY());
            spinnerMsgDistanceFromFixPointX.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getMsgDistanceFromFixPointX());
            spinnerMsgDistanceFromFixPointY.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getMsgDistanceFromFixPointY());
            spinnerResumeToNextStimulusTimeInterval.getValueFactory().setValue(fixationAndOtherMonitorSettingsBlindspot.getResumeToNextStimulusTimeInterval());
        }
    }

    @FXML
    private void setValueForTextFieldFixationMonitorStimulusLuminance() {
        int brightness = Integer.valueOf(spinnerFixationMonitorStimulusBrightness.getValue().toString());
        double fittedLuminance = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getStimulusLuminanceScale().getFittedLuminanceForEachBrightnessValue()[brightness];
        double roundedFittedLuminance = functions.round(fittedLuminance, 2);

        if (roundedFittedLuminance < 0) {
            roundedFittedLuminance = 0;
        }

        textFieldFixationMonitorStimulusLuminance.setText(String.valueOf(roundedFittedLuminance));

        setLookForPanePreview();
    }

    private void setValuesForComboBoxFontWeight() {
        ObservableList<String> observableList = FXCollections.observableArrayList("normal", "bold", "bolder", "lighter");
        comboBoxFontWeight.setItems(observableList);
        comboBoxFontWeight.getSelectionModel().select(0);
    }

    @FXML
    private void setOnActionCancelButton() throws IOException {

        StartApplication.setSceneFixationAndOther();
    }

    @FXML
    private void setOnActionSaveButton() throws IOException {

        if (radioButtonMonitorFixationEveryXStimuli.isSelected()) {
            setValuesForFixationAndOtherMonitorSettingsBlindspotFields();
            StartApplication.setSceneFixationAndOther();
        } else {

            int value1 = Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_1.getValue().toString());
            int value2 = Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_2.getValue().toString());

            if (value2 > value1) {
                setValuesForFixationAndOtherMonitorSettingsBlindspotFields();
                StartApplication.setSceneFixationAndOther();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid values for monitoring fixation every X to Y stimuli.");
                alert.setContentText("Value X can not be higher than value Y.");
                alert.showAndWait();
            }
        }
    }

    private void setValuesForFixationAndOtherMonitorSettingsBlindspotFields() {
        FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();

        // 1.
        fixationAndOtherMonitorSettingsBlindspot.setIsMonitorFixationEveryXStimuliSelected(radioButtonMonitorFixationEveryXStimuli.isSelected());
        fixationAndOtherMonitorSettingsBlindspot.setMonitorFixationEveryXStimuli(Integer.valueOf(spinnerMonitorFixationEveryXStimuli.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setMonitorFixationEveryXYStimuli_1(Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_1.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setMonitorFixationEveryXYStimuli_2(Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_2.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setFixationMonitorStimulusSizeX(Double.valueOf(spinnerFixationMonitorStimulusSizeX.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setFixationMonitorStimulusSizeY(Double.valueOf(spinnerFixationMonitorStimulusSizeY.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setFixationMonitorStimulusBrightness(Integer.valueOf(spinnerFixationMonitorStimulusBrightness.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setFixationMonitorStimulusLuminance(Double.valueOf(textFieldFixationMonitorStimulusLuminance.getText()));
        fixationAndOtherMonitorSettingsBlindspot.setBlindspotDistanceFromFixPointX(Double.valueOf(spinnerBlindspotDistanceFromFixPointX.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setBlindspotDistanceFromFixPointY(Double.valueOf(spinnerBlindspotDistanceFromFixPointY.getValue().toString()));

        // 2.
        fixationAndOtherMonitorSettingsBlindspot.setIsShowPatientMsgSelected(checkBoxShowMessageForPatient.isSelected());
        fixationAndOtherMonitorSettingsBlindspot.setTextOfTheMsg(textFieldTextOfTheMessage.getText());
        fixationAndOtherMonitorSettingsBlindspot.setFontSize(Integer.valueOf(spinnerFontSize.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setFontWeight(comboBoxFontWeight.getSelectionModel().getSelectedItem());
        fixationAndOtherMonitorSettingsBlindspot.setFontColor(colorPickerFontColor.getValue());
        fixationAndOtherMonitorSettingsBlindspot.setMsgBackgroundColor(colorPickerMsgBackgroundColor.getValue());
        fixationAndOtherMonitorSettingsBlindspot.setMsgBackgroundSizeX(Double.valueOf(spinnerMsgBackgroundSizeX.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setMsgBackgroundSizeY(Double.valueOf(spinnerMsgBackgroundSizeY.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setMsgDistanceFromFixPointX(Double.valueOf(spinnerMsgDistanceFromFixPointX.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setMsgDistanceFromFixPointY(Double.valueOf(spinnerMsgDistanceFromFixPointY.getValue().toString()));
        fixationAndOtherMonitorSettingsBlindspot.setResumeToNextStimulusTimeInterval(Integer.valueOf(spinnerResumeToNextStimulusTimeInterval.getValue().toString()));

        StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsBlindspot(fixationAndOtherMonitorSettingsBlindspot);
    }

    @FXML
    private void setLookForPanePreview() {

        // 1. Set background color.
        int hueForBackground = (int) functions.round(luminanceScaleForBackground.getHue(), 0);
        int saturationForBackground = (int) functions.round(luminanceScaleForBackground.getSaturation(), 0);
        int brightnessForBackground = StartApplication.getSpecvisData().getStimulusAndBackground().getBackgroundBrightness();
        String colorForBackground = "-fx-background-color: hsb(" + hueForBackground + ", " + saturationForBackground + "%, " + brightnessForBackground + "%);";

        // 2. Create monitor stimulus.
        Shape monitorStimulus = createEllipseStimulus();

        // 3. Set panePreview.
        if (panePreview.getChildren().size() > 0) {
            panePreview.getChildren().remove(panePreview.getChildren().size() - 1);
        }
        panePreview.setStyle(colorForBackground);
        panePreview.getChildren().add(monitorStimulus);
    }

    private Ellipse createEllipseStimulus() {

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
        double stimulusSizeInDegreesX = Double.valueOf(spinnerFixationMonitorStimulusSizeX.getValue().toString());
        double stimulusSizeInDegreesY = Double.valueOf(spinnerFixationMonitorStimulusSizeY.getValue().toString());

        // 5. Calculate stimulus radius in pixels.
        double stimulusRadiusInPixelsX = (stimulusSizeInDegreesX / 2) * pixelsForOneDegreeX;
        double stimulusRadiusInPixelsY = (stimulusSizeInDegreesY / 2) * pixelsForOneDegreeY;

        // 6. Set color for stimulus.
        double hue = luminanceScaleForStimulus.getHue();
        double saturation = luminanceScaleForStimulus.getSaturation() / 100;
        double brightness = Double.valueOf(spinnerFixationMonitorStimulusBrightness.getValue().toString()) / 100;
        Color color = Color.hsb(hue, saturation, brightness);

        // 7. Create stimulus preview shape.
        Ellipse ellipse;
        ellipse = new Ellipse(panePreview.getPrefWidth() / 2, panePreview.getPrefHeight() / 2, stimulusRadiusInPixelsX, stimulusRadiusInPixelsY);
        ellipse.setFill(color);
        ellipse.setStroke(color);

        return ellipse;
    }

    @FXML
    private void previewStimuliDistribution() {

        setValuesForFixationAndOtherMonitorSettingsBlindspotFields();

        FixationAndOther fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();

        PreviewStimuliDistributionWindow previewStimuliDistributionWindow = new PreviewStimuliDistributionWindow(
                "Blindspot",
                fixationAndOther.getFixationPointLocationX(),
                fixationAndOther.getFixationPointLocationY(),
                Double.valueOf(spinnerBlindspotDistanceFromFixPointX.getValue().toString()),
                Double.valueOf(spinnerBlindspotDistanceFromFixPointY.getValue().toString()),
                false,
                true
        );
        previewStimuliDistributionWindow.show();
    }
}
