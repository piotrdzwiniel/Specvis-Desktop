package org.specvis.datastructures.settings;

import javafx.scene.paint.Color;
import org.specvis.StartApplication;

/**
 * Created by piotr_dzwiniel on 17.08.2017.
 */
public class UISettingsFixMonitorBoth {

    /* TAB - General */
    private boolean isMonitorFixationEveryXStimuliSelected;
    private int monitorFixationEveryXStimuli;
    private int monitorFixationEveryXYStimuli_1;
    private int monitorFixationEveryXYStimuli_2;

    /* TAB - General - Blindspot */
    private double fixMonitorStimulusSizeInDgX;
    private double fixMonitorStimulusSizeInDgY;
    private int fixMonitorStimulusBrightness;
    private double fixMonitorStimulusLuminance;
    private double fixMonitorStimulusDistanceFromFixPointInDgX;
    private double fixMonitorStimulusDistanceFromFixPointInDgY;

    /* TAB - General - Fixation point change */
    private double changedFixPointSizeInDgX;
    private double changedFixPointSizeInDgY;
    private Color changedFixPointColor;
    private double changedFixPointColorLuminance;

    /* TAB - Message after fixation loss */
    private boolean isShowPatientMsgSelected;
    private int resumeProcedureAutomaticallyAfterXMs;
    private int showNextStimAfterMsgAfterXMs;
    private String textOfTheMsg;
    private int fontSize;
    private String fontWeight;
    private Color fontColor;
    private Color msgBackgroundColor;
    private double msgBackgroundSizeInDgX;
    private double msgBackgroundSizeInDgY;
    private double msgDistanceFromFixPointInDgX;
    private double msgDistanceFromFixPointInDgY;

    public void setDefaultValues() {

        /* TAB - General */
        isMonitorFixationEveryXStimuliSelected = true;
        monitorFixationEveryXStimuli = 10;
        monitorFixationEveryXYStimuli_1 = 1;
        monitorFixationEveryXYStimuli_2 = 10;

        /* TAB - General - Blindspot */
        fixMonitorStimulusSizeInDgX = 0.5;
        fixMonitorStimulusSizeInDgY = 0.5;
        fixMonitorStimulusBrightness = 100;
        fixMonitorStimulusLuminance = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().getStimulusLuminanceScale().getFittedLuminanceForEachBrightnessValue()[fixMonitorStimulusBrightness];
        fixMonitorStimulusDistanceFromFixPointInDgX = StartApplication.getSpecvisData().getPatient().getTestedEye().equals("Left") ? -15 : 15;
        fixMonitorStimulusDistanceFromFixPointInDgY = 3;

        /* TAB - General - Fixation point change */
        changedFixPointSizeInDgX = 1.0;
        changedFixPointSizeInDgY = 1.0;
        changedFixPointColor = Color.RED;
        changedFixPointColorLuminance = 0.0;

        /* TAB - Message after fixation loss */
        isShowPatientMsgSelected = true;
        resumeProcedureAutomaticallyAfterXMs = 1000;
        showNextStimAfterMsgAfterXMs = 1000;
        textOfTheMsg = "Please keep eye/s on the fixation point!";
        fontSize = 16;
        fontWeight = "normal";
        fontColor = Color.BLACK;
        msgBackgroundColor = Color.WHITE;
        msgBackgroundSizeInDgX = 12.0;
        msgBackgroundSizeInDgY = 3.0;
        msgDistanceFromFixPointInDgX = 0.0;
        msgDistanceFromFixPointInDgY = -3.0;
    }

    public boolean isMonitorFixationEveryXStimuliSelected() {
        return isMonitorFixationEveryXStimuliSelected;
    }

