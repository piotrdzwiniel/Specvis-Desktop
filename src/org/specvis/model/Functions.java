package org.specvis.model;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.specvis.StartApplication;
import org.specvis.model.procedure.BasicProcedureData;
import org.specvis.model.procedure.BasicProcedureStimulus;
import org.specvis.view.miscellaneous.ExceptionDialogWindow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by Piotr Dzwiniel on 2016-02-12.
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

public class Functions {

    public Functions() {

    }

    public String createIndividualID(String currentDate, int numberOfUnspecifiedSymbols) {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int l = 0; l < numberOfUnspecifiedSymbols; l++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String date = currentDate.replaceAll("-", "");
        return date + "_" + sb.toString();
    }

    public String getCurrentDateYYYYmmDD() {
        Calendar date = new GregorianCalendar();
        String day = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(date.get(Calendar.MONTH) + 1);
        String year = Integer.toString(date.get(Calendar.YEAR));
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String toHexCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public double[] poly2D(double[] coeff, double[] x) throws Exception{
        if (coeff.length != 3) {
            throw new Exception("poly2D method can handle only 3 coefficients.");
        } else {
            double[] y = new double[x.length];
            for (int i = 0; i < y.length; i++) {
                y[i] = coeff[0] * Math.pow(x[i], 2) + coeff[1] * x[i] + coeff[2];
            }
            return y;
        }
    }

    public double[] createBrightnessVector(int vectorLength) {
        double step = 101 / (double) vectorLength;
        double[] brightnessVector = new double[vectorLength];
        for (int i = 0; i < vectorLength; i ++) {
            brightnessVector[i] = (double) i * step;
        }
        return brightnessVector;
    }

    public double[] fitPolynomialToData(double[] x, double[] y, int degree, boolean reverseCoefficients) {
        WeightedObservedPoints obs = new WeightedObservedPoints();
        for (int i = 0; i < x.length; i++) {
            obs.add(1, x[i], y[i]);
        }
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
        double[] coefficients = fitter.fit(obs.toList());
        if (reverseCoefficients) {
            return reverseDoublePrimitiveArray(coefficients);
        } else {
            return coefficients;
        }
    }

    public double[] reverseDoublePrimitiveArray(double[] array) {
        for(int i = 0; i < array.length / 2; i++) {
            double temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
        return array;
    }

    public boolean isThisPrimitiveArrayContainsValue(double[] arr, double targetValue) {
        for (double i: arr){
            if(i == targetValue) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate opposite of the angle.
     * @param a: angle in degrees;
     * @param r:
     * @return: return opposite of the angle;
     */
    public double calculateOppositeAngle(double a, double r) {
        /**
         *      Calculation note:
         *
         *      |\
         *      |a\
         *      |  \
         *     r|   \
         *      |    \
         *      |     \
         *      |______\
         *         x
         *
         *      In order to calculate "x" (opposite of the angle "a") one must do:
         *
         *      x = r / cot(a),
         *
         *      where "a" is in radians. Function "cot" can be also
         *      described as "1/tan".
         */

        double x;
        a = Math.toRadians(a);
        x = r / (1 / Math.tan(a));
        return x;
    }

    /**
     * Convert millimiters to pixels.
     * @param m: millimiters;
     * @param ssp: screen size in pixels;
     * @param ssm: screen size in millimiters;
     * @return: pixels;
     */
    public double millimitersToPixels(double m, double ssp, double ssm) {
        double pixelsForOneMillimiter = ssp /ssm;
        return m * pixelsForOneMillimiter;
    }

    public ArrayList<Double> linspace(double start, double stop, int n, boolean roundToInt) {
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

    public int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public int randomInterval(int constantPart, int randomPart) {
        Random randomGenerator = new Random();
        return constantPart + randomGenerator.nextInt(randomPart + 1);
    }

    public double decibelsValue(double maxLuminance, double stimulusLuminance, double backgroundLuminance, int round) {
        double decibels = 10 * Math.log10(maxLuminance / stimulusLuminance);
        return round(decibels, round);
    }

    public String totalTime(long start, long end) {
        long difference = end - start;
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(difference),
                TimeUnit.MILLISECONDS.toMinutes(difference) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)),
                TimeUnit.MILLISECONDS.toSeconds(difference) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));
    }

    public double[] arange(double start, double end, double step) {
        return IntStream.rangeClosed(0, (int) ((end - start) / step)).mapToDouble(x -> x * step + start).toArray();
    }

    /**
     * Convert ternary value to decimal value;
     * @param ternaryNumber: ternary number splitted to single trits, where ternaryNumber.get(0)
     *                     is the least-significant-trit and ternaryNumber.get(ternaryNumber.size()-1)
     *                     is the most-significant-trit;
     * @return: decimal representation of ternary value;
     */
    public int ternaryToDecimalConverter(ArrayList<Integer> ternaryNumber) {

        int decimalValue = 0;

        for (int i = 0; i < ternaryNumber.size(); i++) {
            if (ternaryNumber.get(i) >= 0 && ternaryNumber.get(i) <= 2) {
                decimalValue += ternaryNumber.get(i) * Math.pow(3, i);
            } else {
                throw new IllegalArgumentException();
            }
        }

        return decimalValue;
    }

    /**
     * Check if a given value is below, within, or above given range.
     * @param startOfRange
     * @param endOfRange
     * @param value
     * @return: 0 - below range, 1 - within range, 2 - above range;
     */
    public int checkIfValueIsInRange(double startOfRange, double endOfRange, double value) {
        if (value < startOfRange) {
            return 0;
        } else if (value >= startOfRange && value <= endOfRange) {
            return 1;
        } else {
            return 2;
        }
    }

    public LuminanceScale findExistingLuminanceScaleByID(String scaleID) {
        LuminanceScale luminanceScale = new LuminanceScale();
        try {
            // 1.
            ArrayList<String> array = new ArrayList<>();
            File file = new File("screenLuminanceScales.s");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                array.add(line);
            }
            bufferedReader.close();

            // 2.
            for (String anArray : array) {
                String[] str = anArray.split("\t");
                if (str[0].equals(scaleID)) {
                    luminanceScale.setId(str[0]);
                    luminanceScale.setName(str[1]);
                    luminanceScale.setHue(Double.valueOf(str[2]));
                    luminanceScale.setSaturation(Double.valueOf(str[3]));
                    luminanceScale.setLuminanceForBrightness0(Double.valueOf(str[4]));
                    luminanceScale.setLuminanceForBrightness20(Double.valueOf(str[5]));
                    luminanceScale.setLuminanceForBrightness40(Double.valueOf(str[6]));
                    luminanceScale.setLuminanceForBrightness60(Double.valueOf(str[7]));
                    luminanceScale.setLuminanceForBrightness80(Double.valueOf(str[8]));
                    luminanceScale.setLuminanceForBrightness100(Double.valueOf(str[9]));
                    luminanceScale.setAdditionalInformation(str[10].replaceAll("#n%", "\n"));
                }
            }

            // 3.
            double[] brightnessValues = new double[] {0, 20, 40, 60, 80, 100};
            double[] measuredLuminances = new double[] {
                    luminanceScale.getLuminanceForBrightness0(),
                    luminanceScale.getLuminanceForBrightness20(),
                    luminanceScale.getLuminanceForBrightness40(),
                    luminanceScale.getLuminanceForBrightness60(),
                    luminanceScale.getLuminanceForBrightness80(),
                    luminanceScale.getLuminanceForBrightness100()
            };
            double[] polynomialFitCoefficients = fitPolynomialToData(brightnessValues, measuredLuminances, 2, true);
            double[] brightnessVector = createBrightnessVector(101);
            double[] fittedLuminanceForEachBrightnessValue = new double[brightnessVector.length];
            try {
                fittedLuminanceForEachBrightnessValue = poly2D(polynomialFitCoefficients, brightnessVector);
            } catch (Exception ex) {
                ExceptionDialogWindow ed = new ExceptionDialogWindow(Alert.AlertType.ERROR, ex);
                ed.setTitle("Exception");
                ed.setHeaderText(ex.getClass().getName());
                ed.showAndWait();
            }
            luminanceScale.setFittedLuminanceForEachBrightnessValue(fittedLuminanceForEachBrightnessValue);

        } catch (Exception ex) {
            ExceptionDialogWindow ed = new ExceptionDialogWindow(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }
        return luminanceScale;
    }

    public ArrayList<Double> logspace(double start, double stop, int n, boolean roundToInt) {
        ArrayList<Double> arrayList = new ArrayList<>();

        n -= 1;

        for (int i = 0; i <= n; i++) {
            double d = start * Math.pow(stop / start, (double) i / n);
            if (roundToInt) {
                round(d, 0);
            }
            arrayList.add(d);
        }

        return arrayList;
    }

    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete(); // The directory is empty now and can be deleted.
    }

