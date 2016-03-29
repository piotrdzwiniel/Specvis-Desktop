package org.specvis.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Piotr Dzwiniel on 2015-10-07.
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

public class ChiSquared {

    public String scienceNotationToDecimalString(double value, int decimalPlaces) {
        return String.format("%1$." + decimalPlaces + "f", value).replaceAll(",", ".");
    }

    public double calculateCriticalValue(double[] expectedValues, double[] observedValues) {
        double criticalValue = 0.0;
        for (int i = 0; i < expectedValues.length; i++) {
            criticalValue += Math.pow(observedValues[i] - expectedValues[i], 2) / expectedValues[i];
        }
        return criticalValue;
    }

    public double calculateChiSquaredPValue(int degreesOfFreedom, double criticalValue) {

        if (criticalValue < 0 || degreesOfFreedom < 1) {
            return 0.0;
        }

        double k = ((double) degreesOfFreedom) * 0.5;
        double x = criticalValue * 0.5;
        if (degreesOfFreedom == 2) {
            return Math.exp(-1.0 * x);
        }

        double pValue = igf(k, x);
        if (Double.isNaN(pValue) || Double.isInfinite(pValue) || pValue <= 1e-8) {
            return 1e-14;
        }

        pValue /= approx_gamma(k);

        return (1.0 - pValue);
    }

    private double igf(double s, double z) {

        if (z < 0.0) {
            return 0.0;
        }

        double sc = (1.0 / s);
        sc *= Math.pow(z, s);
        sc *= Math.exp(-z);

        BigDecimal sBig = new BigDecimal(s);
        BigDecimal zBig = new BigDecimal(z);
        BigDecimal sum = new BigDecimal(1.0);
        BigDecimal nom = new BigDecimal(1.0);
        BigDecimal denom = new BigDecimal(1.0);

        for (int i = 0; i < 200; i++) {
            nom = nom.multiply(zBig);
            sBig = sBig.add(new BigDecimal(1.0));
            denom = denom.multiply(sBig);
            BigDecimal tmp = nom.divide(denom, 10, RoundingMode.HALF_UP);
            sum = sum.add(tmp);
        }

        return sum.doubleValue() * sc;
    }

    private double approx_gamma(double z) {

        double RECIP_E = 0.36787944117144232159552377016147;  // RECIP_E = (E^-1) = (1.0 / E)
        double TWOPI = 6.283185307179586476925286766559;

        double d = 1.0 / (10.0 * z);
        d = 1.0 / ((12 * z) - d);
        d = (d + z) * RECIP_E;
        d = Math.pow(d, z);
        d *= Math.sqrt(TWOPI / z);

        return d;
    }
}
