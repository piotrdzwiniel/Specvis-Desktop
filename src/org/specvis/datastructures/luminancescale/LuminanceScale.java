package org.specvis.datastructures.luminancescale;

/**
 * Created by Piotr Dzwiniel on 2016-02-10.
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

public class LuminanceScale {

    // 1.
    private String id;
    private String name;
    private double hue;
    private double saturation;
    private double luminanceForBrightness0;
    private double luminanceForBrightness20;
    private double luminanceForBrightness40;
    private double luminanceForBrightness60;
    private double luminanceForBrightness80;
    private double luminanceForBrightness100;
    private String additionalInformation;

    // 2.
    private double[] fittedLuminanceForEachBrightnessValue;

    public LuminanceScale() {

    }

    public LuminanceScale(String id, String name, double hue, double saturation, double luminanceForBrightness0, double luminanceForBrightness20, double luminanceForBrightness40, double luminanceForBrightness60, double luminanceForBrightness80, double luminanceForBrightness100, String additionalInformation, double[] fittedLuminanceForEachBrightnessValue) {
        this.id = id;
        this.name = name;
        this.hue = hue;
        this.saturation = saturation;
        this.luminanceForBrightness0 = luminanceForBrightness0;
        this.luminanceForBrightness20 = luminanceForBrightness20;
        this.luminanceForBrightness40 = luminanceForBrightness40;
        this.luminanceForBrightness60 = luminanceForBrightness60;
        this.luminanceForBrightness80 = luminanceForBrightness80;
        this.luminanceForBrightness100 = luminanceForBrightness100;
        this.additionalInformation = additionalInformation;
        this.fittedLuminanceForEachBrightnessValue = fittedLuminanceForEachBrightnessValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHue() {
        return hue;
    }

    public void setHue(double hue) {
        this.hue = hue;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public double getLuminanceForBrightness0() {
        return luminanceForBrightness0;
    }

    public void setLuminanceForBrightness0(double luminanceForBrightness0) {
        this.luminanceForBrightness0 = luminanceForBrightness0;
    }

    public double getLuminanceForBrightness20() {
        return luminanceForBrightness20;
    }

    public void setLuminanceForBrightness20(double luminanceForBrightness20) {
        this.luminanceForBrightness20 = luminanceForBrightness20;
    }

    public double getLuminanceForBrightness40() {
        return luminanceForBrightness40;
    }

    public void setLuminanceForBrightness40(double luminanceForBrightness40) {
        this.luminanceForBrightness40 = luminanceForBrightness40;
    }

    public double getLuminanceForBrightness60() {
        return luminanceForBrightness60;
    }

    public void setLuminanceForBrightness60(double luminanceForBrightness60) {
        this.luminanceForBrightness60 = luminanceForBrightness60;
    }

    public double getLuminanceForBrightness80() {
        return luminanceForBrightness80;
    }

    public void setLuminanceForBrightness80(double luminanceForBrightness80) {
        this.luminanceForBrightness80 = luminanceForBrightness80;
    }

    public double getLuminanceForBrightness100() {
        return luminanceForBrightness100;
    }

    public void setLuminanceForBrightness100(double luminanceForBrightness100) {
        this.luminanceForBrightness100 = luminanceForBrightness100;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public double[] getFittedLuminanceForEachBrightnessValue() {
        return fittedLuminanceForEachBrightnessValue;
    }

    public void setFittedLuminanceForEachBrightnessValue(double[] fittedLuminanceForEachBrightnessValue) {
        this.fittedLuminanceForEachBrightnessValue = fittedLuminanceForEachBrightnessValue;
    }
}
