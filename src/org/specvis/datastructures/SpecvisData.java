package org.specvis.datastructures;

import org.specvis.datastructures.patient.Patient;
import org.specvis.datastructures.procedures.basic.ProcedureBasicData;
import org.specvis.datastructures.settings.*;

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
    private UISettingsScreenAndLuminanceScale uiSettingsScreenAndLuminanceScale;
    private UISettingsStimulusAndBackground uiSettingsStimulusAndBackground;
    private UISettingsFixationAndOther uiSettingsFixationAndOther;
    private UISettingsFixMonitorBlindspot uiSettingsFixMonitorBlindspot;
    private UISettingsFixMonitorFixPointChange uiSettingsProcedureBasicFixPointChange;
    private UISettingsFixMonitorBoth uiSettingsFixMonitorBoth;

    // 2. Procedures.
    private UISettingsProcedureBasic uiSettingsProcedureBasic;
    private ProcedureBasicData procedureBasicData;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public UISettingsScreenAndLuminanceScale getUiSettingsScreenAndLuminanceScale() {
        return uiSettingsScreenAndLuminanceScale;
    }

    public void setUiSettingsScreenAndLuminanceScale(UISettingsScreenAndLuminanceScale uiSettingsScreenAndLuminanceScale) {
        this.uiSettingsScreenAndLuminanceScale = uiSettingsScreenAndLuminanceScale;
    }

    public UISettingsStimulusAndBackground getUiSettingsStimulusAndBackground() {
        return uiSettingsStimulusAndBackground;
    }

    public void setUiSettingsStimulusAndBackground(UISettingsStimulusAndBackground uiSettingsStimulusAndBackground) {
        this.uiSettingsStimulusAndBackground = uiSettingsStimulusAndBackground;
    }

    public UISettingsFixationAndOther getUiSettingsFixationAndOther() {
        return uiSettingsFixationAndOther;
    }

    public void setUiSettingsFixationAndOther(UISettingsFixationAndOther uiSettingsFixationAndOther) {
        this.uiSettingsFixationAndOther = uiSettingsFixationAndOther;
    }

    public UISettingsFixMonitorBlindspot getUiSettingsFixMonitorBlindspot() {
        return uiSettingsFixMonitorBlindspot;
    }

    public void setUiSettingsFixMonitorBlindspot(UISettingsFixMonitorBlindspot uiSettingsFixMonitorBlindspot) {
        this.uiSettingsFixMonitorBlindspot = uiSettingsFixMonitorBlindspot;
    }

    public UISettingsFixMonitorFixPointChange getUiSettingsProcedureBasicFixPointChange() {
        return uiSettingsProcedureBasicFixPointChange;
    }

    public void setUiSettingsProcedureBasicFixPointChange(UISettingsFixMonitorFixPointChange uiSettingsProcedureBasicFixPointChange) {
        this.uiSettingsProcedureBasicFixPointChange = uiSettingsProcedureBasicFixPointChange;
    }

    public UISettingsFixMonitorBoth getUiSettingsFixMonitorBoth() {
        return uiSettingsFixMonitorBoth;
    }

    public void setUiSettingsFixMonitorBoth(UISettingsFixMonitorBoth uiSettingsFixMonitorBoth) {
        this.uiSettingsFixMonitorBoth = uiSettingsFixMonitorBoth;
    }

    public UISettingsProcedureBasic getUiSettingsProcedureBasic() {
        return uiSettingsProcedureBasic;
    }

    public void setUiSettingsProcedureBasic(UISettingsProcedureBasic uiSettingsProcedureBasic) {
        this.uiSettingsProcedureBasic = uiSettingsProcedureBasic;
    }

    public ProcedureBasicData getProcedureBasicData() {
        return procedureBasicData;
    }

    public void setProcedureBasicData(ProcedureBasicData procedureBasicData) {
        this.procedureBasicData = procedureBasicData;
    }
}
