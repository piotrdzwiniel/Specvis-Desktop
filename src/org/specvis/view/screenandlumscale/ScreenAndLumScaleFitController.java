package org.specvis.view.screenandlumscale;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.specvis.StartApplication;
import org.specvis.model.Functions;
import org.specvis.model.LuminanceScale;
import org.specvis.model.ChiSquared;

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

public class ScreenAndLumScaleFitController implements Initializable {

    private Functions functions;

    @FXML
    private VBox vBox;

    @FXML
    private TextField textFieldChiSquared;

    @FXML
    private TextField textFieldPValue;

    @FXML
    private TextField textFieldSTD;

    @FXML
    private LineChart<Number, Number> lineChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        functions = StartApplication.getFunctions();

        setValuesForTextFields();
        initLineChart();
    }

    private void setValuesForTextFields() {

        // 1. Get luminance scale.
        LuminanceScale luminanceScale;
        if (StartApplication.getSpecvisData().getScreenAndLuminanceScale().isThisWindowOpenedForStimulus()) {
            luminanceScale = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getStimulusLuminanceScale();
        } else {
            luminanceScale = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getBackgroundLuminanceScale();
        }

        // 2. Get measured and fitted luminance values.
        double[] measuredLuminances = new double[] {
                luminanceScale.getLuminanceForBrightness0(),
                luminanceScale.getLuminanceForBrightness20(),
                luminanceScale.getLuminanceForBrightness40(),
                luminanceScale.getLuminanceForBrightness60(),
                luminanceScale.getLuminanceForBrightness80(),
                luminanceScale.getLuminanceForBrightness100()
        };

        double[] fittedLuminances = new double[] {
                luminanceScale.getFittedLuminanceForEachBrightnessValue()[0],
                luminanceScale.getFittedLuminanceForEachBrightnessValue()[20],
                luminanceScale.getFittedLuminanceForEachBrightnessValue()[40],
                luminanceScale.getFittedLuminanceForEachBrightnessValue()[60],
                luminanceScale.getFittedLuminanceForEachBrightnessValue()[80],
                luminanceScale.getFittedLuminanceForEachBrightnessValue()[100]
        };

        // 2.1. Set negative fitted values to 0.1. Chi-squared analysis don't "take" negative expected values.
        for (int i = 0; i < fittedLuminances.length; i++) {
            if (fittedLuminances[i] < 0.0) {
                fittedLuminances[i] = 0.1;
            }
        }

        // 3. Calculate difference between measured and fitted values.
        double[] differenceBetweenMeasuredAndFittedLuminances = new double[measuredLuminances.length];
        for (int i = 0; i < measuredLuminances.length; i++) {
            differenceBetweenMeasuredAndFittedLuminances[i] = functions.round(measuredLuminances[i] - fittedLuminances[i], 2);
        }

        // 4. Calculate standard deviation between measured and fitted values.
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (double differenceBetweenMeasuredAndFittedLuminance : differenceBetweenMeasuredAndFittedLuminances) {
            descriptiveStatistics.addValue(differenceBetweenMeasuredAndFittedLuminance);
        }

        double standardDeviation = functions.round(descriptiveStatistics.getStandardDeviation(), 2);

        textFieldSTD.setText(String.valueOf(standardDeviation));

        // 4. Calculate Chi-squared and P-value for fitted luminance values.
        double[] expectedValues = fittedLuminances;
        double[] observedValues = measuredLuminances;
        int degreesOfFreedom = observedValues.length - 1;

        ChiSquared chiSquared = new ChiSquared();
        double criticalValue = functions.round(chiSquared.calculateCriticalValue(expectedValues, observedValues), 4);
        double pValue = functions.round(chiSquared.calculateChiSquaredPValue(degreesOfFreedom, criticalValue), 4);

        textFieldChiSquared.setText(String.valueOf(criticalValue));
        textFieldPValue.setText(String.valueOf(pValue));
    }

    private void initLineChart() {

        // 1. Define axes.
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("HSB Brightness (%)");
        yAxis.setLabel("Luminance (cd/m2)");

        // 2. Init lineChart.
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Screen luminance scale");

        // 3. Define chart series.
        XYChart.Series seriesFittedLuminance = new XYChart.Series();
        seriesFittedLuminance.setName("Fitted luminance");

        XYChart.Series seriesMeasuredLuminance = new XYChart.Series();
        seriesMeasuredLuminance.setName("Measured luminance");

        // 4. Get luminance scale.
        LuminanceScale luminanceScale;
        if (StartApplication.getSpecvisData().getScreenAndLuminanceScale().isThisWindowOpenedForStimulus()) {
            luminanceScale = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getStimulusLuminanceScale();
        } else {
            luminanceScale = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getBackgroundLuminanceScale();
        }

        // 5. Create short brightness vector.
        double[] shortBrightnessVector = new double[] {0, 20, 40, 60, 80, 100};

        // 6. Get measured luminance values.
        double[] measuredLuminances = new double[] {
                luminanceScale.getLuminanceForBrightness0(),
                luminanceScale.getLuminanceForBrightness20(),
                luminanceScale.getLuminanceForBrightness40(),
                luminanceScale.getLuminanceForBrightness60(),
                luminanceScale.getLuminanceForBrightness80(),
                luminanceScale.getLuminanceForBrightness100()
        };

        // 7. Create full brightness vector.
        double[] fullBrightnessVector = functions.createBrightnessVector(101);

        // 8. Nest data in series.
        for (int i = 0; i < fullBrightnessVector.length; i++) {
            seriesFittedLuminance.getData().add(new XYChart.Data(fullBrightnessVector[i], luminanceScale.getFittedLuminanceForEachBrightnessValue()[i]));
        }

        for (int i = 0; i < shortBrightnessVector.length; i++) {
            seriesMeasuredLuminance.getData().add(new XYChart.Data(shortBrightnessVector[i], measuredLuminances[i]));
        }

        // 9. Add series to lineChart.
        lineChart.getData().addAll(seriesFittedLuminance, seriesMeasuredLuminance);
        vBox.getChildren().remove(vBox.getChildren().size() - 1);
        vBox.getChildren().add(lineChart);
        VBox.setVgrow(lineChart, Priority.ALWAYS);
    }

    @FXML
    private void setSceneScreenAndLumScale() throws IOException {
        StartApplication.setSceneScreenAndLumScale();
    }
}
