package org.specvis.model.procedure;

import javafx.scene.shape.Shape;

/**
 * Created by Piotr Dzwiniel on 2016-03-09.
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

public class BasicProcedureStimulus {

    private int index;

    private double positionOnTheScreenInPixelsX;
    private double positionOnTheScreenInPixelsY;

    private double distanceFromFixPointOnTheFieldOfViewInDegreesX;
    private double distanceFromFixPointOnTheFieldOfViewInDegreesY;

    private Shape shape;

    private int displayTime;

//    0 - stimulus was not displayed yet;
//    1 - lack of answer;
//    2 - positive answer;
    private int[] answers;

    private int brightnessThreshold;
    private double luminanceThreshold;
    private double decibelThreshold;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getPositionOnTheScreenInPixelsX() {
        return positionOnTheScreenInPixelsX;
    }

    public void setPositionOnTheScreenInPixelsX(double positionOnTheScreenInPixelsX) {
        this.positionOnTheScreenInPixelsX = positionOnTheScreenInPixelsX;
    }

    public double getPositionOnTheScreenInPixelsY() {
        return positionOnTheScreenInPixelsY;
    }

    public void setPositionOnTheScreenInPixelsY(double positionOnTheScreenInPixelsY) {
        this.positionOnTheScreenInPixelsY = positionOnTheScreenInPixelsY;
    }

    public double getDistanceFromFixPointOnTheFieldOfViewInDegreesX() {
        return distanceFromFixPointOnTheFieldOfViewInDegreesX;
    }

    public void setDistanceFromFixPointOnTheFieldOfViewInDegreesX(double distanceFromFixPointOnTheFieldOfViewInDegreesX) {
        this.distanceFromFixPointOnTheFieldOfViewInDegreesX = distanceFromFixPointOnTheFieldOfViewInDegreesX;
    }

    public double getDistanceFromFixPointOnTheFieldOfViewInDegreesY() {
        return distanceFromFixPointOnTheFieldOfViewInDegreesY;
    }

    public void setDistanceFromFixPointOnTheFieldOfViewInDegreesY(double distanceFromFixPointOnTheFieldOfViewInDegreesY) {
        this.distanceFromFixPointOnTheFieldOfViewInDegreesY = distanceFromFixPointOnTheFieldOfViewInDegreesY;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public int getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(int displayTime) {
        this.displayTime = displayTime;
    }

    public int[] getAnswers() {
        return answers;
    }

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }

    public int getBrightnessThreshold() {
        return brightnessThreshold;
    }

    public void setBrightnessThreshold(int brightnessThreshold) {
        this.brightnessThreshold = brightnessThreshold;
    }

    public double getLuminanceThreshold() {
        return luminanceThreshold;
    }

    public void setLuminanceThreshold(double luminanceThreshold) {
        this.luminanceThreshold = luminanceThreshold;
    }

    public double getDecibelThreshold() {
        return decibelThreshold;
    }

    public void setDecibelThreshold(double decibelThreshold) {
        this.decibelThreshold = decibelThreshold;
    }
}
