package org.specvis.procedures.basic;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import org.specvis.datastructures.procedures.basic.ProcedureBasicData;
import org.specvis.datastructures.procedures.basic.ProcedureBasicStimulus;
import org.specvis.datastructures.settings.ProcedureBasicSettingsFixMonitorBlindspot;
import org.specvis.datastructures.settings.ProcedureBasicSettingsGeneral;
import org.specvis.logic.Functions;
import org.specvis.view.procedure.ViewProcedurePreviewController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Piotr Dzwiniel on 05.04.2017.
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

public class ProcedureBasicFixMonitorBlindspot extends Stage {

    /* Local fields -> Utility fields */
    private Functions functions;
    private ProcedureBasicSettingsGeneral settings;
    private ProcedureBasicSettingsFixMonitorBlindspot settingsFixMonitorBlindspot;
    private ViewProcedurePreviewController viewProcedurePreviewController;

    private Random randomGenerator;

    /* Local fields -> Display Pane field */
    private Pane displayPane;

    /* Local fields -> General settings */
    private int screenResInPxX;
    private int screenResInPxY;

    private double pxForOneDgX;
    private double pxForOneDgY;

    private double centerOfTheGridInPxX;
    private double centerOfTheGridInPxY;

    /* Local fields -> Fixation point settings */
    private double fixPointSizeInPxX;
    private double fixPointSizeInPxY;

    private Color fixPointColor;

    /* Local fields -> Keyboard settings */
    private String keyAnswer;
    private String keyPause;
    private String keyCancel;

    /* Local fields -> Procedure fields */
    private int[] brightnessVector;

    private boolean procedureIsStarted;
    private boolean procedureIsRunning;
    private boolean procedureIsFinished;

    private boolean youCanRespondToStimulus;

    private ArrayList<ProcedureBasicStimulus> arrayListProcedureBasicStimuli;
    private ArrayList<Integer> arrayListActiveStimuliIndices;

    private Timeline timelineBoot;
    private Timeline timelineStopwatch;
    private Timeline timelineStimulus;

    private long timeStartOfTheProcedure;

    private ProcedureBasicStimulus currentlyDisplayedStimulus;

    private int falsePosIndicator_PosAns;
    private int falsePosIndicator_FalseAns;

    /* Local fields -> Fixation monitor fields */
    private int fixationMonitorIterator;
    private int fixationMonitorCheckRate;
    private ArrayList<Boolean> arrayListAnswersToFixMonitor;
    private Timeline timelineFixationMonitor;
    private Shape fixationMonitorShape;

    private Timeline timelineMsgAfterFixLoss;
    private Label messageAfterFixLoss;
    private boolean isMsgAfterFixLossOnScreen;

    /**
     * Constructor for the procedure.
     */
    public ProcedureBasicFixMonitorBlindspot() {

        /* Init local fields */
        initLocalFields();

        /* Init procedure window */
        this.setScene(new Scene(createContent()));

        /* Set procedure window on the chosen screen */
        List<Screen> screenList = Screen.getScreens();
        Screen chosenScreen = screenList.get(settings.getChosenScreenIndex());
        Rectangle2D chosenScreenBounds = chosenScreen.getVisualBounds();

        this.setX(chosenScreenBounds.getMinX());
        this.setY(chosenScreenBounds.getMinY());

        this.setWidth(chosenScreenBounds.getWidth());
        this.setHeight(chosenScreenBounds.getHeight());

        /* Make procedure window undecorated and modal */
        this.initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);

