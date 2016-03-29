package org.specvis.view.miscellaneous;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.specvis.StartApplication;
import org.specvis.model.FixationAndOther;
import org.specvis.model.FixationAndOtherMonitorSettingsBlindspot;
import org.specvis.model.FixationAndOtherMonitorSettingsFixPointChange;
import org.specvis.model.Functions;

import java.util.List;

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

public class PreviewStimuliDistributionWindow extends Stage {

    private Functions functions;

    private int screenResolutionX;
    private int screenResolutionY;
    private boolean correctionForSphericityOfTheFieldOfView;
    private double distanceBetweenStimuliInDegreesX;
    private double distanceBetweenStimuliInDegreesY;
    private String fixationMonitorTechnique;
    private double fixationPointLocationX;
    private double fixationPointLocationY;
    private double blindspotDistanceFromFixPointX;
    private double blindspotDistanceFromFixPointY;
    private boolean previewLocationOfMsgAfterLossOfFixation;
    private boolean previewLocationOfMsgAfterLossOfFixationWithText;

    private double screenWidthInMM;
    private double screenHeightInMM;
    private double pixelsForOneDegreeX;
    private double pixelsForOneDegreeY;
    private Pane displayPane;

    public Parent createContent() {

        /* layout */
        BorderPane layout = new BorderPane();

        /* layout -> display pane */
        displayPane = new Pane();
        displayPane.setStyle("-fx-background-color: #646464;");
        displayPane.setMinSize(screenResolutionX, screenResolutionY);
        displayPane.setMaxSize(screenResolutionX, screenResolutionY);

        /* layout -> display pane -> paint elements */
        drawGrid();
        drawElements();

        /* return layout */
        layout.setCenter(displayPane);
        return layout;
    }

