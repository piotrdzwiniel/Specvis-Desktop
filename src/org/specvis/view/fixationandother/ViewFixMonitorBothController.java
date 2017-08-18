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
import org.specvis.datastructures.settings.UISettingsFixMonitorBoth;
import org.specvis.datastructures.settings.UISettingsFixationAndOther;
import org.specvis.datastructures.settings.UISettingsScreenAndLuminanceScale;
import org.specvis.logic.Functions;
import org.specvis.view.miscellaneous.ViewStimuliDistribution;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by piotr_dzwiniel on 20.07.2017.
 */
public class ViewFixMonitorBothController implements Initializable {

    private Functions functions;
    private UISettingsScreenAndLuminanceScale uiSettingsScreenAndLuminanceScale;
    private LuminanceScale luminanceScaleForStimulus;
    private LuminanceScale luminanceScaleForBackground;

    /* TAB - General */
    @FXML
    private RadioButton radioButtonMonitorFixationEveryXStimuli;

    @FXML
    private Spinner<Integer> spinnerMonitorFixationEveryXStimuli;

    @FXML
    private RadioButton radioButtonMonitorFixationEveryXToYStimuli;

    @FXML
    private Spinner<Integer> spinnerMonitorFixationEveryXToYStimuli_1;

    @FXML
    private Spinner<Integer> spinnerMonitorFixationEveryXToYStimuli_2;

    /* TAB - General - Blindspot */
    @FXML
    private Pane paneBlindspotMonitorPreview;

    @FXML
    private Spinner<Double> spinnerMonitorStimulusSizeInDgX;

    @FXML
    private Spinner<Double> spinnerMonitorStimulusSizeInDgY;

    @FXML
    private Spinner<Integer> spinnerMonitorStimulusBrightness;

    @FXML
    private TextField textFieldMonitorStimulusLuminance;

    @FXML
    private Spinner<Double> spinnerMonitorStimulusDistFromFixPointInDgX;

    @FXML
    private Spinner<Double> spinnerMonitorStimulusDistFromFixPointInDgY;

    /* TAB - General - Fixation point change */
    @FXML
    private Pane paneChangedFixPointPreview;

    @FXML
    private Spinner<Double> spinnerChangedFixPointSizeInDgX;

    @FXML
    private Spinner<Double> spinnerChangedFixPointSizeInDgY;

    @FXML
    private ColorPicker colorPickerChangedFixPoint;

    @FXML
    private TextField textFieldChangedFixPointLuminance;

    /* TAB - Message after fixation loss */
    @FXML
    private CheckBox checkBoxShowMessageForPatient;

    @FXML
    private Spinner<Integer> spinnerResumeProcedureAutomaticallyAfterXMsAfterMessage;

    @FXML
    private Spinner<Integer> spinnerShowNextStimulusAfterMsgAfterXMs;

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


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        functions = StartApplication.getFunctions();
        uiSettingsScreenAndLuminanceScale = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale();
        luminanceScaleForStimulus = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().getStimulusLuminanceScale();
        luminanceScaleForBackground = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().getBackgroundLuminanceScale();

