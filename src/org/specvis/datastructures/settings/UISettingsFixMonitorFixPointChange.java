package org.specvis.datastructures.settings;

import javafx.scene.paint.Color;

/**
 * Created by Piotr Dzwiniel on 2016-02-29.
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

public class UISettingsFixMonitorFixPointChange {

    // 1.
    private boolean isMonitorFixationEveryXStimuliSelected;
    private int monitorFixationEveryXStimuli;
    private int monitorFixationEveryXYStimuli_1;
    private int monitorFixationEveryXYStimuli_2;
    private double changedFixPointSizeX;
    private double changedFixPointSizeY;
    private Color changedFixPointColor;
    private double changedFixPointLuminance;

    // 2.
    private boolean isShowPatientMsgSelected;
    private int resumeProcedureAutomaticallyAfterXMs;
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
        changedFixPointSizeX = 1;
        changedFixPointSizeY = 1;
        changedFixPointColor = Color.RED;
        changedFixPointLuminance = 0;

        // 2.
        isShowPatientMsgSelected = true;
        resumeProcedureAutomaticallyAfterXMs = 1000;
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

    public double getChangedFixPointSizeX() {
        return changedFixPointSizeX;
    }

    public void setChangedFixPointSizeX(double changedFixPointSizeX) {
        this.changedFixPointSizeX = changedFixPointSizeX;
    }

    public double getChangedFixPointSizeY() {
        return changedFixPointSizeY;
    }

    public void setChangedFixPointSizeY(double changedFixPointSizeY) {
        this.changedFixPointSizeY = changedFixPointSizeY;
    }

    public Color getChangedFixPointColor() {
        return changedFixPointColor;
    }

    public void setChangedFixPointColor(Color changedFixPointColor) {
        this.changedFixPointColor = changedFixPointColor;
    }

    public double getChangedFixPointLuminance() {
        return changedFixPointLuminance;
    }

    public void setChangedFixPointLuminance(double changedFixPointLuminance) {
        this.changedFixPointLuminance = changedFixPointLuminance;
    }

    public boolean isShowPatientMsgSelected() {
        return isShowPatientMsgSelected;
    }

    public void setIsShowPatientMsgSelected(boolean isShowPatientMsgSelected) {
        this.isShowPatientMsgSelected = isShowPatientMsgSelected;
    }

    public int getResumeProcedureAutomaticallyAfterXMs() {
        return resumeProcedureAutomaticallyAfterXMs;
    }

    public void setResumeProcedureAutomaticallyAfterXMs(int resumeProcedureAutomaticallyAfterXMs) {
        this.resumeProcedureAutomaticallyAfterXMs = resumeProcedureAutomaticallyAfterXMs;
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
