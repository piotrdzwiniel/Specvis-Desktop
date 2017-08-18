package org.specvis.datastructures.luminancescale;

import javafx.scene.control.Label;

import java.lang.reflect.Field;

/**
 * Created by Piotr Dzwiniel on 2016-02-10.
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

public class ExistingLuminanceScale {

    private String id;
    private String name;
    private int hue;
    private int saturation;
    private Label labelPreviewColor;
    private double minimumMeasuredLuminance;
    private double maximumMeasuredLuminance;

    public ExistingLuminanceScale(String id, String name, int hue, int saturation, Label labelPreviewColor, double minimumMeasuredLuminance, double maximumMeasuredLuminance) {
        this.id = id;
        this.name = name;
        this.hue = hue;
        this.saturation = saturation;
        this.labelPreviewColor = labelPreviewColor;
        this.minimumMeasuredLuminance = minimumMeasuredLuminance;
        this.maximumMeasuredLuminance = maximumMeasuredLuminance;
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

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public Label getLabelPreviewColor() {
        return labelPreviewColor;
    }

    public void setLabelPreviewColor(Label labelPreviewColor) {
        this.labelPreviewColor = labelPreviewColor;
    }

    public double getMinimumMeasuredLuminance() {
        return minimumMeasuredLuminance;
    }

    public void setMinimumMeasuredLuminance(double minimumMeasuredLuminance) {
        this.minimumMeasuredLuminance = minimumMeasuredLuminance;
    }

    public double getMaximumMeasuredLuminance() {
        return maximumMeasuredLuminance;
    }

    public void setMaximumMeasuredLuminance(double maximumMeasuredLuminance) {
        this.maximumMeasuredLuminance = maximumMeasuredLuminance;
    }

    public static String[] getFieldsNames() {
        Field[] fields = ExistingLuminanceScale.class.getDeclaredFields();
        String[] fieldsNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldsNames[i] = fields[i].getName();
        }
        return fieldsNames;
    }
}
