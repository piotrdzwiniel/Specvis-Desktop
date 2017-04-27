package org.specvis.procedures.basic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.specvis.StartApplication;
import org.specvis.model.*;
import org.specvis.model.procedure.BasicProcedureData;
import org.specvis.model.procedure.BasicProcedureStimulus;
import org.specvis.view.procedure.ProcedureController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Piotr Dzwiniel on 2016-03-01.
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

public class Procedure extends Stage {

    /* Fields from StartApplication */
    private Functions functions;
    private ProcedureController procedureController;

    /* Fields from StartApplication */
    private int chosenScreenIndex;
    private int screenResolutionX;
    private int screenResolutionY;
    private int screenWidthInMM;
    private int screenHeightInMM;
    private double involvedVisualFieldX;
    private double involvedVisualFieldY;
    private LuminanceScale luminanceScaleForStimulus;
    private LuminanceScale luminanceScaleForBackground;

    /* Fields from StimulusAndBackground */
    private int stimulusMaxBrightness;
    private int stimulusMinBrightness;
    private String stimulusShape;
    private double stimulusSizeInDegreesX;
    private double stimulusSizeInDegreesY;
    private double stimulusInclination;
    private int stimulusDisplayTime;
    private int interStimuliIntervalConstantPart;
    private int interStimuliIntervalRandomPart;
    private int backgroundBrightness;
    private double distanceBetweenStimuliInDegreesX;
    private double distanceBetweenStimuliInDegreesY;
    private boolean isCorrectionForSphericityCheckBoxChecked;

    /* Fields from FixationAndOther */
    private Color fixationPointColor;
    private double fixationPointSizeX;
    private double fixationPointSizeY;
    private String fixationMonitorTechnique;
    private double fixationPointLocationX;
    private double fixationPointLocationY;
    private String keyAnswerToStimulus;
    private String keyPauseProcedure;
    private String keyCancelProcedure;

    /* Fields from FixationAndOther -> fixation monitor "blindspot" */
    private boolean b_isMonitorFixationEveryXStimuliSelected;
    private int b_monitorFixationEveryXStimuli;
    private int b_monitorFixationEveryXYStimuli_1;
    private int b_monitorFixationEveryXYStimuli_2;
    private double b_fixationMonitorStimulusSizeX;
    private double b_fixationMonitorStimulusSizeY;
    private int b_fixationMonitorStimulusBrightness;
    private double b_blindspotDistanceFromFixPointX;
    private double b_blindspotDistanceFromFixPointY;

    private boolean b_isShowPatientMsgSelected;
    private String b_textOfTheMsg;
    private int b_fontSizeOfTheMsg;
    private String b_fontWeightOfTheMsg;
    private Color b_fontColorOfTheMsg;
    private Color b_msgBackgroundColor;
    private double b_msgBackgroundSizeX;
    private double b_msgBackgroundSizeY;
    private double b_msgDistanceFromFixPointX;
    private double b_msgDistanceFromFixPointY;
    private int b_resumeToNextStimulusTimeInterval;

    /* Fields from FixationAndOther -> fixation monitor "fixation point change" */
    private boolean f_isMonitorFixationEveryXStimuliSelected;
    private int f_monitorFixationEveryXStimuli;
    private int f_monitorFixationEveryXYStimuli_1;
    private int f_monitorFixationEveryXYStimuli_2;
    private double f_changedFixPointSizeX;
    private double f_changedFixPointSizeY;
    private Color f_changedFixPointColor;

    private boolean f_isShowPatientMsgSelected;
    private String f_textOfTheMsg;
    private int f_fontSizeOfTheMsg;
    private String f_fontWeightOfTheMsg;
    private Color f_fontColorOfTheMsg;
    private Color f_msgBackgroundColor;
    private double f_msgBackgroundSizeX;
    private double f_msgBackgroundSizeY;
    private double f_msgDistanceFromFixPointX;
    private double f_msgDistanceFromFixPointY;
    private int f_resumeToNextStimulusTimeInterval;

    /* Local fields */
    private Pane displayPane;
    private Random randomGenerator;
    private int fixationMonitorIterator;
    private int fixationMonitorCheckRate;
    private ArrayList<Boolean> answersToFixationMonitor;
    private double pixelsForOneDegreeX;
    private double pixelsForOneDegreeY;
    private double centerOfTheGridInPixelsX;
    private double centerOfTheGridInPixelsY;

    /* Local fields -> procedure timelines */
    private Timeline procedureTimeline;
    private Timeline stimulusTimeline;
    private Timeline fixationMonitorTimeline;

    /* Local fields -> procedure state indicators */
    private long timeOfTheStartOfTheProcedure;
    private boolean procedureIsStarted;
    private boolean procedureIsRunning;
    private boolean procedureIsFinished;
    private boolean permissionToAnswer;

    /* Local fields -> displaying stimuli and writing answers */
    private BasicProcedureStimulus currentlyDisplayedStimulus;
    private boolean spreadValuesLogarithmically;
    private int visualFieldTestBrightnessVectorLength;
    private int[] brightnessValuesToTest;
    private boolean wasReminderMsgAfterFixLossShown;
    private ArrayList<BasicProcedureStimulus> stimuliList;
    private ArrayList<Integer> activeStimuliIndices;
    private Shape fixationMonitorShape;

    private int falsePositiveIndicator_PositiveAnswer;
    private int falsePositiveIndicator_FalseAnswer;

    private Parent createContent() {

        /* layout */
        BorderPane layout = new BorderPane();

        /* layout -> fixation point */
        drawFixationPoint();
        drawProcedureStateIndicator("Circle");

        /* return layout */
        layout.setCenter(displayPane);
        return layout;
    }

    private Pane initializeDisplayPane() {
        Pane displayPane = new Pane();
        int hueForBackground = (int) functions.round(luminanceScaleForBackground.getHue(), 0);
        int saturationForBackground = (int) functions.round(luminanceScaleForBackground.getSaturation(), 0);
        displayPane.setStyle("-fx-background-color: hsb(" + hueForBackground + ", " + saturationForBackground + "%, " + backgroundBrightness + "%);");
        displayPane.setMinSize(screenResolutionX, screenResolutionY);
        displayPane.setMaxSize(screenResolutionX, screenResolutionY);
        return displayPane;
    }

    private void drawFixationPoint() {

        double radiusX = (fixationPointSizeX / 2) * pixelsForOneDegreeX;
        double radiusY = (fixationPointSizeY / 2) * pixelsForOneDegreeY;

        Ellipse ellipse = new Ellipse(centerOfTheGridInPixelsX, centerOfTheGridInPixelsY, radiusX, radiusY);
        ellipse.setFill(fixationPointColor);
        ellipse.setStroke(fixationPointColor);

        displayPane.getChildren().add(ellipse);
    }

    /**
     * Draw arround the fixation point the procedure state indicator.
     * - Draw circle when waiting for starting the procedure;
     * - Draw triangle when procedure is paused;
     * - Draw square when procedure is finished;
     * @param indicator
     */
    private void drawProcedureStateIndicator(String indicator) {
        switch (indicator) {
            case "Circle":

                double radiusX = (fixationPointSizeX * 1.5) * pixelsForOneDegreeX;
                double radiusY = (fixationPointSizeY * 1.5) * pixelsForOneDegreeY;

                Ellipse ellipse = new Ellipse(centerOfTheGridInPixelsX, centerOfTheGridInPixelsY, radiusX, radiusY);
                ellipse.setFill(Color.TRANSPARENT);
                ellipse.setStroke(fixationPointColor);
                ellipse.setStrokeWidth(4);

                displayPane.getChildren().add(ellipse);

                break;
            case "Triangle":

                double triangleSizeX = (fixationPointSizeX * 4.5) * pixelsForOneDegreeX;
                double triangleSizeY = (fixationPointSizeY * 4.5) * pixelsForOneDegreeY;

                double twoThirdOfTheTriangleHeightX = (triangleSizeX * Math.sqrt(3)) / 3;
                double twoThirdOfTheTriangleHeightY = (triangleSizeY * Math.sqrt(3)) / 3;

                double angleForA = Math.toRadians(270);
                double angleForB = Math.toRadians(30);
                double angleForC = Math.toRadians(150);

                double xA = centerOfTheGridInPixelsX + twoThirdOfTheTriangleHeightX * Math.cos(angleForA);
                double yA = centerOfTheGridInPixelsY - twoThirdOfTheTriangleHeightY * Math.sin(angleForA);

                double xB = centerOfTheGridInPixelsX + twoThirdOfTheTriangleHeightX * Math.cos(angleForB);
                double yB = centerOfTheGridInPixelsY - twoThirdOfTheTriangleHeightY * Math.sin(angleForB);

                double xC = centerOfTheGridInPixelsX + twoThirdOfTheTriangleHeightX * Math.cos(angleForC);
                double yC = centerOfTheGridInPixelsY - twoThirdOfTheTriangleHeightY * Math.sin(angleForC);

                Polygon polygon = new Polygon();
                polygon.getPoints().addAll(xA, yA, xB, yB, xC, yC);
                polygon.setFill(Color.TRANSPARENT);
                polygon.setStroke(fixationPointColor);
                polygon.setStrokeWidth(4);

                displayPane.getChildren().add(polygon);

                break;
            case "Square":

                double sizeX = (fixationPointSizeX * 3) * pixelsForOneDegreeX;
                double sizeY = (fixationPointSizeY * 3) * pixelsForOneDegreeY;

                Rectangle rectangle = new Rectangle(centerOfTheGridInPixelsX - (sizeX / 2), centerOfTheGridInPixelsY - (sizeY / 2), sizeX, sizeY);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(fixationPointColor);
                rectangle.setStrokeWidth(4);

                displayPane.getChildren().add(rectangle);

                break;
        }
    }

