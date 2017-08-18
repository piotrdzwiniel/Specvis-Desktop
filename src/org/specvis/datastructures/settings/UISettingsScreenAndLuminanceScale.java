package org.specvis.datastructures.settings;

import org.specvis.datastructures.luminancescale.LuminanceScale;

import java.awt.*;

/**
 * Created by Piotr Dzwiniel on 2016-02-09.
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

public class UISettingsScreenAndLuminanceScale {

    // 1.
    private GraphicsDevice screen;
    private int chosenScreenIndex;
    private int screenResolutionX;
    private int screenResolutionY;
    private int screenWidth;
    private int screenHeight;
    private int patientDistanceFromTheScreen;
    private double involvedVisualFieldX;
    private double involvedVisualFieldY;
    private LuminanceScale stimulusLuminanceScale;
    private LuminanceScale backgroundLuminanceScale;

    // 2.
    private boolean isThisWindowOpenedForStimulus;

    public GraphicsDevice getScreen() {
        return screen;
    }

    public void setScreen(GraphicsDevice screen) {
        this.screen = screen;
    }

    public int getChosenScreenIndex() {
        return chosenScreenIndex;
    }

    public void setChosenScreenIndex(int chosenScreenIndex) {
        this.chosenScreenIndex = chosenScreenIndex;
    }

    public int getScreenResolutionX() {
        return screenResolutionX;
    }

    public void setScreenResolutionX(int screenResolutionX) {
        this.screenResolutionX = screenResolutionX;
    }

    public int getScreenResolutionY() {
        return screenResolutionY;
    }

    public void setScreenResolutionY(int screenResolutionY) {
        this.screenResolutionY = screenResolutionY;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getPatientDistanceFromTheScreen() {
        return patientDistanceFromTheScreen;
    }

    public void setPatientDistanceFromTheScreen(int patientDistanceFromTheScreen) {
        this.patientDistanceFromTheScreen = patientDistanceFromTheScreen;
    }

    public double getInvolvedVisualFieldX() {
        return involvedVisualFieldX;
    }

    public void setInvolvedVisualFieldX(double involvedVisualFieldX) {
        this.involvedVisualFieldX = involvedVisualFieldX;
    }

    public double getInvolvedVisualFieldY() {
        return involvedVisualFieldY;
    }

    public void setInvolvedVisualFieldY(double involvedVisualFieldY) {
        this.involvedVisualFieldY = involvedVisualFieldY;
    }

    public LuminanceScale getStimulusLuminanceScale() {
        return stimulusLuminanceScale;
    }

    public void setStimulusLuminanceScale(LuminanceScale stimulusLuminanceScale) {
        this.stimulusLuminanceScale = stimulusLuminanceScale;
    }

    public LuminanceScale getBackgroundLuminanceScale() {
        return backgroundLuminanceScale;
    }

    public void setBackgroundLuminanceScale(LuminanceScale backgroundLuminanceScale) {
        this.backgroundLuminanceScale = backgroundLuminanceScale;
    }

    public boolean isThisWindowOpenedForStimulus() {
        return isThisWindowOpenedForStimulus;
    }

    public void setIsThisWindowOpenedForStimulus(boolean isThisWindowOpenedForStimulus) {
        this.isThisWindowOpenedForStimulus = isThisWindowOpenedForStimulus;
    }
}