        setTextFieldMonitorStimulusLuminance();
        setTextFieldChangedFixPointLuminance();
        setValuesForComboBoxFontWeight();
        initValuesForFields();
        setLookForPaneBlindspotMonitorPreview();
        setLookForPaneChangedFixPointPreview();
    }

    private void initValuesForFields() {

        UISettingsFixMonitorBoth uiSettings = StartApplication.getSpecvisData().getUiSettingsFixMonitorBoth();

        if (uiSettings != null) {

            /* TAB General */
            if (uiSettings.isMonitorFixationEveryXStimuliSelected()) {
                radioButtonMonitorFixationEveryXStimuli.setSelected(true);
                radioButtonMonitorFixationEveryXToYStimuli.setSelected(false);
            } else {
                radioButtonMonitorFixationEveryXStimuli.setSelected(false);
                radioButtonMonitorFixationEveryXToYStimuli.setSelected(true);
            }
            spinnerMonitorFixationEveryXStimuli.getValueFactory().setValue(uiSettings.getMonitorFixationEveryXStimuli());
            spinnerMonitorFixationEveryXToYStimuli_1.getValueFactory().setValue(uiSettings.getMonitorFixationEveryXYStimuli_1());
            spinnerMonitorFixationEveryXToYStimuli_2.getValueFactory().setValue(uiSettings.getMonitorFixationEveryXYStimuli_2());

            /* TAB General - TAB Blindspot */
            spinnerMonitorStimulusSizeInDgX.getValueFactory().setValue(uiSettings.getFixMonitorStimulusSizeInDgX());
            spinnerMonitorStimulusSizeInDgY.getValueFactory().setValue(uiSettings.getFixMonitorStimulusSizeInDgY());
            spinnerMonitorStimulusBrightness.getValueFactory().setValue(uiSettings.getFixMonitorStimulusBrightness());
            spinnerMonitorStimulusDistFromFixPointInDgX.getValueFactory().setValue(uiSettings.getFixMonitorStimulusDistanceFromFixPointInDgX());
            spinnerMonitorStimulusDistFromFixPointInDgY.getValueFactory().setValue(uiSettings.getFixMonitorStimulusDistanceFromFixPointInDgY());

            /* TAB General - TAB Fix point change */
            spinnerChangedFixPointSizeInDgX.getValueFactory().setValue(uiSettings.getChangedFixPointSizeInDgX());
            spinnerChangedFixPointSizeInDgY.getValueFactory().setValue(uiSettings.getChangedFixPointSizeInDgY());
            colorPickerChangedFixPoint.setValue(uiSettings.getChangedFixPointColor());
            textFieldChangedFixPointLuminance.setText(String.valueOf(uiSettings.getChangedFixPointColorLuminance()));

            /* TAB Message after fix loss */
            checkBoxShowMessageForPatient.setSelected(uiSettings.isShowPatientMsgSelected());
            spinnerResumeProcedureAutomaticallyAfterXMsAfterMessage.getValueFactory().setValue(uiSettings.getResumeProcedureAutomaticallyAfterXMs());
            spinnerShowNextStimulusAfterMsgAfterXMs.getValueFactory().setValue(uiSettings.getShowNextStimAfterMsgAfterXMs());

            textFieldTextOfTheMessage.setText(uiSettings.getTextOfTheMsg());
            spinnerFontSize.getValueFactory().setValue(uiSettings.getFontSize());
            comboBoxFontWeight.getSelectionModel().select(uiSettings.getFontWeight());
            colorPickerFontColor.setValue(uiSettings.getFontColor());
            colorPickerMsgBackgroundColor.setValue(uiSettings.getMsgBackgroundColor());
            spinnerMsgBackgroundSizeX.getValueFactory().setValue(uiSettings.getMsgBackgroundSizeInDgX());
            spinnerMsgBackgroundSizeY.getValueFactory().setValue(uiSettings.getMsgBackgroundSizeInDgY());
            spinnerMsgDistanceFromFixPointX.getValueFactory().setValue(uiSettings.getMsgDistanceFromFixPointInDgX());
            spinnerMsgDistanceFromFixPointY.getValueFactory().setValue(uiSettings.getMsgDistanceFromFixPointInDgY());
        }
    }

    @FXML
    private void setTextFieldMonitorStimulusLuminance() {

        int brightness = Integer.valueOf(spinnerMonitorStimulusBrightness.getValue().toString());
        double fittedLuminance = luminanceScaleForStimulus.getFittedLuminanceForEachBrightnessValue()[brightness];
        double roundedFittedLuminance = functions.round(fittedLuminance, 2);

        if (roundedFittedLuminance < 0) {
            roundedFittedLuminance = 0;
        }

        textFieldMonitorStimulusLuminance.setText(String.valueOf(roundedFittedLuminance));
        setLookForPaneBlindspotMonitorPreview();
    }

    @FXML
    private void setTextFieldChangedFixPointLuminance() {

        textFieldChangedFixPointLuminance.setText("0.0");
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
            setUISettingsFixMonitorBoth();
            StartApplication.setSceneFixationAndOther();
        } else {

            int value1 = Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_1.getValue().toString());
            int value2 = Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_2.getValue().toString());

            if (value2 > value1) {
                setUISettingsFixMonitorBoth();
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

    private void setUISettingsFixMonitorBoth() {

        UISettingsFixMonitorBoth uiSettings = StartApplication.getSpecvisData().getUiSettingsFixMonitorBoth();

        /* TAB General */
        uiSettings.setMonitorFixationEveryXStimuliSelected(radioButtonMonitorFixationEveryXStimuli.isSelected());
        uiSettings.setMonitorFixationEveryXStimuli(Integer.valueOf(spinnerMonitorFixationEveryXStimuli.getValue().toString()));
        uiSettings.setMonitorFixationEveryXYStimuli_1(Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_1.getValue().toString()));
        uiSettings.setMonitorFixationEveryXYStimuli_2(Integer.valueOf(spinnerMonitorFixationEveryXToYStimuli_2.getValue().toString()));

        /* TAB General - TAB Blindspot */
        uiSettings.setFixMonitorStimulusSizeInDgX(Double.valueOf(spinnerMonitorStimulusSizeInDgX.getValue().toString()));
        uiSettings.setFixMonitorStimulusSizeInDgY(Double.valueOf(spinnerMonitorStimulusSizeInDgY.getValue().toString()));
        uiSettings.setFixMonitorStimulusBrightness(Integer.valueOf(spinnerMonitorStimulusBrightness.getValue().toString()));
        uiSettings.setFixMonitorStimulusLuminance(Double.valueOf(textFieldMonitorStimulusLuminance.getText().toString()));
        uiSettings.setFixMonitorStimulusDistanceFromFixPointInDgX(Double.valueOf(spinnerMonitorStimulusDistFromFixPointInDgX.getValue().toString()));
        uiSettings.setFixMonitorStimulusDistanceFromFixPointInDgY(Double.valueOf(spinnerMonitorStimulusDistFromFixPointInDgY.getValue().toString()));

        /* TAB General - TAB Fix point change */
        uiSettings.setChangedFixPointSizeInDgX(Double.valueOf(spinnerChangedFixPointSizeInDgX.getValue().toString()));
        uiSettings.setChangedFixPointSizeInDgY(Double.valueOf(spinnerChangedFixPointSizeInDgY.getValue().toString()));
        uiSettings.setChangedFixPointColor(colorPickerChangedFixPoint.getValue());
        uiSettings.setChangedFixPointColorLuminance(Double.valueOf(textFieldChangedFixPointLuminance.getText()));

        /* TAB Message after fix loss */
        uiSettings.setShowPatientMsgSelected(checkBoxShowMessageForPatient.isSelected());
        uiSettings.setResumeProcedureAutomaticallyAfterXMs(Integer.valueOf(spinnerResumeProcedureAutomaticallyAfterXMsAfterMessage.getValue().toString()));
        uiSettings.setShowNextStimAfterMsgAfterXMs(Integer.valueOf(spinnerShowNextStimulusAfterMsgAfterXMs.getValue().toString()));
        uiSettings.setTextOfTheMsg(textFieldTextOfTheMessage.getText());
        uiSettings.setFontSize(Integer.valueOf(spinnerFontSize.getValue().toString()));
        uiSettings.setFontWeight(comboBoxFontWeight.getSelectionModel().getSelectedItem());
        uiSettings.setFontColor(colorPickerFontColor.getValue());
        uiSettings.setMsgBackgroundColor(colorPickerMsgBackgroundColor.getValue());
        uiSettings.setMsgBackgroundSizeInDgX(Double.valueOf(spinnerMsgBackgroundSizeX.getValue().toString()));
        uiSettings.setMsgBackgroundSizeInDgY(Double.valueOf(spinnerMsgBackgroundSizeY.getValue().toString()));
        uiSettings.setMsgDistanceFromFixPointInDgX(Double.valueOf(spinnerMsgDistanceFromFixPointX.getValue().toString()));
        uiSettings.setMsgDistanceFromFixPointInDgY(Double.valueOf(spinnerMsgDistanceFromFixPointY.getValue().toString()));

        /* Set settings to StartApplication Specvis data */
        StartApplication.getSpecvisData().setUiSettingsFixMonitorBoth(uiSettings);
    }

    @FXML
    private void setLookForPaneBlindspotMonitorPreview() {

        // 1. Set background color.
        int hueForBackground = (int) functions.round(luminanceScaleForBackground.getHue(), 0);
        int saturationForBackground = (int) functions.round(luminanceScaleForBackground.getSaturation(), 0);
        int brightnessForBackground = StartApplication.getSpecvisData().getUiSettingsStimulusAndBackground().getBackgroundBrightness();
        String colorForBackground = "-fx-background-color: hsb(" + hueForBackground + ", " + saturationForBackground + "%, " + brightnessForBackground + "%);";

        // 2. Create changed fix point.
        double hue = luminanceScaleForStimulus.getHue();
        double saturation = luminanceScaleForStimulus.getSaturation() / 100;
        double brightness = Double.valueOf(spinnerMonitorStimulusBrightness.getValue().toString()) / 100;
        Color color = Color.hsb(hue, saturation, brightness);

        Shape changedFixPoint = createEllipseStimulus(
                Double.valueOf(spinnerMonitorStimulusSizeInDgX.getValue().toString()),
                Double.valueOf(spinnerMonitorStimulusSizeInDgY.getValue().toString()),
                color,
                paneBlindspotMonitorPreview
        );

        // 3. Set panePreview.
        if (paneBlindspotMonitorPreview.getChildren().size() > 0) {
            paneBlindspotMonitorPreview.getChildren().remove(paneBlindspotMonitorPreview.getChildren().size() - 1);
        }
        paneBlindspotMonitorPreview.setStyle(colorForBackground);
        paneBlindspotMonitorPreview.getChildren().add(changedFixPoint);
    }

    @FXML
    private void setLookForPaneChangedFixPointPreview() {

        // 1. Set background color.
        int hueForBackground = (int) functions.round(luminanceScaleForBackground.getHue(), 0);
        int saturationForBackground = (int) functions.round(luminanceScaleForBackground.getSaturation(), 0);
        int brightnessForBackground = StartApplication.getSpecvisData().getUiSettingsStimulusAndBackground().getBackgroundBrightness();
        String colorForBackground = "-fx-background-color: hsb(" + hueForBackground + ", " + saturationForBackground + "%, " + brightnessForBackground + "%);";

        // 2. Create changed fix point.
        Shape changedFixPoint = createEllipseStimulus(
                Double.valueOf(spinnerChangedFixPointSizeInDgX.getValue().toString()),
                Double.valueOf(spinnerChangedFixPointSizeInDgY.getValue().toString()),
                colorPickerChangedFixPoint.getValue(),
                paneChangedFixPointPreview
        );

        // 3. Set panePreview.
        if (paneChangedFixPointPreview.getChildren().size() > 0) {
            paneChangedFixPointPreview.getChildren().remove(paneChangedFixPointPreview.getChildren().size() - 1);
        }
        paneChangedFixPointPreview.setStyle(colorForBackground);
        paneChangedFixPointPreview.getChildren().add(changedFixPoint);
    }

    private Ellipse createEllipseStimulus(double sizeInDgX, double sizeInDgY, Color color, Pane previewPane) {

        // 1. Get screen resolution.
        double screenResolutionX = uiSettingsScreenAndLuminanceScale.getScreenResolutionX();
        double screenResolutionY = uiSettingsScreenAndLuminanceScale.getScreenResolutionY();

        // 2. Get involved visual field.
        double involvedVisualFieldX = uiSettingsScreenAndLuminanceScale.getInvolvedVisualFieldX();
        double involvedVisualFieldY = uiSettingsScreenAndLuminanceScale.getInvolvedVisualFieldY();

        // 3. Calculate pixels per one degree.
        double pixelsForOneDegreeX = screenResolutionX / involvedVisualFieldX;
        double pixelsForOneDegreeY = screenResolutionY / involvedVisualFieldY;

        // 5. Calculate changed fix point radius in pixels.
        double stimulusRadiusInPixelsX = (sizeInDgX / 2) * pixelsForOneDegreeX;
        double stimulusRadiusInPixelsY = (sizeInDgY / 2) * pixelsForOneDegreeY;

        // 7. Create changed fix point preview shape.
        Ellipse ellipse;
        ellipse = new Ellipse(previewPane.getPrefWidth() / 2, previewPane.getPrefHeight() / 2, stimulusRadiusInPixelsX, stimulusRadiusInPixelsY);
        ellipse.setFill(color);
        ellipse.setStroke(color);

        return ellipse;
    }

    @FXML
    private void previewStimuliDistribution() {

        setUISettingsFixMonitorBoth();

        UISettingsFixationAndOther uiSettings = StartApplication.getSpecvisData().getUiSettingsFixationAndOther();

        ViewStimuliDistribution viewStimuliDistribution = new ViewStimuliDistribution(
                "Both",
                uiSettings.getFixationPointLocationX(),
                uiSettings.getFixationPointLocationY(),
                Double.valueOf(spinnerMonitorStimulusDistFromFixPointInDgX.getValue().toString()),
                Double.valueOf(spinnerMonitorStimulusDistFromFixPointInDgY.getValue().toString()),
                false,
                true
        );
        viewStimuliDistribution.show();
    }

    @FXML
    private void measureFixationPointLuminance() throws IOException {

        setUISettingsFixMonitorBoth();

        Stage stage = new Stage();

        Parent root = FXMLLoader.load(ViewFixationAndOtherController.class.getResource("ViewFixPointChangedLuminance.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Specvis");
        stage.getIcons().add(new Image("/org/specvis/graphics/SpecvisIcon.png"));
        stage.show();
    }
}
