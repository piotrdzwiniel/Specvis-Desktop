package org.specvis.view.screenandlumscale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.specvis.StartApplication;
import org.specvis.model.Functions;
import org.specvis.model.LuminanceScale;
import org.specvis.view.miscellaneous.ExceptionDialogWindow;

import java.io.*;
import java.net.URL;
import java.util.*;

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

public class ScreenAndLumScaleNewController implements Initializable {

    private Functions functions;
    private double[] measuredLuminances;
    private int previousIndexOfTheComboBoxBrightness;

    @FXML
    private ComboBox<Integer> comboBoxBrightness;

    @FXML
    private Label labelForLuminanceMeasurement;

    @FXML
    private Spinner<Double> spinnerMeasuredLuminance;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldName;

    @FXML
    private Slider sliderHue;

    @FXML
    private Slider sliderSaturation;

    @FXML
    private TextField textFieldHSBColorValue;

    @FXML
    private TextField textFieldWebColorValue;

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        functions = StartApplication.getFunctions();

        setItemsForComboBoxBrightness();

        // Initialize measuredLuminances array. Its length depends on comboBoxBrightness.getItems().size().
        measuredLuminances = new double[comboBoxBrightness.getItems().size()];

        // Set init previousIndexOfTheComboBoxBrightness.
        previousIndexOfTheComboBoxBrightness = comboBoxBrightness.getSelectionModel().getSelectedIndex();

        setTextForTextFieldID();

        sliderHue.valueProperty().addListener((observable, oldValue, newValue) -> {
            setSlidersHueAndSaturationOnAction();
        });

