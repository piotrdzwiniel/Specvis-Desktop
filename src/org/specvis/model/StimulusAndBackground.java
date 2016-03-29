package org.specvis.model;

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

public class StimulusAndBackground {

    private int stimulusMaxBrightness;
    private double stimulusMaxLuminance;
    private int stimulusMinBrightness;
    private double stimulusMinLuminance;
    private String stimulusShape;
    private double stimulusInclination;
    private double stimulusSizeX;
    private double stimulusSizeY;
    private int stimulusDisplayTime;
    private int constantPartOfInterval;
    private int randomPartOfInterval;
    private boolean isCorrectionForSphericityCheckBoxChecked;
    private int backgroundBrightness;
    private double backgroundLuminance;
    private double distanceBetweenStimuliInDegreesX;
    private double distanceBetweenStimuliInDegreesY;

    public int getStimulusMaxBrightness() {
        return stimulusMaxBrightness;
    }

    public void setStimulusMaxBrightness(int stimulusMaxBrightness) {
        this.stimulusMaxBrightness = stimulusMaxBrightness;
    }

    public double getStimulusMaxLuminance() {
        return stimulusMaxLuminance;
    }

    public void setStimulusMaxLuminance(double stimulusMaxLuminance) {
        this.stimulusMaxLuminance = stimulusMaxLuminance;
    }

    public int getStimulusMinBrightness() {
        return stimulusMinBrightness;
    }

    public void setStimulusMinBrightness(int stimulusMinBrightness) {
        this.stimulusMinBrightness = stimulusMinBrightness;
    }

    public double getStimulusMinLuminance() {
        return stimulusMinLuminance;
    }

    public void setStimulusMinLuminance(double stimulusMinLuminance) {
        this.stimulusMinLuminance = stimulusMinLuminance;
    }

    public String getStimulusShape() {
        return stimulusShape;
    }

    public void setStimulusShape(String stimulusShape) {
        this.stimulusShape = stimulusShape;
    }

    public double getStimulusInclination() {
        return stimulusInclination;
    }

    public void setStimulusInclination(double stimulusInclination) {
        this.stimulusInclination = stimulusInclination;
    }

    public double getStimulusSizeX() {
        return stimulusSizeX;
    }

    public void setStimulusSizeX(double stimulusSizeX) {
        this.stimulusSizeX = stimulusSizeX;
    }

    public double getStimulusSizeY() {
        return stimulusSizeY;
    }

    public void setStimulusSizeY(double stimulusSizeY) {
        this.stimulusSizeY = stimulusSizeY;
    }

    public int getStimulusDisplayTime() {
        return stimulusDisplayTime;
    }

    public void setStimulusDisplayTime(int stimulusDisplayTime) {
        this.stimulusDisplayTime = stimulusDisplayTime;
    }

    public int getConstantPartOfInterval() {
        return constantPartOfInterval;
    }

    public void setConstantPartOfInterval(int constantPartOfInterval) {
        this.constantPartOfInterval = constantPartOfInterval;
    }

    public int getRandomPartOfInterval() {
        return randomPartOfInterval;
    }

    public void setRandomPartOfInterval(int randomPartOfInterval) {
        this.randomPartOfInterval = randomPartOfInterval;
    }

    public boolean isCorrectionForSphericityCheckBoxChecked() {
        return isCorrectionForSphericityCheckBoxChecked;
    }

    public void setIsCorrectionForSphericityCheckBoxChecked(boolean isCorrectionForSphericityCheckBoxChecked) {
        this.isCorrectionForSphericityCheckBoxChecked = isCorrectionForSphericityCheckBoxChecked;
    }

    public int getBackgroundBrightness() {
        return backgroundBrightness;
    }

    public void setBackgroundBrightness(int backgroundBrightness) {
        this.backgroundBrightness = backgroundBrightness;
    }

    public double getBackgroundLuminance() {
        return backgroundLuminance;
    }

    public void setBackgroundLuminance(double backgroundLuminance) {
        this.backgroundLuminance = backgroundLuminance;
    }

    public double getDistanceBetweenStimuliInDegreesX() {
        return distanceBetweenStimuliInDegreesX;
    }

    public void setDistanceBetweenStimuliInDegreesX(double distanceBetweenStimuliInDegreesX) {
        this.distanceBetweenStimuliInDegreesX = distanceBetweenStimuliInDegreesX;
    }

    public double getDistanceBetweenStimuliInDegreesY() {
        return distanceBetweenStimuliInDegreesY;
    }

    public void setDistanceBetweenStimuliInDegreesY(double distanceBetweenStimuliInDegreesY) {
        this.distanceBetweenStimuliInDegreesY = distanceBetweenStimuliInDegreesY;
    }
}
