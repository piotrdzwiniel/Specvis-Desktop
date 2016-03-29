package org.contour2dplot;

/**
 * Created by Piotr Dzwiniel on 2016-03-21.
 */

/*
 * Copyright 2016 Piotr Dzwiniel
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

public class CubicInterpolator {

    public static double getValue(double[] p, double x) {

        int xi = (int) x;

        x -= xi;

        double p0 = p[Math.max(0, xi - 1)];
        double p1 = p[xi];
        double p2 = p[Math.min(p.length - 1, xi + 1)];
        double p3 = p[Math.min(p.length - 1, xi + 2)];

        return p1 + 0.5 * x * (p2 - p0 + x * (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3 + x * (3.0 * (p1 - p2) + p3 - p0)));
    }
}
