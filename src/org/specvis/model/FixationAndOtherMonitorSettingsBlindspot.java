package org.specvis.model;

import javafx.scene.paint.Color;
import org.specvis.StartApplication;

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

public class FixationAndOtherMonitorSettingsBlindspot {

    // 1.
    private boolean isMonitorFixationEveryXStimuliSelected;
    private int monitorFixationEveryXStimuli;
    private int monitorFixationEveryXYStimuli_1;
    private int monitorFixationEveryXYStimuli_2;
    private double fixationMonitorStimulusSizeX;
    private double fixationMonitorStimulusSizeY;
    private int fixationMonitorStimulusBrightness;
    private double fixationMonitorStimulusLuminance;
    private double blindspotDistanceFromFixPointX;
    private double blindspotDistanceFromFixPointY;

    // 2.
    private boolean isShowPatientMsgSelected;
    private String textOfTheMsg;
    private int fontSize;
    private String fontWeight;
    private Color fontColor;
    private Color msgBackgroundColor;
    private double msgBackgroundSizeX;
    private double msgBackgroundSizeY;
    private double msgDistanceFromFixPointX;
    private double msgDistanceFromFixPointY;
    private int resumeToNextStimulusTimeInterval;

    public void setDefaultValues() {

        // 1.
        isMonitorFixationEveryXStimuliSelected = true;
        monitorFixationEveryXStimuli = 10;
        monitorFixationEveryXYStimuli_1 = 1;
        monitorFixationEveryXYStimuli_2 = 10;
        fixationMonitorStimulusSizeX = 1;
        fixationMonitorStimulusSizeY = 1;
        fixationMonitorStimulusBrightness = 100;
        fixationMonitorStimulusLuminance = StartApplication.getSpecvisData().getScreenAndLuminanceScale().getStimulusLuminanceScale().getFittedLuminanceForEachBrightnessValue()[100];
        blindspotDistanceFromFixPointX = StartApplication.getSpecvisData().getPatient().getTestedEye().equals("Left") ? -15 : 15;
        blindspotDistanceFromFixPointY = 3;

        // 2.
        isShowPatientMsgSelected = true;
        textOfTheMsg = "Please keep eye/s on the fixation point!";
        fontSize = 16;
        fontWeight = "normal";
        fontColor = Color.BLACK;
        msgBackgroundColor = Color.WHITE;
        msgBackgroundSizeX = 12;
        msgBackgroundSizeY = 3;
        msgDistanceFromFixPointX = 0;
        msgDistanceFromFixPointY = -3;
        resumeToNextStimulusTimeInterval = 1000;
    }

    public boolean isMonitorFixationEveryXStimuliSelected() {
        return isMonitorFixationEveryXStimuliSelected;
    }

    public void setIsMonitorFixationEveryXStimuliSelected(boolean isMonitorFixationEveryXStimuliSelected) {
        this.isMonitorFixationEveryXStimuliSelected = isMonitorFixationEveryXStimuliSelected;
    }

    public int getMonitorFixationEveryXStimuli() {
        return monitorFixationEveryXStimuli;
    }

    public void setMonitorFixationEveryXStimuli(int monitorFixationEveryXStimuli) {
        this.monitorFixationEveryXStimuli = monitorFixationEveryXStimuli;
    }

    public int getMonitorFixationEveryXYStimuli_1() {
        return monitorFixationEveryXYStimuli_1;
    }

    public void setMonitorFixationEveryXYStimuli_1(int monitorFixationEveryXYStimuli_1) {
        this.monitorFixationEveryXYStimuli_1 = monitorFixationEveryXYStimuli_1;
    }

    public int getMonitorFixationEveryXYStimuli_2() {
        return monitorFixationEveryXYStimuli_2;
    }

    public void setMonitorFixationEveryXYStimuli_2(int monitorFixationEveryXYStimuli_2) {
        this.monitorFixationEveryXYStimuli_2 = monitorFixationEveryXYStimuli_2;
    }

