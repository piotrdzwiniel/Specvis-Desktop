package org.specvis.model;

import org.specvis.model.procedure.BasicProcedureData;
import org.specvis.model.procedure.BasicProcedureSettings;

/**
 * Created by Piotr Dzwiniel on 2016-02-09.
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

public class SpecvisData {

    // 1. Core of Specvis app.
    private Patient patient;
    private ScreenAndLuminanceScale screenAndLuminanceScale;
    private StimulusAndBackground stimulusAndBackground;
    private FixationAndOther fixationAndOther;
    private FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot;
    private FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange;

    // 2. Procedures.
    private BasicProcedureSettings basicProcedureSettings;
    private BasicProcedureData basicProcedureData;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ScreenAndLuminanceScale getScreenAndLuminanceScale() {
        return screenAndLuminanceScale;
    }

    public void setScreenAndLuminanceScale(ScreenAndLuminanceScale screenAndLuminanceScale) {
        this.screenAndLuminanceScale = screenAndLuminanceScale;
    }

    public StimulusAndBackground getStimulusAndBackground() {
        return stimulusAndBackground;
    }

    public void setStimulusAndBackground(StimulusAndBackground stimulusAndBackground) {
        this.stimulusAndBackground = stimulusAndBackground;
    }

    public FixationAndOther getFixationAndOther() {
        return fixationAndOther;
    }

    public void setFixationAndOther(FixationAndOther fixationAndOther) {
        this.fixationAndOther = fixationAndOther;
    }

    public FixationAndOtherMonitorSettingsBlindspot getFixationAndOtherMonitorSettingsBlindspot() {
        return fixationAndOtherMonitorSettingsBlindspot;
    }

    public void setFixationAndOtherMonitorSettingsBlindspot(FixationAndOtherMonitorSettingsBlindspot fixationAndOtherMonitorSettingsBlindspot) {
        this.fixationAndOtherMonitorSettingsBlindspot = fixationAndOtherMonitorSettingsBlindspot;
    }

    public FixationAndOtherMonitorSettingsFixPointChange getFixationAndOtherMonitorSettingsFixPointChange() {
        return fixationAndOtherMonitorSettingsFixPointChange;
    }

    public void setFixationAndOtherMonitorSettingsFixPointChange(FixationAndOtherMonitorSettingsFixPointChange fixationAndOtherMonitorSettingsFixPointChange) {
        this.fixationAndOtherMonitorSettingsFixPointChange = fixationAndOtherMonitorSettingsFixPointChange;
    }

    public BasicProcedureSettings getBasicProcedureSettings() {
        return basicProcedureSettings;
    }

    public void setBasicProcedureSettings(BasicProcedureSettings basicProcedureSettings) {
        this.basicProcedureSettings = basicProcedureSettings;
    }

    public BasicProcedureData getBasicProcedureData() {
        return basicProcedureData;
    }

    public void setBasicProcedureData(BasicProcedureData basicProcedureData) {
        this.basicProcedureData = basicProcedureData;
    }
}