    public Procedure() {

        /**
         * INIT FIELDS
         */
        /* Fields from StartApplication */
        functions = StartApplication.getFunctions();
        procedureController = StartApplication.getProcedureController();

        /* Fields from StartApplication */
        chosenScreenIndex = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getChosenScreenIndex();
        screenResolutionX = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getScreenResolutionX();
        screenResolutionY = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getScreenResolutionY();
        screenWidthInMM = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getScreenWidth();
        screenHeightInMM = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getScreenHeight();
        involvedVisualFieldX = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getInvolvedVisualFieldX();
        involvedVisualFieldY = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getInvolvedVisualFieldY();
        luminanceScaleForStimulus = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getStimulusLuminanceScale();
        luminanceScaleForBackground = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getBackgroundLuminanceScale();

        /* Fields from StimulusAndBackground */
        stimulusMaxBrightness = StartApplication.getSpecvisData().getStimulusAndBackground().getStimulusMaxBrightness();
        stimulusMinBrightness = StartApplication.getSpecvisData().getStimulusAndBackground().getStimulusMinBrightness();
        stimulusShape = StartApplication.getSpecvisData().getStimulusAndBackground().getStimulusShape();
        stimulusSizeInDegreesX = StartApplication.getSpecvisData().getStimulusAndBackground().getStimulusSizeX();
        stimulusSizeInDegreesY = StartApplication.getSpecvisData().getStimulusAndBackground().getStimulusSizeY();
        stimulusInclination = StartApplication.getSpecvisData().getStimulusAndBackground().getStimulusInclination();
        stimulusDisplayTime = StartApplication.getSpecvisData().getStimulusAndBackground().getStimulusDisplayTime();
        interStimuliIntervalConstantPart = StartApplication.getSpecvisData().getStimulusAndBackground().getConstantPartOfInterval();
        interStimuliIntervalRandomPart = StartApplication.getSpecvisData().getStimulusAndBackground().getRandomPartOfInterval();
        backgroundBrightness = StartApplication.getSpecvisData().getStimulusAndBackground().getBackgroundBrightness();
        distanceBetweenStimuliInDegreesX = StartApplication.getSpecvisData().getStimulusAndBackground().getDistanceBetweenStimuliInDegreesX();
        distanceBetweenStimuliInDegreesY = StartApplication.getSpecvisData().getStimulusAndBackground().getDistanceBetweenStimuliInDegreesY();
        isCorrectionForSphericityCheckBoxChecked = StartApplication.getSpecvisData().getStimulusAndBackground().isCorrectionForSphericityCheckBoxChecked();

        /* Fields from FixationAndOther */
        fixationPointColor = StartApplication.getSpecvisData().getFixationAndOther().getFixationPointColor();
        fixationPointSizeX = StartApplication.getSpecvisData().getFixationAndOther().getFixationPointSizeX();
        fixationPointSizeY = StartApplication.getSpecvisData().getFixationAndOther().getFixationPointSizeY();
        fixationMonitorTechnique = StartApplication.getSpecvisData().getFixationAndOther().getFixationMonitorTechnique();
        fixationPointLocationX = StartApplication.getSpecvisData().getFixationAndOther().getFixationPointLocationX();
        fixationPointLocationY = StartApplication.getSpecvisData().getFixationAndOther().getFixationPointLocationY();
        keyAnswerToStimulus = StartApplication.getSpecvisData().getFixationAndOther().getAnswerToStimulus();
        keyPauseProcedure = StartApplication.getSpecvisData().getFixationAndOther().getPauseProcedure();
        keyCancelProcedure = StartApplication.getSpecvisData().getFixationAndOther().getCancelProcedure();

        /* Fields from FixationAndOther -> fixation monitor "blindspot" */
        if (fixationMonitorTechnique.equals("Blindspot")) {
            FixationAndOtherMonitorSettingsBlindspot faomsb = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsBlindspot();

            b_isMonitorFixationEveryXStimuliSelected = faomsb.isMonitorFixationEveryXStimuliSelected();
            b_monitorFixationEveryXStimuli = faomsb.getMonitorFixationEveryXStimuli();
            b_monitorFixationEveryXYStimuli_1 = faomsb.getMonitorFixationEveryXYStimuli_1();
            b_monitorFixationEveryXYStimuli_2 = faomsb.getMonitorFixationEveryXYStimuli_2();
            b_fixationMonitorStimulusSizeX = faomsb.getFixationMonitorStimulusSizeX();
            b_fixationMonitorStimulusSizeY = faomsb.getFixationMonitorStimulusSizeY();
            b_fixationMonitorStimulusBrightness = faomsb.getFixationMonitorStimulusBrightness();
            b_blindspotDistanceFromFixPointX = faomsb.getBlindspotDistanceFromFixPointX();
            b_blindspotDistanceFromFixPointY = faomsb.getBlindspotDistanceFromFixPointY();

            b_isShowPatientMsgSelected = faomsb.isShowPatientMsgSelected();
            if (b_isShowPatientMsgSelected) {
                b_textOfTheMsg = faomsb.getTextOfTheMsg();
                b_fontSizeOfTheMsg = faomsb.getFontSize();
                b_fontWeightOfTheMsg = faomsb.getFontWeight();
                b_fontColorOfTheMsg = faomsb.getFontColor();
                b_msgBackgroundColor = faomsb.getMsgBackgroundColor();
                b_msgBackgroundSizeX = faomsb.getMsgBackgroundSizeX();
                b_msgBackgroundSizeY = faomsb.getMsgBackgroundSizeY();
                b_msgDistanceFromFixPointX = faomsb.getMsgDistanceFromFixPointX();
                b_msgDistanceFromFixPointY = faomsb.getMsgDistanceFromFixPointY();
                b_resumeToNextStimulusTimeInterval = faomsb.getResumeToNextStimulusTimeInterval();
            }
        }

        /* Fields from FixationAndOther -> fixation monitor "fixation point change" */
        if (fixationMonitorTechnique.equals("Fixation point change")) {
            FixationAndOtherMonitorSettingsFixPointChange faomsfpc = StartApplication.getSpecvisData().getFixationAndOtherMonitorSettingsFixPointChange();

            f_isMonitorFixationEveryXStimuliSelected = faomsfpc.isMonitorFixationEveryXStimuliSelected();
            f_monitorFixationEveryXStimuli = faomsfpc.getMonitorFixationEveryXStimuli();
            f_monitorFixationEveryXYStimuli_1 = faomsfpc.getMonitorFixationEveryXYStimuli_1();
            f_monitorFixationEveryXYStimuli_2 = faomsfpc.getMonitorFixationEveryXYStimuli_2();
            f_changedFixPointSizeX = faomsfpc.getChangedFixPointSizeX();
            f_changedFixPointSizeY = faomsfpc.getChangedFixPointSizeY();
            f_changedFixPointColor = faomsfpc.getChangedFixPointColor();

            f_isShowPatientMsgSelected = faomsfpc.isShowPatientMsgSelected();
            if (f_isShowPatientMsgSelected) {
                f_textOfTheMsg = faomsfpc.getTextOfTheMsg();
                f_fontSizeOfTheMsg = faomsfpc.getFontSize();
                f_fontWeightOfTheMsg = faomsfpc.getFontWeight();
                f_fontColorOfTheMsg = faomsfpc.getFontColor();
                f_msgBackgroundColor = faomsfpc.getMsgBackgroundColor();
                f_msgBackgroundSizeX = faomsfpc.getMsgBackgroundSizeX();
                f_msgBackgroundSizeY = faomsfpc.getMsgBackgroundSizeY();
                f_msgDistanceFromFixPointX = faomsfpc.getMsgDistanceFromFixPointX();
                f_msgDistanceFromFixPointY = faomsfpc.getMsgDistanceFromFixPointY();
                f_resumeToNextStimulusTimeInterval = faomsfpc.getResumeToNextStimulusTimeInterval();
            }
        }

        /* Local fields */
        displayPane = initializeDisplayPane();
        randomGenerator = new Random();

        if (!fixationMonitorTechnique.equals("None")) {

            fixationMonitorIterator = 0;

            switch (fixationMonitorTechnique) {
                case "Blindspot":
                    if (b_isMonitorFixationEveryXStimuliSelected) {
                        fixationMonitorCheckRate = b_monitorFixationEveryXStimuli;
                    } else {
                        fixationMonitorCheckRate = functions.randomInt(b_monitorFixationEveryXYStimuli_1, b_monitorFixationEveryXYStimuli_2);
                    }
                    break;
                case "Fixation point change":
                    if (f_isMonitorFixationEveryXStimuliSelected) {
                        fixationMonitorCheckRate = f_monitorFixationEveryXStimuli;
                    } else {
                        fixationMonitorCheckRate = functions.randomInt(f_monitorFixationEveryXYStimuli_1, f_monitorFixationEveryXYStimuli_2);
                    }
                    break;
            }

            answersToFixationMonitor = new ArrayList<>();
        }

        pixelsForOneDegreeX = screenResolutionX / involvedVisualFieldX;
        pixelsForOneDegreeY = screenResolutionY / involvedVisualFieldY;
        centerOfTheGridInPixelsX = (screenResolutionX / 2) + (pixelsForOneDegreeX * fixationPointLocationX);
        centerOfTheGridInPixelsY = (screenResolutionY / 2) + (pixelsForOneDegreeY * fixationPointLocationY);

        /* Local fields -> procedure state indicators */
        procedureIsStarted = false;
        procedureIsRunning = false;
        procedureIsFinished = false;
        permissionToAnswer = false;

        /* Local fields -> displaying stimuli and writing answers */
        spreadValuesLogarithmically = StartApplication.getSpecvisData().getBasicProcedureSettings().isSpreadValuesLogarithmically();
        visualFieldTestBrightnessVectorLength = StartApplication.getSpecvisData().getBasicProcedureSettings().getVisualFieldTestBrightnessVectorLength();

        brightnessValuesToTest = new int[visualFieldTestBrightnessVectorLength];
        ArrayList<Double> vector;
        if (spreadValuesLogarithmically) {
            vector = functions.logspace(stimulusMinBrightness, stimulusMaxBrightness, visualFieldTestBrightnessVectorLength, true);
        } else {
            vector = functions.linspace(stimulusMinBrightness, stimulusMaxBrightness, visualFieldTestBrightnessVectorLength, true);
        }
        for (int i = 0; i < brightnessValuesToTest.length; i++) {
            double d = vector.get(i);
            brightnessValuesToTest[i] = (int) d;
        }

        wasReminderMsgAfterFixLossShown = false;

        stimuliList = createListOfStimuli();
        activeStimuliIndices = new ArrayList<>();
        activeStimuliIndices.addAll(stimuliList.stream().map(BasicProcedureStimulus::getIndex).collect(Collectors.toList()));

        falsePositiveIndicator_PositiveAnswer = 0;
        falsePositiveIndicator_FalseAnswer = 0;

        /**
         * INIT WINDOW
         */
        this.setScene(new Scene(createContent()));

        /* Set window on the chosen screen. */
        List<Screen> screens = Screen.getScreens();
        Screen chosenScreen = screens.get(chosenScreenIndex);
        Rectangle2D chosenScreenBounds = chosenScreen.getVisualBounds();

        this.setX(chosenScreenBounds.getMinX());
        this.setY(chosenScreenBounds.getMinY());

        this.setWidth(chosenScreenBounds.getWidth());
        this.setHeight(chosenScreenBounds.getHeight());

        this.initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);

