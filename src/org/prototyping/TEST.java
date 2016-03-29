package org.prototyping;

import org.specvis.model.Functions;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by pdzwiniel on 2016-03-18.
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

public class TEST {

    public static void main(String[] args) {

        ArrayList<Double> arrayList = logspace(10, 100, 9);
        for (double d : arrayList) {
            System.out.println(round(d, 1));
        }
    }

    public static ArrayList<Double> linspace(double start, double stop, int n, boolean roundToInt) {
        ArrayList<Double> result = new ArrayList();
        double step = (stop-start)/(n-1);
        for(int i = 0; i <= n-2; i++) {
            if (roundToInt) {
                BigDecimal bd = new BigDecimal(start + (i * step));
                bd = bd.setScale(0, RoundingMode.HALF_UP);
                result.add(bd.doubleValue());
            } else {
                result.add(start + (i * step));
            }
        }
        result.add(stop);
        return result;
    }

    public static ArrayList<Double> logspace(double start, double stop, int n) {
        ArrayList<Double> arrayList = new ArrayList<>();

        n -= 1;

        for (int i = 0; i <= n; i++) {
            double d = start * Math.pow(stop / start, (double) i / n);
            arrayList.add(d);
        }

        return arrayList;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
