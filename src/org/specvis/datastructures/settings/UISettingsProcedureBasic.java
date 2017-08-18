package org.specvis.datastructures.settings;

/**
 * Created by Piotr Dzwiniel on 2016-03-09.
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

public class UISettingsProcedureBasic {

    private boolean spreadValuesLogarithmically;
    private int visualFieldTestBrightnessVectorLength;

    public void setDefaultValues() {

        spreadValuesLogarithmically = false;
        visualFieldTestBrightnessVectorLength = 9;
    }

    public boolean isSpreadValuesLogarithmically() {
        return spreadValuesLogarithmically;
    }

    public void setSpreadValuesLogarithmically(boolean spreadValuesLogarithmically) {
        this.spreadValuesLogarithmically = spreadValuesLogarithmically;
    }

    public int getVisualFieldTestBrightnessVectorLength() {
        return visualFieldTestBrightnessVectorLength;
    }

    public void setVisualFieldTestBrightnessVectorLength(int visualFieldTestBrightnessVectorLength) {
        this.visualFieldTestBrightnessVectorLength = visualFieldTestBrightnessVectorLength;
    }
}