    public void setMonitorFixationEveryXStimuliSelected(boolean monitorFixationEveryXStimuliSelected) {
        isMonitorFixationEveryXStimuliSelected = monitorFixationEveryXStimuliSelected;
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

    public double getFixMonitorStimulusSizeInDgX() {
        return fixMonitorStimulusSizeInDgX;
    }

    public void setFixMonitorStimulusSizeInDgX(double fixMonitorStimulusSizeInDgX) {
        this.fixMonitorStimulusSizeInDgX = fixMonitorStimulusSizeInDgX;
    }

    public double getFixMonitorStimulusSizeInDgY() {
        return fixMonitorStimulusSizeInDgY;
    }

    public void setFixMonitorStimulusSizeInDgY(double fixMonitorStimulusSizeInDgY) {
        this.fixMonitorStimulusSizeInDgY = fixMonitorStimulusSizeInDgY;
    }

    public int getFixMonitorStimulusBrightness() {
        return fixMonitorStimulusBrightness;
    }

    public void setFixMonitorStimulusBrightness(int fixMonitorStimulusBrightness) {
        this.fixMonitorStimulusBrightness = fixMonitorStimulusBrightness;
    }

    public double getFixMonitorStimulusLuminance() {
        return fixMonitorStimulusLuminance;
    }

    public void setFixMonitorStimulusLuminance(double fixMonitorStimulusLuminance) {
        this.fixMonitorStimulusLuminance = fixMonitorStimulusLuminance;
    }

    public double getFixMonitorStimulusDistanceFromFixPointInDgX() {
        return fixMonitorStimulusDistanceFromFixPointInDgX;
    }

    public void setFixMonitorStimulusDistanceFromFixPointInDgX(double fixMonitorStimulusDistanceFromFixPointInDgX) {
        this.fixMonitorStimulusDistanceFromFixPointInDgX = fixMonitorStimulusDistanceFromFixPointInDgX;
    }

    public double getFixMonitorStimulusDistanceFromFixPointInDgY() {
        return fixMonitorStimulusDistanceFromFixPointInDgY;
    }

    public void setFixMonitorStimulusDistanceFromFixPointInDgY(double fixMonitorStimulusDistanceFromFixPointInDgY) {
        this.fixMonitorStimulusDistanceFromFixPointInDgY = fixMonitorStimulusDistanceFromFixPointInDgY;
    }

    public double getChangedFixPointSizeInDgX() {
        return changedFixPointSizeInDgX;
    }

    public void setChangedFixPointSizeInDgX(double changedFixPointSizeInDgX) {
        this.changedFixPointSizeInDgX = changedFixPointSizeInDgX;
    }

    public double getChangedFixPointSizeInDgY() {
        return changedFixPointSizeInDgY;
    }

    public void setChangedFixPointSizeInDgY(double changedFixPointSizeInDgY) {
        this.changedFixPointSizeInDgY = changedFixPointSizeInDgY;
    }

    public Color getChangedFixPointColor() {
        return changedFixPointColor;
    }

    public void setChangedFixPointColor(Color changedFixPointColor) {
        this.changedFixPointColor = changedFixPointColor;
    }

    public double getChangedFixPointColorLuminance() {
        return changedFixPointColorLuminance;
    }

    public void setChangedFixPointColorLuminance(double changedFixPointColorLuminance) {
        this.changedFixPointColorLuminance = changedFixPointColorLuminance;
    }

    public boolean isShowPatientMsgSelected() {
        return isShowPatientMsgSelected;
    }

    public void setShowPatientMsgSelected(boolean showPatientMsgSelected) {
        isShowPatientMsgSelected = showPatientMsgSelected;
    }

    public int getResumeProcedureAutomaticallyAfterXMs() {
        return resumeProcedureAutomaticallyAfterXMs;
    }

    public void setResumeProcedureAutomaticallyAfterXMs(int resumeProcedureAutomaticallyAfterXMs) {
        this.resumeProcedureAutomaticallyAfterXMs = resumeProcedureAutomaticallyAfterXMs;
    }

    public int getShowNextStimAfterMsgAfterXMs() {
        return showNextStimAfterMsgAfterXMs;
    }

    public void setShowNextStimAfterMsgAfterXMs(int showNextStimAfterMsgAfterXMs) {
        this.showNextStimAfterMsgAfterXMs = showNextStimAfterMsgAfterXMs;
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

    public double getMsgBackgroundSizeInDgX() {
        return msgBackgroundSizeInDgX;
    }

    public void setMsgBackgroundSizeInDgX(double msgBackgroundSizeInDgX) {
        this.msgBackgroundSizeInDgX = msgBackgroundSizeInDgX;
    }

    public double getMsgBackgroundSizeInDgY() {
        return msgBackgroundSizeInDgY;
    }

    public void setMsgBackgroundSizeInDgY(double msgBackgroundSizeInDgY) {
        this.msgBackgroundSizeInDgY = msgBackgroundSizeInDgY;
    }

    public double getMsgDistanceFromFixPointInDgX() {
        return msgDistanceFromFixPointInDgX;
    }

    public void setMsgDistanceFromFixPointInDgX(double msgDistanceFromFixPointInDgX) {
        this.msgDistanceFromFixPointInDgX = msgDistanceFromFixPointInDgX;
    }

    public double getMsgDistanceFromFixPointInDgY() {
        return msgDistanceFromFixPointInDgY;
    }

    public void setMsgDistanceFromFixPointInDgY(double msgDistanceFromFixPointInDgY) {
        this.msgDistanceFromFixPointInDgY = msgDistanceFromFixPointInDgY;
    }
}