    public ArrayList<ArrayList<BasicProcedureStimulus>> getSortedDataFromFromBasicProcedure() {

        ArrayList<ArrayList<BasicProcedureStimulus>> sortedData = new ArrayList<>();

        // Get BasicProcedureData.
        BasicProcedureData basicProcedureData = StartApplication.getSpecvisData().getBasicProcedureData();
        ArrayList<BasicProcedureStimulus> data = basicProcedureData.getArrayListBasicProcedureStimulus();

        // Sort data by positionOnTheScreenInPixelsY.
        Collections.sort(data, (o1, o2) -> Double.compare(o1.getPositionOnTheScreenInPixelsY(), o2.getPositionOnTheScreenInPixelsY()));

        // Sort data by positionOnTheScreenInPixelsX.
        double temp = 0;
        for (int i = 0; i < data.size(); i++) {

            if (temp != data.get(i).getPositionOnTheScreenInPixelsY()) {
                ArrayList<BasicProcedureStimulus> arrayList = new ArrayList<>(); // getAllStimuliWithSpecifiYPosition(data.get(i).getPositionOnTheScreenInPixelsY(), data);

                for (int j = 0; j < data.size(); j++) {
                    if (data.get(i).getPositionOnTheScreenInPixelsY() == data.get(j).getPositionOnTheScreenInPixelsY()) {
                        arrayList.add(data.get(j));
                    }
                }

                Collections.sort(arrayList, (o1, o2) -> Double.compare(o1.getPositionOnTheScreenInPixelsX(), o2.getPositionOnTheScreenInPixelsX()));
                sortedData.add(arrayList);
            }

            temp = data.get(i).getPositionOnTheScreenInPixelsY();
        }

        return sortedData;
    }
}