    private void drawGrid() {

        // Define color and width for lines.
        Color lineColor = Color.WHITE;
        double lineWidth = 1.5;

        // Get center of the grid.
        double centerOfTheGridX = (screenResolutionX / 2) + (pixelsForOneDegreeX * fixationPointLocationX);
        double centerOfTheGridY = (screenResolutionY / 2) + (pixelsForOneDegreeY * fixationPointLocationY);

        // Draw center horizontal line.
        Line line = new Line(0, centerOfTheGridY, screenResolutionX, centerOfTheGridY);
        line.setStroke(lineColor);
        line.setStrokeWidth(lineWidth);
        displayPane.getChildren().add(line);

        // Draw center vertical line.
        line = new Line(centerOfTheGridX, 0, centerOfTheGridX, screenResolutionY);
        line.setStroke(lineColor);
        line.setStrokeWidth(lineWidth);
        displayPane.getChildren().add(line);

        // Draw horizontal lines.
        if (correctionForSphericityOfTheFieldOfView) {

            // Draw horizontal lines above center of the grid.
            double a = distanceBetweenStimuliInDegreesY;
            double r = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getPatientDistanceFromTheScreen();

            double m = functions.calculateOppositeAngle(a, r);
            double mPixels = functions.millimitersToPixels(m, screenResolutionY, screenHeightInMM);

            double tempLocationY = centerOfTheGridY - mPixels;
            int iterator = 1;

            while (tempLocationY >= 0) {
                m = functions.calculateOppositeAngle(a * iterator, r);
                mPixels = functions.millimitersToPixels(m, screenResolutionY, screenHeightInMM);

                Line lineHorizontal = new Line(0, centerOfTheGridY - mPixels, screenResolutionX, centerOfTheGridY - mPixels);
                lineHorizontal.setStroke(lineColor);
                lineHorizontal.setStrokeWidth(lineWidth);
                displayPane.getChildren().add(lineHorizontal);

                iterator += 1;
                m = functions.calculateOppositeAngle(a * iterator, r);
                mPixels = functions.millimitersToPixels(m, screenResolutionY, screenHeightInMM);
                tempLocationY = centerOfTheGridY - mPixels;
            }

            // Draw horizontal lines below center of the grid.
            m = functions.calculateOppositeAngle(a, r);
            mPixels = functions.millimitersToPixels(m, screenResolutionY, screenHeightInMM);

            tempLocationY = centerOfTheGridY + mPixels;
            iterator = 1;

            while (tempLocationY <= screenResolutionY) {
                m = functions.calculateOppositeAngle(a * iterator, r);
                mPixels = functions.millimitersToPixels(m, screenResolutionY, screenHeightInMM);

                Line lineHorizontal = new Line(0, centerOfTheGridY + mPixels, screenResolutionX, centerOfTheGridY + mPixels);
                lineHorizontal.setStroke(lineColor);
                lineHorizontal.setStrokeWidth(lineWidth);
                displayPane.getChildren().add(lineHorizontal);

                iterator += 1;
                m = functions.calculateOppositeAngle(a * iterator, r);
                mPixels = functions.millimitersToPixels(m, screenResolutionY, screenHeightInMM);
                tempLocationY = centerOfTheGridY + mPixels;
            }
        } else {

            double distanceBetweenStimuliInPixelsY = pixelsForOneDegreeY * distanceBetweenStimuliInDegreesY;

            // Draw horizontal lines above center of the grid.
            double tempLocationY = centerOfTheGridY - distanceBetweenStimuliInPixelsY;
            int iterator = 1;

            while (tempLocationY >= 0) {
                Line lineHorizontal = new Line(0, centerOfTheGridY - (distanceBetweenStimuliInPixelsY * iterator), screenResolutionX, centerOfTheGridY - (distanceBetweenStimuliInPixelsY * iterator));
                lineHorizontal.setStroke(lineColor);
                lineHorizontal.setStrokeWidth(lineWidth);
                displayPane.getChildren().add(lineHorizontal);

                iterator += 1;
                tempLocationY = centerOfTheGridY - (distanceBetweenStimuliInPixelsY * iterator);
            }

            // Draw horizontal lines below center of the grid.
            tempLocationY = centerOfTheGridY + distanceBetweenStimuliInPixelsY;
            iterator = 1;

            while (tempLocationY <= screenResolutionY) {
                Line lineHorizontal = new Line(0, centerOfTheGridY + (distanceBetweenStimuliInPixelsY * iterator), screenResolutionX, centerOfTheGridY + (distanceBetweenStimuliInPixelsY * iterator));
                lineHorizontal.setStroke(lineColor);
                lineHorizontal.setStrokeWidth(lineWidth);
                displayPane.getChildren().add(lineHorizontal);

                iterator += 1;
                tempLocationY = centerOfTheGridY + (distanceBetweenStimuliInPixelsY * iterator);
            }
        }

        // Draw vertical lines.
        if (correctionForSphericityOfTheFieldOfView) {

            // Draw vertical lines to the left of the grid center.
            double a = distanceBetweenStimuliInDegreesX;
            double r = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getPatientDistanceFromTheScreen();

            double m = functions.calculateOppositeAngle(a, r);
            double mPixels = functions.millimitersToPixels(m, screenResolutionX, screenWidthInMM);

            double tempLocationX = centerOfTheGridX - mPixels;
            int iterator = 1;

            while (tempLocationX >= 0) {
                m = functions.calculateOppositeAngle(a * iterator, r);
                mPixels = functions.millimitersToPixels(m, screenResolutionX, screenWidthInMM);

                Line lineVertical = new Line(centerOfTheGridX - mPixels, 0, centerOfTheGridX - mPixels, screenResolutionY);
                lineVertical.setStroke(lineColor);
                lineVertical.setStrokeWidth(lineWidth);
                displayPane.getChildren().add(lineVertical);

                iterator += 1;
                m = functions.calculateOppositeAngle(a * iterator, r);
                mPixels = functions.millimitersToPixels(m, screenResolutionX, screenWidthInMM);
                tempLocationX = centerOfTheGridX - mPixels;
            }

            // Draw vertical lines to the right of the grid center.
            m = functions.calculateOppositeAngle(a, r);
            mPixels = functions.millimitersToPixels(m, screenResolutionX, screenWidthInMM);

            tempLocationX = centerOfTheGridX + mPixels;
            iterator = 1;

            while (tempLocationX <= screenResolutionX) {
                m = functions.calculateOppositeAngle(a * iterator, r);
                mPixels = functions.millimitersToPixels(m, screenResolutionX, screenWidthInMM);

                Line lineVertical = new Line(centerOfTheGridX + mPixels, 0, centerOfTheGridX + mPixels, screenResolutionY);
                lineVertical.setStroke(lineColor);
                lineVertical.setStrokeWidth(lineWidth);
                displayPane.getChildren().add(lineVertical);

                iterator += 1;
                m = functions.calculateOppositeAngle(a * iterator, r);
                mPixels = functions.millimitersToPixels(m, screenResolutionX, screenWidthInMM);
                tempLocationX = centerOfTheGridX + mPixels;
            }
        } else {

            double distanceBetweenStimuliInPixelsX = pixelsForOneDegreeX * distanceBetweenStimuliInDegreesX;

            // Draw vertical lines to the left of the grid center.
            double tempLocationX = centerOfTheGridX - distanceBetweenStimuliInPixelsX;
            int iterator = 1;

            while (tempLocationX >= 0) {
                Line lineVertical = new Line(centerOfTheGridX - (distanceBetweenStimuliInPixelsX * iterator), 0, centerOfTheGridX - (distanceBetweenStimuliInPixelsX * iterator), screenResolutionY);
                lineVertical.setStroke(lineColor);
                lineVertical.setStrokeWidth(lineWidth);
                displayPane.getChildren().add(lineVertical);

                iterator += 1;
                tempLocationX = centerOfTheGridX - (distanceBetweenStimuliInPixelsX * iterator);
            }

            // Draw vertical lines to the right of the grid center.
            tempLocationX = centerOfTheGridX + distanceBetweenStimuliInPixelsX;
            iterator = 1;

            while (tempLocationX <= screenResolutionX) {
                Line lineVertical = new Line(centerOfTheGridX + (distanceBetweenStimuliInPixelsX * iterator), 0, centerOfTheGridX + (distanceBetweenStimuliInPixelsX * iterator), screenResolutionY);
                lineVertical.setStroke(lineColor);
                lineVertical.setStrokeWidth(lineWidth);
                displayPane.getChildren().add(lineVertical);

                iterator += 1;
                tempLocationX = centerOfTheGridX + (distanceBetweenStimuliInPixelsX * iterator);
            }
        }
    }