        sliderSaturation.valueProperty().addListener((observable, oldValue, newValue) -> {
            setSlidersHueAndSaturationOnAction();
        });
    }

    @FXML
    private void setComboBoxBrightnessOnAction() {

        // Get HSB values.
        int brightness = comboBoxBrightness.getSelectionModel().getSelectedItem();
        int hue = (int) functions.round(sliderHue.getValue(), 0);
        int saturation = (int) functions.round(sliderSaturation.getValue(), 0);

        // Set color for labelForColorPreview.
        labelForLuminanceMeasurement.setStyle("-fx-background-color: hsb(" + hue + ", " + saturation + "%, " + brightness + "%);");

        // Set text for textFieldHSBColorValue.
        textFieldHSBColorValue.setText("HSB("+hue+", "+saturation+"%, "+brightness+"%)");

        // Set text for textFieldWebColorValue.
        Color color = Color.hsb((double) hue, (double) saturation / 100, (double) brightness / 100);
        String hexColor = functions.toHexCode(color).toUpperCase();
        textFieldWebColorValue.setText(hexColor);

        // Remember measured luminance values.
        measuredLuminances[previousIndexOfTheComboBoxBrightness] = Double.valueOf(spinnerMeasuredLuminance.getValue().toString());
        previousIndexOfTheComboBoxBrightness = comboBoxBrightness.getSelectionModel().getSelectedIndex();

        // Set spinner value to appropriate luminance value.
        spinnerMeasuredLuminance.getValueFactory().setValue(measuredLuminances[comboBoxBrightness.getSelectionModel().getSelectedIndex()]);
    }

    private void setSlidersHueAndSaturationOnAction() {

        // Get HSB values.
        int brightness = comboBoxBrightness.getSelectionModel().getSelectedItem();
        int hue = (int) functions.round(sliderHue.getValue(), 0);
        int saturation = (int) functions.round(sliderSaturation.getValue(), 0);

        // Set color for labelForLuminanceMeasurement.
        labelForLuminanceMeasurement.setStyle("-fx-background-color: hsb(" + hue + ", " + saturation + "%, " + brightness + "%);");

        // Set text for textFieldHSBColorValue.
        textFieldHSBColorValue.setText("HSB("+hue+", "+saturation+"%, "+brightness+"%)");

        // Set text for textFieldWebColorValue.
        Color color = Color.hsb((double) hue, (double) saturation / 100, (double) brightness / 100);
        String hexColor = functions.toHexCode(color).toUpperCase();
        textFieldWebColorValue.setText(hexColor);
    }

    private void setItemsForComboBoxBrightness() {
        ObservableList<Integer> observableList = FXCollections.observableArrayList(100, 80, 60, 40, 20, 0);
        comboBoxBrightness.setItems(observableList);
        comboBoxBrightness.getSelectionModel().select(0);
    }

    private void setTextForTextFieldID() {
        String id = "S_" + functions.createIndividualID(functions.getCurrentDateYYYYmmDD(), 4);
        textFieldID.setText(id);
    }

    @FXML
    private void setSceneScreenAndLumScale() throws IOException {
        StartApplication.setSceneScreenAndLumScale();
    }

    @FXML
    private void saveNewScale() throws IOException {

        measuredLuminances[comboBoxBrightness.getSelectionModel().getSelectedIndex()] = Double.valueOf(spinnerMeasuredLuminance.getValue().toString());

        if (functions.isThisPrimitiveArrayContainsValue(measuredLuminances, 0) || textFieldName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must provide scale name and luminance measurements for all six brightness values.");
            alert.showAndWait();
        } else {

            // Manage empty textArea.
            if (textArea.getText().equals("")) {
                textArea.setText("#");
            }

            // Create vector of brightness values from comboBoxBrightness.
            double[] brightnessValuesFromComboBoxBrightness = new double[comboBoxBrightness.getItems().size()];
            int numberOfBrightnessValues = brightnessValuesFromComboBoxBrightness.length;
            for (int i = 0; i < numberOfBrightnessValues; i++) {
                brightnessValuesFromComboBoxBrightness[i] = comboBoxBrightness.getItems().get(numberOfBrightnessValues - 1 - i);
            }

            // Reverse vector of measured luminance values for each brightness from comboBoxBrightness.
            double[] reversedMeasuredLuminances = functions.reverseDoublePrimitiveArray(measuredLuminances);

            // Get polynomial fit coefficients.
            double[] polynomialFitCoefficients = functions.fitPolynomialToData(brightnessValuesFromComboBoxBrightness, reversedMeasuredLuminances, 2, true);

            // Create brightness vector.
            double[] brightnessVector = functions.createBrightnessVector(101);

            // Assign fitted luminance for each 100 brightness values.
            double[] fittedLuminanceForEachBrightnessValue = new double[brightnessVector.length];
            try {
                fittedLuminanceForEachBrightnessValue = functions.poly2D(polynomialFitCoefficients, brightnessVector);
            } catch (Exception ex) {
                ExceptionDialogWindow ed = new ExceptionDialogWindow(Alert.AlertType.ERROR, ex);
                ed.setTitle("Exception");
                ed.setHeaderText(ex.getClass().getName());
                ed.showAndWait();
            }

            // Create new LuminanceScale.
            LuminanceScale luminanceScale = new LuminanceScale(
                textFieldID.getText(),
                    textFieldName.getText(),
                    sliderHue.getValue(),
                    sliderSaturation.getValue(),
                    measuredLuminances[0],
                    measuredLuminances[1],
                    measuredLuminances[2],
                    measuredLuminances[3],
                    measuredLuminances[4],
                    measuredLuminances[5],
                    textArea.getText(),
                    fittedLuminanceForEachBrightnessValue
            );

            // Assign created luminance scale in SpecvisData.
            if (StartApplication.getSpecvisData().getScreenAndLuminanceScale().isThisWindowOpenedForStimulus()) {
                StartApplication.getSpecvisData().getScreenAndLuminanceScale().setStimulusLuminanceScale(luminanceScale);
            } else {
                StartApplication.getSpecvisData().getScreenAndLuminanceScale().setBackgroundLuminanceScale(luminanceScale);
            }

            // Save luminance scale to screenLuminanceScales.s file.
            File file = new File("screenLuminanceScales.s");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
            bufferedWriter.write(
                    luminanceScale.getId() + "\t" +
                            luminanceScale.getName() + "\t" +
                            luminanceScale.getHue() + "\t" +
                            luminanceScale.getSaturation() + "\t" +
                            luminanceScale.getLuminanceForBrightness0() + "\t" +
                            luminanceScale.getLuminanceForBrightness20() + "\t" +
                            luminanceScale.getLuminanceForBrightness40() + "\t" +
                            luminanceScale.getLuminanceForBrightness60() + "\t" +
                            luminanceScale.getLuminanceForBrightness80() + "\t" +
                            luminanceScale.getLuminanceForBrightness100() + "\t" +
                            luminanceScale.getAdditionalInformation().replaceAll("\\n", "#n%") + "\n"
            );
            bufferedWriter.close();

            // Show information dialog.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("New luminance scale has been saved.");
            alert.showAndWait();

            // Show Screen and Luminance Scale scene.
            StartApplication.setSceneScreenAndLumScale();
        }
    }
}
