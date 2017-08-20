package org.specvis.view.patient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.contour2dplot.Contour2DMap;
import org.specvis.StartApplication;
import org.specvis.datastructures.patient.Patient;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by piotr_dzwiniel on 20.08.2017.
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

public class ViewComparePatientResultsMapsController implements Initializable {

    @FXML
    private VBox vBoxResults_1;

    @FXML
    private VBox vBoxResults_2;

    @FXML
    private VBox vBoxResultsDiff;

    @FXML
    private TextArea textAreaResults_1;

    @FXML
    private TextArea textAreaResults_2;

    @FXML
    private HBox colorBarTopRow;

    @FXML
    private Pane colorBarBottomRow;

    private Contour2DMap contour2DMapDiff;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        drawMaps();
        drawColorBar();
        drawColorBarNumberScale();
        setTextAreas();
    }

    @FXML
    private void setOnActionCloseButton() throws Exception {

        StartApplication.setScenePatientResultsMap();
    }

    private void drawMaps() {

        /* Parameters for maps */
        double isoFactor = 0.5;
        int interpolation = 5;
        String cmap = "Color";

        /* Get patient data */
        Patient patient = StartApplication.getSpecvisData().getPatient();

        /* Draw map for results 1 */
        double[][] dataResults_1 = patient.getResultsData();
        double minValue_1 = Arrays.stream(dataResults_1).flatMapToDouble(a -> Arrays.stream(a)).min().getAsDouble();
        double maxValue_1 = Arrays.stream(dataResults_1).flatMapToDouble(a -> Arrays.stream(a)).max().getAsDouble();
        Contour2DMap contour2DMap_1 = new Contour2DMap(250, 250, minValue_1, maxValue_1);
        contour2DMap_1.setMinSize(250, 250);
        contour2DMap_1.setPrefSize(250, 250);
        contour2DMap_1.setMaxSize(250, 250);
        contour2DMap_1.setData(dataResults_1);
        contour2DMap_1.setIsoFactor(isoFactor);
        contour2DMap_1.setInterpolationFactor(interpolation);
        contour2DMap_1.setMapColorScale(cmap);
        contour2DMap_1.draw();
        vBoxResults_1.getChildren().set(1, contour2DMap_1);

        /* Draw map for results 2 */
        double[][] dataResults_2 = patient.getResultsDataToCompare();
        double minValue_2 = Arrays.stream(dataResults_2).flatMapToDouble(a -> Arrays.stream(a)).min().getAsDouble();
        double maxValue_2 = Arrays.stream(dataResults_2).flatMapToDouble(a -> Arrays.stream(a)).max().getAsDouble();
        Contour2DMap contour2DMap_2 = new Contour2DMap(250, 250, minValue_2, maxValue_2);
        contour2DMap_2.setMinSize(250, 250);
        contour2DMap_2.setPrefSize(250, 250);
        contour2DMap_2.setMaxSize(250, 250);
        contour2DMap_2.setData(dataResults_2);
        contour2DMap_2.setIsoFactor(isoFactor);
        contour2DMap_2.setInterpolationFactor(interpolation);
        contour2DMap_2.setMapColorScale(cmap);
        contour2DMap_2.draw();
        vBoxResults_2.getChildren().set(1, contour2DMap_2);

        /* Draw map for results difference */
        double[][] dataDifference = subtractTwo2DArrays(dataResults_1, dataResults_2);
        double minValue_diff = Arrays.stream(dataDifference).flatMapToDouble(a -> Arrays.stream(a)).min().getAsDouble();
        double maxValue_diff = Arrays.stream(dataDifference).flatMapToDouble(a -> Arrays.stream(a)).max().getAsDouble();
        contour2DMapDiff = new Contour2DMap(250, 250, minValue_diff, maxValue_diff);
        contour2DMapDiff.setMinSize(250, 250);
        contour2DMapDiff.setPrefSize(250, 250);
        contour2DMapDiff.setMaxSize(250, 250);
        contour2DMapDiff.setData(dataDifference);
        contour2DMapDiff.setIsoFactor(isoFactor);
        contour2DMapDiff.setInterpolationFactor(interpolation);
        contour2DMapDiff.setMapColorScale(cmap);
        contour2DMapDiff.draw();
        vBoxResultsDiff.getChildren().set(1, contour2DMapDiff);
    }

    private double[][] subtractTwo2DArrays(double[][] a, double[][] b) {

        double[][] c = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                c[i][j] = a[i][j] - b[i][j];
            }
        }
        return c;
    }

    private void setTextAreas() {

        /* Get patient data */
        Patient patient = StartApplication.getSpecvisData().getPatient();

        /* Set text area for results 1 */
        ArrayList<String> resultsInfo_1 = patient.getResultsInfo();
        for (int i = 0; i < resultsInfo_1.size(); i++) {
            textAreaResults_1.appendText(resultsInfo_1.get(i) + "\n");
        }

        /* Set text area for results 2 */
        ArrayList<String> resultsInfo_2 = patient.getResultsInfo();
        for (int i = 0; i < resultsInfo_2.size(); i++) {
            textAreaResults_2.appendText(resultsInfo_2.get(i) + "\n");
        }
    }

    private void drawColorBar() {

        if (colorBarTopRow.getChildren().size() > 0) {
            colorBarTopRow.getChildren().clear();
        }

        if (colorBarBottomRow.getChildren().size() > 0) {
            colorBarBottomRow.getChildren().clear();
        }

        ArrayList<Color> colorScale = contour2DMapDiff.getColorScale();

        for (int i = 0; i < colorScale.size(); i++) {

            Label colorBarCell = new Label();
            colorBarCell.setPrefSize(colorBarTopRow.getPrefWidth() / colorScale.size(), 25);
            colorBarCell.setStyle("-fx-background-color: " + StartApplication.getFunctions().toHexCode(colorScale.get(i)) + "");
            colorBarTopRow.getChildren().add(colorBarCell);
        }

        drawColorBarNumberScale();
    }

    @FXML
    private void drawColorBarNumberScale() {

        if (colorBarBottomRow.getChildren().size() > 0) {
            colorBarBottomRow.getChildren().clear();
        }

        int colorbarStep = Integer.valueOf(5);
        ArrayList<Double> isoValues = contour2DMapDiff.getArrayListOfIsoValues();

        ArrayList<Double> colorbarValues = StartApplication.getFunctions().linspace(isoValues.get(0), isoValues.get(isoValues.size() - 1), colorbarStep, false);
        ArrayList<Double> colorbarPositionsX = StartApplication.getFunctions().linspace(2, colorBarBottomRow.getPrefWidth() - 2, colorbarStep, false);

        for (int i = 0; i < colorbarValues.size(); i++) {
            colorbarValues.set(i, StartApplication.getFunctions().round(colorbarValues.get(i), 1));
        }

        for (int i = 0; i < colorbarValues.size(); i++) {

            Circle circle = new Circle(colorbarPositionsX.get(i), 10, 2);
            circle.setFill(Color.BLACK);
            colorBarBottomRow.getChildren().add(circle);

            Label valueCell = new Label();
            valueCell.setMinSize(30, 30);
            valueCell.setPrefSize(30, 30);
            valueCell.setStyle("-fx-font-size: 12px;");
            valueCell.setText(String.valueOf(colorbarValues.get(i)));
            valueCell.setAlignment(Pos.CENTER);
            valueCell.setLayoutX(colorbarPositionsX.get(i) - (valueCell.getPrefWidth() / 2));
            valueCell.setLayoutY(valueCell.getPrefHeight() / 2);
            colorBarBottomRow.getChildren().add(valueCell);
        }

        colorBarBottomRow.setStyle(
                "-fx-border-width: 2px; " +
                        "-fx-border-color: transparent;"
        );
    }
}