    public double getFixationMonitorStimulusSizeX() {
        return fixationMonitorStimulusSizeX;
    }

    public void setFixationMonitorStimulusSizeX(double fixationMonitorStimulusSizeX) {
        this.fixationMonitorStimulusSizeX = fixationMonitorStimulusSizeX;
    }

    public double getFixationMonitorStimulusSizeY() {
        return fixationMonitorStimulusSizeY;
    }

    public void setFixationMonitorStimulusSizeY(double fixationMonitorStimulusSizeY) {
        this.fixationMonitorStimulusSizeY = fixationMonitorStimulusSizeY;
    }

    public int getFixationMonitorStimulusBrightness() {
        return fixationMonitorStimulusBrightness;
    }

    public void setFixationMonitorStimulusBrightness(int fixationMonitorStimulusBrightness) {
        this.fixationMonitorStimulusBrightness = fixationMonitorStimulusBrightness;
    }

    public double getFixationMonitorStimulusLuminance() {
        return fixationMonitorStimulusLuminance;
    }

    public void setFixationMonitorStimulusLuminance(double fixationMonitorStimulusLuminance) {
        this.fixationMonitorStimulusLuminance = fixationMonitorStimulusLuminance;
    }

    public double getBlindspotDistanceFromFixPointX() {
        return blindspotDistanceFromFixPointX;
    }

    public void setBlindspotDistanceFromFixPointX(double blindspotDistanceFromFixPointX) {
        this.blindspotDistanceFromFixPointX = blindspotDistanceFromFixPointX;
    }

    public double getBlindspotDistanceFromFixPointY() {
        return blindspotDistanceFromFixPointY;
    }

    public void setBlindspotDistanceFromFixPointY(double blindspotDistanceFromFixPointY) {
        this.blindspotDistanceFromFixPointY = blindspotDistanceFromFixPointY;
    }

    public boolean isShowPatientMsgSelected() {
        return isShowPatientMsgSelected;
    }

    public void setIsShowPatientMsgSelected(boolean isShowPatientMsgSelected) {
        this.isShowPatientMsgSelected = isShowPatientMsgSelected;
    }

    public String getTextOfTheMsg() {
        return textOfTheMsg;
    }

    public void setTextOfTheMsg(String textOfTheMsg) {
        this.textOfTheMsg = textOfTheMsg;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public Color getMsgBackgroundColor() {
        return msgBackgroundColor;
    }

    public void setMsgBackgroundColor(Color msgBackgroundColor) {
        this.msgBackgroundColor = msgBackgroundColor;
    }

    public double getMsgBackgroundSizeX() {
        return msgBackgroundSizeX;
    }

    public void setMsgBackgroundSizeX(double msgBackgroundSizeX) {
        this.msgBackgroundSizeX = msgBackgroundSizeX;
    }

    public double getMsgBackgroundSizeY() {
        return msgBackgroundSizeY;
    }

    public void setMsgBackgroundSizeY(double msgBackgroundSizeY) {
        this.msgBackgroundSizeY = msgBackgroundSizeY;
    }

    public double getMsgDistanceFromFixPointX() {
        return msgDistanceFromFixPointX;
    }

    public void setMsgDistanceFromFixPointX(double msgDistanceFromFixPointX) {
        this.msgDistanceFromFixPointX = msgDistanceFromFixPointX;
    }

    public double getMsgDistanceFromFixPointY() {
        return msgDistanceFromFixPointY;
    }

    public void setMsgDistanceFromFixPointY(double msgDistanceFromFixPointY) {
        this.msgDistanceFromFixPointY = msgDistanceFromFixPointY;
    }

    public int getResumeToNextStimulusTimeInterval() {
        return resumeToNextStimulusTimeInterval;
    }

    public void setResumeToNextStimulusTimeInterval(int resumeToNextStimulusTimeInterval) {
        this.resumeToNextStimulusTimeInterval = resumeToNextStimulusTimeInterval;
    }
}
