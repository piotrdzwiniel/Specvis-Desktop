package org.specvis.view.patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.contour2dplot.Contour2DMap;
import org.specvis.StartApplication;
import org.specvis.model.Functions;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Piotr Dzwiniel on 2016-03-23.
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

public class PatientResultsMapController implements Initializable {

    private Functions functions;

    @FXML
    private VBox vBox;

    @FXML
    private HBox colorBarTopRow;

    @FXML
    private Pane colorBarBottomRow;

    @FXML
    private Spinner<Double> spinnerIsofactor;

    @FXML
    private Spinner<Integer> spinnerInterpolation;

    @FXML
    private Spinner<Integer> spinnerColorbarStep;

    @FXML
    private ComboBox<String> comboBoxMap;

    @FXML
    private CheckBox checkBoxShowAxes;

    @FXML
    private CheckBox checkBoxShowFixationPoint;

    private Contour2DMap contour2DMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        functions = StartApplication.getFunctions();

        setItemsForComboBoxMap();
        drawMap();
        drawColorBar();
    }

    private void setItemsForComboBoxMap() {
        ObservableList<String> observableList = FXCollections.observableArrayList("Monochromatic", "Color");
        comboBoxMap.setItems(observableList);
        comboBoxMap.getSelectionModel().select(0);
    }

    private void drawMap() {

        // Get patient results info.
        ArrayList<String> patientResultsInfo = StartApplication.getSpecvisData().getPatient().getResultsInfo();

        // Prepare dataset.
        double[][] decibelData = StartApplication.getSpecvisData().getPatient().getResultsData();

        // Get values of GUI parameters.
        double isoFactor = Double.valueOf(spinnerIsofactor.getValue().toString());
        int interpolationFactor = Integer.valueOf(spinnerInterpolation.getValue().toString());
        String mapColorScale = comboBoxMap.getSelectionModel().getSelectedItem();

        boolean showAxes = checkBoxShowAxes.isSelected();
        boolean showFixationPoint = checkBoxShowFixationPoint.isSelected();

        // Create map.
        BorderPane borderPane = new BorderPane();

        double bgLuminance = 0;
        double stimulusMinLuminance = 0;
        double stimulusMaxLuminance = 0;

        for (int i = 0; i < patientResultsInfo.size(); i++) {
            String[] str = patientResultsInfo.get(i).split(": ");

            if (str[0].equals("Stimulus max luminance (cd/m2)")) {
                stimulusMaxLuminance = Double.valueOf(str[1]);
            }

            if (str[0].equals("Stimulus min luminance (cd/m2)")) {
                stimulusMinLuminance = Double.valueOf(str[1]);
            }

            if (str[0].equals("Background luminance (cd/m2)")) {
                bgLuminance = Double.valueOf(str[1]);
                break;
            }
        }


        double maxDBValue = functions.decibelsValue(stimulusMaxLuminance, stimulusMinLuminance, bgLuminance, 2);
        contour2DMap = new Contour2DMap(550, 475, -1, maxDBValue);

        contour2DMap.setMinSize(550, 475);
        contour2DMap.setPrefSize(550, 475);
        contour2DMap.setMaxSize(550, 475);

        contour2DMap.setData(decibelData);
        contour2DMap.setIsoFactor(isoFactor);
        contour2DMap.setInterpolationFactor(interpolationFactor);
        contour2DMap.setMapColorScale(mapColorScale);
        contour2DMap.draw();

        borderPane.setStyle(
                "-fx-border-color: #202020; " +
                        "-fx-border-width: 4px;"
        );

        borderPane.setCenter(contour2DMap);

        vBox.getChildren().set(0, borderPane);

        // Draw additional elements.
        double pixelsForOneDegreeX = 0;
        double pixelsForOneDegreeY = 0;

        double centerOfTheGridX = 0;
        double centerOfTheGridY = 0;

        double involvedVisualFieldX = 0;
        double involvedVisualFieldY = 0;

        for (int i = 0; i < patientResultsInfo.size(); i++) {
            String[] str = patientResultsInfo.get(i).split(": ");

            if (str[0].equals("Involved visual field (\u00b0)")) {
                String[] visualField = str[1].split("/");
                involvedVisualFieldX = Double.valueOf(visualField[0]);
                involvedVisualFieldY = Double.valueOf(visualField[1]);

                break;
            }
        }

        double fixationPointLocationX = 0;
        double fixationPointLocationY = 0;

        for (int i = 0; i < patientResultsInfo.size(); i++) {
            String[] str = patientResultsInfo.get(i).split(": ");
            if (str[0].equals("Fixation point location (\u00b0)")) {
                String[] visualField = str[1].split("/");
                fixationPointLocationX = Double.valueOf(visualField[0]);
                fixationPointLocationY = Double.valueOf(visualField[1]);

                break;
            }
        }

        if (showAxes || showFixationPoint) {
            pixelsForOneDegreeX = contour2DMap.getPrefWidth() / involvedVisualFieldX;
            pixelsForOneDegreeY = contour2DMap.getPrefHeight() / involvedVisualFieldY;

            centerOfTheGridX = (contour2DMap.getPrefWidth() / 2) + (pixelsForOneDegreeX * fixationPointLocationX);
            centerOfTheGridY = (contour2DMap.getPrefHeight() / 2) + (pixelsForOneDegreeY * fixationPointLocationY);
        }

        if (showAxes) {
            drawAxes(centerOfTheGridX, centerOfTheGridY, pixelsForOneDegreeX, pixelsForOneDegreeY);
        }

        if (showFixationPoint) {
            drawFixationPoint(centerOfTheGridX, centerOfTheGridY, pixelsForOneDegreeX, pixelsForOneDegreeY);
        }
    }

    private void drawAxes(double centerOfTheGridX, double centerOfTheGridY, double pixelsForOneDegreeX, double pixelsForOneDegreeY) {

        // Draw center axes.
        Line line = new Line(0, centerOfTheGridY, contour2DMap.getPrefWidth(), centerOfTheGridY);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);
        contour2DMap.getChildren().add(line);

        line = new Line(centerOfTheGridX, 0, centerOfTheGridX, contour2DMap.getPrefHeight());
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);
        contour2DMap.getChildren().add(line);

        // Draw 10 degrees step points.

        // Horizontal left.
        double positionX = centerOfTheGridX - (pixelsForOneDegreeX * 10);
        double positionY = centerOfTheGridY;

        while (positionX >= 0) {

            Circle point = new Circle(positionX, positionY, 4);
            point.setFill(Color.BLACK);
            contour2DMap.getChildren().add(point);

            positionX -= pixelsForOneDegreeX * 10;
        }

        // Horizontal right.
        positionX = centerOfTheGridX + (pixelsForOneDegreeX * 10);
        positionY = centerOfTheGridY;

        while (positionX <= contour2DMap.getPrefWidth()) {

            Circle point = new Circle(positionX, positionY, 4);
            point.setFill(Color.BLACK);
            contour2DMap.getChildren().add(point);

            positionX += pixelsForOneDegreeX * 10;
        }

        // Vertical up.
        positionX = centerOfTheGridX;
        positionY = centerOfTheGridY - (pixelsForOneDegreeY * 10);

        while (positionY >= 0) {

            Circle point = new Circle(positionX, positionY, 4);
            point.setFill(Color.BLACK);
            contour2DMap.getChildren().add(point);

            positionY -= pixelsForOneDegreeY * 10;
        }

        // Vertical down.
        positionX = centerOfTheGridX;
        positionY = centerOfTheGridY + (pixelsForOneDegreeY * 10);

        while (positionY <= contour2DMap.getPrefHeight()) {

            Circle point = new Circle(positionX, positionY, 4);
            point.setFill(Color.BLACK);
            contour2DMap.getChildren().add(point);

            positionY += pixelsForOneDegreeY * 10;
        }
    }

    private void drawFixationPoint(double centerOfTheGridX, double centerOfTheGridY, double pixelsForOneDegreeX, double pixelsForOneDegreeY) {

        Label labelFixationPoint;
        Label labelFixationMonitor;

        int widthForLabelFixPointAndFixMonitor = 40;
        int heightForLabelFixPointAndFixMonitor = 40;

        String styleForLabelFixPoint = "-fx-border-width: 1; " +
                "-fx-border-color: white; " +
                "-fx-border-style: solid; " +
                "-fx-background-color: #55FF55; " +
                "-fx-font-size: 14px;";

        String styleForLabelFixMonitor = "-fx-border-width: 1; " +
                "-fx-border-color: white; " +
                "-fx-border-style: solid; " +
                "-fx-background-color: #FF5555; " +
                "-fx-font-size: 14px;";

        ArrayList<String> patientResultsInfo = StartApplication.getSpecvisData().getPatient().getResultsInfo();

        String fixationMonitorTechnique = "";

        for (int i = 0; i < patientResultsInfo.size(); i++) {
            String[] str = patientResultsInfo.get(i).split(": ");
            if (str[0].equals("Fixation monitor technique")) {
                fixationMonitorTechnique = str[1];

                break;
            }
        }

        switch (fixationMonitorTechnique) {
            case "blindspot":

                labelFixationPoint = new Label("F");
                labelFixationPoint.setMinSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setMaxSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setLayoutX(centerOfTheGridX - (widthForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setLayoutY(centerOfTheGridY - (heightForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setStyle(styleForLabelFixPoint);
                labelFixationPoint.setAlignment(Pos.CENTER);
                contour2DMap.getChildren().add(labelFixationPoint);

                labelFixationMonitor = new Label("M");
                labelFixationMonitor.setMinSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationMonitor.setMaxSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);

                double blindspotDistanceFromFixPointX = 0;
                double blindspotDistanceFromFixPointY = 0;

                for (int i = 0; i < patientResultsInfo.size(); i++) {
                    String[] str = patientResultsInfo.get(i).split(": ");
                    if (str[0].equals("Blindspot distance from fixation point (\u00b0)")) {
                        String[] bsDistance = str[1].split("/");
                        blindspotDistanceFromFixPointX = Double.valueOf(bsDistance[0]);
                        blindspotDistanceFromFixPointY = Double.valueOf(bsDistance[1]);

                        break;
                    }
                }

                boolean isCorrectionForSphericityCheckBoxChecked = false;

                for (int i = 0; i < patientResultsInfo.size(); i++) {
                    String[] str = patientResultsInfo.get(i).split(": ");
                    if (str[0].equals("Sphericity correction")) {
                        switch (str[1]) {
                            case "no":
                                isCorrectionForSphericityCheckBoxChecked = false;
                                break;
                            case "yes":
                                isCorrectionForSphericityCheckBoxChecked = true;
                                break;
                        }

                        break;
                    }
                }

                if (isCorrectionForSphericityCheckBoxChecked) {

                    double ax = blindspotDistanceFromFixPointX * pixelsForOneDegreeX;
                    double ay = blindspotDistanceFromFixPointY * pixelsForOneDegreeY;

                    double fixationMonitorLocationX = centerOfTheGridX + ax - (widthForLabelFixPointAndFixMonitor / 2);
                    double fixationMonitorLocationY = centerOfTheGridY + ay - (heightForLabelFixPointAndFixMonitor / 2);

                    labelFixationMonitor.setLayoutX(fixationMonitorLocationX);
                    labelFixationMonitor.setLayoutY(fixationMonitorLocationY);

                } else {
                    double fixationMonitorLocationX = centerOfTheGridX + (pixelsForOneDegreeX * blindspotDistanceFromFixPointX) - (widthForLabelFixPointAndFixMonitor / 2);
                    double fixationMonitorLocationY = centerOfTheGridY + (pixelsForOneDegreeY * blindspotDistanceFromFixPointY) - (heightForLabelFixPointAndFixMonitor / 2);

                    labelFixationMonitor.setLayoutX(fixationMonitorLocationX);
                    labelFixationMonitor.setLayoutY(fixationMonitorLocationY);
                }

                labelFixationMonitor.setStyle(styleForLabelFixMonitor);
                labelFixationMonitor.setAlignment(Pos.CENTER);
                contour2DMap.getChildren().add(labelFixationMonitor);

                break;
            case "fixation point change":
                labelFixationPoint = new Label("F+M");
                labelFixationPoint.setMinSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setMaxSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setLayoutX(centerOfTheGridX - (widthForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setLayoutY(centerOfTheGridY - (heightForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setStyle(styleForLabelFixPoint);
                labelFixationPoint.setAlignment(Pos.CENTER);
                contour2DMap.getChildren().add(labelFixationPoint);
                break;
            default:
                labelFixationPoint = new Label("F");
                labelFixationPoint.setMinSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setMaxSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setLayoutX(centerOfTheGridX - (widthForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setLayoutY(centerOfTheGridY - (heightForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setStyle(styleForLabelFixPoint);
                labelFixationPoint.setAlignment(Pos.CENTER);
                contour2DMap.getChildren().add(labelFixationPoint);
                break;
        }
    }

    private void drawColorBar() {

        if (colorBarTopRow.getChildren().size() > 0) {
            colorBarTopRow.getChildren().clear();
        }

        if (colorBarBottomRow.getChildren().size() > 0) {
            colorBarBottomRow.getChildren().clear();
        }

        ArrayList<Color> colorScale = contour2DMap.getColorScale();

        for (int i = 0; i < colorScale.size(); i++) {

            Label colorBarCell = new Label();
            colorBarCell.setPrefSize(colorBarTopRow.getPrefWidth() / colorScale.size(), 25);
            colorBarCell.setStyle("-fx-background-color: " + functions.toHexCode(colorScale.get(i)) + "");
            colorBarTopRow.getChildren().add(colorBarCell);
        }

        drawColorBarNumberScale();
    }

    @FXML
    private void drawColorBarNumberScale() {

        if (colorBarBottomRow.getChildren().size() > 0) {
            colorBarBottomRow.getChildren().clear();
        }

        int colorbarStep = Integer.valueOf(spinnerColorbarStep.getValue().toString());
        ArrayList<Double> isoValues = contour2DMap.getArrayListOfIsoValues();

        ArrayList<Double> colorbarValues = functions.linspace(isoValues.get(0), isoValues.get(isoValues.size() - 1), colorbarStep, false);
        ArrayList<Double> colorbarPositionsX = functions.linspace(2, colorBarBottomRow.getPrefWidth() - 2, colorbarStep, false);

        for (int i = 0; i < colorbarValues.size(); i++) {
            colorbarValues.set(i, functions.round(colorbarValues.get(i), 1));
        }

        for (int i = 0; i < colorbarValues.size(); i++) {

            Circle circle = new Circle(colorbarPositionsX.get(i), 10, 2);
            circle.setFill(Color.BLACK);
            colorBarBottomRow.getChildren().add(circle);

            Label valueCell = new Label();
            valueCell.setPrefSize(25, 25);
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

    @FXML
    private void setOnActionSaveMapButton() {

    }

    @FXML
    private void setOnActionRedrawMapButton() {
        drawMap();
        drawColorBar();
    }

    @FXML
    private void setOnActionCloseButton() throws IOException {

        StartApplication.setScenePatientResults();
    }
}
