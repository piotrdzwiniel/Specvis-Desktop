package org.specvis.view.stimulusandbackground;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import org.specvis.StartApplication;
import org.specvis.model.*;
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

public class StimulusAndBackgroundController implements Initializable {

    private Functions functions;
    private ScreenAndLuminanceScale screenAndLuminanceScale;
    private LuminanceScale luminanceScaleForStimulus;
    private LuminanceScale luminanceScaleForBackground;

    @FXML
    private TextField textFieldMinDecibelRange;

    @FXML
    private TextField textFieldMaxDecibelRange;

    @FXML
    private Spinner<Integer> spinnerStimulusMaxBrightness;

    @FXML
    private TextField textFieldStimulusMaxLuminance;

    @FXML
    private Spinner<Integer> spinnerStimulusMinBrightness;

    @FXML
    private TextField textFieldStimulusMinLuminance;

    @FXML
    private ComboBox<String> comboBoxStimulusShape;

    @FXML
    private Spinner<Double> spinnerStimulusInclination;

    @FXML
    private Spinner<Double> spinnerStimulusSizeX;

    @FXML
    private Spinner<Double> spinnerStimulusSizeY;

    @FXML
    private Spinner<Integer> spinnerStimulusDisplayTime;

    @FXML
    private Spinner<Integer> spinnerIntervalConst;

    @FXML
    private Spinner<Integer> spinnerIntervalRand;

    @FXML
    private CheckBox checkBoxCorrectionForSphericity;

    @FXML
    private Spinner<Integer> spinnerBackgroundBrightness;

    @FXML
    private TextField textFieldBackgroundLuminance;

    @FXML
    private Spinner<Double> spinnerDistBetweenStimuliX;

    @FXML
    private Spinner<Double> spinnerDistBetweenStimuliY;

    @FXML
    private Pane panePreview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        functions = StartApplication.getFunctions();

        screenAndLuminanceScale = StartApplication.getSpecvisData().getScreenAndLuminanceScale();

        luminanceScaleForStimulus = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getStimulusLuminanceScale();
        luminanceScaleForBackground = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getBackgroundLuminanceScale();

        setItemsForComboBoxStimulusShape();

        initValuesForFields();

        setValueForTextFieldStimulusMaxLuminance();
        setValueForTextFieldStimulusMinLuminance();
        setValueForTextFieldBackgroundLuminance();

        calculateDecibelRange();