        /**
         * DEFINE ACTION FOR "CANCEL PROCEDURE" KEY
         */
        this.getScene().addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.valueOf(keyCancelProcedure)) {
                if (procedureTimeline != null) {
                    procedureTimeline.stop();
                }
                if (stimulusTimeline != null) {
                    stimulusTimeline.stop();
                }
                if (fixationMonitorTimeline != null) {
                    fixationMonitorTimeline.stop();
                }

                if (activeStimuliIndices.size() == 0) {
                    close();
                } else {
                    procedureController.setColorForCircle(Color.web("#FF5555"));
                    procedureController.setTextForLabel("Canceled");
                    close();
                }
            }
        });

        /**
         * DEFINE ACTION FOR "ANSWER TO STIMULUS" AND "PAUSE PROCEDURE" KEYS
         */
        this.getScene().addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
            if (!procedureIsStarted) {
                if (ke.getCode() == KeyCode.valueOf(keyAnswerToStimulus)) {

                    displayPane.getChildren().remove(1);

                    timeOfTheStartOfTheProcedure = System.currentTimeMillis();
                    procedureIsRunning = true;
                    procedureIsStarted = true;
                    runProcedureTimeline();
                    procedureController.setColorForCircle(Color.web("#33FF33"));
                    procedureController.setTextForLabel("Running");
                }
            } else {
                if (ke.getCode() == KeyCode.valueOf(keyAnswerToStimulus)) {
                    if (procedureIsRunning) {
                        if (permissionToAnswer) {

                            falsePositiveIndicator_PositiveAnswer += 1;

                            if (fixationMonitorTechnique.equals("None")) {
                                for (int i = 0; i < currentlyDisplayedStimulus.getAnswers().length; i++) {
                                    if (currentlyDisplayedStimulus.getAnswers()[i] == 0) {
                                        currentlyDisplayedStimulus.getAnswers()[i] = 2;
                                        break;
                                    }
                                }
                            } else {
                                if (fixationMonitorIterator != fixationMonitorCheckRate) {
                                    for (int i = 0; i < currentlyDisplayedStimulus.getAnswers().length; i++) {
                                        if (currentlyDisplayedStimulus.getAnswers()[i] == 0) {
                                            currentlyDisplayedStimulus.getAnswers()[i] = 2;
                                            break;
                                        }
                                    }
                                } else {
                                    answersToFixationMonitor.add(true);
                                }
                            }
                            permissionToAnswer = false;
                        } else {
                            falsePositiveIndicator_FalseAnswer += 1;
                        }
                    }
                } else if (ke.getCode() == KeyCode.valueOf(keyPauseProcedure)) {
                    if (procedureIsRunning) {

                        drawProcedureStateIndicator("Triangle");

                        procedureIsRunning = false;

                        if (fixationMonitorTechnique.equals("None")) {
                            if (stimulusTimeline != null) {
                                stimulusTimeline.pause();
                                procedureController.setColorForCircle(Color.web("#FFAA00"));
                                procedureController.setTextForLabel("Paused");
                            }
                        } else {
                            if (fixationMonitorIterator != fixationMonitorCheckRate) {
                                if (stimulusTimeline != null) {
                                    stimulusTimeline.pause();
                                    procedureController.setColorForCircle(Color.web("#FFAA00"));
                                    procedureController.setTextForLabel("Paused");
                                }
                            } else {
                                if (fixationMonitorTimeline != null) {
                                    fixationMonitorTimeline.pause();
                                    procedureController.setColorForCircle(Color.web("#FFAA00"));
                                    procedureController.setTextForLabel("Paused");
                                }
                            }
                        }
                    } else {

                        displayPane.getChildren().remove(1);

                        procedureIsRunning = true;

                        if (fixationMonitorTechnique.equals("None")) {
                            if (stimulusTimeline != null) {
                                stimulusTimeline.play();
                                procedureController.setColorForCircle(Color.web("#33FF33"));
                                procedureController.setTextForLabel("Running");
                            }
                        } else {
                            if (fixationMonitorIterator != fixationMonitorCheckRate) {
                                if (stimulusTimeline != null) {
                                    stimulusTimeline.play();
                                    procedureController.setColorForCircle(Color.web("#33FF33"));
                                    procedureController.setTextForLabel("Running");
                                }
                            } else {
                                if (fixationMonitorTimeline != null) {
//                                    if (isMsgAfterFixLossDisplayedOnTheScreen) {
//                                        displayPane.getChildren().remove(1);
//                                        isMsgAfterFixLossDisplayedOnTheScreen = false;
//                                    }
                                    fixationMonitorTimeline.play();
                                    procedureController.setColorForCircle(Color.web("#33FF33"));
                                    procedureController.setTextForLabel("Running");
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private ArrayList<BasicProcedureStimulus> createListOfStimuli() {
        ArrayList<BasicProcedureStimulus> arrayList = new ArrayList<>();

        /** Quarters:
         *  Q0  |  Q1
         *  ---------
         *  Q3  |  Q2
         */

        /** Indexing stimuli rule:
         *  03 01 | 05 07
         *  02 00 | 04 06
         *  -------------
         *  14 12 | 08 10
         *  15 13 | 09 11
         *
         */
        if (isCorrectionForSphericityCheckBoxChecked) {

            BasicProcedureStimulus basicProcedureStimulus;
            int stimulusIndexIterator = 0;

            // Prepare stimuli for Quarter 0.
            double angleX = distanceBetweenStimuliInDegreesX / 2;
            double angleY = distanceBetweenStimuliInDegreesY / 2;

            double r = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getPatientDistanceFromTheScreen();

            double oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
            double oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);

            double oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResolutionX, screenWidthInMM);
            double oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

            double tempLocationX = centerOfTheGridInPixelsX - oppAngleInPixelsX;
            double tempLocationY = centerOfTheGridInPixelsY - oppAngleInPixelsY;

            double[] correctedStimulusSizeInPixelsXY = new double[] {
                    stimulusSizeInDegreesX * pixelsForOneDegreeX,
                    stimulusSizeInDegreesY * pixelsForOneDegreeY
            };

            while (tempLocationX >= 0) {
                while (tempLocationY >= 0) {

//                    double[] correctedStimulusSizeInPixelsXY = calculateCorrectedStimulusSizeInPixelsXY(
//                            angleX, angleY, stimulusSizeInDegreesX, stimulusSizeInDegreesY, screenResolutionX, screenResolutionY,
//                            screenWidthInMM, screenHeightInMM, r
//                    );

                    basicProcedureStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(basicProcedureStimulus);

                    angleY += distanceBetweenStimuliInDegreesY;
                    oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                    oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

                    tempLocationY = centerOfTheGridInPixelsY - oppAngleInPixelsY;
                    stimulusIndexIterator += 1;
                }

                angleX += distanceBetweenStimuliInDegreesX;
                oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
                oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResolutionX, screenWidthInMM);

                tempLocationX = centerOfTheGridInPixelsX - oppAngleInPixelsX;

                angleY = distanceBetweenStimuliInDegreesY / 2;
                oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

                tempLocationY = centerOfTheGridInPixelsY - oppAngleInPixelsY;
            }

            // Prepare stimuli for Quarter 1.
            angleX = distanceBetweenStimuliInDegreesX / 2;
            angleY = distanceBetweenStimuliInDegreesY / 2;

            oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
            oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);

            oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResolutionX, screenWidthInMM);
            oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

            tempLocationX = centerOfTheGridInPixelsX + oppAngleInPixelsX;
            tempLocationY = centerOfTheGridInPixelsY - oppAngleInPixelsY;

            while (tempLocationX <= screenResolutionX) {
                while (tempLocationY >= 0) {

//                    double[] correctedStimulusSizeInPixelsXY = calculateCorrectedStimulusSizeInPixelsXY(
//                            angleX, angleY, stimulusSizeInDegreesX, stimulusSizeInDegreesY, screenResolutionX, screenResolutionY,
//                            screenWidthInMM, screenHeightInMM, r
//                    );

                    basicProcedureStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(basicProcedureStimulus);

                    angleY += distanceBetweenStimuliInDegreesY;
                    oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                    oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

                    tempLocationY = centerOfTheGridInPixelsY - oppAngleInPixelsY;
                    stimulusIndexIterator += 1;
                }

                angleX += distanceBetweenStimuliInDegreesX;
                oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
                oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResolutionX, screenWidthInMM);

                tempLocationX = centerOfTheGridInPixelsX + oppAngleInPixelsX;

                angleY = distanceBetweenStimuliInDegreesY / 2;
                oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

                tempLocationY = centerOfTheGridInPixelsY - oppAngleInPixelsY;
            }

            // Prepare stimuli for Quarter 2.
            angleX = distanceBetweenStimuliInDegreesX / 2;
            angleY = distanceBetweenStimuliInDegreesY / 2;

            oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
            oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);

            oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResolutionX, screenWidthInMM);
            oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

            tempLocationX = centerOfTheGridInPixelsX + oppAngleInPixelsX;
            tempLocationY = centerOfTheGridInPixelsY + oppAngleInPixelsY;

            while (tempLocationX <= screenResolutionX) {
                while (tempLocationY <= screenResolutionY) {

//                    double[] correctedStimulusSizeInPixelsXY = calculateCorrectedStimulusSizeInPixelsXY(
//                            angleX, angleY, stimulusSizeInDegreesX, stimulusSizeInDegreesY, screenResolutionX, screenResolutionY,
//                            screenWidthInMM, screenHeightInMM, r
//                    );

                    basicProcedureStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(basicProcedureStimulus);

                    angleY += distanceBetweenStimuliInDegreesY;
                    oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                    oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

                    tempLocationY = centerOfTheGridInPixelsY + oppAngleInPixelsY;
                    stimulusIndexIterator += 1;
                }

                angleX += distanceBetweenStimuliInDegreesX;
                oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
                oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResolutionX, screenWidthInMM);

                tempLocationX = centerOfTheGridInPixelsX + oppAngleInPixelsX;

                angleY = distanceBetweenStimuliInDegreesY / 2;
                oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

                tempLocationY = centerOfTheGridInPixelsY + oppAngleInPixelsY;
            }

            // Prepare stimuli for Quarter 3.
            angleX = distanceBetweenStimuliInDegreesX / 2;
            angleY = distanceBetweenStimuliInDegreesY / 2;

            oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
            oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);

            oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResolutionX, screenWidthInMM);
            oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

            tempLocationX = centerOfTheGridInPixelsX - oppAngleInPixelsX;
            tempLocationY = centerOfTheGridInPixelsY + oppAngleInPixelsY;

            while (tempLocationX >= 0) {
                while (tempLocationY <= screenResolutionY) {

//                    double[] correctedStimulusSizeInPixelsXY = calculateCorrectedStimulusSizeInPixelsXY(
//                            angleX, angleY, stimulusSizeInDegreesX, stimulusSizeInDegreesY, screenResolutionX, screenResolutionY,
//                            screenWidthInMM, screenHeightInMM, r
//                    );

                    basicProcedureStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(basicProcedureStimulus);

                    angleY += distanceBetweenStimuliInDegreesY;
                    oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                    oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

                    tempLocationY = centerOfTheGridInPixelsY + oppAngleInPixelsY;
                    stimulusIndexIterator += 1;
                }

                angleX += distanceBetweenStimuliInDegreesX;
                oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
                oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResolutionX, screenWidthInMM);

                tempLocationX = centerOfTheGridInPixelsX - oppAngleInPixelsX;

                angleY = distanceBetweenStimuliInDegreesY / 2;
                oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResolutionY, screenHeightInMM);

                tempLocationY = centerOfTheGridInPixelsY + oppAngleInPixelsY;
            }
        } else {

            BasicProcedureStimulus basicProcedureStimulus;
            int stimulusIndexIterator = 0;

            double distanceBetweenStimuliInPixelsX = pixelsForOneDegreeX * distanceBetweenStimuliInDegreesX;
            double distanceBetweenStimuliInPixelsY = pixelsForOneDegreeY * distanceBetweenStimuliInDegreesY;

            // Prepare stimuli for Quarter 0.
            double tempLocationX = centerOfTheGridInPixelsX - (distanceBetweenStimuliInPixelsX / 2);
            double tempLocationY = centerOfTheGridInPixelsY - (distanceBetweenStimuliInPixelsY / 2);

            double[] correctedStimulusSizeInPixelsXY = new double[] {
                    stimulusSizeInDegreesX * pixelsForOneDegreeX,
                    stimulusSizeInDegreesY * pixelsForOneDegreeY
            };

            while (tempLocationX >= 0) {
                while (tempLocationY >= 0) {

                    basicProcedureStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(basicProcedureStimulus);

                    tempLocationY -= distanceBetweenStimuliInPixelsY;
                    stimulusIndexIterator += 1;
                }

                tempLocationX -= distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridInPixelsY - (distanceBetweenStimuliInPixelsY / 2);
            }

            // Prepare stimuli for Quarter 1.
            tempLocationX = centerOfTheGridInPixelsX + (distanceBetweenStimuliInPixelsX / 2);
            tempLocationY = centerOfTheGridInPixelsY - (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX <= screenResolutionX) {
                while (tempLocationY >= 0) {

                    basicProcedureStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(basicProcedureStimulus);

                    tempLocationY -= distanceBetweenStimuliInPixelsY;
                    stimulusIndexIterator += 1;
                }

                tempLocationX += distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridInPixelsY - (distanceBetweenStimuliInPixelsY / 2);
            }

            // Prepare stimuli for Quarter 2.
            tempLocationX = centerOfTheGridInPixelsX + (distanceBetweenStimuliInPixelsX / 2);
            tempLocationY = centerOfTheGridInPixelsY + (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX <= screenResolutionX) {
                while (tempLocationY <= screenResolutionY) {

                    basicProcedureStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(basicProcedureStimulus);

                    tempLocationY += distanceBetweenStimuliInPixelsY;
                    stimulusIndexIterator += 1;
                }

                tempLocationX += distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridInPixelsY + (distanceBetweenStimuliInPixelsY / 2);
            }

            // Prepare stimuli for Quarter 3.
            tempLocationX = centerOfTheGridInPixelsX - (distanceBetweenStimuliInPixelsX / 2);
            tempLocationY = centerOfTheGridInPixelsY + (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX >= 0) {
                while (tempLocationY <= screenResolutionY) {

                    basicProcedureStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(basicProcedureStimulus);

                    tempLocationY += distanceBetweenStimuliInPixelsY;
                    stimulusIndexIterator += 1;
                }

                tempLocationX -= distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridInPixelsY + (distanceBetweenStimuliInPixelsY / 2);
            }
        }
        return arrayList;
    }

    /**
     * Description of the method.
     *
     * C - Center of the grid;
     * D - Center of the stimulus;
     * A - Left edge of the stimulus;
     * B - right edge of the stimulus;
     *
     * Calculating corrected size of the stimulus X:
     *
     * X            X   X   X
     * C            A   D   B
     *
     * 1. Calculate angle between "CA", that is "angleDistanceFromCenterX - (stimulusSizeInDegreesX / 2)".
     * 2. Calculate angle between "CB", that is "angleDistanceFromCenterX + (stimulusSizeInDegreesX / 2)".
     * 3. Calculate opposites in pixels for angles "CA" and "CB", eg. respectively "OCA" and "OCB".
     * 4. Calculate "OCB - OCA". Result is corrected stimulus size in pixels X.
     *
     * @param angleDistanceFromCenterX
     * @param angleDistanceFromCenterY
     * @param stimulusSizeInDegreesX
     * @param stimulusSizeInDegreesY
     * @param patientDistanceFromTheScreenInMM
     * @return
     */
    private double[] calculateCorrectedStimulusSizeInPixelsXY(double angleDistanceFromCenterX, double angleDistanceFromCenterY,
                                                    double stimulusSizeInDegreesX, double stimulusSizeInDegreesY,
                                                    int screenResolutionX, int screenResolutionY,
                                                    int screenWidthInMM, int screenHeightInMM,
                                                    double patientDistanceFromTheScreenInMM) {

        double[] stimulusSizeInPixelsXY = new double[2];

        // Calculate stimulus size in pixels X.
        double CA_X = angleDistanceFromCenterX - (stimulusSizeInDegreesX / 2);
        double CB_X = angleDistanceFromCenterX + (stimulusSizeInDegreesX / 2);

        double OCA_in_MM_X = functions.calculateOppositeAngle(CA_X, patientDistanceFromTheScreenInMM);
        double OCB_in_MM_X = functions.calculateOppositeAngle(CB_X, patientDistanceFromTheScreenInMM);

        double OCA_in_pixels_X = functions.millimitersToPixels(OCA_in_MM_X, screenResolutionX, screenWidthInMM);
        double OCB_in_pixels_X = functions.millimitersToPixels(OCB_in_MM_X, screenResolutionX, screenWidthInMM);

        stimulusSizeInPixelsXY[0] = OCB_in_pixels_X - OCA_in_pixels_X;

        // Calculate stimulus size in pixels Y.
        double CA_Y = angleDistanceFromCenterY - (stimulusSizeInDegreesY / 2);
        double CB_Y = angleDistanceFromCenterY + (stimulusSizeInDegreesY / 2);

        double OCA_in_MM_Y = functions.calculateOppositeAngle(CA_Y, patientDistanceFromTheScreenInMM);
        double OCB_in_MM_Y = functions.calculateOppositeAngle(CB_Y, patientDistanceFromTheScreenInMM);

        double OCA_in_pixels_Y = functions.millimitersToPixels(OCA_in_MM_Y, screenResolutionY, screenHeightInMM);
        double OCB_in_pixels_Y = functions.millimitersToPixels(OCB_in_MM_Y, screenResolutionY, screenHeightInMM);

        stimulusSizeInPixelsXY[1] = OCB_in_pixels_Y - OCA_in_pixels_Y;

        return stimulusSizeInPixelsXY;
    }
    
    private BasicProcedureStimulus initializeStimulus(int stimulusIndexIterator, double tempLocationX, double tempLocationY, double[] correctedStimulusSizeInPixelsXY) {

        BasicProcedureStimulus basicProcedureStimulus = new BasicProcedureStimulus();
        basicProcedureStimulus.setIndex(stimulusIndexIterator);
        basicProcedureStimulus.setPositionOnTheScreenInPixelsX(tempLocationX);
        basicProcedureStimulus.setPositionOnTheScreenInPixelsY(tempLocationY);
        basicProcedureStimulus.setDistanceFromFixPointOnTheFieldOfViewInDegreesX((tempLocationX - centerOfTheGridInPixelsX) / pixelsForOneDegreeX);
        basicProcedureStimulus.setDistanceFromFixPointOnTheFieldOfViewInDegreesY((tempLocationY - centerOfTheGridInPixelsY) / pixelsForOneDegreeY);

        switch (stimulusShape) {
            case "Ellipse":
                basicProcedureStimulus.setShape(createEllipseStimulus(tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY));
                break;
            case "Polygon":
                basicProcedureStimulus.setShape(createPolygonStimulus(tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY));
                break;
        }
        basicProcedureStimulus.setDisplayTime(stimulusDisplayTime);

        /*
        Depending on the value of "visualFieldTestBrightnessVectorLength" (n) Specvis sets different value
        for "lengthOfVectorForAnswersToStimulus" (x). Basically, Specvis calculates "x" value depending on
        "n" value, based on following equation: "x = (n + 7) / 4". For instance, for "n = 9", "x = 4",
         because "(9 + 7) / 4 = 4".
         */
        int lengthOfVectorForAnswersToStimulus = (visualFieldTestBrightnessVectorLength + 7) / 4;

        basicProcedureStimulus.setAnswers(new int[lengthOfVectorForAnswersToStimulus]);
        for (int i = 0; i < basicProcedureStimulus.getAnswers().length; i++) {
            basicProcedureStimulus.getAnswers()[i] = 0;
        }

        basicProcedureStimulus.setBrightnessThreshold(0);
        basicProcedureStimulus.setLuminanceThreshold(0);
        basicProcedureStimulus.setDecibelThreshold(0);

        return basicProcedureStimulus;
    }

    private Ellipse createEllipseStimulus(double positionX, double positionY, double[] correctedStimulusSizeInPixelsXY) {

        // 1. Calculate stimulus radius in pixels.
//        double stimulusRadiusInPixelsX = (stimulusSizeInDegreesX / 2) * pixelsForOneDegreeX;
//        double stimulusRadiusInPixelsY = (stimulusSizeInDegreesY / 2) * pixelsForOneDegreeY;

        double stimulusRadiusInPixelsX = correctedStimulusSizeInPixelsXY[0] / 2;
        double stimulusRadiusInPixelsY = correctedStimulusSizeInPixelsXY[1] / 2;

        // 2. Set color for stimulus.
        double hue = luminanceScaleForStimulus.getHue();
        double saturation = luminanceScaleForStimulus.getSaturation() / 100;
        double brightness = stimulusMaxBrightness / 100;
        Color color = Color.hsb(hue, saturation, brightness);

        // 3. Create stimulus preview shape.
        Ellipse ellipse;
        ellipse = new Ellipse(positionX, positionY, stimulusRadiusInPixelsX, stimulusRadiusInPixelsY);
        ellipse.setFill(color);
        ellipse.setStroke(color);

        return ellipse;
    }

    private Polygon createPolygonStimulus(double positionX, double positionY, double[] correctedStimulusSizeInPixelsXY) {

        // 1. Calculate stimulus radius in pixels.
//        double stimulusSizeInPixelsX = stimulusSizeInDegreesX * pixelsForOneDegreeX;
//        double stimulusSizeInPixelsY = stimulusSizeInDegreesY * pixelsForOneDegreeY;

        double stimulusSizeInPixelsX = correctedStimulusSizeInPixelsXY[0];
        double stimulusSizeInPixelsY = correctedStimulusSizeInPixelsXY[1];

        // 2. Calculate stimulus diagonal.
        double diagonal = Math.sqrt(Math.pow(stimulusSizeInPixelsX, 2) + Math.pow(stimulusSizeInPixelsY, 2));

        // 3. Calculate polygon inner angle and positions of ABCD points in reference to stimulus inclination.
        double x = positionX + stimulusSizeInPixelsX;
        double y = positionY - stimulusSizeInPixelsY;

        double polygonInnerAngle = Math.toDegrees(Math.atan2(x - positionX, positionY - y));

        double positionAx = positionX + ((diagonal / 2) * Math.cos(Math.toRadians(stimulusInclination + 270 + (90 - polygonInnerAngle) - 90)));
        double positionAy = positionY + ((diagonal / 2) * Math.sin(Math.toRadians(stimulusInclination + 270 + (90 - polygonInnerAngle) - 90)));

        double positionBx = positionX + ((diagonal / 2) * Math.cos(Math.toRadians(stimulusInclination + polygonInnerAngle - 90)));
        double positionBy = positionY + ((diagonal / 2) * Math.sin(Math.toRadians(stimulusInclination + polygonInnerAngle - 90)));

        double positionCx = positionX + ((diagonal / 2) * Math.cos(Math.toRadians(stimulusInclination + 90 + (90 - polygonInnerAngle) - 90)));
        double positionCy = positionY + ((diagonal / 2) * Math.sin(Math.toRadians(stimulusInclination + 90 + (90 - polygonInnerAngle) - 90)));

        double positionDx = positionX + ((diagonal / 2) * Math.cos(Math.toRadians(stimulusInclination + 180 + polygonInnerAngle - 90)));
        double positionDy = positionY + ((diagonal / 2) * Math.sin(Math.toRadians(stimulusInclination + 180 + polygonInnerAngle - 90)));

        // 4. Set color for stimulus.
        double hue = luminanceScaleForStimulus.getHue();
        double saturation = luminanceScaleForStimulus.getSaturation() / 100;
        double brightness = stimulusMaxBrightness / 100;
        Color color = Color.hsb(hue, saturation, brightness);

        // 5. Create stimulus preview shape.
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(positionAx, positionAy, positionBx, positionBy, positionCx, positionCy, positionDx, positionDy);
        polygon.setFill(color);

        return polygon;
    }

    public void runProcedureTimeline() {

        procedureTimeline = new Timeline();

        KeyFrame start = new KeyFrame(Duration.millis(1000), event -> {
            runStimulusTimeline();
            procedureTimeline.stop();
        });
        procedureTimeline.getKeyFrames().add(start);
        procedureTimeline.play();
    }

    public void runStimulusTimeline() {

        stimulusTimeline = new Timeline();

        int r = randomGenerator.nextInt(activeStimuliIndices.size());
        int index = activeStimuliIndices.get(r);
        currentlyDisplayedStimulus = stimuliList.get(index);

        KeyFrame displayStimulus = new KeyFrame(Duration.millis(0), event -> {
            permissionToAnswer = true;

            // Set stimulus brightness value based on provided answers.
            int brightnessLevel = getIndexOfNextStimulusBrightness(currentlyDisplayedStimulus.getAnswers());

            Color newColor = Color.hsb((int) functions.round(luminanceScaleForStimulus.getHue(), 0),
                    luminanceScaleForStimulus.getSaturation() / 100,
                    (double) brightnessLevel / 100);

            currentlyDisplayedStimulus.getShape().setFill(newColor);
            currentlyDisplayedStimulus.getShape().setStroke(newColor);

            displayPane.getChildren().add(currentlyDisplayedStimulus.getShape());
        });
        stimulusTimeline.getKeyFrames().add(displayStimulus);

        KeyFrame removeStimulus = new KeyFrame(Duration.millis(currentlyDisplayedStimulus.getDisplayTime()), event -> {
            if (displayPane.getChildren().size() > 1) {
                displayPane.getChildren().remove(1);
            }
        });
        stimulusTimeline.getKeyFrames().add(removeStimulus);

        int interval = functions.randomInterval(interStimuliIntervalConstantPart, interStimuliIntervalRandomPart);

        KeyFrame intervalBetweenStimuli = new KeyFrame(Duration.millis(interval), event -> {

            // Write NEGATIVE answer to stimulus.
            if (permissionToAnswer) {
                for (int i = 0; i < currentlyDisplayedStimulus.getAnswers().length; i++) {
                    if (currentlyDisplayedStimulus.getAnswers()[i] == 0) {
                        currentlyDisplayedStimulus.getAnswers()[i] = 1;
                        break;
                    }
                }
            }

            // Remove stimulus from active stimulus list if all posible answers were provided.
            deactivateStimulusIfBrightnessThresholdIsEvaluated(currentlyDisplayedStimulus.getAnswers());

            if (!fixationMonitorTechnique.equals("None")) {
                fixationMonitorIterator += 1;
            }

            // Recursion.
            if (!procedureIsFinished) {
                if (!fixationMonitorTechnique.equals("None")) {
                    if (fixationMonitorIterator != fixationMonitorCheckRate) {
                        stimulusTimeline.stop();
                        runStimulusTimeline();
                    } else {
                        stimulusTimeline.stop();
                        runFixationMonitorTimeline();
                    }
                } else {
                    stimulusTimeline.stop();
                    runStimulusTimeline();
                }
            }
        });
        stimulusTimeline.getKeyFrames().add(intervalBetweenStimuli);
        stimulusTimeline.play();
    }

    private void runFixationMonitorTimeline() {

        fixationMonitorTimeline = new Timeline();

        double radiusX;
        double radiusY;

        switch (fixationMonitorTechnique) {
            case "Blindspot":

                radiusX = (b_fixationMonitorStimulusSizeX / 2) * pixelsForOneDegreeX;
                radiusY = (b_fixationMonitorStimulusSizeY / 2) * pixelsForOneDegreeY;

                double positionX;
                double positionY;

                if (isCorrectionForSphericityCheckBoxChecked) {

                    double r = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getPatientDistanceFromTheScreen();

                    double ax = b_blindspotDistanceFromFixPointX;
                    double ay = b_blindspotDistanceFromFixPointY;

                    double mx = functions.calculateOppositeAngle(ax, r);
                    double my = functions.calculateOppositeAngle(ay, r);

                    double mxPixels = functions.millimitersToPixels(mx, screenResolutionX, screenWidthInMM);
                    double myPixels = functions.millimitersToPixels(my, screenResolutionY, screenHeightInMM);

                    positionX = centerOfTheGridInPixelsX + mxPixels;
                    positionY = centerOfTheGridInPixelsY + myPixels;

                } else {

                    positionX = centerOfTheGridInPixelsX + (b_blindspotDistanceFromFixPointX * pixelsForOneDegreeX);
                    positionY = centerOfTheGridInPixelsY + (b_blindspotDistanceFromFixPointY * pixelsForOneDegreeY);
                }

                double hue = luminanceScaleForStimulus.getHue();
                double saturation = luminanceScaleForStimulus.getSaturation() / 100;
                double brightness = Double.valueOf(b_fixationMonitorStimulusBrightness) / 100;
                Color color = Color.hsb(hue, saturation, brightness);

                fixationMonitorShape = new Ellipse(positionX, positionY, radiusX, radiusY);
                fixationMonitorShape.setFill(color);
                fixationMonitorShape.setStroke(color);

                break;
            case "Fixation point change":

                radiusX = (f_changedFixPointSizeX / 2) * pixelsForOneDegreeX;
                radiusY = (f_changedFixPointSizeY / 2) * pixelsForOneDegreeY;

                fixationMonitorShape = new Ellipse(centerOfTheGridInPixelsX, centerOfTheGridInPixelsY, radiusX, radiusY);
                fixationMonitorShape.setFill(f_changedFixPointColor);
                fixationMonitorShape.setStroke(f_changedFixPointColor);

                break;
        }

        KeyFrame displayFixationMonitorStimulus = new KeyFrame(Duration.millis(0), event -> {

            permissionToAnswer = true;

            switch (fixationMonitorTechnique) {
                case "Blindspot":
                    displayPane.getChildren().add(fixationMonitorShape);
                    break;
                case "Fixation point change":
                    if (displayPane.getChildren().size() == 1) {
                        displayPane.getChildren().remove(0);
                    }
                    displayPane.getChildren().add(fixationMonitorShape);
                    break;
            }
        });
        fixationMonitorTimeline.getKeyFrames().add(displayFixationMonitorStimulus);

        KeyFrame removeFixationMonitorStimulus = new KeyFrame(Duration.millis(stimulusDisplayTime), event -> {
            switch (fixationMonitorTechnique) {
                case "Blindspot":
                    if (displayPane.getChildren().size() > 1) {
                        displayPane.getChildren().remove(1);
                    }
                    break;
                case "Fixation point change":
                    if (displayPane.getChildren().size() == 1) {
                        displayPane.getChildren().remove(0);
                    }
                    drawFixationPoint();
                    break;
            }
        });
        fixationMonitorTimeline.getKeyFrames().add(removeFixationMonitorStimulus);

        int interval = functions.randomInterval(interStimuliIntervalConstantPart, interStimuliIntervalRandomPart) + stimulusDisplayTime;

        KeyFrame intervalBetweenStimuli = new KeyFrame(Duration.millis(interval), event -> {

            // Write NEGATIVE answer to fixation monitor.
            if (permissionToAnswer) {
                answersToFixationMonitor.add(false);
            }

            // Display message with loss of fixation for patient.
            switch (fixationMonitorTechnique) {
                case "Blindspot":
                    if (b_isShowPatientMsgSelected) {
                        if (!permissionToAnswer) {

                            // Show msg.
                            Label label = createMsgForPatientAfterFixLoss();
                            displayPane.getChildren().add(label);

                            // Pause procedure.
                            procedureIsRunning = false;

                            procedureController.setColorForCircle(Color.web("#FFAA00"));
                            procedureController.setTextForLabel("Fix loss");

                            //isMsgAfterFixLossDisplayedOnTheScreen = true;
                            wasReminderMsgAfterFixLossShown = true;

                            fixationMonitorTimeline.pause();
                        }
                    }
                    break;
                case "Fixation point change":
                    if (f_isShowPatientMsgSelected) {
                        if (permissionToAnswer) {

                            // Show msg.
                            Label label = createMsgForPatientAfterFixLoss();
                            displayPane.getChildren().add(label);

                            // Pause procedure.
                            procedureIsRunning = false;

                            procedureController.setColorForCircle(Color.web("#FFAA00"));
                            procedureController.setTextForLabel("Fix loss");

                            //isMsgAfterFixLossDisplayedOnTheScreen = true;
                            wasReminderMsgAfterFixLossShown = true;

                            System.out.println("Fixx Loss");

                            fixationMonitorTimeline.pause();
                        }
                    }
                    break;
            }
        });
        fixationMonitorTimeline.getKeyFrames().add(intervalBetweenStimuli);

        // TODO: WATCH OUT!
        int unsafeInterval = 100; // Watch out on this. If it is equal to 10 procedure might go insane. 100 is a safe value.
        KeyFrame runNextTimeline = new KeyFrame(Duration.millis(interval + unsafeInterval), event -> {
            runNextTimelineAfterShowingMsgAfterFixLoss();
        });

        fixationMonitorTimeline.getKeyFrames().add(runNextTimeline);
        fixationMonitorTimeline.play();
    }

    private void runNextTimelineAfterShowingMsgAfterFixLoss() {
        Timeline nextTimeline = new Timeline();

        int interval = 10;
        if (wasReminderMsgAfterFixLossShown) {
            switch (fixationMonitorTechnique) {
                case "Blindspot":
                    interval = b_resumeToNextStimulusTimeInterval;
                    wasReminderMsgAfterFixLossShown = false;
                    break;
                case "Fixation point change":
                    interval = f_resumeToNextStimulusTimeInterval;
                    wasReminderMsgAfterFixLossShown = false;
                    break;
            }
        }

        KeyFrame next = new KeyFrame(Duration.millis(interval), event -> {
            // Recursion.
            if (!procedureIsFinished) {
                fixationMonitorIterator = 0;
                switch (fixationMonitorTechnique) {
                    case "Blindspot":
                        if (!b_isMonitorFixationEveryXStimuliSelected) {
                            fixationMonitorCheckRate = functions.randomInt(b_monitorFixationEveryXYStimuli_1, b_monitorFixationEveryXYStimuli_2);
                        }
                        break;
                    case "Fixation point change":
                        if (!f_isMonitorFixationEveryXStimuliSelected) {
                            fixationMonitorCheckRate = functions.randomInt(f_monitorFixationEveryXYStimuli_1, f_monitorFixationEveryXYStimuli_2);
                        }
                        break;
                }
                // nextTimeline.stop();
                fixationMonitorTimeline.stop();
                runStimulusTimeline();
            }
        });

        nextTimeline.getKeyFrames().add(next);
        nextTimeline.play();
    }

    private Label createMsgForPatientAfterFixLoss() {

        double msgBoxWidthInPixels = 0;
        double msgBoxHeightInPixels = 0;

        double msgBoxLocationX = 0;
        double msgBoxLocationY = 0;

        String textOfTheMsg = "";
        String backgroundColor = "";
        String fontColor = "";
        String fontWeight = "";
        int fontSize = 0;

        switch (fixationMonitorTechnique) {
            case "Blindspot":

                msgBoxWidthInPixels = b_msgBackgroundSizeX * pixelsForOneDegreeX;
                msgBoxHeightInPixels = b_msgBackgroundSizeY * pixelsForOneDegreeY;

                msgBoxLocationX = centerOfTheGridInPixelsX + (b_msgDistanceFromFixPointX * pixelsForOneDegreeX) - (msgBoxWidthInPixels / 2);
                msgBoxLocationY = centerOfTheGridInPixelsY + (b_msgDistanceFromFixPointY * pixelsForOneDegreeY) - (msgBoxHeightInPixels / 2);

                textOfTheMsg = b_textOfTheMsg;
                backgroundColor = functions.toHexCode(b_msgBackgroundColor);
                fontColor = functions.toHexCode(b_fontColorOfTheMsg);
                fontWeight = b_fontWeightOfTheMsg;
                fontSize = b_fontSizeOfTheMsg;

                break;
            case "Fixation point change":

                msgBoxWidthInPixels = f_msgBackgroundSizeX * pixelsForOneDegreeX;
                msgBoxHeightInPixels = f_msgBackgroundSizeY * pixelsForOneDegreeY;

                msgBoxLocationX = centerOfTheGridInPixelsX + (f_msgDistanceFromFixPointX * pixelsForOneDegreeX) - (msgBoxWidthInPixels / 2);
                msgBoxLocationY = centerOfTheGridInPixelsY + (f_msgDistanceFromFixPointY * pixelsForOneDegreeY) - (msgBoxHeightInPixels / 2);

                textOfTheMsg = f_textOfTheMsg;
                backgroundColor = functions.toHexCode(f_msgBackgroundColor);
                fontColor = functions.toHexCode(f_fontColorOfTheMsg);
                fontWeight = f_fontWeightOfTheMsg;
                fontSize = f_fontSizeOfTheMsg;

                break;
        }

        Label msgBox = new Label();
        msgBox.setWrapText(true);
        msgBox.setAlignment(Pos.CENTER);
        msgBox.setTextAlignment(TextAlignment.CENTER);

        msgBox.setText(textOfTheMsg);
        msgBox.setMinSize(msgBoxWidthInPixels, msgBoxHeightInPixels);
        msgBox.setMaxSize(msgBoxWidthInPixels, msgBoxHeightInPixels);
        msgBox.setLayoutX(msgBoxLocationX);
        msgBox.setLayoutY(msgBoxLocationY);

        String style = "-fx-border-width: 2; " +
                "-fx-border-color: black; " +
                "-fx-border-style: solid; " +
                "-fx-background-color: " + backgroundColor + "; " +
                "-fx-text-fill: " + fontColor + "; " +
                "-fx-font-weight: " + fontWeight + ";" +
                "-fx-font-size: " + fontSize + "px;";
        msgBox.setStyle(style);

        return msgBox;
    }

    public int getIndexOfNextStimulusBrightness(int[] answers) {

        int brightnessIndex = visualFieldTestBrightnessVectorLength / 2;
        int latestAnswer = -1;

        for (int answer : answers) {
            if (answer != 0) {
                switch (answer) {
                    case 1:
                        switch (latestAnswer) {
                            case 1:
                                brightnessIndex += 2;
                                break;
                            case 2:
                                brightnessIndex += 1;
                                break;
                            default:
                                brightnessIndex += 2;
                                break;
                        }
                        break;
                    case 2:
                        switch (latestAnswer) {
                            case 1:
                                brightnessIndex -= 1;
                                break;
                            case 2:
                                brightnessIndex -= 2;
                                break;
                            default:
                                brightnessIndex -= 2;
                                break;
                        }
                        break;
                }
                latestAnswer = answer;
            } else {
                break;
            }
        }

        return brightnessValuesToTest[brightnessIndex];
    }

    /**
     * Method responsible for deactivating currently displayed stimulus based on patient answers provided
     * to this stimulus and evaluating its brightness threshold.
     *
     * Lets consider situation with 9 element stimulus brightness vector. According to the equation
     * "x = (n + 7) / 4", for the stimulus with 9 element brightness vector patient can provide
     * maximum 4 answers, ie. "(9 + 7) / 4 = 4". Answer "YES" is see by Specvis as "2", "NO" as "1",
     * lack of the answer for brightness value not yet used is see as "0".
     *
     * Taking this into account, lets consider how Specvis will evaluate brightness threshold for stimulus
     * with 4 given answers, ie. [1, 1, 2, 2].
     *
     * 1. Specvis display stimulus with brightness value from the middle of the brightness vector (d).
     *
     * [ ] [ ] [ ] [ ] [d] [ ] [ ] [ ] [ ]
     *
     * 2. Patient answers "No" to this stimulus. All fields on the left of the current brightness value are
     * disabled (x) including current brightness value. Next stimulus is displayed with brightness value
     * two fields higher than last (d).
     *
     * [x] [x] [x] [x] [x] [ ] [d] [ ] [ ]
     *
     * 3. Patient answers "No" to this stimulus. All fields on the left of the current brightness value are
     * disabled (x) including current brightness value. Next stimulus is displayed with brightness value
     * two fields higher than last (d).
     *
     * [x] [x] [x] [x] [x] [x] [x] [ ] [d]
     *
     * 4. Patient answers "Yes" to this stimulus. All fields on the right of the current brightness value are
     * disabled (x) excluding current brightness value. Next stimulus is displayed with brightness value
     * one field lower than last (d).
     *
     * [x] [x] [x] [x] [x] [x] [x] [d] [ ]
     *
     * 5. Patient answers "Yes" to this stimulus. All fields on the right of the current brightness value are
     * disabled (x) excluding current brightness value. Next stimulus is displayed with brightness value
     * two field lower than last - but in this case all four answers were provided so evaluation is ended.
     * Brightness threshold is equal to the brightness vector element with index equal to the only not-disabled
     * field, ie. 7 (e).
     *
     * [x] [x] [x] [x] [x] [x] [x] [e] [x]
     *
     * @param answers: description.
     */
    public void deactivateStimulusIfBrightnessThresholdIsEvaluated(int[] answers) {

        boolean[] brightnessVectorRepresentation = new boolean[visualFieldTestBrightnessVectorLength];
        for (int i = 0; i < brightnessVectorRepresentation.length; i++) {
            brightnessVectorRepresentation[i] = true;
        }
        int currentElementIndex = visualFieldTestBrightnessVectorLength / 2;
        int latestAnswer = -1;

        for (int answer : answers) {
            if (answer != 0) {
                switch (answer) {
                    case 1:
                        // Disable all fields to the left from current element index with inclusion of itself.
                        // ...
                        // i - current element index
                        // x - disabled field
                        // [ ] [ ] [ ] [ ] [i] [ ] [ ] [ ] [ ]
                        // [x] [x] [x] [x] [x] [ ] [ ] [ ] [ ]
                        for (int j = 0; j <= currentElementIndex; j++) {
                            brightnessVectorRepresentation[j] = false;
                        }

                        // Change current element index base on latest answer.
                        switch (latestAnswer) {
                            case 1:
                                currentElementIndex += 2;
                                break;
                            case 2:
                                currentElementIndex += 1;
                                break;
                            default:
                                currentElementIndex += 2;
                                break;
                        }
                        break;
                    case 2:
                        // Disable all fields to the right from current element index with exclusion of itself.
                        // ...
                        // i - current element index
                        // x - disabled field
                        // [ ] [ ] [ ] [ ] [i] [ ] [ ] [ ] [ ]
                        // [ ] [ ] [ ] [ ] [i] [x] [x] [x] [x]
                        if (currentElementIndex != brightnessVectorRepresentation.length - 1) {
                            for (int j = currentElementIndex + 1; j < brightnessVectorRepresentation.length; j++) {
                                brightnessVectorRepresentation[j] = false;
                            }
                        }

                        // Change current element index base on latest answer.
                        switch (latestAnswer) {
                            case 1:
                                currentElementIndex -= 1;
                                break;
                            case 2:
                                currentElementIndex -= 2;
                                break;
                            default:
                                currentElementIndex -= 2;
                                break;
                        }
                }

                // Set latest answer.
                latestAnswer = answer;
            } else {

                // If there are no more answers, break the loop.
                break;
            }
        }

        // If there is only one not-disabled field,
        // deactivate current stimulus.
        int truesCount = 0;
        int indexOfTheOnlyTrue = -1;
        boolean deactivateThisStimulus = false;
        for (int i = 0; i < brightnessVectorRepresentation.length; i++) {
            if (brightnessVectorRepresentation[i]) {
                truesCount++;
                indexOfTheOnlyTrue = i;
            }
        }
        if (truesCount <= 1) {
            deactivateThisStimulus = true;
        }

        // 1. Set evaluated brightness, luminance and decibel thresholds.
        // 2. Remove currently displayed stimulus from active stimuli list.
        // 3. [removed]
        // 4. Set progress for ProgressIndicator in Procedure scene.
        // 5. [removed]
        // 6. If active stimuli list is empty, finish the procedure.
        if (deactivateThisStimulus) {

            // Ad. 1.
            if (indexOfTheOnlyTrue == -1) {
                currentlyDisplayedStimulus.setBrightnessThreshold(indexOfTheOnlyTrue);
                currentlyDisplayedStimulus.setLuminanceThreshold(indexOfTheOnlyTrue);
                currentlyDisplayedStimulus.setDecibelThreshold(indexOfTheOnlyTrue);
            } else {
                currentlyDisplayedStimulus.setBrightnessThreshold(brightnessValuesToTest[indexOfTheOnlyTrue]);
                currentlyDisplayedStimulus.setLuminanceThreshold(functions.round(luminanceScaleForStimulus.getFittedLuminanceForEachBrightnessValue()[currentlyDisplayedStimulus.getBrightnessThreshold()], 2));

                double stimMaxLum = luminanceScaleForStimulus.getFittedLuminanceForEachBrightnessValue()[stimulusMaxBrightness];
                double stimThresholdLum = currentlyDisplayedStimulus.getLuminanceThreshold();
                double bgLum = luminanceScaleForBackground.getFittedLuminanceForEachBrightnessValue()[backgroundBrightness];

                currentlyDisplayedStimulus.setDecibelThreshold(functions.decibelsValue(stimMaxLum, stimThresholdLum, bgLum, 2));
            }

            // Ad. 2.
            activeStimuliIndices.remove(new Integer(currentlyDisplayedStimulus.getIndex()));

            // Ad. 3.

            // Ad. 4.
            double progress;
            if (activeStimuliIndices.size() > 0) {
                progress = 1.0 - ((double) activeStimuliIndices.size() / (double) stimuliList.size());
            } else {
                progress = 1.0;
            }
            procedureController.setProgressForProgressIndicator(progress);

            // Ad. 5.

            // Ad. 6.
            if (activeStimuliIndices.size() == 0) {

                // Stop procedure timeline and set procedure status as finished.
                stimulusTimeline.stop();

                procedureIsFinished = true;

                procedureController.setColorForCircle(Color.WHITE);
                procedureController.setTextForLabel("Finished");
                procedureController.setStyleForLabel("-fx-text-fill: black;");
                procedureController.setButtonStartDisable(true);

                drawProcedureStateIndicator("Square");

                // Prepare data and display some additional information in text area in Procedure scene.
                procedureController.addTextToTextArea("PROCEDURE INFORMATION" + "\n\n");

                int totalFixationMonitorChecks = 0;
                int positiveFixationMonitorChecks = 0;
                double fixationMonitorAccuracyInPercentages = 0;

                if (!fixationMonitorTechnique.equals("None")) {

                    totalFixationMonitorChecks = answersToFixationMonitor.size();
                    if (fixationMonitorTechnique.equals("Blindspot")) {
                        positiveFixationMonitorChecks = (int) answersToFixationMonitor.stream().filter(f -> !f).count();
                    } else {
                        positiveFixationMonitorChecks = (int) answersToFixationMonitor.stream().filter(f -> f).count();
                    }
                    fixationMonitorAccuracyInPercentages = functions.round(((double) positiveFixationMonitorChecks / totalFixationMonitorChecks) * 100, 2);

                    procedureController.addTextToTextArea("Total fixation monitor checks: " + totalFixationMonitorChecks + "\n");
                    procedureController.addTextToTextArea("Positive fixation monitor checks: " + positiveFixationMonitorChecks + "\n");
                    procedureController.addTextToTextArea("Fixation monitor accuracy (%): " + fixationMonitorAccuracyInPercentages + "\n\n");
                }

                int falsePositive_Positive = falsePositiveIndicator_PositiveAnswer;
                int falsePositive_False = falsePositiveIndicator_FalseAnswer;

                double falsePositiveAnswers;

                try {
                    falsePositiveAnswers = functions.round(((double) falsePositive_False / (falsePositive_Positive + falsePositive_False)) * 100, 2);

                    procedureController.addTextToTextArea("False-positive answers (%): " + falsePositiveAnswers + " (" + falsePositive_False + "f/" + falsePositive_Positive + "p" + ")" + "\n\n");
                }catch (NumberFormatException ex) {
                    procedureController.addTextToTextArea("False-positive answers (%): " + "NaN" + " (" + falsePositive_False + "f/" + falsePositive_Positive + "p" + ")" + "\n\n");
                }

                long timeOfTheEndOfTheProcedure = System.currentTimeMillis();
                String procedureDuration = functions.totalTime(timeOfTheStartOfTheProcedure, timeOfTheEndOfTheProcedure);
                procedureController.addTextToTextArea("Test duration: " + procedureDuration + "\n");

                // Initialize BasicProcedureData and set data for it.
                BasicProcedureData basicProcedureData = new BasicProcedureData();
                basicProcedureData.setArrayListBasicProcedureStimulus(stimuliList);

                basicProcedureData.setTotalFixationMonitorChecks(totalFixationMonitorChecks);
                basicProcedureData.setPositiveFixationMonitorChecks(positiveFixationMonitorChecks);
                basicProcedureData.setFixationMonitorAccuracyInPercentages(fixationMonitorAccuracyInPercentages);

                basicProcedureData.setTestDuration(procedureDuration);
                StartApplication.getSpecvisData().setBasicProcedureData(basicProcedureData);
            }
        }
    }
}