        /* Procedure window -> Define actions for "Answer to stimulus", "Pause procedure", and "Cancel procedure" keys */
        this.getScene().addEventHandler(KeyEvent.KEY_PRESSED, ke -> {

            /* Get pressed key code */
            String option = ke.getCode().toString();

            /* Perform specific action depending on pressed key */
            if (option.equals(keyAnswer)) {
                /* KEY PRESSED - Answer to stimulus */

                /* If procedure IS started */
                if (procedureIsStarted) {

                    /* If procedure IS running */
                    if (procedureIsRunning) {
                        if (youCanRespondToStimulus) {

                            falsePosIndicator_PosAns += 1;

                            if (fixationMonitorIterator != fixationMonitorCheckRate) {
                                for (int i = 0; i < currentlyDisplayedStimulus.getAnswers().length; i++) {
                                    if (currentlyDisplayedStimulus.getAnswers()[i] == 0) {
                                        currentlyDisplayedStimulus.getAnswers()[i] = 2;
                                        break;
                                    }
                                }
                            } else {
                                arrayListAnswersToFixMonitor.add(true);
                            }

                            youCanRespondToStimulus = false;

                        } else {
                            falsePosIndicator_FalseAns += 1;
                        }
                    }
                }

                /* If procedure IS NOT started */
                else {

                    /* Remove from Display Pane procedure state indicator */
                    displayPane.getChildren().remove(1);

                    /* Get starting time of the procedure in millis */
                    timeStartOfTheProcedure = System.currentTimeMillis();

                    /* Set values for some boolean variables */
                    procedureIsStarted = true;
                    procedureIsRunning = true;

                    /* Initialize and run boot timeline */
                    initAndRunBootTimeline();

                    /* Change some visual characteristics in Procedure Preview window */
                    viewProcedurePreviewController.setColorForCircle(Color.web("#33FF33"));
                    viewProcedurePreviewController.setTextForLabel("Running");
                }

            } else if (option.equals(keyPause)) {
                /* KEY PRESSED - Pause procedure */

                /* If procedure IS running */
                if (procedureIsRunning) {

                    drawProcedureStateIndicator("Triangle");
                    procedureIsRunning = false;

                    if (fixationMonitorIterator != fixationMonitorCheckRate) {

                        if (isMsgAfterFixLossOnScreen) {
                            if (timelineMsgAfterFixLoss != null) {
                                timelineMsgAfterFixLoss.pause();
                                viewProcedurePreviewController.setColorForCircle(Color.web("#FFAA00"));
                                viewProcedurePreviewController.setTextForLabel("Paused");
                            }
                        } else {
                            if (timelineStimulus != null) {
                                timelineStimulus.pause();
                                viewProcedurePreviewController.setColorForCircle(Color.web("#FFAA00"));
                                viewProcedurePreviewController.setTextForLabel("Paused");
                            }
                        }

                    } else {
                        if (timelineFixationMonitor != null) {
                            timelineFixationMonitor.pause();
                            viewProcedurePreviewController.setColorForCircle(Color.web("#FFAA00"));
                            viewProcedurePreviewController.setTextForLabel("Paused");
                        }
                    }
                }

                /* If procedure IS NOT running */
                else {

                    displayPane.getChildren().remove(1);

                    procedureIsRunning = true;

                    if (fixationMonitorIterator != fixationMonitorCheckRate) {

                        if (isMsgAfterFixLossOnScreen) {
                            if (timelineMsgAfterFixLoss != null) {
                                timelineMsgAfterFixLoss.play();
                                viewProcedurePreviewController.setColorForCircle(Color.web("#33FF33"));
                                viewProcedurePreviewController.setTextForLabel("Running");
                            }
                        } else {
                            if (timelineStimulus != null) {
                                timelineStimulus.play();
                                viewProcedurePreviewController.setColorForCircle(Color.web("#33FF33"));
                                viewProcedurePreviewController.setTextForLabel("Running");
                            }
                        }

                    } else {
                        if (timelineFixationMonitor != null) {
                            timelineFixationMonitor.play();
                            viewProcedurePreviewController.setColorForCircle(Color.web("#33FF33"));
                            viewProcedurePreviewController.setTextForLabel("Running");
                        }
                    }
                }

            } else if (option.equals(keyCancel)) {
                /* KEY PRESSED - Cancel procedure */

                /* Stop boot timeline if it exists */
                if (timelineBoot != null) {
                    timelineBoot.stop();
                }

                /* Stop stimulus timeline if it exists */
                if (timelineStimulus != null) {
                    timelineStimulus.stop();
                }

                /* Stop fixation monitor timeline if it exists */
                if (timelineFixationMonitor != null) {
                    timelineFixationMonitor.stop();
                }

                /* Stop stopwatch timeline if it exists */
                if (timelineStopwatch != null) {
                    timelineStopwatch.stop();
                }

                /* Close procedure window */
                if (arrayListActiveStimuliIndices.size() == 0) {
                    close();
                } else {
                    viewProcedurePreviewController.setColorForCircle(Color.web("#FF5555"));
                    viewProcedurePreviewController.setTextForLabel("Canceled");
                    close();
                }
            } else {
                /* KEY PRESSED - Any other key */
            }
        });
    }

    /**
     * Initialize all of the local fields.
     */
    private void initLocalFields() {

        /* Local fields -> Utility */
        functions = StartApplication.getFunctions();
        settings = new ProcedureBasicSettingsGeneral();
        settingsFixMonitorBlindspot = new ProcedureBasicSettingsFixMonitorBlindspot();
        viewProcedurePreviewController = StartApplication.getViewProcedurePreviewController();

        randomGenerator = new Random();

        /* Local fields -> Display Pane */
        displayPane = initializeDisplayPane();

        /* Local fields -> General settings */
        screenResInPxX = settings.getScreenResolutionInPxHorizontal();
        screenResInPxY = settings.getScreenResolutionInPxVertical();

        pxForOneDgX = functions.pxForOneDg(screenResInPxX, settings.getInvolvedVisualFieldInDegreesHorizontal());
        pxForOneDgY = functions.pxForOneDg(screenResInPxY, settings.getInvolvedVisualFieldInDegreesVertical());

        centerOfTheGridInPxX = (settings.getScreenResolutionInPxHorizontal() / 2) + (pxForOneDgX * settings.getFixationPointLocationHorizontal());
        centerOfTheGridInPxY = (settings.getScreenResolutionInPxVertical() / 2) + (pxForOneDgY * settings.getFixationPointLocationVertical());

        /* Local fields -> Fixation point settings */
        fixPointSizeInPxX = settings.getFixationPointSizeInDegreesHorizontal() * pxForOneDgX;
        fixPointSizeInPxY = settings.getFixationPointSizeInDegreesVertical() * pxForOneDgY;

        fixPointColor = settings.getFixationPointColor();

        /* Local fields -> Keyboard */
        keyAnswer = settings.getKeyAnswerToStimulus();
        keyPause = settings.getKeyPauseProcedure();
        keyCancel = settings.getKeyCancelProcedure();

        /* Local fields -> Procedure */
        brightnessVector = createBrightnessVector(settings.getStimuliMinBrightness(), settings.getStimuliMaxBrightness(), settings.getBrightnessVectorLen(), settings.isSpreadValuesLog());

        procedureIsStarted = false;
        procedureIsRunning = false;
        procedureIsFinished = false;

        youCanRespondToStimulus = false;

        arrayListProcedureBasicStimuli = createListOfStimuli();
        arrayListActiveStimuliIndices = new ArrayList<>();
        arrayListActiveStimuliIndices.addAll(arrayListProcedureBasicStimuli.stream().map(ProcedureBasicStimulus::getIndex).collect(Collectors.toList()));

        falsePosIndicator_PosAns = 0;
        falsePosIndicator_FalseAns = 0;

        /* Local fields -> Fixation monitor fields */
        fixationMonitorIterator = 0;

        if (settingsFixMonitorBlindspot.isMonitorFixationEveryXStimuli()) {
            fixationMonitorCheckRate = settingsFixMonitorBlindspot.getMonitorFixationEveryXStimuli();
        } else {
            fixationMonitorCheckRate = functions.randomInt(
                    settingsFixMonitorBlindspot.getMonitorFixationEveryXToYStimuli_X(),
                    settingsFixMonitorBlindspot.getMonitorFixationEveryXToYStimuli_Y()
            );
        }

        arrayListAnswersToFixMonitor = new ArrayList<>();

        isMsgAfterFixLossOnScreen = false;
    }

    /**
     * Create/set content of the Parent layout of the main scene.
     * @return Layout with nested Display Pane.
     */
    private Parent createContent() {

        /* Layout */
        BorderPane layout = new BorderPane();

        /* Draw fixation point and procedure state indicator around fixation point */
        drawFixationPoint();
        drawProcedureStateIndicator("Circle");

        /* Return layout */
        layout.setCenter(displayPane);
        return layout;
    }

    /**
     * Initialize procedure Display Pane with setting its background color and min-max size.
     * @return Display Pane.
     */
    private Pane initializeDisplayPane() {

        /* Create Pane object */
        Pane displayPane = new Pane();

        /* Get Hue and saturation for Display Pane background */
        int hueForBg = (int) functions.round(settings.getLuminanceScaleForBackground().getHue(), 0);
        int saturationForBg = (int) functions.round(settings.getLuminanceScaleForBackground().getSaturation(), 0);

        /* Set Display Pane background color */
        displayPane.setStyle("-fx-background-color: hsb(" + hueForBg + ", " + saturationForBg + "%, " + settings.getBackgroundBrightness() + "%);");

        /* Set min-max size of the Display Pane */
        displayPane.setMinSize(settings.getScreenResolutionInPxHorizontal(), settings.getScreenResolutionInPxVertical());
        displayPane.setMaxSize(settings.getScreenResolutionInPxHorizontal(), settings.getScreenResolutionInPxVertical());

        return displayPane;
    }

    /**
     * Draw fixation point on given location on Display Pane.
     */
    private void drawFixationPoint() {

        /* Create Ellipse object, fill it and set its stroke */
        Ellipse fixPoint = new Ellipse(centerOfTheGridInPxX, centerOfTheGridInPxY, fixPointSizeInPxX / 2, fixPointSizeInPxY / 2);
        fixPoint.setFill(fixPointColor);
        fixPoint.setStroke(fixPointColor);

        /* Add created fixation point to the Display Pane */
        displayPane.getChildren().add(fixPoint);
    }

    /**
     * Draw arround the fixation point the procedure state indicator.
     * - Draw CIRCLE when waiting for starting the procedure;
     * - Draw TRIANGLE when procedure is paused;
     * - Draw SQUARE when procedure is finished;
     * @param indicator
     */
    private void drawProcedureStateIndicator(String indicator) {
        switch (indicator) {
            case "Circle":

                double radiusX = fixPointSizeInPxX * 1.5;
                double radiusY = fixPointSizeInPxY * 1.5;

                Ellipse ellipse = new Ellipse(centerOfTheGridInPxX, centerOfTheGridInPxY, radiusX, radiusY);
                ellipse.setFill(Color.TRANSPARENT);
                ellipse.setStroke(fixPointColor);
                ellipse.setStrokeWidth(4);

                displayPane.getChildren().add(ellipse);

                break;
            case "Triangle":

                double triangleSizeX = fixPointSizeInPxX * 4.5;
                double triangleSizeY = fixPointSizeInPxY * 4.5;

                double twoThirdOfTheTriangleHeightX = (triangleSizeX * Math.sqrt(3)) / 3;
                double twoThirdOfTheTriangleHeightY = (triangleSizeY * Math.sqrt(3)) / 3;

                double angleForA = Math.toRadians(270);
                double angleForB = Math.toRadians(30);
                double angleForC = Math.toRadians(150);

                double xA = centerOfTheGridInPxX + twoThirdOfTheTriangleHeightX * Math.cos(angleForA);
                double yA = centerOfTheGridInPxY - twoThirdOfTheTriangleHeightY * Math.sin(angleForA);

                double xB = centerOfTheGridInPxX + twoThirdOfTheTriangleHeightX * Math.cos(angleForB);
                double yB = centerOfTheGridInPxY - twoThirdOfTheTriangleHeightY * Math.sin(angleForB);

                double xC = centerOfTheGridInPxX + twoThirdOfTheTriangleHeightX * Math.cos(angleForC);
                double yC = centerOfTheGridInPxY - twoThirdOfTheTriangleHeightY * Math.sin(angleForC);

                Polygon polygon = new Polygon();
                polygon.getPoints().addAll(xA, yA, xB, yB, xC, yC);
                polygon.setFill(Color.TRANSPARENT);
                polygon.setStroke(fixPointColor);
                polygon.setStrokeWidth(4);

                displayPane.getChildren().add(polygon);

                break;
            case "Square":

                double sizeX = fixPointSizeInPxX * 3;
                double sizeY = fixPointSizeInPxY * 3;

                Rectangle rectangle = new Rectangle(centerOfTheGridInPxX - (sizeX / 2), centerOfTheGridInPxY - (sizeY / 2), sizeX, sizeY);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(fixPointColor);
                rectangle.setStrokeWidth(4);

                displayPane.getChildren().add(rectangle);

                break;
        }
    }

    /**
     * Create brightness vector with values spread logarithmically or lineary.
     * @param start
     * @param end
     * @param vectorLen
     * @param spreadLog
     * @return Brightness vector.
     */
    private int[] createBrightnessVector(int start, int end, int vectorLen, boolean spreadLog) {

        int[] vec = new int[vectorLen];

        ArrayList<Double> vector;

        if (spreadLog) {
            vector = functions.logspace(start, end, vectorLen, true);
        } else {
            vector = functions.linspace(start, end, vectorLen, true);
        }

        for (int i = 0; i < vec.length; i++) {
            double d = vector.get(i);
            vec[i] = (int) d;
        }

        return vec;
    }

    /**
     * Create list of procedure stimuli.
     * @return List of stimuli.
     */
    private ArrayList<ProcedureBasicStimulus> createListOfStimuli() {

        ArrayList<ProcedureBasicStimulus> arrayList = new ArrayList<>();

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
        if (settings.isUseCorrectionForSphericityOfTheFieldOfView()) {

            ProcedureBasicStimulus procedureBasicStimulus;
            int stimulusIndexIterator = 0;

            // Prepare stimuli for Quarter 0.
            double angleX = settings.getDistanceBetweenStimuliInDegreesHorizontal() / 2;
            double angleY = settings.getDistanceBetweenStimuliInDegreesVertical() / 2;

            double r = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().getPatientDistanceFromTheScreen();

            double oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
            double oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);

            double oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResInPxX, settings.getScreenWidthInMm());
            double oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

            double tempLocationX = centerOfTheGridInPxX - oppAngleInPixelsX;
            double tempLocationY = centerOfTheGridInPxY - oppAngleInPixelsY;

            double[] correctedStimulusSizeInPixelsXY = new double[] {
                    settings.getStimuliSizeInDegreesHorizontal() * pxForOneDgX,
                    settings.getStimuliSizeInDegreesVertical() * pxForOneDgY
            };

            while (tempLocationX >= 0) {
                while (tempLocationY >= 0) {

                    procedureBasicStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(procedureBasicStimulus);

                    angleY += settings.getDistanceBetweenStimuliInDegreesVertical();
                    oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                    oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

                    tempLocationY = centerOfTheGridInPxY - oppAngleInPixelsY;
                    stimulusIndexIterator += 1;
                }

                angleX += settings.getDistanceBetweenStimuliInDegreesHorizontal();
                oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
                oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResInPxX, settings.getScreenWidthInMm());

                tempLocationX = centerOfTheGridInPxX - oppAngleInPixelsX;

                angleY = settings.getDistanceBetweenStimuliInDegreesVertical() / 2;
                oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

                tempLocationY = centerOfTheGridInPxY - oppAngleInPixelsY;
            }

            // Prepare stimuli for Quarter 1.
            angleX = settings.getDistanceBetweenStimuliInDegreesHorizontal() / 2;
            angleY = settings.getDistanceBetweenStimuliInDegreesVertical() / 2;

            oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
            oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);

            oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResInPxX, settings.getScreenWidthInMm());
            oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

            tempLocationX = centerOfTheGridInPxX + oppAngleInPixelsX;
            tempLocationY = centerOfTheGridInPxY - oppAngleInPixelsY;

            while (tempLocationX <= screenResInPxX) {
                while (tempLocationY >= 0) {

                    procedureBasicStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(procedureBasicStimulus);

                    angleY += settings.getDistanceBetweenStimuliInDegreesVertical();
                    oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                    oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

                    tempLocationY = centerOfTheGridInPxY - oppAngleInPixelsY;
                    stimulusIndexIterator += 1;
                }

                angleX += settings.getDistanceBetweenStimuliInDegreesHorizontal();
                oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
                oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResInPxX, settings.getScreenWidthInMm());

                tempLocationX = centerOfTheGridInPxX + oppAngleInPixelsX;

                angleY = settings.getDistanceBetweenStimuliInDegreesVertical() / 2;
                oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

                tempLocationY = centerOfTheGridInPxY - oppAngleInPixelsY;
            }

            // Prepare stimuli for Quarter 2.
            angleX = settings.getDistanceBetweenStimuliInDegreesHorizontal() / 2;
            angleY = settings.getDistanceBetweenStimuliInDegreesVertical() / 2;

            oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
            oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);

            oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResInPxX, settings.getScreenWidthInMm());
            oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

            tempLocationX = centerOfTheGridInPxX + oppAngleInPixelsX;
            tempLocationY = centerOfTheGridInPxY + oppAngleInPixelsY;

            while (tempLocationX <= screenResInPxX) {
                while (tempLocationY <= screenResInPxY) {

                    procedureBasicStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(procedureBasicStimulus);

                    angleY += settings.getDistanceBetweenStimuliInDegreesVertical();
                    oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                    oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

                    tempLocationY = centerOfTheGridInPxY + oppAngleInPixelsY;
                    stimulusIndexIterator += 1;
                }

                angleX += settings.getDistanceBetweenStimuliInDegreesHorizontal();
                oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
                oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResInPxX, settings.getScreenWidthInMm());

                tempLocationX = centerOfTheGridInPxX + oppAngleInPixelsX;

                angleY = settings.getDistanceBetweenStimuliInDegreesVertical() / 2;
                oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

                tempLocationY = centerOfTheGridInPxY + oppAngleInPixelsY;
            }

            // Prepare stimuli for Quarter 3.
            angleX = settings.getDistanceBetweenStimuliInDegreesHorizontal() / 2;
            angleY = settings.getDistanceBetweenStimuliInDegreesVertical() / 2;

            oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
            oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);

            oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResInPxX, settings.getScreenWidthInMm());
            oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

            tempLocationX = centerOfTheGridInPxX - oppAngleInPixelsX;
            tempLocationY = centerOfTheGridInPxY + oppAngleInPixelsY;

            while (tempLocationX >= 0) {
                while (tempLocationY <= screenResInPxY) {

                    procedureBasicStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(procedureBasicStimulus);

                    angleY += settings.getDistanceBetweenStimuliInDegreesVertical();
                    oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                    oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

                    tempLocationY = centerOfTheGridInPxY + oppAngleInPixelsY;
                    stimulusIndexIterator += 1;
                }

                angleX += settings.getDistanceBetweenStimuliInDegreesHorizontal();
                oppAngleInMmX = functions.calculateOppositeAngle(angleX, r);
                oppAngleInPixelsX = functions.millimitersToPixels(oppAngleInMmX, screenResInPxX, settings.getScreenWidthInMm());

                tempLocationX = centerOfTheGridInPxX - oppAngleInPixelsX;

                angleY = settings.getDistanceBetweenStimuliInDegreesVertical() / 2;
                oppAngleInMmY = functions.calculateOppositeAngle(angleY, r);
                oppAngleInPixelsY = functions.millimitersToPixels(oppAngleInMmY, screenResInPxY, settings.getScreenHeightInMm());

                tempLocationY = centerOfTheGridInPxY + oppAngleInPixelsY;
            }
        } else {

            ProcedureBasicStimulus procedureBasicStimulus;
            int stimulusIndexIterator = 0;

            double distanceBetweenStimuliInPixelsX = pxForOneDgX * settings.getDistanceBetweenStimuliInDegreesHorizontal();
            double distanceBetweenStimuliInPixelsY = pxForOneDgY * settings.getDistanceBetweenStimuliInDegreesVertical();

            // Prepare stimuli for Quarter 0.
            double tempLocationX = centerOfTheGridInPxX - (distanceBetweenStimuliInPixelsX / 2);
            double tempLocationY = centerOfTheGridInPxY - (distanceBetweenStimuliInPixelsY / 2);

            double[] correctedStimulusSizeInPixelsXY = new double[] {
                    settings.getStimuliSizeInDegreesHorizontal() * pxForOneDgX,
                    settings.getStimuliSizeInDegreesVertical() * pxForOneDgY
            };

            while (tempLocationX >= 0) {
                while (tempLocationY >= 0) {

                    procedureBasicStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(procedureBasicStimulus);

                    tempLocationY -= distanceBetweenStimuliInPixelsY;
                    stimulusIndexIterator += 1;
                }

                tempLocationX -= distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridInPxY - (distanceBetweenStimuliInPixelsY / 2);
            }

            // Prepare stimuli for Quarter 1.
            tempLocationX = centerOfTheGridInPxX + (distanceBetweenStimuliInPixelsX / 2);
            tempLocationY = centerOfTheGridInPxY - (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX <= screenResInPxX) {
                while (tempLocationY >= 0) {

                    procedureBasicStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(procedureBasicStimulus);

                    tempLocationY -= distanceBetweenStimuliInPixelsY;
                    stimulusIndexIterator += 1;
                }

                tempLocationX += distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridInPxY - (distanceBetweenStimuliInPixelsY / 2);
            }

            // Prepare stimuli for Quarter 2.
            tempLocationX = centerOfTheGridInPxX + (distanceBetweenStimuliInPixelsX / 2);
            tempLocationY = centerOfTheGridInPxY + (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX <= screenResInPxX) {
                while (tempLocationY <= screenResInPxY) {

                    procedureBasicStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(procedureBasicStimulus);

                    tempLocationY += distanceBetweenStimuliInPixelsY;
                    stimulusIndexIterator += 1;
                }

                tempLocationX += distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridInPxY + (distanceBetweenStimuliInPixelsY / 2);
            }

            // Prepare stimuli for Quarter 3.
            tempLocationX = centerOfTheGridInPxX - (distanceBetweenStimuliInPixelsX / 2);
            tempLocationY = centerOfTheGridInPxY + (distanceBetweenStimuliInPixelsY / 2);

            while (tempLocationX >= 0) {
                while (tempLocationY <= screenResInPxY) {

                    procedureBasicStimulus = initializeStimulus(stimulusIndexIterator, tempLocationX, tempLocationY, correctedStimulusSizeInPixelsXY);

                    arrayList.add(procedureBasicStimulus);

                    tempLocationY += distanceBetweenStimuliInPixelsY;
                    stimulusIndexIterator += 1;
                }

                tempLocationX -= distanceBetweenStimuliInPixelsX;
                tempLocationY = centerOfTheGridInPxY + (distanceBetweenStimuliInPixelsY / 2);
            }
        }
        return arrayList;
    }

    /**
     * Create ProcedureBasicStimulus object, ie. so called visual "stimulus", which will be displayed
     * in different locations to the patient during the visual field testing procedure.
     * @param index
     * @param locationX
     * @param locationY
     * @param sizeXY
     * @return Stimulus.
     */
    private ProcedureBasicStimulus initializeStimulus(int index, double locationX, double locationY, double[] sizeXY) {

        ProcedureBasicStimulus stimulus = new ProcedureBasicStimulus();
        stimulus.setIndex(index);
        stimulus.setPositionOnTheScreenInPixelsX(locationX);
        stimulus.setPositionOnTheScreenInPixelsY(locationY);
        stimulus.setDistanceFromFixPointOnTheFieldOfViewInDegreesX((locationX - centerOfTheGridInPxX) / pxForOneDgX);
        stimulus.setDistanceFromFixPointOnTheFieldOfViewInDegreesY((locationY - centerOfTheGridInPxY) / pxForOneDgY);

        switch (settings.getStimuliShape()) {
            case "Ellipse":
                stimulus.setShape(createEllipseStimulus(locationX, locationY, sizeXY));
                break;
            case "Polygon":
                stimulus.setShape(createPolygonStimulus(locationX, locationY, sizeXY));
                break;
        }

        stimulus.setDisplayTime(settings.getStimuliDisplayTimeInMs());

        /*
         * Depending of the value of "ProcedureBasicSettingsGeneral.getBrightnessVectorLen()" (n)
         * Specvis sets different value of "answersToStimulusVectorLength" (x). Value of x depends
         * on value of n, based on equation: x = (n + 7) / 4. For instance, for n = 9, x = 4,
         * because (9 + 4) / 4 = 4.
         */
        int answersToStimulusVectorLength = (settings.getBrightnessVectorLen() + 7) / 4;

        stimulus.setAnswers(new int[answersToStimulusVectorLength]);
        for (int i = 0; i < stimulus.getAnswers().length; i++) {
            stimulus.getAnswers()[i] = 0;
        }

        stimulus.setBrightnessThreshold(0);
        stimulus.setLuminanceThreshold(0);
        stimulus.setDecibelThreshold(0);

        return stimulus;
    }

    /**
     * Create stimulus in ellipse shape.
     * @param locationX
     * @param locationY
     * @param sizeXY
     * @return Ellipse stimulus.
     */
    private Ellipse createEllipseStimulus(double locationX, double locationY, double[] sizeXY) {

        /* Get horizontal and vertical radius of the ellipse */
        double radiusX = sizeXY[0] / 2;
        double radiusY = sizeXY[1] / 2;

        /* Get ellipse color */
        double hue = settings.getLuminanceScaleForStimuli().getHue();
        double saturation = settings.getLuminanceScaleForStimuli().getSaturation() / 100;
        double brightness = settings.getStimuliMaxBrightness() / 100;
        Color color = Color.hsb(hue, saturation, brightness);

        /* Create ellipse */
        Ellipse ellipse = new Ellipse(locationX, locationY, radiusX, radiusY);
        ellipse.setFill(color);
        ellipse.setStroke(color);

        return ellipse;
    }

    /**
     * Create stimulus in polygon shape.
     * @param locationX
     * @param locationY
     * @param sizeXY
     * @return Polygon stimulus.
     */
    private Polygon createPolygonStimulus(double locationX, double locationY, double[] sizeXY) {

        /* Polygon size */
        double sizeX = sizeXY[0];
        double sizeY = sizeXY[1];

        /* Polygon diagonal */
        double diagonal = Math.sqrt(Math.pow(sizeX, 2) + Math.pow(sizeY, 2));

        /* Polygon inner angle on the Cartesian plane and positions of ABCS points in reference to stimulus inclination in degrees */
        double cartesianX = locationX + sizeX;
        double cartesianY = locationY - sizeY;

        double innerAngle = Math.toDegrees(Math.atan2(cartesianX - locationX, locationY - cartesianY));

        double inclination = settings.getStimuliInclinationInDegrees();

        double aX = locationX + ((diagonal / 2) * Math.cos(Math.toRadians(inclination + 270 + (90 - innerAngle) - 90)));
        double aY = locationY + ((diagonal / 2) * Math.sin(Math.toRadians(inclination + 270 + (90 - innerAngle) - 90)));

        double bX = locationX + ((diagonal / 2) * Math.cos(Math.toRadians(inclination + innerAngle - 90)));
        double bY = locationY + ((diagonal / 2) * Math.sin(Math.toRadians(inclination + innerAngle - 90)));

        double cX = locationX + ((diagonal / 2) * Math.cos(Math.toRadians(inclination + 90 + (90 - innerAngle) - 90)));
        double cY = locationY + ((diagonal / 2) * Math.sin(Math.toRadians(inclination + 90 + (90 - innerAngle) - 90)));

        double dX = locationX + ((diagonal / 2) * Math.cos(Math.toRadians(inclination + 180 + innerAngle - 90)));
        double dY = locationY + ((diagonal / 2) * Math.sin(Math.toRadians(inclination + 180 + innerAngle - 90)));

        /* Set color of the polygon */
        double hue = settings.getLuminanceScaleForStimuli().getHue();
        double saturation = settings.getLuminanceScaleForStimuli().getSaturation() / 100;
        double brightness = settings.getStimuliMaxBrightness() / 100;
        Color color = Color.hsb(hue, saturation, brightness);

        /* Create polygon stimulus */
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(aX, aY, bX, bY, cX, cY, dX, dY);
        polygon.setFill(color);

        return polygon;
    }

    /**
     * Init and run boot timeline. Boot timeline is for booting the whole visual field test
     * procedure. It starts, waits 1000 ms, and then start individual stimulus initialization
     * and display by calling the "initAndRunStimulusTimeline" method.
     */
    private void initAndRunBootTimeline() {

        /* Init boot timeline */
        timelineBoot = new Timeline();

        /*
         * Create initial key frame (start) which is responsible for starting boot timeline
         * with 1000 ms delay after starting the procedure.
         */
        KeyFrame start = new KeyFrame(Duration.millis(1000), event -> {
            initAndRunStimulusTimeline();
            timelineBoot.stop();
        });

        /* Add initial key frame to the boot timeline */
        timelineBoot.getKeyFrames().add(start);

        /* Start main timeline */
        timelineBoot.play();

        /* Init and run stopwatch timeline in procedure preview window */
        timelineStopwatch = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    viewProcedurePreviewController.setTextForStopwatch(timeStartOfTheProcedure, System.currentTimeMillis());
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timelineStopwatch.setCycleCount(Animation.INDEFINITE);
        timelineStopwatch.play();
    }

    /**
     * Init and run stimulus timeline.
     */
    private void initAndRunStimulusTimeline() {

        /* Init stimulus timeline */
        timelineStimulus = new Timeline();

        /* Get random index of active stimulus */
        int r = randomGenerator.nextInt(arrayListActiveStimuliIndices.size());
        int index = arrayListActiveStimuliIndices.get(r);
        currentlyDisplayedStimulus = arrayListProcedureBasicStimuli.get(index);

        /* Initialize cumulative time interval */
        int cumulativeTimeInterval = 0;

        /* Create KeyFrame for displaying stimulus */
        KeyFrame keyFrameDisplayStimulus = new KeyFrame(Duration.millis(cumulativeTimeInterval), event -> {

            /* Allow responding to stimulus */
            youCanRespondToStimulus = true;

            /* Set stimulus brightness based on previous answers to this stimulus */
            int brightness = getBrightnessForNextStimulusPresentation(currentlyDisplayedStimulus.getAnswers());

            /* Create and set color for stimulus */
            Color color = Color.hsb(
                    (int) functions.round(settings.getLuminanceScaleForStimuli().getHue(), 0),
                    settings.getLuminanceScaleForStimuli().getSaturation() / 100,
                    (double) brightness / 100
            );

            currentlyDisplayedStimulus.getShape().setFill(color);
            currentlyDisplayedStimulus.getShape().setStroke(color);

            /* Add stimulus to display pane */
            displayPane.getChildren().add(currentlyDisplayedStimulus.getShape());
        });

        /* Add key frame to timeline */
        timelineStimulus.getKeyFrames().add(keyFrameDisplayStimulus);

        /* Update cumulative time interval */
        cumulativeTimeInterval += currentlyDisplayedStimulus.getDisplayTime();

        /* Create KeyFrame for removing stimulus from display pane */
        KeyFrame keyFrameRemoveStimulus = new KeyFrame(Duration.millis(cumulativeTimeInterval), event -> {
            if (displayPane.getChildren().size() > 1) {
                displayPane.getChildren().remove(1);
            }
        });

        /* Add key frame to timeline */
        timelineStimulus.getKeyFrames().add(keyFrameRemoveStimulus);

        /* Specify time interval between next stimulus display */
        int interval = functions.randomInterval(settings.getInterStimuliIntervalInMsConstantPart(),
                settings.getInterStimuliIntervalInMsRandomPart());

        /* Update cumulative time interval */
        cumulativeTimeInterval += interval;

        /* Create KeyFrame for time interval between stimuli */
        KeyFrame keyFrameIntervalBetweenStimuli = new KeyFrame(Duration.millis(cumulativeTimeInterval), event -> {

            /* Write negative answer to stimulus if there was no answer to it */
            if (youCanRespondToStimulus) {
                for (int i = 0; i < currentlyDisplayedStimulus.getAnswers().length; i++) {
                    if (currentlyDisplayedStimulus.getAnswers()[i] == 0) {
                        currentlyDisplayedStimulus.getAnswers()[i] = 1;
                        break;
                    }
                }
            }

            /* Remove stimulus from active stimulus list if all possible answers were provided */
            decideAboutStimulusDeactivation(currentlyDisplayedStimulus.getAnswers());

            /* Increase fixation monitor iterator by 1 */
            fixationMonitorIterator += 1;

            /* Run next stimulus display */
            if (!procedureIsFinished) {
                if (fixationMonitorIterator != fixationMonitorCheckRate) {
                    timelineStimulus.stop();
                    initAndRunStimulusTimeline();
                } else {
                    timelineStimulus.stop();
                    initAndRunFixationMonitorTimeline();
                }
            }
        });

        /* Add key frame to timeline */
        timelineStimulus.getKeyFrames().add(keyFrameIntervalBetweenStimuli);

        /* Play timeline */
        timelineStimulus.play();
    }

    /**
     * Init and run fixation monitor timeline.
     */
    private void initAndRunFixationMonitorTimeline() {

        /* Init fixation monitor shape */
        if (fixationMonitorShape == null) {
            initFixationMonitorShape();
        }

        /* Reset fixation monitor iterator */
        fixationMonitorIterator = 0;

        /* Init fixation monitor timeline */
        timelineFixationMonitor = new Timeline();

        /* Initialize cumulative time interval */
        int cumulativeTimeInterval = 0;

        /* Create KeyFrame for displaying fixation monitor stimulus */
        KeyFrame keyFrameDispFixMonStim = new KeyFrame(Duration.millis(cumulativeTimeInterval), event -> {

            /* Allow responding to stimulus */
            youCanRespondToStimulus = true;

            /* Add fixation monitor stimulus to display pane */
            displayPane.getChildren().add(fixationMonitorShape);
        });

        /* Add key frame to timeline */
        timelineFixationMonitor.getKeyFrames().add(keyFrameDispFixMonStim);

        /* Update cumulative time interval */
        cumulativeTimeInterval += settings.getStimuliDisplayTimeInMs();

        /* Create KeyFrame for removing fixation monitor stimulus */
        KeyFrame keyFrameRemoveFixMonStim = new KeyFrame(Duration.millis(cumulativeTimeInterval), event -> {
            if (displayPane.getChildren().size() > 1) {
                displayPane.getChildren().remove(1);
            }
        });

        /* Add key frame to timeline */
        timelineFixationMonitor.getKeyFrames().add(keyFrameRemoveFixMonStim);

        /* Specify time interval between next stimulus display */
        int interval = functions.randomInterval(settings.getInterStimuliIntervalInMsConstantPart(),
                settings.getInterStimuliIntervalInMsRandomPart());

        /* Update cumulative time interval */
        cumulativeTimeInterval += interval;

        /* Create KeyFrame for time interval between stimuli */
        KeyFrame keyFrameIntervalBetweenStimuli = new KeyFrame(Duration.millis(cumulativeTimeInterval), event -> {

            /* Write negative answer to fixation monitor if there was no answer to it */
            /* and show optionally message after fixation loss */
            if (youCanRespondToStimulus) {
                arrayListAnswersToFixMonitor.add(false);

                timelineFixationMonitor.stop();

                if (settingsFixMonitorBlindspot.isShowMessage()) {
                    isMsgAfterFixLossOnScreen = true;
                    initAndRunMsgAfterFixLossTimeline();
                } else {
                    initAndRunStimulusTimeline();
                }
            }

            /* Run timeline for next stimulus */
            else {
                if (!procedureIsFinished) {
                    timelineFixationMonitor.stop();
                    initAndRunStimulusTimeline();
                }
            }
        });

        /* Add key frame to timeline */
        timelineFixationMonitor.getKeyFrames().add(keyFrameIntervalBetweenStimuli);

        /* Play timeline */
        timelineFixationMonitor.play();
    }

    /**
     * Init fixation monitor shape.
     */
    private void initFixationMonitorShape() {

        double monitorStimulusRadiusX = (settingsFixMonitorBlindspot.getMonitorStimulusSizeInDegreesHorizontal() / 2) * pxForOneDgX;
        double monitorStimulusRadiusY = (settingsFixMonitorBlindspot.getMonitorStimulusSizeInDegreesVertical() / 2) * pxForOneDgY;

        double monitorStimulusPositionX;
        double monitorStimulusPositionY;

        if (settings.isUseCorrectionForSphericityOfTheFieldOfView()) {

            double r = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().getPatientDistanceFromTheScreen();

            double ax = settingsFixMonitorBlindspot.getMonitorStimulusDistanceFromFixPointInDegreesHorizontal();
            double ay = settingsFixMonitorBlindspot.getMonitorStimulusDistanceFromFixPointInDegreesVertical();

            double mx = functions.calculateOppositeAngle(ax, r);
            double my = functions.calculateOppositeAngle(ay, r);

            double mxPixels = functions.millimitersToPixels(mx, screenResInPxX, settings.getScreenWidthInMm());
            double myPixels = functions.millimitersToPixels(my, screenResInPxY, settings.getScreenHeightInMm());

            monitorStimulusPositionX = centerOfTheGridInPxX + mxPixels;
            monitorStimulusPositionY = centerOfTheGridInPxY + myPixels;

        } else {
            monitorStimulusPositionX = centerOfTheGridInPxX + (settingsFixMonitorBlindspot.getMonitorStimulusDistanceFromFixPointInDegreesHorizontal() * pxForOneDgX);
            monitorStimulusPositionY = centerOfTheGridInPxY + (settingsFixMonitorBlindspot.getMonitorStimulusDistanceFromFixPointInDegreesVertical() * pxForOneDgY);
        }

        double hue = settings.getLuminanceScaleForStimuli().getHue();
        double saturation = settings.getLuminanceScaleForStimuli().getSaturation() / 100;
        double brightness = Double.valueOf(settingsFixMonitorBlindspot.getMonitorStimulusBrightness()) / 100;
        Color color = Color.hsb(hue, saturation, brightness);

        fixationMonitorShape = new Ellipse(monitorStimulusPositionX, monitorStimulusPositionY, monitorStimulusRadiusX, monitorStimulusRadiusY);
        fixationMonitorShape.setFill(color);
        fixationMonitorShape.setStroke(color);
    }

    /**
     * Init and run timeline for displaying message after fixation loss.
     */
    private void initAndRunMsgAfterFixLossTimeline() {

        /* Init message after fixation loss */
        if (messageAfterFixLoss == null) {
            initMessageAfterFixationLoss();
        }

        /* Init timeline for displaying message after fixation loss */
        timelineMsgAfterFixLoss = new Timeline();

        /* Initialize cumulative time interval */
        int cumulativeTimeInterval = 0;

        /* Create KeyFrame for displaying message after fixation loss */
        KeyFrame keyFrameDisplayMsg = new KeyFrame(Duration.millis(cumulativeTimeInterval), event -> {

            /* Add message to display pane */
            displayPane.getChildren().add(messageAfterFixLoss);
        });

        /* Add key frame to timeline */
        timelineMsgAfterFixLoss.getKeyFrames().add(keyFrameDisplayMsg);

        /* Update cumulative time interval */
        cumulativeTimeInterval += settingsFixMonitorBlindspot.getResumeProcedureAutomaticallyAfterXMs();

        /* Create KeyFrame for removing message from display pane */
        KeyFrame keyFrameRemoveMsg = new KeyFrame(Duration.millis(cumulativeTimeInterval), event -> {
            if (displayPane.getChildren().size() > 1) {
                displayPane.getChildren().remove(1);
            }
        });

        /* Add key frame to timeline */
        timelineMsgAfterFixLoss.getKeyFrames().add(keyFrameRemoveMsg);

        /* Update cumulative time interval */
        cumulativeTimeInterval += settingsFixMonitorBlindspot.getNextStimulusAfterXMsAfterMessage();

        /* Create KeyFrame for time interval between next stimulus */
        KeyFrame keyFrameIntervalBetweenStimuli = new KeyFrame(Duration.millis(cumulativeTimeInterval), event -> {
           if (!procedureIsFinished) {
               timelineMsgAfterFixLoss.stop();
               isMsgAfterFixLossOnScreen = false;
               initAndRunStimulusTimeline();
           }
        });

        /* Add key frame to timeline */
        timelineMsgAfterFixLoss.getKeyFrames().add(keyFrameIntervalBetweenStimuli);

        /* Play timeline */
        timelineMsgAfterFixLoss.play();
    }

    /**
     * Init message after fixation loss.
     */
    private void initMessageAfterFixationLoss() {

        Label msgBox = new Label();
        msgBox.setWrapText(true);
        msgBox.setAlignment(Pos.CENTER);
        msgBox.setTextAlignment(TextAlignment.CENTER);
        msgBox.setText(settingsFixMonitorBlindspot.getMessageText());

        double msgBoxWidthInPx = settingsFixMonitorBlindspot.getMessageBackgroundSizeInDegreesHorizontal() * pxForOneDgX;
        double msgBoxHeightInPx = settingsFixMonitorBlindspot.getMessageBackgroundSizeInDegreesVertical() * pxForOneDgY;

        double msgBoxLocationX = centerOfTheGridInPxX + (settingsFixMonitorBlindspot.getMessageDistanceFromFixPointInDegreesHorizontal() * pxForOneDgX) - (msgBoxWidthInPx / 2);
        double msgBoxLocationY = centerOfTheGridInPxY + (settingsFixMonitorBlindspot.getMessageDistanceFromFixPointInDegreesVertical() * pxForOneDgY) - (msgBoxHeightInPx / 2);

        msgBox.setMinSize(msgBoxWidthInPx, msgBoxHeightInPx);
        msgBox.setMaxSize(msgBoxWidthInPx, msgBoxHeightInPx);
        msgBox.setLayoutX(msgBoxLocationX);
        msgBox.setLayoutY(msgBoxLocationY);

        String style = "-fx-border-width: 2; " +
                "-fx-border-color: black; " +
                "-fx-border-style: solid; " +
                "-fx-background-color: " + functions.toHexCode(settingsFixMonitorBlindspot.getMessageBackgroundColor()) + "; " +
                "-fx-text-fill: " + functions.toHexCode(settingsFixMonitorBlindspot.getMessageFontColor()) + "; " +
                "-fx-font-weight: " + settingsFixMonitorBlindspot.getMessageFontWeight() + ";" +
                "-fx-font-size: " + settingsFixMonitorBlindspot.getMessageFontSize() + "px;";
        msgBox.setStyle(style);

        messageAfterFixLoss = msgBox;
    }

    /**
     * Get brightness value for next stimulus presentation based on previous answers to this stimulus.
     * @param answers
     * @return Brightness value.
     */
    public int getBrightnessForNextStimulusPresentation(int[] answers) {

        int brightnessIndex = settings.getBrightnessVectorLen() / 2;
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

        return brightnessVector[brightnessIndex];
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
    public void decideAboutStimulusDeactivation(int[] answers) {

        boolean[] brightnessVectorRepresentation = new boolean[settings.getBrightnessVectorLen()];
        for (int i = 0; i < brightnessVectorRepresentation.length; i++) {
            brightnessVectorRepresentation[i] = true;
        }
        int currentElementIndex = settings.getBrightnessVectorLen() / 2;
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
        // 4. Set progress for ProgressIndicator in DEPRECATED_Procedure scene.
        // 5. [removed]
        // 6. If active stimuli list is empty, finish the procedure.
        if (deactivateThisStimulus) {

            // Ad. 1.
            if (indexOfTheOnlyTrue == -1) {
                currentlyDisplayedStimulus.setBrightnessThreshold(indexOfTheOnlyTrue);
                currentlyDisplayedStimulus.setLuminanceThreshold(indexOfTheOnlyTrue);
                currentlyDisplayedStimulus.setDecibelThreshold(indexOfTheOnlyTrue);
            } else {
                currentlyDisplayedStimulus.setBrightnessThreshold(brightnessVector[indexOfTheOnlyTrue]);
                currentlyDisplayedStimulus.setLuminanceThreshold(functions.round(settings.getLuminanceScaleForStimuli().getFittedLuminanceForEachBrightnessValue()[currentlyDisplayedStimulus.getBrightnessThreshold()], 2));

                double stimMaxLum = settings.getLuminanceScaleForStimuli().getFittedLuminanceForEachBrightnessValue()[settings.getStimuliMaxBrightness()];
                double stimThresholdLum = currentlyDisplayedStimulus.getLuminanceThreshold();
                double bgLum = settings.getLuminanceScaleForBackground().getFittedLuminanceForEachBrightnessValue()[settings.getBackgroundBrightness()];

                currentlyDisplayedStimulus.setDecibelThreshold(functions.decibelsValue(stimMaxLum, stimThresholdLum, bgLum, 2));
            }

            // Ad. 2.
            arrayListActiveStimuliIndices.remove(new Integer(currentlyDisplayedStimulus.getIndex()));

            // Ad. 3.

            // Ad. 4.
            double progress;
            if (arrayListActiveStimuliIndices.size() > 0) {
                progress = 1.0 - ((double) arrayListActiveStimuliIndices.size() / (double) arrayListProcedureBasicStimuli.size());
            } else {
                progress = 1.0;
            }
            viewProcedurePreviewController.setProgressForProgressIndicator(progress);

            // Ad. 5.

            // Ad. 6.
            if (arrayListActiveStimuliIndices.size() == 0) {

                // Stop procedure timeline and set procedure status as finished.
                timelineStimulus.stop();
                timelineStopwatch.stop();

                procedureIsFinished = true;

                viewProcedurePreviewController.setColorForCircle(Color.WHITE);
                viewProcedurePreviewController.setTextForLabel("Finished");
                viewProcedurePreviewController.setStyleForLabel("-fx-text-fill: black;");
                viewProcedurePreviewController.setButtonStartDisable(true);

                drawProcedureStateIndicator("Square");

                // Prepare data and display some additional information in text area in DEPRECATED_Procedure scene.
                viewProcedurePreviewController.addTextToTextArea("PROCEDURE INFORMATION" + "\n\n");

                /* Calculate totat and positive fixation monitor checks, as well as fixation monitor accuracy (%) */
                int totalFixMonChecks = arrayListAnswersToFixMonitor.size();
                int posFixMonChecks = (int) arrayListAnswersToFixMonitor.stream().filter(f -> !f).count();
                double fixMonAccuracyInPerc = functions.round(((double) posFixMonChecks / totalFixMonChecks) * 100, 2);

                /* Write results in text area in Procedure Preview View */
                viewProcedurePreviewController.addTextToTextArea("Total B fixation checks: " + totalFixMonChecks + "\n");
                viewProcedurePreviewController.addTextToTextArea("Positive B fixation checks: " + posFixMonChecks + "\n");
                viewProcedurePreviewController.addTextToTextArea("B fixation accuracy (%): " + fixMonAccuracyInPerc + "\n\n");

                int falsePositive_Positive = falsePosIndicator_PosAns;
                int falsePositive_False = falsePosIndicator_FalseAns;

                double falsePositiveAnswers;

                try {
                    falsePositiveAnswers = functions.round(((double) falsePositive_False / (falsePositive_Positive + falsePositive_False)) * 100, 2);

                    viewProcedurePreviewController.addTextToTextArea("False-positive answers (%): " + falsePositiveAnswers + " (" + falsePositive_False + "f/" + falsePositive_Positive + "p" + ")" + "\n\n");
                }catch (NumberFormatException ex) {
                    viewProcedurePreviewController.addTextToTextArea("False-positive answers (%): " + "NaN" + " (" + falsePositive_False + "f/" + falsePositive_Positive + "p" + ")" + "\n\n");
                }

                long timeOfTheEndOfTheProcedure = System.currentTimeMillis();
                String procedureDuration = functions.totalTime(timeStartOfTheProcedure, timeOfTheEndOfTheProcedure);
                viewProcedurePreviewController.addTextToTextArea("Test duration: " + procedureDuration + "\n");

                // Initialize ProcedureBasicData and set data for it.
                ProcedureBasicData procedureBasicData = new ProcedureBasicData();
                procedureBasicData.setArrayListProcedureBasicStimulus(arrayListProcedureBasicStimuli);

                procedureBasicData.setTotalFixationMonitorChecks(totalFixMonChecks);
                procedureBasicData.setPositiveFixationMonitorChecks(posFixMonChecks);
                procedureBasicData.setFixationMonitorAccuracyInPercentages(fixMonAccuracyInPerc);

                procedureBasicData.setTestDuration(procedureDuration);
                StartApplication.getSpecvisData().setProcedureBasicData(procedureBasicData);
            }
        }
    }
}