        setLookForPanePreview();
    }

    private void initValuesForFields() {
        StimulusAndBackground stimulusAndBackground = StartApplication.getSpecvisData().getStimulusAndBackground();
        if (stimulusAndBackground != null) {

            spinnerStimulusMaxBrightness.getValueFactory().setValue(stimulusAndBackground.getStimulusMaxBrightness());
            spinnerStimulusMinBrightness.getValueFactory().setValue(stimulusAndBackground.getStimulusMinBrightness());
            comboBoxStimulusShape.getSelectionModel().select(stimulusAndBackground.getStimulusShape());
            spinnerStimulusInclination.getValueFactory().setValue(stimulusAndBackground.getStimulusInclination());
            spinnerStimulusSizeX.getValueFactory().setValue(stimulusAndBackground.getStimulusSizeX());
            spinnerStimulusSizeY.getValueFactory().setValue(stimulusAndBackground.getStimulusSizeY());
            spinnerStimulusDisplayTime.getValueFactory().setValue(stimulusAndBackground.getStimulusDisplayTime());
            spinnerIntervalConst.getValueFactory().setValue(stimulusAndBackground.getConstantPartOfInterval());
            spinnerIntervalRand.getValueFactory().setValue(stimulusAndBackground.getRandomPartOfInterval());
            checkBoxCorrectionForSphericity.setSelected(stimulusAndBackground.isCorrectionForSphericityCheckBoxChecked());
            spinnerBackgroundBrightness.getValueFactory().setValue(stimulusAndBackground.getBackgroundBrightness());
            spinnerDistBetweenStimuliX.getValueFactory().setValue(stimulusAndBackground.getDistanceBetweenStimuliInDegreesX());
            spinnerDistBetweenStimuliY.getValueFactory().setValue(stimulusAndBackground.getDistanceBetweenStimuliInDegreesY());
        }
    }

    private void calculateDecibelRange() {

        boolean minStiLumEmpty = textFieldStimulusMinLuminance.getText().isEmpty();
        boolean maxStiLumEmpty = textFieldStimulusMaxLuminance.getText().isEmpty();
        boolean bgLumEmpty = textFieldBackgroundLuminance.getText().isEmpty();

        if (!minStiLumEmpty && !maxStiLumEmpty && !bgLumEmpty) {
            double stimulusMinLuminance = Double.valueOf(textFieldStimulusMinLuminance.getText());
            double stimulusMaxLuminance = Double.valueOf(textFieldStimulusMaxLuminance.getText());
            double backgroundLuminance = Double.valueOf(textFieldBackgroundLuminance.getText());

            try {
                double minDB = functions.decibelsValue(stimulusMaxLuminance, stimulusMaxLuminance, backgroundLuminance, 2);
                textFieldMinDecibelRange.setText(String.valueOf(minDB));
            } catch (NumberFormatException ex) {
                textFieldMinDecibelRange.setText("NaN");
            }

            try {
                double maxDB = functions.decibelsValue(stimulusMaxLuminance, stimulusMinLuminance, backgroundLuminance, 2);
                textFieldMaxDecibelRange.setText(String.valueOf(maxDB));
            } catch (NumberFormatException ex) {
                textFieldMaxDecibelRange.setText("NaN");
            }
        }
    }

    private void setItemsForComboBoxStimulusShape() {
        ObservableList<String> observableList = FXCollections.observableArrayList("Ellipse", "Polygon");
        comboBoxStimulusShape.setItems(observableList);
        comboBoxStimulusShape.getSelectionModel().select(0);
    }

    @FXML
    private void setActionForComboBoxStimulusShape() {
        setLookForPanePreview();
    }

    @FXML
    private void setValueForTextFieldStimulusMaxLuminance() {

        int maxStimulusBrightness = spinnerStimulusMaxBrightness.getValue();
        int minStimulusBrightness = spinnerStimulusMinBrightness.getValue();

        if (maxStimulusBrightness <= minStimulusBrightness) {
            spinnerStimulusMaxBrightness.getValueFactory().setValue(minStimulusBrightness + 1);
        }

        int brightness = Integer.valueOf(spinnerStimulusMaxBrightness.getValue().toString());
        double luminance = luminanceScaleForStimulus.getFittedLuminanceForEachBrightnessValue()[brightness];

        if (luminance < 0) {
            luminance = 0;
        }

        textFieldStimulusMaxLuminance.setText(String.valueOf(functions.round(luminance, 2)));

        calculateDecibelRange();

        setLookForPanePreview();
    }

    @FXML
    private void setValueForTextFieldStimulusMinLuminance() {

        int minStimulusBrightness = spinnerStimulusMinBrightness.getValue();
        int maxStimulusBrightness = spinnerStimulusMaxBrightness.getValue();
        //int backgroundBrightness = spinnerBackgroundBrightness.getValue();

        if (minStimulusBrightness >= maxStimulusBrightness) {
            spinnerStimulusMinBrightness.getValueFactory().setValue(maxStimulusBrightness - 1);
        }

//        if (minStimulusBrightness <= backgroundBrightness) {
//            spinnerStimulusMinBrightness.getValueFactory().setValue(backgroundBrightness + 1);
//        }

        int brightness = Integer.valueOf(spinnerStimulusMinBrightness.getValue().toString());
        double luminance = luminanceScaleForStimulus.getFittedLuminanceForEachBrightnessValue()[brightness];

        if (luminance < 0) {
            luminance = 0;
        }

        textFieldStimulusMinLuminance.setText(String.valueOf(functions.round(luminance, 2)));

        calculateDecibelRange();
    }

    @FXML
    private void setActionForSpinnerStimulusInclination() {
        if (comboBoxStimulusShape.getSelectionModel().getSelectedItem().equals("Polygon")) {
            setLookForPanePreview();
        }
    }

    private double calculateDistanceBetweenStimuliInDegrees(int screenResolution, double involvedVisualField, int quarterGridResolution) {
        double pixelsForOneDegree = (double) screenResolution / involvedVisualField;
        return functions.round(((double) screenResolution / ((double) quarterGridResolution) / 2) / pixelsForOneDegree, 2);
    }

    @FXML
    private void setValueForTextFieldBackgroundLuminance() {

//        int minStimulusBrightness = spinnerStimulusMinBrightness.getValue();
//        int backgroundBrightness = spinnerBackgroundBrightness.getValue();
//
//        if (backgroundBrightness >= minStimulusBrightness) {
//            spinnerBackgroundBrightness.getValueFactory().setValue(minStimulusBrightness - 1);
//        }

        int brightness = Integer.valueOf(spinnerBackgroundBrightness.getValue().toString());
        double luminance = luminanceScaleForBackground.getFittedLuminanceForEachBrightnessValue()[brightness];

        if (luminance < 0) {
            luminance = 0;
        }

        textFieldBackgroundLuminance.setText(String.valueOf(functions.round(luminance, 2)));

        calculateDecibelRange();

        setLookForPanePreview();
    }

    @FXML
    private void setLookForPanePreview() {

        // 1. Set background color.
        int hueForBackground = (int) functions.round(luminanceScaleForBackground.getHue(), 0);
        int saturationForBackground = (int) functions.round(luminanceScaleForBackground.getSaturation(), 0);
        int brightnessForBackground = Integer.valueOf(spinnerBackgroundBrightness.getValue().toString());
        String colorForBackground = "-fx-background-color: hsb(" + hueForBackground + ", " + saturationForBackground + "%, " + brightnessForBackground + "%);";

        // 2. Create stimulus.
        Shape stimulus;
        if (comboBoxStimulusShape.getSelectionModel().getSelectedItem().equals("Ellipse")) {
            stimulus = createEllipseStimulus();
        } else {
            stimulus = createPolygonStimulus();
        }

        // 3. Set panePreview.
        if (panePreview.getChildren().size() > 0) {
            panePreview.getChildren().remove(panePreview.getChildren().size() - 1);
        }
        panePreview.setStyle(colorForBackground);
        panePreview.getChildren().add(stimulus);
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
        double stimulusSizeInDegreesX = Double.valueOf(spinnerStimulusSizeX.getValue().toString());
        double stimulusSizeInDegreesY = Double.valueOf(spinnerStimulusSizeY.getValue().toString());

        // 5. Calculate stimulus radius in pixels.
        double stimulusRadiusInPixelsX = (stimulusSizeInDegreesX / 2) * pixelsForOneDegreeX;
        double stimulusRadiusInPixelsY = (stimulusSizeInDegreesY / 2) * pixelsForOneDegreeY;

        // 6. Set color for stimulus.
        double hue = luminanceScaleForStimulus.getHue();
        double saturation = luminanceScaleForStimulus.getSaturation() / 100;
        double brightness = Double.valueOf(spinnerStimulusMaxBrightness.getValue().toString()) / 100;
        Color color = Color.hsb(hue, saturation, brightness);

        // 7. Create stimulus preview shape.
        Ellipse ellipse;
        ellipse = new Ellipse(panePreview.getPrefWidth() / 2, panePreview.getPrefHeight() / 2, stimulusRadiusInPixelsX, stimulusRadiusInPixelsY);
        ellipse.setFill(color);
        ellipse.setStroke(color);

        return ellipse;
    }

    private Polygon createPolygonStimulus() {

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
        double stimulusSizeInDegreesX = Double.valueOf(spinnerStimulusSizeX.getValue().toString());
        double stimulusSizeInDegreesY = Double.valueOf(spinnerStimulusSizeY.getValue().toString());

        // 5. Calculate stimulus radius in pixels.
        double stimulusSizeInPixelsX = stimulusSizeInDegreesX * pixelsForOneDegreeX;
        double stimulusSizeInPixelsY = stimulusSizeInDegreesY * pixelsForOneDegreeY;

        // 6. Get center of the panePreview.
        double centerX = panePreview.getPrefWidth() / 2;
        double centerY = panePreview.getPrefHeight() / 2;

        // 7. Calculate stimulus diagonal.
        double diagonal = Math.sqrt(Math.pow(stimulusSizeInPixelsX, 2) + Math.pow(stimulusSizeInPixelsY, 2));

        // 8. Calculate polygon inner angle and positions of ABCD points in reference to stimulus inclination.
        double inclination = Double.valueOf(spinnerStimulusInclination.getValue().toString());

        double x = centerX + stimulusSizeInPixelsX;
        double y = centerY - stimulusSizeInPixelsY;

        double polygonInnerAngle = Math.toDegrees(Math.atan2(x - centerX, centerY - y));

        double positionAx = centerX + ((diagonal / 2) * Math.cos(Math.toRadians(inclination + 270 + (90 - polygonInnerAngle) - 90)));
        double positionAy = centerY + ((diagonal / 2) * Math.sin(Math.toRadians(inclination + 270 + (90 - polygonInnerAngle) - 90)));

        double positionBx = centerX + ((diagonal / 2) * Math.cos(Math.toRadians(inclination + polygonInnerAngle - 90)));
        double positionBy = centerY + ((diagonal / 2) * Math.sin(Math.toRadians(inclination + polygonInnerAngle - 90)));

        double positionCx = centerX + ((diagonal / 2) * Math.cos(Math.toRadians(inclination + 90 + (90 - polygonInnerAngle) - 90)));
        double positionCy = centerY + ((diagonal / 2) * Math.sin(Math.toRadians(inclination + 90 + (90 - polygonInnerAngle) - 90)));

        double positionDx = centerX + ((diagonal / 2) * Math.cos(Math.toRadians(inclination + 180 + polygonInnerAngle - 90)));
        double positionDy = centerY + ((diagonal / 2) * Math.sin(Math.toRadians(inclination + 180 + polygonInnerAngle - 90)));

        // 9. Set color for stimulus.
        double hue = luminanceScaleForStimulus.getHue();
        double saturation = luminanceScaleForStimulus.getSaturation() / 100;
        double brightness = Double.valueOf(spinnerStimulusMaxBrightness.getValue().toString()) / 100;
        Color color = Color.hsb(hue, saturation, brightness);

        // 10. Create stimulus preview shape.
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(positionAx, positionAy, positionBx, positionBy, positionCx, positionCy, positionDx, positionDy);
        polygon.setFill(color);

        return polygon;
    }

    private void setValuesForStimulusAndBackgroundeFields() {
        StimulusAndBackground stimulusAndBackground = StartApplication.getSpecvisData().getStimulusAndBackground();
        if (stimulusAndBackground == null) {
            stimulusAndBackground = new StimulusAndBackground();
            StartApplication.getSpecvisData().setStimulusAndBackground(stimulusAndBackground);
        }

        // Set values for stimulusAndBackground.
        stimulusAndBackground.setStimulusMaxBrightness(Integer.valueOf(spinnerStimulusMaxBrightness.getValue().toString()));
        stimulusAndBackground.setStimulusMaxLuminance(Double.valueOf(textFieldStimulusMaxLuminance.getText()));
        stimulusAndBackground.setStimulusMinBrightness(Integer.valueOf(spinnerStimulusMinBrightness.getValue().toString()));
        stimulusAndBackground.setStimulusMinLuminance(Double.valueOf(textFieldStimulusMinLuminance.getText()));
        stimulusAndBackground.setStimulusShape(comboBoxStimulusShape.getSelectionModel().getSelectedItem());
        stimulusAndBackground.setStimulusInclination(Double.valueOf(spinnerStimulusInclination.getValue().toString()));
        stimulusAndBackground.setStimulusSizeX(Double.valueOf(spinnerStimulusSizeX.getValue().toString()));
        stimulusAndBackground.setStimulusSizeY(Double.valueOf(spinnerStimulusSizeY.getValue().toString()));
        stimulusAndBackground.setStimulusDisplayTime(Integer.valueOf(spinnerStimulusDisplayTime.getValue().toString()));
        stimulusAndBackground.setConstantPartOfInterval(Integer.valueOf(spinnerIntervalConst.getValue().toString()));
        stimulusAndBackground.setRandomPartOfInterval(Integer.valueOf(spinnerIntervalRand.getValue().toString()));
        stimulusAndBackground.setIsCorrectionForSphericityCheckBoxChecked(checkBoxCorrectionForSphericity.isSelected());
        stimulusAndBackground.setBackgroundBrightness(Integer.valueOf(spinnerBackgroundBrightness.getValue().toString()));
        stimulusAndBackground.setBackgroundLuminance(Double.valueOf(textFieldBackgroundLuminance.getText()));
        stimulusAndBackground.setDistanceBetweenStimuliInDegreesX(Double.valueOf(spinnerDistBetweenStimuliX.getValue().toString()));
        stimulusAndBackground.setDistanceBetweenStimuliInDegreesY(Double.valueOf(spinnerDistBetweenStimuliY.getValue().toString()));
    }

    @FXML
    private void previewStimuliDistribution() {

        setValuesForStimulusAndBackgroundeFields();
        FixationAndOther fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();

        if (fixationAndOther != null) {

            PreviewStimuliDistributionWindow previewStimuliDistributionWindow;

            switch (fixationAndOther.getFixationMonitorTechnique()) {

                case "Blindspot":

                    // Show PreviewStimuliDistributionWindow with respect to Blindspot settings.
                    FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();

                    if (fixationAndOtherMonitorSettingsBlindspot == null) {
                        fixationAndOtherMonitorSettingsBlindspot = new FixationAndOtherMonitorSettingsBlindspot();
                        fixationAndOtherMonitorSettingsBlindspot.setDefaultValues();
                    }

                    previewStimuliDistributionWindow = new PreviewStimuliDistributionWindow(
                            "Blindspot",
                            fixationAndOther.getFixationPointLocationX(),
                            fixationAndOther.getFixationPointLocationY(),
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
                    }

                    previewStimuliDistributionWindow = new PreviewStimuliDistributionWindow(
                            "Fixation point change",
                            fixationAndOther.getFixationPointLocationX(),
                            fixationAndOther.getFixationPointLocationY(),
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
                            fixationAndOther.getFixationPointLocationX(),
                            fixationAndOther.getFixationPointLocationY(),
                            0.0,
                            0.0,
                            false,
                            false
                    );
                    previewStimuliDistributionWindow.show();

                    break;
            }
        } else {
            PreviewStimuliDistributionWindow previewStimuliDistributionWindow = new PreviewStimuliDistributionWindow(
                    "None",
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    false,
                    false
            );
            previewStimuliDistributionWindow.show();
        }
    }

    @FXML
    private void setSceneScreenAndLumScale() throws IOException {

        int value1 = Integer.valueOf(spinnerStimulusMinBrightness.getValue().toString());
        int value2 = Integer.valueOf(spinnerStimulusMaxBrightness.getValue().toString());

        if (value2 > value1) {
            if (!textFieldMinDecibelRange.getText().equals("NaN") && !textFieldMaxDecibelRange.getText().equals("NaN")) {
                setValuesForStimulusAndBackgroundeFields();
                StartApplication.setSceneScreenAndLumScale();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid decibel range.");
                alert.setContentText("Values of the decibel range can not be NaN. Try to change min and max stimulus " +
                        "brightness values so the min and max stimulus luminance values are different than 0.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid values for min and max brightness of the stimulus.");
            alert.setContentText("Min brightness can not be higher than max brightness.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setSceneFixationAndOther() throws IOException {

        int value1 = Integer.valueOf(spinnerStimulusMinBrightness.getValue().toString());
        int value2 = Integer.valueOf(spinnerStimulusMaxBrightness.getValue().toString());

        if (value2 > value1) {
            if (!textFieldMinDecibelRange.getText().equals("NaN") && !textFieldMaxDecibelRange.getText().equals("NaN")) {
                setValuesForStimulusAndBackgroundeFields();
                StartApplication.setSceneFixationAndOther();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid decibel range.");
                alert.setContentText("Values of the decibel range can not be NaN. Try to change min and max stimulus " +
                        "brightness values so the min and max stimulus luminance values are different than 0.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid values for min and max brightness of the stimulus.");
            alert.setContentText("Min brightness can not be higher than max brightness.");
            alert.showAndWait();
        }
    }
}
