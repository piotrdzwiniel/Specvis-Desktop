package org.specvis.logic;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.specvis.StartApplication;
import org.specvis.datastructures.luminancescale.LuminanceScale;
import org.specvis.datastructures.procedures.basic.ProcedureBasicData;
import org.specvis.datastructures.procedures.basic.ProcedureBasicStimulus;
import org.specvis.view.miscellaneous.ViewExceptionDialog;

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

public class Functions {

    public Functions() {

    }

    /**
     * Create individual identificator consisted of current date and N number
     * of unspecified symbols (lower- and upper-case letters and 0-9 digits). Format
     * of the identificator is "yyyyMMdd_nsymbols".
     * @param currentDate
     * @param numberOfUnspecifiedSymbols
     * @return Unique identificator consisted of current date and N symbols in a format "yyyyMMdd_symbols".
     */
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

    /**
     * Get current date in "yyyyMMdd" format.
     * @return Current date in "yyyyMMdd" format.
     */
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

    /**
     * Round given double value to N decimal places.
     * @param value
     * @param places
     * @return Rounded double value.
     */
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Convert javafx.scene.paint.Color to its HEX value.
     * @param color
     * @return Color in HEX value in "#RRGGBB" format.
     */
    public String toHexCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    /**
     * Fit 2nd degree polynomial to some X vector based on given coefficients.
     * @param coeff
     * @param x
     * @return Fitted 2nd degree polynomial in a form of a vector.
     * @throws Exception
     */
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

    /**
     * Create brightness vector based on provided vector length.
     * @param vectorLength
     * @return Brightness vector.
     */
    public double[] createBrightnessVector(int vectorLength) {
        double step = 101 / (double) vectorLength;
        double[] brightnessVector = new double[vectorLength];
        for (int i = 0; i < vectorLength; i ++) {
            brightnessVector[i] = (double) i * step;
        }
        return brightnessVector;
    }

    /**
     * Get fitting coefficients for N degree polynomial.
     * @param x
     * @param y
     * @param degree
     * @param reverseCoefficients
     * @return Fitting coefficients.
     */
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

