package org.specvis.datastructures.settings;

import javafx.scene.paint.Color;
import org.specvis.StartApplication;
import org.specvis.datastructures.luminancescale.LuminanceScale;
import org.specvis.datastructures.SpecvisData;

/**
 * Created by piotr_dzwiniel on 18.07.2017.
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

public class ProcedureBasicSettingsGeneral {

    /* Local fields */
    private SpecvisData specvisData;

    /* Parameters from "Screen and luminance scale" */
    private int chosenScreenIndex;
    private int screenResolutionInPxHorizontal;
    private int screenResolutionInPxVertical;
    private int screenWidthInMm;
    private int screenHeightInMm;
    private double involvedVisualFieldInDegreesHorizontal;
    private double involvedVisualFieldInDegreesVertical;
    private LuminanceScale luminanceScaleForStimuli;
    private LuminanceScale luminanceScaleForBackground;

    /* Parameters from "Stimulus and background */
    private int stimuliMaxBrightness;
    private int stimuliMinBrightness;
    private String stimuliShape;
    private double stimuliSizeInDegreesHorizontal;
    private double stimuliSizeInDegreesVertical;
    private double stimuliInclinationInDegrees;
    private int stimuliDisplayTimeInMs;
    private int interStimuliIntervalInMsConstantPart;
    private int interStimuliIntervalInMsRandomPart;
    private int backgroundBrightness;
    private double distanceBetweenStimuliInDegreesHorizontal;
    private double distanceBetweenStimuliInDegreesVertical;
    private boolean useCorrectionForSphericityOfTheFieldOfView;

    /* Parameters from "Fixation and other" */
    private String fixationMonitorTechnique;
    private Color fixationPointColor;
    private double fixationPointSizeInDegreesHorizontal;
    private double fixationPointSizeInDegreesVertical;
    private double fixationPointLocationHorizontal;
    private double fixationPointLocationVertical;
    private String keyAnswerToStimulus;
    private String keyPauseProcedure;
    private String keyCancelProcedure;

    /* Parameters from "DEPRECATED_Procedure basic" */
    private boolean spreadValuesLog;
    private int brightnessVectorLen;

    public ProcedureBasicSettingsGeneral() {
        init();
    }

    private void init() {

        /* INIT - Local fields */
        specvisData = StartApplication.getSpecvisData();

        /* INIT - Parameters from "Screen and luminance scale" */
        chosenScreenIndex = specvisData.getUiSettingsScreenAndLuminanceScale().getChosenScreenIndex();
        screenResolutionInPxHorizontal = specvisData.getUiSettingsScreenAndLuminanceScale().getScreenResolutionX();
        screenResolutionInPxVertical = specvisData.getUiSettingsScreenAndLuminanceScale().getScreenResolutionY();
        screenWidthInMm = specvisData.getUiSettingsScreenAndLuminanceScale().getScreenWidth();
        screenHeightInMm = specvisData.getUiSettingsScreenAndLuminanceScale().getScreenHeight();
        involvedVisualFieldInDegreesHorizontal = specvisData.getUiSettingsScreenAndLuminanceScale().getInvolvedVisualFieldX();
        involvedVisualFieldInDegreesVertical = specvisData.getUiSettingsScreenAndLuminanceScale().getInvolvedVisualFieldY();
        luminanceScaleForStimuli = specvisData.getUiSettingsScreenAndLuminanceScale().getStimulusLuminanceScale();
        luminanceScaleForBackground = specvisData.getUiSettingsScreenAndLuminanceScale().getBackgroundLuminanceScale();

        /* INIT - Parameters from "Stimulus and background */
        stimuliMaxBrightness = specvisData.getUiSettingsStimulusAndBackground().getStimulusMaxBrightness();
        stimuliMinBrightness = specvisData.getUiSettingsStimulusAndBackground().getStimulusMinBrightness();
        stimuliShape = specvisData.getUiSettingsStimulusAndBackground().getStimulusShape();
        stimuliSizeInDegreesHorizontal = specvisData.getUiSettingsStimulusAndBackground().getStimulusSizeX();
        stimuliSizeInDegreesVertical = specvisData.getUiSettingsStimulusAndBackground().getStimulusSizeY();
        stimuliInclinationInDegrees = specvisData.getUiSettingsStimulusAndBackground().getStimulusInclination();
        stimuliDisplayTimeInMs = specvisData.getUiSettingsStimulusAndBackground().getStimulusDisplayTime();
        interStimuliIntervalInMsConstantPart = specvisData.getUiSettingsStimulusAndBackground().getConstantPartOfInterval();
        interStimuliIntervalInMsRandomPart = specvisData.getUiSettingsStimulusAndBackground().getRandomPartOfInterval();
        backgroundBrightness = specvisData.getUiSettingsStimulusAndBackground().getBackgroundBrightness();
        distanceBetweenStimuliInDegreesHorizontal = specvisData.getUiSettingsStimulusAndBackground().getDistanceBetweenStimuliInDegreesX();
        distanceBetweenStimuliInDegreesVertical = specvisData.getUiSettingsStimulusAndBackground().getDistanceBetweenStimuliInDegreesY();
        useCorrectionForSphericityOfTheFieldOfView = specvisData.getUiSettingsStimulusAndBackground().isCorrectionForSphericityCheckBoxChecked();

        /* INIT - Parameters from "Fixation and other" */
        fixationMonitorTechnique = specvisData.getUiSettingsFixationAndOther().getFixationMonitorTechnique();
        fixationPointColor = specvisData.getUiSettingsFixationAndOther().getFixationPointColor();
        fixationPointSizeInDegreesHorizontal = specvisData.getUiSettingsFixationAndOther().getFixationPointSizeX();
        fixationPointSizeInDegreesVertical = specvisData.getUiSettingsFixationAndOther().getFixationPointSizeY();
        fixationPointLocationHorizontal = specvisData.getUiSettingsFixationAndOther().getFixationPointLocationX();
        fixationPointLocationVertical = specvisData.getUiSettingsFixationAndOther().getFixationPointLocationY();
        keyAnswerToStimulus = specvisData.getUiSettingsFixationAndOther().getAnswerToStimulus();
        keyPauseProcedure = specvisData.getUiSettingsFixationAndOther().getPauseProcedure();
        keyCancelProcedure = specvisData.getUiSettingsFixationAndOther().getCancelProcedure();

        /* INIT - Parameters from "DEPRECATED_Procedure basic" */
        spreadValuesLog = specvisData.getUiSettingsProcedureBasic().isSpreadValuesLogarithmically();
        brightnessVectorLen = specvisData.getUiSettingsProcedureBasic().getVisualFieldTestBrightnessVectorLength();

    }

    public int getChosenScreenIndex() {
        return chosenScreenIndex;
    }

    public int getScreenResolutionInPxHorizontal() {
        return screenResolutionInPxHorizontal;
    }

    public int getScreenResolutionInPxVertical() {
        return screenResolutionInPxVertical;
    }

    public int getScreenWidthInMm() {
        return screenWidthInMm;
    }

    public int getScreenHeightInMm() {
        return screenHeightInMm;
    }

    public double getInvolvedVisualFieldInDegreesHorizontal() {
        return involvedVisualFieldInDegreesHorizontal;
    }

    public double getInvolvedVisualFieldInDegreesVertical() {
        return involvedVisualFieldInDegreesVertical;
    }

    public LuminanceScale getLuminanceScaleForStimuli() {
        return luminanceScaleForStimuli;
    }

    public LuminanceScale getLuminanceScaleForBackground() {
        return luminanceScaleForBackground;
    }

    public int getStimuliMaxBrightness() {
        return stimuliMaxBrightness;
    }

    public int getStimuliMinBrightness() {
        return stimuliMinBrightness;
    }

    public String getStimuliShape() {
        return stimuliShape;
    }

    public double getStimuliSizeInDegreesHorizontal() {
        return stimuliSizeInDegreesHorizontal;
    }

    public double getStimuliSizeInDegreesVertical() {
        return stimuliSizeInDegreesVertical;
    }

    public double getStimuliInclinationInDegrees() {
        return stimuliInclinationInDegrees;
    }

    public int getStimuliDisplayTimeInMs() {
        return stimuliDisplayTimeInMs;
    }

    public int getInterStimuliIntervalInMsConstantPart() {
        return interStimuliIntervalInMsConstantPart;
    }

    public int getInterStimuliIntervalInMsRandomPart() {
        return interStimuliIntervalInMsRandomPart;
    }

    public int getBackgroundBrightness() {
        return backgroundBrightness;
    }

    public double getDistanceBetweenStimuliInDegreesHorizontal() {
        return distanceBetweenStimuliInDegreesHorizontal;
    }

    public double getDistanceBetweenStimuliInDegreesVertical() {
        return distanceBetweenStimuliInDegreesVertical;
    }

    public boolean isUseCorrectionForSphericityOfTheFieldOfView() {
        return useCorrectionForSphericityOfTheFieldOfView;
    }

    public String getFixationMonitorTechnique() {
        return fixationMonitorTechnique;
    }

    public Color getFixationPointColor() {
        return fixationPointColor;
    }

    public double getFixationPointSizeInDegreesHorizontal() {
        return fixationPointSizeInDegreesHorizontal;
    }

    public double getFixationPointSizeInDegreesVertical() {
        return fixationPointSizeInDegreesVertical;
    }

    public double getFixationPointLocationHorizontal() {
        return fixationPointLocationHorizontal;
    }

    public double getFixationPointLocationVertical() {
        return fixationPointLocationVertical;
    }

    public String getKeyAnswerToStimulus() {
        return keyAnswerToStimulus;
    }

    public String getKeyPauseProcedure() {
        return keyPauseProcedure;
    }

    public String getKeyCancelProcedure() {
        return keyCancelProcedure;
    }

    public boolean isSpreadValuesLog() {
        return spreadValuesLog;
    }

    public int getBrightnessVectorLen() {
        return brightnessVectorLen;
    }
}