    public void drawElements() {

        // Define objects and their settings.
        Label labelFixationPoint;
        Label labelFixationMonitor;
        Label labelStimulus;

        int widthForLabelFixPointAndFixMonitor = 40;
        int heightForLabelFixPointAndFixMonitor = 40;

        int widthForLabelStimulus = 30;
        int heightForLabelStimulus = 30;

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

        String styleForLabelStimulus = "-fx-border-width: 1; " +
                "-fx-border-color: white; " +
                "-fx-border-style: solid; " +
                "-fx-background-color: #FFF; " +
                "-fx-font-size: 14px;";

        // Get center of the grid.
        double centerOfTheGridX = (screenResolutionX / 2) + (pixelsForOneDegreeX * fixationPointLocationX);
        double centerOfTheGridY = (screenResolutionY / 2) + (pixelsForOneDegreeY * fixationPointLocationY);

        // Draw fixation point and fixation monitor labels.
        switch (fixationMonitorTechnique) {
            case "Blindspot":

                labelFixationPoint = new Label("F");
                labelFixationPoint.setMinSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setMaxSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setLayoutX(centerOfTheGridX - (widthForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setLayoutY(centerOfTheGridY - (heightForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setStyle(styleForLabelFixPoint);
                labelFixationPoint.setAlignment(Pos.CENTER);
                displayPane.getChildren().add(labelFixationPoint);

                labelFixationMonitor = new Label("M");
                labelFixationMonitor.setMinSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationMonitor.setMaxSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);

                if (correctionForSphericityOfTheFieldOfView) {

                    double r = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getPatientDistanceFromTheScreen();

                    double ax = blindspotDistanceFromFixPointX;
                    double ay = blindspotDistanceFromFixPointY;

                    double mx = functions.calculateOppositeAngle(ax, r);
                    double my = functions.calculateOppositeAngle(ay, r);

                    double mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);
                    double myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                    double fixationMonitorLocationX = centerOfTheGridX + mxPixels - (widthForLabelFixPointAndFixMonitor / 2);
                    double fixationMonitorLocationY = centerOfTheGridY + myPixels - (heightForLabelFixPointAndFixMonitor / 2);

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
                displayPane.getChildren().add(labelFixationMonitor);

                break;
            case "Fixation point change":
                labelFixationPoint = new Label("F+M");
                labelFixationPoint.setMinSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setMaxSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setLayoutX(centerOfTheGridX - (widthForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setLayoutY(centerOfTheGridY - (heightForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setStyle(styleForLabelFixPoint);
                labelFixationPoint.setAlignment(Pos.CENTER);
                displayPane.getChildren().add(labelFixationPoint);
                break;
            default:
                labelFixationPoint = new Label("F");
                labelFixationPoint.setMinSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setMaxSize(widthForLabelFixPointAndFixMonitor, heightForLabelFixPointAndFixMonitor);
                labelFixationPoint.setLayoutX(centerOfTheGridX - (widthForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setLayoutY(centerOfTheGridY - (heightForLabelFixPointAndFixMonitor / 2));
                labelFixationPoint.setStyle(styleForLabelFixPoint);
                labelFixationPoint.setAlignment(Pos.CENTER);
                displayPane.getChildren().add(labelFixationPoint);
                break;
        }

        // Draw stimuli labels.
        /** Quarters:
         *  Q0  |  Q1
         *  ---------
         *  Q3  |  Q2
         */
        if (correctionForSphericityOfTheFieldOfView) {

            // Draw stimuli in Quarter 0.
            double ax = distanceBetweenStimuliInDegreesX / 2;
            double ay = distanceBetweenStimuliInDegreesY / 2;

            double r = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getPatientDistanceFromTheScreen();

            double mx = functions.calculateOppositeAngle(ax, r);
            double my = functions.calculateOppositeAngle(ay, r);

            double mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);
            double myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

            double tempLocationX = centerOfTheGridX - mxPixels;
            double tempLocationY = centerOfTheGridY - myPixels;

            while (tempLocationX >= 0) {
                while (tempLocationY >= 0) {

                    labelStimulus = new Label("S");
                    labelStimulus.setMinSize(widthForLabelStimulus, heightForLabelStimulus);
                    labelStimulus.setMaxSize(widthForLabelStimulus, heightForLabelStimulus);

                    labelStimulus.setLayoutX(tempLocationX - (widthForLabelStimulus / 2));
                    labelStimulus.setLayoutY(tempLocationY - (heightForLabelStimulus / 2));

                    labelStimulus.setStyle(styleForLabelStimulus);
                    labelStimulus.setAlignment(Pos.CENTER);
                    displayPane.getChildren().add(labelStimulus);

                    ay += distanceBetweenStimuliInDegreesY;
                    my = functions.calculateOppositeAngle(ay, r);
                    myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                    tempLocationY = centerOfTheGridY - myPixels;
                }

                ax += distanceBetweenStimuliInDegreesX;
                mx = functions.calculateOppositeAngle(ax, r);
                mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);

                tempLocationX = centerOfTheGridX - mxPixels;

                ay = distanceBetweenStimuliInDegreesY / 2;
                my = functions.calculateOppositeAngle(ay, r);
                myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                tempLocationY = centerOfTheGridY - myPixels;
            }

            // Draw stimuli in Quarter 1.
            ax = distanceBetweenStimuliInDegreesX / 2;
            ay = distanceBetweenStimuliInDegreesY / 2;

            mx = functions.calculateOppositeAngle(ax, r);
            my = functions.calculateOppositeAngle(ay, r);

            mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);
            myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

            tempLocationX = centerOfTheGridX + mxPixels;
            tempLocationY = centerOfTheGridY - myPixels;

            while (tempLocationX <= screenResolutionX) {
                while (tempLocationY >= 0) {

                    labelStimulus = new Label("S");
                    labelStimulus.setMinSize(widthForLabelStimulus, heightForLabelStimulus);
                    labelStimulus.setMaxSize(widthForLabelStimulus, heightForLabelStimulus);

                    labelStimulus.setLayoutX(tempLocationX - (widthForLabelStimulus / 2));
                    labelStimulus.setLayoutY(tempLocationY - (heightForLabelStimulus / 2));

                    labelStimulus.setStyle(styleForLabelStimulus);
                    labelStimulus.setAlignment(Pos.CENTER);
                    displayPane.getChildren().add(labelStimulus);

                    ay += distanceBetweenStimuliInDegreesY;
                    my = functions.calculateOppositeAngle(ay, r);
                    myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                    tempLocationY = centerOfTheGridY - myPixels;
                }

                ax += distanceBetweenStimuliInDegreesX;
                mx = functions.calculateOppositeAngle(ax, r);
                mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);

                tempLocationX = centerOfTheGridX + mxPixels;

                ay = distanceBetweenStimuliInDegreesY / 2;
                my = functions.calculateOppositeAngle(ay, r);
                myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                tempLocationY = centerOfTheGridY - myPixels;
            }

            // Draw stimuli in Quarter 2.
            ax = distanceBetweenStimuliInDegreesX / 2;
            ay = distanceBetweenStimuliInDegreesY / 2;

            mx = functions.calculateOppositeAngle(ax, r);
            my = functions.calculateOppositeAngle(ay, r);

            mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);
            myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

            tempLocationX = centerOfTheGridX + mxPixels;
            tempLocationY = centerOfTheGridY + myPixels;

            while (tempLocationX <= screenResolutionX) {
                while (tempLocationY <= screenResolutionY) {

                    labelStimulus = new Label("S");
                    labelStimulus.setMinSize(widthForLabelStimulus, heightForLabelStimulus);
                    labelStimulus.setMaxSize(widthForLabelStimulus, heightForLabelStimulus);

                    labelStimulus.setLayoutX(tempLocationX - (widthForLabelStimulus / 2));
                    labelStimulus.setLayoutY(tempLocationY - (heightForLabelStimulus / 2));

                    labelStimulus.setStyle(styleForLabelStimulus);
                    labelStimulus.setAlignment(Pos.CENTER);
                    displayPane.getChildren().add(labelStimulus);

                    ay += distanceBetweenStimuliInDegreesY;
                    my = functions.calculateOppositeAngle(ay, r);
                    myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                    tempLocationY = centerOfTheGridY + myPixels;
                }

                ax += distanceBetweenStimuliInDegreesX;
                mx = functions.calculateOppositeAngle(ax, r);
                mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);

                tempLocationX = centerOfTheGridX + mxPixels;

                ay = distanceBetweenStimuliInDegreesY / 2;
                my = functions.calculateOppositeAngle(ay, r);
                myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                tempLocationY = centerOfTheGridY + myPixels;
            }

            // Draw stimuli in Quarter 3.
            ax = distanceBetweenStimuliInDegreesX / 2;
            ay = distanceBetweenStimuliInDegreesY / 2;

            mx = functions.calculateOppositeAngle(ax, r);
            my = functions.calculateOppositeAngle(ay, r);

            mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);
            myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

            tempLocationX = centerOfTheGridX - mxPixels;
            tempLocationY = centerOfTheGridY + myPixels;

            while (tempLocationX >= 0) {
                while (tempLocationY <= screenResolutionY) {

                    labelStimulus = new Label("S");
                    labelStimulus.setMinSize(widthForLabelStimulus, heightForLabelStimulus);
                    labelStimulus.setMaxSize(widthForLabelStimulus, heightForLabelStimulus);

                    labelStimulus.setLayoutX(tempLocationX - (widthForLabelStimulus / 2));
                    labelStimulus.setLayoutY(tempLocationY - (heightForLabelStimulus / 2));

                    labelStimulus.setStyle(styleForLabelStimulus);
                    labelStimulus.setAlignment(Pos.CENTER);
                    displayPane.getChildren().add(labelStimulus);

                    ay += distanceBetweenStimuliInDegreesY;
                    my = functions.calculateOppositeAngle(ay, r);
                    myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                    tempLocationY = centerOfTheGridY + myPixels;
                }

                ax += distanceBetweenStimuliInDegreesX;
                mx = functions.calculateOppositeAngle(ax, r);
                mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);

                tempLocationX = centerOfTheGridX - mxPixels;

                ay = distanceBetweenStimuliInDegreesY / 2;
                my = functions.calculateOppositeAngle(ay, r);
                myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                tempLocationY = centerOfTheGridY + myPixels;
            }

        } else {

            double distanceBetweenStimuliInPixelsX = pixelsForOneDegreeX * distanceBetweenStimuliInDegreesX;
            double distanceBetweenStimuliInPixelsY = pixelsForOneDegreeY * distanceBetweenStimuliInDegreesY;

            // Draw stimuli in Quarter 0.
            double tempLocationX = centerOfTheGridX - (distanceBetweenStimuliInPixelsX / 2);
            double tempLocationY = centerOfTheGridY - (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX >= 0) {
                while (tempLocationY >= 0) {

                    labelStimulus = new Label("S");
                    labelStimulus.setMinSize(widthForLabelStimulus, heightForLabelStimulus);
                    labelStimulus.setMaxSize(widthForLabelStimulus, heightForLabelStimulus);

                    labelStimulus.setLayoutX(tempLocationX - (widthForLabelStimulus / 2));
                    labelStimulus.setLayoutY(tempLocationY - (heightForLabelStimulus / 2));

                    labelStimulus.setStyle(styleForLabelStimulus);
                    labelStimulus.setAlignment(Pos.CENTER);
                    displayPane.getChildren().add(labelStimulus);

                    tempLocationY -= distanceBetweenStimuliInPixelsY;
                }

                tempLocationX -= distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridY - (distanceBetweenStimuliInPixelsY / 2);
            }

            // Draw stimuli in Quarter 1.
            tempLocationX = centerOfTheGridX + (distanceBetweenStimuliInPixelsX / 2);
            tempLocationY = centerOfTheGridY - (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX <= screenResolutionX) {
                while (tempLocationY >= 0) {

                    labelStimulus = new Label("S");
                    labelStimulus.setMinSize(widthForLabelStimulus, heightForLabelStimulus);
                    labelStimulus.setMaxSize(widthForLabelStimulus, heightForLabelStimulus);

                    labelStimulus.setLayoutX(tempLocationX - (widthForLabelStimulus / 2));
                    labelStimulus.setLayoutY(tempLocationY - (heightForLabelStimulus / 2));

                    labelStimulus.setStyle(styleForLabelStimulus);
                    labelStimulus.setAlignment(Pos.CENTER);
                    displayPane.getChildren().add(labelStimulus);

                    tempLocationY -= distanceBetweenStimuliInPixelsY;
                }

                tempLocationX += distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridY - (distanceBetweenStimuliInPixelsY / 2);
            }

            // Draw stimuli in Quarter 2.
            tempLocationX = centerOfTheGridX + (distanceBetweenStimuliInPixelsX / 2);
            tempLocationY = centerOfTheGridY + (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX <= screenResolutionX) {
                while (tempLocationY <= screenResolutionY) {

                    labelStimulus = new Label("S");
                    labelStimulus.setMinSize(widthForLabelStimulus, heightForLabelStimulus);
                    labelStimulus.setMaxSize(widthForLabelStimulus, heightForLabelStimulus);

                    labelStimulus.setLayoutX(tempLocationX - (widthForLabelStimulus / 2));
                    labelStimulus.setLayoutY(tempLocationY - (heightForLabelStimulus / 2));

                    labelStimulus.setStyle(styleForLabelStimulus);
                    labelStimulus.setAlignment(Pos.CENTER);
                    displayPane.getChildren().add(labelStimulus);

                    tempLocationY += distanceBetweenStimuliInPixelsY;
                }

                tempLocationX += distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridY + (distanceBetweenStimuliInPixelsY / 2);
            }

            // Draw stimuli in Quarter 3.
            tempLocationX = centerOfTheGridX - (distanceBetweenStimuliInPixelsX / 2);
            tempLocationY = centerOfTheGridY + (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX >= 0) {
                while (tempLocationY <= screenResolutionY) {

                    labelStimulus = new Label("S");
                    labelStimulus.setMinSize(widthForLabelStimulus, heightForLabelStimulus);
                    labelStimulus.setMaxSize(widthForLabelStimulus, heightForLabelStimulus);

                    labelStimulus.setLayoutX(tempLocationX - (widthForLabelStimulus / 2));
                    labelStimulus.setLayoutY(tempLocationY - (heightForLabelStimulus / 2));

                    labelStimulus.setStyle(styleForLabelStimulus);
                    labelStimulus.setAlignment(Pos.CENTER);
                    displayPane.getChildren().add(labelStimulus);

                    tempLocationY += distanceBetweenStimuliInPixelsY;
                }

                tempLocationX -= distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridY + (distanceBetweenStimuliInPixelsY / 2);
            }
        }

        // Draw contour of the box for message after loss of fixation.
        if (previewLocationOfMsgAfterLossOfFixation) {

            FixationAndOther fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();

            double msgBoxWidthInDegrees;
            double msgBoxHeightInDegrees;

            double msgBoxWidthInPixels;
            double msgBoxHeightInPixels;

            double msgBoxLocationX;
            double msgBoxLocationY;

            Rectangle msgBox;

            switch (fixationAndOther.getFixationMonitorTechnique()) {
                case "Blindspot":

                    FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();

                    if (fixationAndOtherMonitorSettingsBlindspot == null) {
                        fixationAndOtherMonitorSettingsBlindspot = new FixationAndOtherMonitorSettingsBlindspot();
                        fixationAndOtherMonitorSettingsBlindspot.setDefaultValues();
                    }

                    msgBoxWidthInDegrees = fixationAndOtherMonitorSettingsBlindspot.getMsgBackgroundSizeX();
                    msgBoxHeightInDegrees = fixationAndOtherMonitorSettingsBlindspot.getMsgBackgroundSizeY();

                    msgBoxWidthInPixels = msgBoxWidthInDegrees * pixelsForOneDegreeX;
                    msgBoxHeightInPixels = msgBoxHeightInDegrees * pixelsForOneDegreeY;

                    msgBoxLocationX = centerOfTheGridX + (fixationAndOtherMonitorSettingsBlindspot.getMsgDistanceFromFixPointX() * pixelsForOneDegreeX) - (msgBoxWidthInPixels / 2);
                    msgBoxLocationY = centerOfTheGridY + (fixationAndOtherMonitorSettingsBlindspot.getMsgDistanceFromFixPointY() * pixelsForOneDegreeY) - (msgBoxHeightInPixels / 2);

                    msgBox = new Rectangle(msgBoxLocationX, msgBoxLocationY, msgBoxWidthInPixels, msgBoxHeightInPixels);

                    msgBox.setFill(Color.TRANSPARENT);
                    msgBox.setStroke(Color.web("#FFAA00"));
                    msgBox.setStrokeWidth(2);

                    displayPane.getChildren().add(msgBox);

                    break;
                case "Fixation point change":

                    FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();

                    if (fixationAndOtherMonitorSettingsFixPointChange == null) {
                        fixationAndOtherMonitorSettingsFixPointChange = new FixationAndOtherMonitorSettingsFixPointChange();
                        fixationAndOtherMonitorSettingsFixPointChange.setDefaultValues();
                    }

                    msgBoxWidthInDegrees = fixationAndOtherMonitorSettingsFixPointChange.getMsgBackgroundSizeX();
                    msgBoxHeightInDegrees = fixationAndOtherMonitorSettingsFixPointChange.getMsgBackgroundSizeY();

                    msgBoxWidthInPixels = msgBoxWidthInDegrees * pixelsForOneDegreeX;
                    msgBoxHeightInPixels = msgBoxHeightInDegrees * pixelsForOneDegreeY;

                    msgBoxLocationX = centerOfTheGridX + (fixationAndOtherMonitorSettingsFixPointChange.getMsgDistanceFromFixPointX() * pixelsForOneDegreeX) - (msgBoxWidthInPixels / 2);
                    msgBoxLocationY = centerOfTheGridY + (fixationAndOtherMonitorSettingsFixPointChange.getMsgDistanceFromFixPointY() * pixelsForOneDegreeY) - (msgBoxHeightInPixels / 2);

                    msgBox = new Rectangle(msgBoxLocationX, msgBoxLocationY, msgBoxWidthInPixels, msgBoxHeightInPixels);

                    msgBox.setFill(Color.TRANSPARENT);
                    msgBox.setStroke(Color.web("#FFAA00"));
                    msgBox.setStrokeWidth(2);

                    displayPane.getChildren().add(msgBox);

                    break;
            }
        }

        // Draw box and text for message after loss of fixation.
        if (previewLocationOfMsgAfterLossOfFixationWithText) {
            FixationAndOther fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();

            double msgBoxWidthInDegrees;
            double msgBoxHeightInDegrees;

            double msgBoxWidthInPixels;
            double msgBoxHeightInPixels;

            double msgBoxLocationX;
            double msgBoxLocationY;

            Label msgBox = new Label();
            msgBox.setWrapText(true);
            msgBox.setAlignment(Pos.CENTER);
            msgBox.setTextAlignment(TextAlignment.CENTER);

            String style;
            String backgroundColor;
            String fontColor;
            String fontWeight;
            int fontSize;

            switch (fixationAndOther.getFixationMonitorTechnique()) {
                case "Blindspot":

                    FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();

                    if (fixationAndOtherMonitorSettingsBlindspot == null) {
                        fixationAndOtherMonitorSettingsBlindspot = new FixationAndOtherMonitorSettingsBlindspot();
                        fixationAndOtherMonitorSettingsBlindspot.setDefaultValues();
                    }

                    msgBoxWidthInDegrees = fixationAndOtherMonitorSettingsBlindspot.getMsgBackgroundSizeX();
                    msgBoxHeightInDegrees = fixationAndOtherMonitorSettingsBlindspot.getMsgBackgroundSizeY();

                    msgBoxWidthInPixels = msgBoxWidthInDegrees * pixelsForOneDegreeX;
                    msgBoxHeightInPixels = msgBoxHeightInDegrees * pixelsForOneDegreeY;

                    msgBoxLocationX = centerOfTheGridX + (fixationAndOtherMonitorSettingsBlindspot.getMsgDistanceFromFixPointX() * pixelsForOneDegreeX) - (msgBoxWidthInPixels / 2);
                    msgBoxLocationY = centerOfTheGridY + (fixationAndOtherMonitorSettingsBlindspot.getMsgDistanceFromFixPointY() * pixelsForOneDegreeY) - (msgBoxHeightInPixels / 2);

                    msgBox.setText(fixationAndOtherMonitorSettingsBlindspot.getTextOfTheMsg());
                    msgBox.setMinSize(msgBoxWidthInPixels, msgBoxHeightInPixels);
                    msgBox.setMaxSize(msgBoxWidthInPixels, msgBoxHeightInPixels);
                    msgBox.setLayoutX(msgBoxLocationX);
                    msgBox.setLayoutY(msgBoxLocationY);

                    backgroundColor = StartApplication.getFunctions().toHexCode(fixationAndOtherMonitorSettingsBlindspot.getMsgBackgroundColor());
                    fontColor = StartApplication.getFunctions().toHexCode(fixationAndOtherMonitorSettingsBlindspot.getFontColor());
                    fontWeight = fixationAndOtherMonitorSettingsBlindspot.getFontWeight();
                    fontSize = fixationAndOtherMonitorSettingsBlindspot.getFontSize();

                    style = "-fx-border-width: 2; " +
                            "-fx-border-color: black; " +
                            "-fx-border-style: solid; " +
                            "-fx-background-color: " + backgroundColor + "; " +
                            "-fx-text-fill: " + fontColor + "; " +
                            "-fx-font-weight: " + fontWeight + ";" +
                            "-fx-font-size: " + fontSize + "px;";
                    msgBox.setStyle(style);

                    displayPane.getChildren().add(msgBox);

                    break;
                case "Fixation point change":

                    FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();

                    if (fixationAndOtherMonitorSettingsFixPointChange == null) {
                        fixationAndOtherMonitorSettingsFixPointChange = new FixationAndOtherMonitorSettingsFixPointChange();
                        fixationAndOtherMonitorSettingsFixPointChange.setDefaultValues();
                    }

                    msgBoxWidthInDegrees = fixationAndOtherMonitorSettingsFixPointChange.getMsgBackgroundSizeX();
                    msgBoxHeightInDegrees = fixationAndOtherMonitorSettingsFixPointChange.getMsgBackgroundSizeY();

                    msgBoxWidthInPixels = msgBoxWidthInDegrees * pixelsForOneDegreeX;
                    msgBoxHeightInPixels = msgBoxHeightInDegrees * pixelsForOneDegreeY;

                    msgBoxLocationX = centerOfTheGridX + (fixationAndOtherMonitorSettingsFixPointChange.getMsgDistanceFromFixPointX() * pixelsForOneDegreeX) - (msgBoxWidthInPixels / 2);
                    msgBoxLocationY = centerOfTheGridY + (fixationAndOtherMonitorSettingsFixPointChange.getMsgDistanceFromFixPointY() * pixelsForOneDegreeY) - (msgBoxHeightInPixels / 2);

                    msgBox.setText(fixationAndOtherMonitorSettingsFixPointChange.getTextOfTheMsg());
                    msgBox.setMinSize(msgBoxWidthInPixels, msgBoxHeightInPixels);
                    msgBox.setMaxSize(msgBoxWidthInPixels, msgBoxHeightInPixels);
                    msgBox.setLayoutX(msgBoxLocationX);
                    msgBox.setLayoutY(msgBoxLocationY);

                    backgroundColor = StartApplication.getFunctions().toHexCode(fixationAndOtherMonitorSettingsFixPointChange.getMsgBackgroundColor());
                    fontColor = StartApplication.getFunctions().toHexCode(fixationAndOtherMonitorSettingsFixPointChange.getFontColor());
                    fontWeight = fixationAndOtherMonitorSettingsFixPointChange.getFontWeight();
                    fontSize = fixationAndOtherMonitorSettingsFixPointChange.getFontSize();

                    style = "-fx-border-width: 2; " +
                            "-fx-border-color: black; " +
                            "-fx-border-style: solid; " +
                            "-fx-background-color: " + backgroundColor + "; " +
                            "-fx-text-fill: " + fontColor + "; " +
                            "-fx-font-weight: " + fontWeight + ";" +
                            "-fx-font-size: " + fontSize + "px;";
                    msgBox.setStyle(style);

                    displayPane.getChildren().add(msgBox);

                    break;
            }
        }
    }

    public PreviewStimuliDistributionWindow(String fixationMonitorTechnique, double fixationPointLocationX, double fixationPointLocationY, double blindspotDistanceFromFixPointX, double blindspotDistanceFromFixPointY, boolean previewLocationOfMsgAfterLossOfFixation, boolean previewLocationOfMsgAfterLossOfFixationWithText) {

        // Get Functions.
        functions = StartApplication.getFunctions();

        // Init fields.
        int chosenScreenIndex = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getChosenScreenIndex();
        this.screenResolutionX = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getScreenResolutionX();
        this.screenResolutionY = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getScreenResolutionY();
        double involvedVisualFieldX = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getInvolvedVisualFieldX();
        double involvedVisualFieldY = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getInvolvedVisualFieldY();
        this.correctionForSphericityOfTheFieldOfView = StartApplication.getSpecvisData().getStimulusAndBackground().isCorrectionForSphericityCheckBoxChecked();
        this.distanceBetweenStimuliInDegreesX = StartApplication.getSpecvisData().getStimulusAndBackground().getDistanceBetweenStimuliInDegreesX();
        this.distanceBetweenStimuliInDegreesY = StartApplication.getSpecvisData().getStimulusAndBackground().getDistanceBetweenStimuliInDegreesY();
        this.fixationMonitorTechnique = fixationMonitorTechnique;
        this.fixationPointLocationX = fixationPointLocationX;
        this.fixationPointLocationY = fixationPointLocationY;
        this.blindspotDistanceFromFixPointX = blindspotDistanceFromFixPointX;
        this.blindspotDistanceFromFixPointY = blindspotDistanceFromFixPointY;
        this.previewLocationOfMsgAfterLossOfFixation = previewLocationOfMsgAfterLossOfFixation;
        this.previewLocationOfMsgAfterLossOfFixationWithText = previewLocationOfMsgAfterLossOfFixationWithText;

        // Init other fields.
        screenWidthInMM = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getScreenWidth();
        screenHeightInMM = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getScreenHeight();

        pixelsForOneDegreeX = screenResolutionX / involvedVisualFieldX;
        pixelsForOneDegreeY = screenResolutionY / involvedVisualFieldY;

        // Set scene.
        this.setScene(new Scene(createContent()));

        // Set size and position of the window.
        List<Screen> displays = Screen.getScreens();
        Screen activeDisplay = displays.get(chosenScreenIndex);
        Rectangle2D activeDisplayBounds = activeDisplay.getVisualBounds();

        this.setX(activeDisplayBounds.getMinX());
        this.setY(activeDisplayBounds.getMinY());
        this.setWidth(activeDisplayBounds.getWidth());
        this.setHeight(activeDisplayBounds.getHeight());
        this.initStyle(StageStyle.UNDECORATED);

        // Set close action for ESCAPE key.
        this.getScene().addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                close();
            }
        });
    }
}
