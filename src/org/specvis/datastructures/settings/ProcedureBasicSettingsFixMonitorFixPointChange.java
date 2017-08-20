package org.specvis.datastructures.settings;

import javafx.scene.paint.Color;
import org.specvis.StartApplication;

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

public class ProcedureBasicSettingsFixMonitorFixPointChange {

    /* Local fields */
    private UISettingsFixMonitorFixPointChange specvisData;

    /* Parameters from "Fixation and other" -> Fixation monitor technique "Fixation point change" -> General */
    private boolean isMonitorFixationEveryXStimuli;
    private boolean isMonitorFixationEveryXToYStimuli;
    private int monitorFixationEveryXStimuli;
    private int monitorFixationEveryXToYStimuli_X;
    private int monitorFixationEveryXToYStimuli_Y;
    private double changedFixPointSizeInDegreesHorizontal;
    private double changedFixPointSizeInDegreesVertical;
    private Color changedFixPointColor;

    /* Parameters from "Fixation and other" -> Fixation monitor technique "Fixation point change" -> Message */
    private boolean isShowMessage;
    private int resumeProcedureAutomaticallyAfterXMs;
    private String messageText;
    private int messageFontSize;
    private String messageFontWeight;
    private Color messageFontColor;
    private Color messageBackgroundColor;
    private double messageBackgroundSizeInDegreesHorizontal;
    private double messageBackgroundSizeInDegreesVertical;
    private double messageDistanceFromFixPointInDegreesHorizontal;
    private double messageDistanceFromFixPointInDegreesVertical;
    private int nextStimulusAfterXMsAfterMessage;

    public ProcedureBasicSettingsFixMonitorFixPointChange() {
        init();
    }

    private void init() {

        /* INIT - Local fields */
        specvisData = StartApplication.getSpecvisData().getUiSettingsProcedureBasicFixPointChange();

        /* INIT - Parameters from "Fixation and other" -> Fixation monitor technique "Fixation point change" -> General */
        isMonitorFixationEveryXStimuli = specvisData.isMonitorFixationEveryXStimuliSelected();
        isMonitorFixationEveryXToYStimuli = !specvisData.isMonitorFixationEveryXStimuliSelected();
        monitorFixationEveryXStimuli = specvisData.getMonitorFixationEveryXStimuli();
        monitorFixationEveryXToYStimuli_X = specvisData.getMonitorFixationEveryXYStimuli_1();
        monitorFixationEveryXToYStimuli_Y = specvisData.getMonitorFixationEveryXYStimuli_2();
        changedFixPointSizeInDegreesHorizontal = specvisData.getChangedFixPointSizeX();
        changedFixPointSizeInDegreesVertical = specvisData.getChangedFixPointSizeY();
        changedFixPointColor = specvisData.getChangedFixPointColor();

        /* INIT - Parameters from "Fixation and other" -> Fixation monitor technique "Fixation point change" -> Message */
        isShowMessage = specvisData.isShowPatientMsgSelected();
        resumeProcedureAutomaticallyAfterXMs = specvisData.getResumeProcedureAutomaticallyAfterXMs();
        messageText = specvisData.getTextOfTheMsg();
        messageFontSize = specvisData.getFontSize();
        messageFontWeight = specvisData.getFontWeight();
        messageFontColor = specvisData.getFontColor();
        messageBackgroundColor = specvisData.getMsgBackgroundColor();
        messageBackgroundSizeInDegreesHorizontal = specvisData.getMsgBackgroundSizeX();
        messageBackgroundSizeInDegreesVertical = specvisData.getMsgBackgroundSizeY();
        messageDistanceFromFixPointInDegreesHorizontal = specvisData.getMsgDistanceFromFixPointX();
        messageDistanceFromFixPointInDegreesVertical = specvisData.getMsgDistanceFromFixPointY();
        nextStimulusAfterXMsAfterMessage = specvisData.getResumeToNextStimulusTimeInterval();
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

    public int getMonitorFixationEveryXToYStimuli_X() {
        return monitorFixationEveryXToYStimuli_X;
    }

    public int getMonitorFixationEveryXToYStimuli_Y() {
        return monitorFixationEveryXToYStimuli_Y;
    }

    public double getChangedFixPointSizeInDegreesHorizontal() {
        return changedFixPointSizeInDegreesHorizontal;
    }

    public double getChangedFixPointSizeInDegreesVertical() {
        return changedFixPointSizeInDegreesVertical;
    }

    public Color getChangedFixPointColor() {
        return changedFixPointColor;
    }

    public boolean isShowMessage() {
        return isShowMessage;
    }

    public int getResumeProcedureAutomaticallyAfterXMs() {
        return resumeProcedureAutomaticallyAfterXMs;
    }

    public String getMessageText() {
        return messageText;
    }

    public int getMessageFontSize() {
        return messageFontSize;
    }

    public String getMessageFontWeight() {
        return messageFontWeight;
    }

    public Color getMessageFontColor() {
        return messageFontColor;
    }

    public Color getMessageBackgroundColor() {
        return messageBackgroundColor;
    }

    public double getMessageBackgroundSizeInDegreesHorizontal() {
        return messageBackgroundSizeInDegreesHorizontal;
    }

    public double getMessageBackgroundSizeInDegreesVertical() {
        return messageBackgroundSizeInDegreesVertical;
    }

    public double getMessageDistanceFromFixPointInDegreesHorizontal() {
        return messageDistanceFromFixPointInDegreesHorizontal;
    }

    public double getMessageDistanceFromFixPointInDegreesVertical() {
        return messageDistanceFromFixPointInDegreesVertical;
    }

    public int getNextStimulusAfterXMsAfterMessage() {
        return nextStimulusAfterXMsAfterMessage;
    }
}
