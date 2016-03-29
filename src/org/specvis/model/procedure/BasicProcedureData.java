package org.specvis.model.procedure;

import java.util.ArrayList;

/**
 * Created by Piotr Dzwiniel on 2016-03-10.
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

public class BasicProcedureData {

    private ArrayList<BasicProcedureStimulus> arrayListBasicProcedureStimulus;

    private int totalFixationMonitorChecks;
    private int positiveFixationMonitorChecks;
    private double fixationMonitorAccuracyInPercentages;

    private String testDuration;

    public ArrayList<BasicProcedureStimulus> getArrayListBasicProcedureStimulus() {
        return arrayListBasicProcedureStimulus;
    }

    public void setArrayListBasicProcedureStimulus(ArrayList<BasicProcedureStimulus> arrayListBasicProcedureStimulus) {
        this.arrayListBasicProcedureStimulus = arrayListBasicProcedureStimulus;
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
