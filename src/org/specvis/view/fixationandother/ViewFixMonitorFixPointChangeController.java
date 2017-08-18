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
import org.specvis.datastructures.settings.UISettingsFixMonitorFixPointChange;
import org.specvis.datastructures.settings.UISettingsFixationAndOther;
import org.specvis.datastructures.settings.UISettingsScreenAndLuminanceScale;
import org.specvis.logic.Functions;
import org.specvis.view.miscellaneous.ViewStimuliDistribution;

import java.io.IOException;
import java.net.URL;
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

public class ViewFixMonitorFixPointChangeController implements Initializable {

    private Functions functions;
    private UISettingsScreenAndLuminanceScale uiSettingsScreenAndLuminanceScale;
    private LuminanceScale luminanceScaleForBackground;

    // 1. Tab "ProcedureBasicSettingsGeneral".
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
    private Spinner<Integer> spinnerResumeProcedureAutomaticallyAfterXMsAfterMessage;

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

        uiSettingsScreenAndLuminanceScale = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale();

        luminanceScaleForBackground = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().getBackgroundLuminanceScale();

        setInitialValueForTextFieldChangedFixPointLuminance();
        setValuesForComboBoxFontWeight();

        initValuesForFields();

        setLookForPanePreview();
    }

    private void initValuesForFields() {
        UISettingsFixMonitorFixPointChange uiSettingsProcedureBasicFixPointChange = StartApplication.getSpecvisData().getUiSettingsProcedureBasicFixPointChange();

        if (uiSettingsProcedureBasicFixPointChange != null) {

            // 1.
            if (uiSettingsProcedureBasicFixPointChange.isMonitorFixationEveryXStimuliSelected()) {
                radioButtonMonitorFixationEveryXStimuli.setSelected(true);
                radioButtonMonitorFixationEveryXToYStimuli.setSelected(false);
            } else {
                radioButtonMonitorFixationEveryXStimuli.setSelected(false);
                radioButtonMonitorFixationEveryXToYStimuli.setSelected(true);
            }
            spinnerMonitorFixationEveryXStimuli.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getMonitorFixationEveryXStimuli());
            spinnerMonitorFixationEveryXToYStimuli_1.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getMonitorFixationEveryXYStimuli_1());
            spinnerMonitorFixationEveryXToYStimuli_2.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getMonitorFixationEveryXYStimuli_2());
            spinnerChangedFixPointSizeX.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getChangedFixPointSizeX());
            spinnerChangedFixPointSizeY.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getChangedFixPointSizeY());
            colorPickerChangedFixPointColor.setValue(uiSettingsProcedureBasicFixPointChange.getChangedFixPointColor());
            textFieldChangedFixPointLuminance.setText(String.valueOf(uiSettingsProcedureBasicFixPointChange.getChangedFixPointLuminance()));

            // 2.
            checkBoxShowMessageForPatient.setSelected(uiSettingsProcedureBasicFixPointChange.isShowPatientMsgSelected());
            spinnerResumeProcedureAutomaticallyAfterXMsAfterMessage.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getResumeProcedureAutomaticallyAfterXMs());

            textFieldTextOfTheMessage.setText(uiSettingsProcedureBasicFixPointChange.getTextOfTheMsg());
            spinnerFontSize.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getFontSize());
            comboBoxFontWeight.getSelectionModel().select(uiSettingsProcedureBasicFixPointChange.getFontWeight());
            colorPickerFontColor.setValue(uiSettingsProcedureBasicFixPointChange.getFontColor());
            colorPickerMsgBackgroundColor.setValue(uiSettingsProcedureBasicFixPointChange.getMsgBackgroundColor());
            spinnerMsgBackgroundSizeX.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getMsgBackgroundSizeX());
            spinnerMsgBackgroundSizeY.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getMsgBackgroundSizeY());
            spinnerMsgDistanceFromFixPointX.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getMsgDistanceFromFixPointX());
            spinnerMsgDistanceFromFixPointY.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getMsgDistanceFromFixPointY());
            spinnerResumeToNextStimulusTimeInterval.getValueFactory().setValue(uiSettingsProcedureBasicFixPointChange.getResumeToNextStimulusTimeInterval());
        }
    }

    private void setInitialValueForTextFieldChangedFixPointLuminance() {
        textFieldChangedFixPointLuminance.setText("0.0");
    }

    public void updateValueForTextFieldChangedFixPointLuminance() {
        UISettingsFixMonitorFixPointChange uiSettingsProcedureBasicFixPointChange = StartApplication.getSpecvisData().getUiSettingsProcedureBasicFixPointChange();
        textFieldChangedFixPointLuminance.setText(String.valueOf(uiSettingsProcedureBasicFixPointChange.getChangedFixPointLuminance()));
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
        UISettingsFixMonitorFixPointChange uiSettingsProcedureBasicFixPointChange = StartApplication.getSpecvisData().getUiSettingsProcedureBasicFixPointChange();

        // 1.
        uiSettingsProcedureBasicFixPointChange.setIsMonitorFixationEveryXStimuliSelected(radioButtonMonitorFixationEveryXStimuli.isSelected());
        uiSettingsProcedureBasicFixPointChange.setMonitorFixationEveryXStimuli(Integer.valueOf(spinnerMonitorFixationEveryXStimuli.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setMonitorFixationEveryXYStimuli_1(Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_1.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setMonitorFixationEveryXYStimuli_2(Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_2.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setChangedFixPointSizeX(Double.valueOf(spinnerChangedFixPointSizeX.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setChangedFixPointSizeY(Double.valueOf(spinnerChangedFixPointSizeY.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setChangedFixPointColor(colorPickerChangedFixPointColor.getValue());
        uiSettingsProcedureBasicFixPointChange.setChangedFixPointLuminance(Double.valueOf(textFieldChangedFixPointLuminance.getText()));

        // 2.
        uiSettingsProcedureBasicFixPointChange.setIsShowPatientMsgSelected(checkBoxShowMessageForPatient.isSelected());
        uiSettingsProcedureBasicFixPointChange.setResumeProcedureAutomaticallyAfterXMs(Integer.valueOf(spinnerResumeProcedureAutomaticallyAfterXMsAfterMessage.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setTextOfTheMsg(textFieldTextOfTheMessage.getText());
        uiSettingsProcedureBasicFixPointChange.setFontSize(Integer.valueOf(spinnerFontSize.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setFontWeight(comboBoxFontWeight.getSelectionModel().getSelectedItem());
        uiSettingsProcedureBasicFixPointChange.setFontColor(colorPickerFontColor.getValue());
        uiSettingsProcedureBasicFixPointChange.setMsgBackgroundColor(colorPickerMsgBackgroundColor.getValue());
        uiSettingsProcedureBasicFixPointChange.setMsgBackgroundSizeX(Double.valueOf(spinnerMsgBackgroundSizeX.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setMsgBackgroundSizeY(Double.valueOf(spinnerMsgBackgroundSizeY.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setMsgDistanceFromFixPointX(Double.valueOf(spinnerMsgDistanceFromFixPointX.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setMsgDistanceFromFixPointY(Double.valueOf(spinnerMsgDistanceFromFixPointY.getValue().toString()));
        uiSettingsProcedureBasicFixPointChange.setResumeToNextStimulusTimeInterval(Integer.valueOf(spinnerResumeToNextStimulusTimeInterval.getValue().toString()));

        StartApplication.getSpecvisData().setUiSettingsProcedureBasicFixPointChange(uiSettingsProcedureBasicFixPointChange);
    }

    @FXML
    private void measureFixationPointLuminance() throws IOException {

        setValuesForFixationAndOtherMonitorSettingsFixPointChangeFields();

        Stage stage = new Stage();

        Parent root = FXMLLoader.load(ViewFixationAndOtherController.class.getResource("ViewFixPointChangedLuminance.fxml"));
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
        int brightnessForBackground = StartApplication.getSpecvisData().getUiSettingsStimulusAndBackground().getBackgroundBrightness();
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
        double screenResolutionX = uiSettingsScreenAndLuminanceScale.getScreenResolutionX();
        double screenResolutionY = uiSettingsScreenAndLuminanceScale.getScreenResolutionY();

        // 2. Get involved visual field.
        double involvedVisualFieldX = uiSettingsScreenAndLuminanceScale.getInvolvedVisualFieldX();
        double involvedVisualFieldY = uiSettingsScreenAndLuminanceScale.getInvolvedVisualFieldY();

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

        UISettingsFixationAndOther uiSettingsFixationAndOther = StartApplication.getSpecvisData().getUiSettingsFixationAndOther();

        ViewStimuliDistribution viewStimuliDistribution = new ViewStimuliDistribution(
                "Fixation point change",
                uiSettingsFixationAndOther.getFixationPointLocationX(),
                uiSettingsFixationAndOther.getFixationPointLocationY(),
                0.0,
                0.0,
                false,
                true
        );
        viewStimuliDistribution.show();
    }
}