    /**
     * Reverse double vector.
     * @param array
     * @return Reversed double vector.
     */
    public double[] reverseDoublePrimitiveArray(double[] array) {
        for(int i = 0; i < array.length / 2; i++) {
            double temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
        return array;
    }

    /**
     * Check whether given double vector contains some specific X value, ie. "target value".
     * @param arr
     * @param targetValue
     * @return True if given vector contains provided target value; False if given vector do not contains target value.
     */
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
     * @return: Opposite of the angle;
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
     * @return: Pixels;
     */
    public double millimitersToPixels(double m, double ssp, double ssm) {
        double pixelsForOneMillimiter = ssp /ssm;
        return m * pixelsForOneMillimiter;
    }

    /**
     * If some given "resolution" expressed in pixels (px) can be expressed in degrees (dg) of the visual field,
     * say 1920px can be expressed as 50dg of the visual field, that it is possible to calculate
     * how many pixels is for one degree of the visual field. In this example 1dg = 38.4px.
     * @param resolution
     * @param field
     * @return Pixels per one degree.
     */
    public double pxForOneDg(int resolution, double field) {
        return resolution / field;
    }

    /**
     * Generate double linspace vector.
     * @param start
     * @param stop
     * @param n
     * @param roundToInt
     * @return Double linspace vector.
     */
    public ArrayList<Double> linspace(double start, double stop, int n, boolean roundToInt) {
        ArrayList<Double> result = new ArrayList();
        double step = (stop - start) / (n - 1);
        for(int i = 0; i <= n - 2; i++) {
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

    /**
     * Generate random int value from some given min-max range.
     * @param min
     * @param max
     * @return Random int value.
     */
    public int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generate random int value from some given "(0 to N) + M" range.
     * @param constantPart
     * @param randomPart
     * @return Random int value.
     */
    public int randomInterval(int constantPart, int randomPart) {
        Random randomGenerator = new Random();
        return constantPart + randomGenerator.nextInt(randomPart + 1);
    }

    /**
     * Get rounded decibel (dB) double value based on provided luminance parameters.
     * @param maxLuminance
     * @param stimulusLuminance
     * @param backgroundLuminance
     * @param round
     * @return Decibel (dB) double value.
     */
    public double decibelsValue(double maxLuminance, double stimulusLuminance, double backgroundLuminance, int round) {
        double decibels = 10 * Math.log10(maxLuminance / stimulusLuminance);
        return round(decibels, round);
    }

    /**
     * Get time interval between some start and end value in "hh:mm:ss" format.
     * @param start
     * @param end
     * @return Time in "hh:mm:ss" format.
     */
    public String totalTime(long start, long end) {
        long difference = end - start;
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(difference),
                TimeUnit.MILLISECONDS.toMinutes(difference) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)),
                TimeUnit.MILLISECONDS.toSeconds(difference) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));
    }

    /**
     * Create arange vector.
     * @param start
     * @param end
     * @param step
     * @return Arange vector.
     */
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

    /**
     * Find existing Luminance Scale by its ID.
     * @param scaleID
     * @return Found Luminance Scale.
     */
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
                ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
                ed.setTitle("Exception");
                ed.setHeaderText(ex.getClass().getName());
                ed.showAndWait();
            }
            luminanceScale.setFittedLuminanceForEachBrightnessValue(fittedLuminanceForEachBrightnessValue);

        } catch (Exception ex) {
            ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }
        return luminanceScale;
    }

    /**
     * Create logspace vector.
     * @param start
     * @param stop
     * @param n
     * @param roundToInt
     * @return Logspace vector.
     */
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

    /**
     * Delete given directory.
     * @param dir
     * @return True if directory was deleted succesfully.
     */
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

    /**
     * Get sorted data from Basic procedure.
     * @return Sorted data from Basic procedure.
     */
    public ArrayList<ArrayList<ProcedureBasicStimulus>> getSortedDataFromFromBasicProcedure() {

        ArrayList<ArrayList<ProcedureBasicStimulus>> sortedData = new ArrayList<>();

        // Get ProcedureBasicData.
        ProcedureBasicData procedureBasicData = StartApplication.getSpecvisData().getProcedureBasicData();
        ArrayList<ProcedureBasicStimulus> data = procedureBasicData.getArrayListProcedureBasicStimulus();

        // Sort data by positionOnTheScreenInPixelsY.
        Collections.sort(data, Comparator.comparingDouble(ProcedureBasicStimulus::getPositionOnTheScreenInPixelsY));

        // Sort data by positionOnTheScreenInPixelsX.
        double temp = 0;
        for (int i = 0; i < data.size(); i++) {

            if (temp != data.get(i).getPositionOnTheScreenInPixelsY()) {
                ArrayList<ProcedureBasicStimulus> arrayList = new ArrayList<>(); // getAllStimuliWithSpecifiYPosition(data.get(i).getPositionOnTheScreenInPixelsY(), data);

                for (int j = 0; j < data.size(); j++) {
                    if (data.get(i).getPositionOnTheScreenInPixelsY() == data.get(j).getPositionOnTheScreenInPixelsY()) {
                        arrayList.add(data.get(j));
                    }
                }

                Collections.sort(arrayList, Comparator.comparingDouble(ProcedureBasicStimulus::getPositionOnTheScreenInPixelsX));
                sortedData.add(arrayList);
            }

            temp = data.get(i).getPositionOnTheScreenInPixelsY();
        }

        return sortedData;
    }

    /**
     * Find real involved visual field during the visual field examination. Real visual field
     * is a visual field counted from the locations of most distant visual stimuli presented
     * to the patient.
     * @param visualField
     * @param centerPosition
     * @param distanceBetweenStimuli
     * @return Real visual field value.
     */
    public double findRealInvolvedVisualFieldNormal(double visualField, double centerPosition, double distanceBetweenStimuli) {

        double realInvolvedVisualField;

        // 1.
        double leftHalf = (visualField / 2) + centerPosition;
        double rightHalf = visualField - leftHalf;

        // 2. Find real visual field.
        double tempLeftVF = distanceBetweenStimuli / 2;
        while (true) {
            if (tempLeftVF + distanceBetweenStimuli > leftHalf) {
                break;
            } else {
                tempLeftVF += distanceBetweenStimuli;
            }
        }

        double tempRightVF = distanceBetweenStimuli / 2;
        while(true) {
            if (tempRightVF + distanceBetweenStimuli > rightHalf) {
                break;
            } else {
                tempRightVF += distanceBetweenStimuli;
            }
        }

        realInvolvedVisualField = tempLeftVF + tempRightVF;

        return realInvolvedVisualField;
    }

    /**
     * Find real involved visual field during the visual field examination. Real visual field
     * is a visual field counted from the locations of most distant visual stimuli presented
     * to the patient. Here, the correction for sphericity of the field of view is used.
     * @param visualField
     * @param centerPosition
     * @param patientDistance
     * @param screenSize
     * @param distanceBetweenStimuli
     * @return Real visual field value.
     */
    public double findRealInvolvedVisualFieldWithCorrectionForSphericity(double visualField, double centerPosition, double patientDistance, double screenSize, double distanceBetweenStimuli) {

        double realInvolvedVisualField;

        // Define fields.
        double alpha = distanceBetweenStimuli;
        double radius = patientDistance;
        double base = screenSize;

        // 1.
        double leftHalfVF = (visualField / 2) + centerPosition;
        double rightHalfVF = visualField - leftHalfVF;

        // 2.
        double leftHalfBaseInMM = calculateOppositeAngle(leftHalfVF, radius);
        double rightHalfBaseInMM = calculateOppositeAngle(rightHalfVF, radius);

        // 3.
        double tempLeftVFinMM = calculateOppositeAngle(alpha / 2, radius);
        double tempAlphaLeft = alpha / 2;
        while (true) {
            double tempCondition = calculateOppositeAngle(tempAlphaLeft + distanceBetweenStimuli, radius);
            if (tempCondition > leftHalfBaseInMM) {
                break;
            } else {
                tempLeftVFinMM = tempCondition;
                tempAlphaLeft += distanceBetweenStimuli;
            }
        }

        // 4.
        double tempRightVFinMM = calculateOppositeAngle(alpha / 2, radius);
        double tempAlphaRight = alpha / 2;
        while (true) {
            double tempCondition = calculateOppositeAngle(tempAlphaRight + distanceBetweenStimuli, radius);
            if (tempCondition > rightHalfBaseInMM) {
                break;
            } else {
                tempRightVFinMM = tempCondition;
                tempAlphaRight += distanceBetweenStimuli;
            }
        }

        // 5.
        double totalUsedVFinMM = tempLeftVFinMM + tempRightVFinMM;

        // 6.
        realInvolvedVisualField = 2 * Math.atan2(totalUsedVFinMM / 10, 2 * (radius / 10)) * (180 / Math.PI);

        return realInvolvedVisualField;
    }
}
