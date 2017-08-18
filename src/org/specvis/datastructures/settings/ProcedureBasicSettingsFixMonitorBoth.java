package org.specvis.datastructures.settings;

import javafx.scene.paint.Color;
import org.specvis.StartApplication;

/**
 * Created by piotr_dzwiniel on 18.07.2017.
 * Last modification on 2017-08-18.
 *
 * Class for storing settings information for fixation monitor technique "Both".
 */
public class ProcedureBasicSettingsFixMonitorBoth {

    /* Local fields */
    private UISettingsFixMonitorBoth specvisData;

    /* Parameters from "Fixation and other" -> Fixation monitor technique "Both" -> General TAB */
    private boolean isMonitorFixationEveryXStimuli;
    private boolean isMonitorFixationEveryXToYStimuli;
    private int monitorFixationEveryXStimuli;
    private int monitorFixationEveryXToYStimuli_1;
    private int monitorFixationEveryXToYStimuli_2;

    /* Parameters from "Fixation and other" -> Fixation monitor technique "Both" -> General TAB -> Blindspot TAB */
    private double fixMonitorStimulusSizeInDgX;
    private double fixMonitorStimulusSizeInDgY;
    private int fixMonitorStimulusBrightness;
    private double fixMonitorStimulusDistanceFromFixPointInDgX;
    private double fixMonitorStimulusDistanceFromFixPointInDgY;

    /* Parameters from "Fixation and other" -> Fixation monitor technique "Both" -> General TAB -> Fixation point change TAB */
    private double changedFixPointSizeInDgX;
    private double changedFixPointSizeInDgY;
    private Color changedFixPointColor;

    /* Parameters from "Fixation and other" -> Fixation monitor technique "Both" -> Message after fix loss TAB */
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

    public ProcedureBasicSettingsFixMonitorBoth() {
        init();
    }

    private void init() {

        /* INIT Local fields */
        specvisData = StartApplication.getSpecvisData().getUiSettingsFixMonitorBoth();

        /* INIT Fix monitor technique "Both" - General TAB */
        isMonitorFixationEveryXStimuli = specvisData.isMonitorFixationEveryXStimuliSelected();
        isMonitorFixationEveryXToYStimuli = !specvisData.isMonitorFixationEveryXStimuliSelected();
        monitorFixationEveryXStimuli = specvisData.getMonitorFixationEveryXStimuli();
        monitorFixationEveryXToYStimuli_1 = specvisData.getMonitorFixationEveryXYStimuli_1();
        monitorFixationEveryXToYStimuli_2 = specvisData.getMonitorFixationEveryXYStimuli_2();

        /* INIT Fix monitor technique "Both" - General TAB - Blindspot TAB */
        fixMonitorStimulusSizeInDgX = specvisData.getFixMonitorStimulusSizeInDgX();
        fixMonitorStimulusSizeInDgY = specvisData.getFixMonitorStimulusSizeInDgY();
        fixMonitorStimulusBrightness = specvisData.getFixMonitorStimulusBrightness();
        fixMonitorStimulusDistanceFromFixPointInDgX = specvisData.getFixMonitorStimulusDistanceFromFixPointInDgX();
        fixMonitorStimulusDistanceFromFixPointInDgY = specvisData.getFixMonitorStimulusDistanceFromFixPointInDgY();

        /* INIT Fix monitor technique "Both" - General TAB - Fix point change TAB */
        changedFixPointSizeInDgX = specvisData.getChangedFixPointSizeInDgX();
        changedFixPointSizeInDgY = specvisData.getChangedFixPointSizeInDgY();
        changedFixPointColor = specvisData.getChangedFixPointColor();

        /* INIT Fix monitor technique "Both" - General TAB - Message TAB */
        isShowPatientMsgSelected = specvisData.isShowPatientMsgSelected();
        resumeProcedureAutomaticallyAfterXMs = specvisData.getResumeProcedureAutomaticallyAfterXMs();
        showNextStimAfterMsgAfterXMs = specvisData.getShowNextStimAfterMsgAfterXMs();
        textOfTheMsg = specvisData.getTextOfTheMsg();
        fontSize = specvisData.getFontSize();
        fontWeight = specvisData.getFontWeight();
        fontColor = specvisData.getFontColor();
        msgBackgroundColor = specvisData.getMsgBackgroundColor();
        msgBackgroundSizeInDgX = specvisData.getMsgBackgroundSizeInDgX();
        msgBackgroundSizeInDgY = specvisData.getMsgBackgroundSizeInDgY();
        msgDistanceFromFixPointInDgX = specvisData.getMsgDistanceFromFixPointInDgX();
        msgDistanceFromFixPointInDgY = specvisData.getMsgDistanceFromFixPointInDgY();
    }

    public boolean isMonitorFixationEveryXStimuli() {
        return isMonitorFixationEveryXStimuli;
    }

    public boolean isMonitorFixationEveryXToYStimuli() {
        return isMonitorFixationEveryXToYStimuli;
    }

    public int getMonitorFixationEveryXStimuli() {
        return monitorFixationEveryXStimuli;
    }

    public int getMonitorFixationEveryXToYStimuli_1() {
        return monitorFixationEveryXToYStimuli_1;
    }

    public int getMonitorFixationEveryXToYStimuli_2() {
        return monitorFixationEveryXToYStimuli_2;
    }

    public double getFixMonitorStimulusSizeInDgX() {
        return fixMonitorStimulusSizeInDgX;
    }

    public double getFixMonitorStimulusSizeInDgY() {
        return fixMonitorStimulusSizeInDgY;
    }

    public int getFixMonitorStimulusBrightness() {
        return fixMonitorStimulusBrightness;
    }

    public double getFixMonitorStimulusDistanceFromFixPointInDgX() {
        return fixMonitorStimulusDistanceFromFixPointInDgX;
    }

    public double getFixMonitorStimulusDistanceFromFixPointInDgY() {
        return fixMonitorStimulusDistanceFromFixPointInDgY;
    }

    public double getChangedFixPointSizeInDgX() {
        return changedFixPointSizeInDgX;
    }

    public double getChangedFixPointSizeInDgY() {
        return changedFixPointSizeInDgY;
    }

    public Color getChangedFixPointColor() {
        return changedFixPointColor;
    }

    public boolean isShowPatientMsgSelected() {
        return isShowPatientMsgSelected;
    }

    public int getResumeProcedureAutomaticallyAfterXMs() {
        return resumeProcedureAutomaticallyAfterXMs;
    }

    public int getShowNextStimAfterMsgAfterXMs() {
        return showNextStimAfterMsgAfterXMs;
    }

    public String getTextOfTheMsg() {
        return textOfTheMsg;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public Color getMsgBackgroundColor() {
        return msgBackgroundColor;
    }

    public double getMsgBackgroundSizeInDgX() {
        return msgBackgroundSizeInDgX;
    }

    public double getMsgBackgroundSizeInDgY() {
        return msgBackgroundSizeInDgY;
    }

    public double getMsgDistanceFromFixPointInDgX() {
        return msgDistanceFromFixPointInDgX;
    }

    public double getMsgDistanceFromFixPointInDgY() {
        return msgDistanceFromFixPointInDgY;
    }
}
