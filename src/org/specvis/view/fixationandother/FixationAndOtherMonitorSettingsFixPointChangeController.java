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

public class FixationAndOtherMonitorSettingsFixPointChangeController implements Initializable {

    private Functions functions;
    private ScreenAndLuminanceScale screenAndLuminanceScale;
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
    private Spinner<Double> spinnerChangedFixPointSizeX;

    @FXML
    private Spinner<Double> spinnerChangedFixPointSizeY;

    @FXML
    private ColorPicker colorPickerChangedFixPointColor;

    @FXML
    private TextField textFieldChangedFixPointLuminance;

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

        luminanceScaleForBackground = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getBackgroundLuminanceScale();

        setInitialValueForTextFieldChangedFixPointLuminance();
        setValuesForComboBoxFontWeight();

        initValuesForFields();

        setLookForPanePreview();
    }

    private void initValuesForFields() {
        FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();

        if (fixationAndOtherMonitorSettingsFixPointChange != null) {

            // 1.
            if (fixationAndOtherMonitorSettingsFixPointChange.isMonitorFixationEveryXStimuliSelected()) {
                radioButtonMonitorFixationEveryXStimuli.setSelected(true);
                radioButtonMonitorFixationEveryXToYStimuli.setSelected(false);
            } else {
                radioButtonMonitorFixationEveryXStimuli.setSelected(false);
                radioButtonMonitorFixationEveryXToYStimuli.setSelected(true);
            }
            spinnerMonitorFixationEveryXStimuli.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getMonitorFixationEveryXStimuli());
            spinnerMonitorFixationEveryXToYStimuli_1.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getMonitorFixationEveryXYStimuli_1());
            spinnerMonitorFixationEveryXToYStimuli_2.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getMonitorFixationEveryXYStimuli_2());
            spinnerChangedFixPointSizeX.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getChangedFixPointSizeX());
            spinnerChangedFixPointSizeY.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getChangedFixPointSizeY());
            colorPickerChangedFixPointColor.setValue(fixationAndOtherMonitorSettingsFixPointChange.getChangedFixPointColor());
            textFieldChangedFixPointLuminance.setText(String.valueOf(fixationAndOtherMonitorSettingsFixPointChange.getChangedFixPointLuminance()));

            // 2.
            checkBoxShowMessageForPatient.setSelected(fixationAndOtherMonitorSettingsFixPointChange.isShowPatientMsgSelected());
            textFieldTextOfTheMessage.setText(fixationAndOtherMonitorSettingsFixPointChange.getTextOfTheMsg());
            spinnerFontSize.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getFontSize());
            comboBoxFontWeight.getSelectionModel().select(fixationAndOtherMonitorSettingsFixPointChange.getFontWeight());
            colorPickerFontColor.setValue(fixationAndOtherMonitorSettingsFixPointChange.getFontColor());
            colorPickerMsgBackgroundColor.setValue(fixationAndOtherMonitorSettingsFixPointChange.getMsgBackgroundColor());
            spinnerMsgBackgroundSizeX.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getMsgBackgroundSizeX());
            spinnerMsgBackgroundSizeY.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getMsgBackgroundSizeY());
            spinnerMsgDistanceFromFixPointX.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getMsgDistanceFromFixPointX());
            spinnerMsgDistanceFromFixPointY.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getMsgDistanceFromFixPointY());
            spinnerResumeToNextStimulusTimeInterval.getValueFactory().setValue(fixationAndOtherMonitorSettingsFixPointChange.getResumeToNextStimulusTimeInterval());
        }
    }

    private void setInitialValueForTextFieldChangedFixPointLuminance() {
        textFieldChangedFixPointLuminance.setText("0.0");
    }

    public void updateValueForTextFieldChangedFixPointLuminance() {
        FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();
        textFieldChangedFixPointLuminance.setText(String.valueOf(fixationAndOtherMonitorSettingsFixPointChange.getChangedFixPointLuminance()));
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
            setValuesForFixationAndOtherMonitorSettingsFixPointChangeFields();
            StartApplication.setSceneFixationAndOther();
        } else {

            int value1 = Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_1.getValue().toString());
            int value2 = Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_2.getValue().toString());

            if (value2 > value1) {
                setValuesForFixationAndOtherMonitorSettingsFixPointChangeFields();
                StartApplication.setSceneFixationAndOther();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid value for monitoring fixation every X to Y stimuli.");
                alert.setContentText("Value X can not be higher than value Y.");
                alert.showAndWait();
            }
        }
    }

    private void setValuesForFixationAndOtherMonitorSettingsFixPointChangeFields() {
        FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();

        // 1.
        fixationAndOtherMonitorSettingsFixPointChange.setIsMonitorFixationEveryXStimuliSelected(radioButtonMonitorFixationEveryXStimuli.isSelected());
        fixationAndOtherMonitorSettingsFixPointChange.setMonitorFixationEveryXStimuli(Integer.valueOf(spinnerMonitorFixationEveryXStimuli.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setMonitorFixationEveryXYStimuli_1(Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_1.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setMonitorFixationEveryXYStimuli_2(Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_2.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setChangedFixPointSizeX(Double.valueOf(spinnerChangedFixPointSizeX.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setChangedFixPointSizeY(Double.valueOf(spinnerChangedFixPointSizeY.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setChangedFixPointColor(colorPickerChangedFixPointColor.getValue());
        fixationAndOtherMonitorSettingsFixPointChange.setChangedFixPointLuminance(Double.valueOf(textFieldChangedFixPointLuminance.getText()));

        // 2.
        fixationAndOtherMonitorSettingsFixPointChange.setIsShowPatientMsgSelected(checkBoxShowMessageForPatient.isSelected());
        fixationAndOtherMonitorSettingsFixPointChange.setTextOfTheMsg(textFieldTextOfTheMessage.getText());
        fixationAndOtherMonitorSettingsFixPointChange.setFontSize(Integer.valueOf(spinnerFontSize.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setFontWeight(comboBoxFontWeight.getSelectionModel().getSelectedItem());
        fixationAndOtherMonitorSettingsFixPointChange.setFontColor(colorPickerFontColor.getValue());
        fixationAndOtherMonitorSettingsFixPointChange.setMsgBackgroundColor(colorPickerMsgBackgroundColor.getValue());
        fixationAndOtherMonitorSettingsFixPointChange.setMsgBackgroundSizeX(Double.valueOf(spinnerMsgBackgroundSizeX.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setMsgBackgroundSizeY(Double.valueOf(spinnerMsgBackgroundSizeY.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setMsgDistanceFromFixPointX(Double.valueOf(spinnerMsgDistanceFromFixPointX.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setMsgDistanceFromFixPointY(Double.valueOf(spinnerMsgDistanceFromFixPointY.getValue().toString()));
        fixationAndOtherMonitorSettingsFixPointChange.setResumeToNextStimulusTimeInterval(Integer.valueOf(spinnerResumeToNextStimulusTimeInterval.getValue().toString()));

        StartApplication.getSpecvisData().setFixationAndOtherMonitorSettingsFixPointChange(fixationAndOtherMonitorSettingsFixPointChange);
    }

    @FXML
    private void measureFixationPointLuminance() throws IOException {

        setValuesForFixationAndOtherMonitorSettingsFixPointChangeFields();

        Stage stage = new Stage();

        Parent root = FXMLLoader.load(FixationAndOtherController.class.getResource("FixationAndOtherMeasureLumForChangedFixPoint.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Specvis");
        stage.getIcons().add(new Image("/org/specvis/graphics/SpecvisIcon.png"));
        stage.show();
    }

    @FXML
    private void setLookForPanePreview() {

        // 1. Set background color.
        int hueForBackground = (int) functions.round(luminanceScaleForBackground.getHue(), 0);
        int saturationForBackground = (int) functions.round(luminanceScaleForBackground.getSaturation(), 0);
        int brightnessForBackground = StartApplication.getSpecvisData().getStimulusAndBackground().getBackgroundBrightness();
        String colorForBackground = "-fx-background-color: hsb(" + hueForBackground + ", " + saturationForBackground + "%, " + brightnessForBackground + "%);";

        // 2. Create changed fix point.
        Shape changedFixPoint = createEllipseStimulus();

        // 3. Set panePreview.
        if (panePreview.getChildren().size() > 0) {
            panePreview.getChildren().remove(panePreview.getChildren().size() - 1);
        }
        panePreview.setStyle(colorForBackground);
        panePreview.getChildren().add(changedFixPoint);
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

        // 4. Get changed fix point size in degrees.
        double stimulusSizeInDegreesX = Double.valueOf(spinnerChangedFixPointSizeX.getValue().toString());
        double stimulusSizeInDegreesY = Double.valueOf(spinnerChangedFixPointSizeY.getValue().toString());

        // 5. Calculate changed fix point radius in pixels.
        double stimulusRadiusInPixelsX = (stimulusSizeInDegreesX / 2) * pixelsForOneDegreeX;
        double stimulusRadiusInPixelsY = (stimulusSizeInDegreesY / 2) * pixelsForOneDegreeY;

        // 6. Set color for changed fix point.
        Color color = colorPickerChangedFixPointColor.getValue();

        // 7. Create changed fix point preview shape.
        Ellipse ellipse;
        ellipse = new Ellipse(panePreview.getPrefWidth() / 2, panePreview.getPrefHeight() / 2, stimulusRadiusInPixelsX, stimulusRadiusInPixelsY);
        ellipse.setFill(color);
        ellipse.setStroke(color);

        return ellipse;
    }

    @FXML
    private void setOnActionColorPickerChangedFixPointColor() {
        setLookForPanePreview();
        textFieldChangedFixPointLuminance.setText("0.0");
    }

    @FXML
    private void previewStimuliDistribution() {

        setValuesForFixationAndOtherMonitorSettingsFixPointChangeFields();

        FixationAndOther fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();

        PreviewStimuliDistributionWindow previewStimuliDistributionWindow = new PreviewStimuliDistributionWindow(
                "Fixation point change",
                fixationAndOther.getFixationPointLocationX(),
                fixationAndOther.getFixationPointLocationY(),
                0.0,
                0.0,
                false,
                true
        );
        previewStimuliDistributionWindow.show();
    }
}
