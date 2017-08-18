package org.specvis.datastructures.procedures.basic;

import java.util.ArrayList;

/**
 * Created by Piotr Dzwiniel on 2016-03-10.
 * Last modification on 2017-07-19.
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

public class ProcedureBasicData {

    private ArrayList<ProcedureBasicStimulus> arrayListProcedureBasicStimulus;

    private int totalFixationMonitorChecks;
    private int positiveFixationMonitorChecks;
    private double fixationMonitorAccuracyInPercentages;

    private String testDuration;

    public ArrayList<ProcedureBasicStimulus> getArrayListProcedureBasicStimulus() {
        return arrayListProcedureBasicStimulus;
    }

    public void setArrayListProcedureBasicStimulus(ArrayList<ProcedureBasicStimulus> arrayListProcedureBasicStimulus) {
        this.arrayListProcedureBasicStimulus = arrayListProcedureBasicStimulus;
    }

    public int getTotalFixationMonitorChecks() {
        return totalFixationMonitorChecks;
    }

    public void setTotalFixationMonitorChecks(int totalFixationMonitorChecks) {
        this.totalFixationMonitorChecks = totalFixationMonitorChecks;
    }

    public int getPositiveFixationMonitorChecks() {
        return positiveFixationMonitorChecks;
    }

    public void setPositiveFixationMonitorChecks(int positiveFixationMonitorChecks) {
        this.positiveFixationMonitorChecks = positiveFixationMonitorChecks;
    }

    public double getFixationMonitorAccuracyInPercentages() {
        return fixationMonitorAccuracyInPercentages;
    }

    public void setFixationMonitorAccuracyInPercentages(double fixationMonitorAccuracyInPercentages) {
        this.fixationMonitorAccuracyInPercentages = fixationMonitorAccuracyInPercentages;
    }

    public String getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(String testDuration) {
        this.testDuration = testDuration;
    }
}
