package org.specvis.datastructures.settings;

import javafx.scene.paint.Color;

/**
 * Created by Piotr Dzwiniel on 2016-02-09.
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

public class UISettingsFixationAndOther {

    private Color fixationPointColor;
    private double fixationPointLuminance;
    private double fixationPointSizeX;
    private double fixationPointSizeY;
    private String fixationMonitorTechnique;
    private double fixationPointLocationX;
    private double fixationPointLocationY;
    private String answerToStimulus;
    private String pauseProcedure;
    private String cancelProcedure;
    private String procedure;

    public Color getFixationPointColor() {
        return fixationPointColor;
    }

    public void setFixationPointColor(Color fixationPointColor) {
        this.fixationPointColor = fixationPointColor;
    }

    public double getFixationPointLuminance() {
        return fixationPointLuminance;
    }

    public void setFixationPointLuminance(double fixationPointLuminance) {
        this.fixationPointLuminance = fixationPointLuminance;
    }

    public double getFixationPointSizeX() {
        return fixationPointSizeX;
    }

    public void setFixationPointSizeX(double fixationPointSizeX) {
        this.fixationPointSizeX = fixationPointSizeX;
    }

    public double getFixationPointSizeY() {
        return fixationPointSizeY;
    }

    public void setFixationPointSizeY(double fixationPointSizeY) {
        this.fixationPointSizeY = fixationPointSizeY;
    }

    public String getFixationMonitorTechnique() {
        return fixationMonitorTechnique;
    }

    public void setFixationMonitorTechnique(String fixationMonitorTechnique) {
        this.fixationMonitorTechnique = fixationMonitorTechnique;
    }

    public double getFixationPointLocationX() {
        return fixationPointLocationX;
    }

    public void setFixationPointLocationX(double fixationPointLocationX) {
        this.fixationPointLocationX = fixationPointLocationX;
    }

    public double getFixationPointLocationY() {
        return fixationPointLocationY;
    }

    public void setFixationPointLocationY(double fixationPointLocationY) {
        this.fixationPointLocationY = fixationPointLocationY;
    }

    public String getAnswerToStimulus() {
        return answerToStimulus;
    }

    public void setAnswerToStimulus(String answerToStimulus) {
        this.answerToStimulus = answerToStimulus;
    }

    public String getPauseProcedure() {
        return pauseProcedure;
    }

    public void setPauseProcedure(String pauseProcedure) {
        this.pauseProcedure = pauseProcedure;
    }

    public String getCancelProcedure() {
        return cancelProcedure;
    }

    public void setCancelProcedure(String cancelProcedure) {
        this.cancelProcedure = cancelProcedure;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }
}
