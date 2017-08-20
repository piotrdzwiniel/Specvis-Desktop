package org.contour2dplot;

/**
 * Created by Piotr Dzwiniel on 2016-03-21.
 */

/*
 * Copyright from 2016 till now - Piotr Dzwiniel
 *
 * This file is part of org.contour2dplot package.
 *
 * org.contour2dplot package is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * org.contour2dplot package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with org.contour2dplot package; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

public class BicubicInterpolator extends CubicInterpolator {

    private double[] arr = new double[4];

    public double getValue(double[][] p, double x, double y) {

        int xi = (int) x;

        x -= xi;

        arr[0] = getValue(p[Math.max(0, xi - 1)], y);
        arr[1] = getValue(p[xi], y);
        arr[2] = getValue(p[Math.min(p.length - 1, xi + 1)], y);
        arr[3] = getValue(p[Math.min(p.length - 1, xi + 2)], y);

        return getValue(arr, x + 1);
    }
}
