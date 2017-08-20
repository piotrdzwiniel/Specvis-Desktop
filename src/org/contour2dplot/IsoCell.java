package org.contour2dplot;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

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
    
public class IsoCell extends Pane {

    private double cellSizeX;
    private double cellSizeY;
    private int ternaryIndexOfAverageOfCorners;

    public IsoCell(double cellSizeX, double cellSizeY) {
        super();

        this.cellSizeX = cellSizeX;
        this.cellSizeY = cellSizeY;

        this.setMinSize(cellSizeX, cellSizeY);
        this.setPrefSize(cellSizeX, cellSizeY);
        this.setMaxSize(cellSizeX, cellSizeY);
    }

    public void drawIsoBand(int ternaryIndex, Color isoColor) {

        Polygon polygon = new Polygon();

        polygon.setFill(isoColor);
        polygon.setStrokeWidth(2);
        polygon.setStroke(isoColor);

        Polygon additionalPolygon = new Polygon();

        additionalPolygon.setFill(isoColor);
        additionalPolygon.setStrokeWidth(2);
        additionalPolygon.setStroke(isoColor);

        double ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy, hx, hy;

        switch (ternaryIndex) {
            case 1:

                // Single triangle 0001.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = 0;
                cy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                this.getChildren().add(polygon);

                break;
            case 2:

                // Single trapezoid 0002.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX * 0.4;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = cellSizeY;

                dx = 0;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 3:

                // Single triangle 0010.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                this.getChildren().add(polygon);

                break;
            case 4:

                // Single rectangle 0011.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = 0;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 5:

                // Single pentagon 0012.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX * 0.4;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY;

                dx = cellSizeX;
                dy = cellSizeY * 0.4;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 6:

                // Single trapezoid 0020.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 7:

                // Single pentagon 0021.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = cellSizeY * 0.4;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 8:

                // Single rectangle 0022.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = 0;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 9:

                // Single triangle 0100.
                ax = cellSizeX * 0.4;
                ay = 0;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                this.getChildren().add(polygon);

                break;
            case 10:

                // Saddle 0101.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 0:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = 0;
                        cy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX * 0.4;
                        ay = 0;

                        bx = cellSizeX;
                        by = cellSizeY * 0.6;

                        cx = cellSizeX;
                        cy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    case 1:

                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 11:

                // Saddle 0102.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 0:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX;
                        by = cellSizeY * 0.6;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = 0;
                        dy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX * 0.4;
                        ay = 0;

                        bx = cellSizeX;
                        by = cellSizeY * 0.6;

                        cx = cellSizeX;
                        cy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                    default:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 12:

                // Single rectangle 0110.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 13:

                // Single pentagon 0111.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = 0;

                dx = cellSizeX * 0.6;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.6;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 14:

                // Single hexagon 0112.
                ax = 0;
                ay = cellSizeY * 0.8;

                bx = cellSizeX * 0.4;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY;

                dx = cellSizeX;
                dy = 0;

                ex = cellSizeX * 0.4;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.2;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 15:

                // Single pentagon 0120.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = 0;

                ex = cellSizeX * 0.4;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 16:

                // Single hexagon 0121.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = 0;

                ex = cellSizeX * 0.4;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 17:

                // Single pentagon 0122.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 18:

                // Single trapezoid 0200.
                ax = cellSizeX;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.4;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 19:

                // Saddle 0201.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 0:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = 0;
                        cy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX;
                        by = cellSizeY * 0.4;

                        cx = cellSizeX * 0.6;
                        cy = 0;

                        dx = cellSizeX * 0.4;
                        dy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    case 1:

                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = cellSizeX * 0.4;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                    default:

                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = cellSizeX * 0.4;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 20:

                // Saddle 0202.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 0:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX;
                        by = cellSizeY * 0.6;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = 0;
                        dy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX;
                        by = cellSizeY * 0.4;

                        cx = cellSizeX * 0.6;
                        cy = 0;

                        dx = cellSizeX * 0.4;
                        dy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = cellSizeX * 0.4;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.4;

                        hx = 0;
                        hy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy, hx, hy);
                        this.getChildren().add(polygon);

                        break;
                    case 2:

                        // Polygon no. 1.
                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX * 0.6;
                        by = 0;

                        cx = cellSizeX * 0.4;
                        cy = 0;

                        dx = 0;
                        dy = cellSizeY * 0.4;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(additionalPolygon);

                        break;
                }

                break;
            case 21:

                // Single pentagon 0210.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX * 0.6;
                dy = 0;

                ex = cellSizeX * 0.4;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 22:

                // Single hexagon 0211.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX * 0.8;
                dy = 0;

                ex = cellSizeX * 0.2;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 23:

                // Saddle 0212.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = cellSizeX * 0.6;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                    case 2:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX * 0.6;
                        by = 0;

                        cx = cellSizeX * 0.4;
                        cy = 0;

                        dx = 0;
                        dy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    default:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = cellSizeX * 0.6;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 24:

                // Single rectangle 0220.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 25:

                // Single pentagon 0221.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 26:

                // Single trapezoid 0222.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX * 0.6;
                by = 0;

                cx = cellSizeX * 0.4;
                cy = 0;

                dx = 0;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 27:

                // Single triangle 1000.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX * 0.6;
                by = 0;

                cx = 0;
                cy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                this.getChildren().add(polygon);

                break;
            case 28:

                // Single rectangle 1001.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = 0;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 29:

                // Single pentagon 1002.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = 0;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.6;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 30:

                // Saddle 1010.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 0:

                        // Polygon no. 1.
                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX * 0.6;
                        by = 0;

                        cx = 0;
                        cy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = cellSizeX * 0.6;
                        dy = 0;

                        ex = 0;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 31:

                // Single pentagon 1011.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX * 0.4;
                dy = 0;

                ex = 0;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 32:

                // Single hexagon 1012.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX * 0.6;
                dy = 0;

                ex = 0;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.6;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 33:

                // Saddle 1020.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 0:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX * 0.6;
                        by = 0;

                        cx = 0;
                        cy = 0;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = 0;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                    default:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = 0;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 34:

                // Single hexagon 1021.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.8;

                dx = cellSizeX;
                dy = cellSizeY * 0.2;

                ex = cellSizeX * 0.6;
                ey = 0;

                fx = 0;
                fy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 35:

                // Single pentagon 1022.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX * 0.6;
                dy = 0;

                ex = 0;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 36:

                // Single rectangle 1100.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = 0;

                dx = 0;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 37:

                // Single pentagon 1101.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.4;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX;
                dy = 0;

                ex = 0;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 38:

                // Single hexagon 1102.
                ax = cellSizeX * 0.2;
                ay = cellSizeY;

                bx = cellSizeX * 0.8;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = 0;

                ex = 0;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.6;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 39:

                // Single pentagon 1110.
                ax = cellSizeX * 0.6;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = 0;

                dx = 0;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 40:

                // Single square 1111.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = 0;

                dx = 0;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 41:

                // Single pentagon 1112.
                ax = cellSizeX * 0.6;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = 0;

                dx = 0;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 42:

                // Single hexagon 1120.
                ax = cellSizeX * 0.2;
                ay = cellSizeY;

                bx = cellSizeX * 0.8;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = 0;

                ex = 0;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.6;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 43:

                // Single pentagon 1121.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.4;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX;
                dy = 0;

                ex = 0;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 44:

                // Single rectangle 1122.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = 0;

                dx = 0;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 45:

                // Single pentagon 1200.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX * 0.6;
                dy = 0;

                ex = 0;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 46:

                // Single hexagon 1201.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.8;

                dx = cellSizeX;
                dy = cellSizeY * 0.2;

                ex = cellSizeX * 0.6;
                ey = 0;

                fx = 0;
                fy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 47:

                // Saddle 1202.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = 0;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                    case 2:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = 0;
                        dy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX * 0.6;
                        by = 0;

                        cx = 0;
                        cy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    default:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = 0;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 48:

                // Single hexagon 1210.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX * 0.6;
                dy = 0;

                ex = 0;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.6;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 49:

                // Single pentagon 1211.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX * 0.4;
                dy = 0;

                ex = 0;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 50:

                // Saddle 1212.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = cellSizeX * 0.6;
                        dy = 0;

                        ex = 0;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                        this.getChildren().add(polygon);

                        break;
                    case 2:

                        // Polygon no. 1.
                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX * 0.6;
                        by = 0;

                        cx = 0;
                        cy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(additionalPolygon);

                        break;
                }

                break;
            case 51:

                // Single pentagon 1220.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = 0;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.6;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 52:

                // Single rectangle 1221.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = 0;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 53:

                // Single triangle 1222.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX * 0.6;
                by = 0;

                cx = 0;
                cy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                this.getChildren().add(polygon);

                break;
            case 54:

                // Single trapezoid 2000.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX * 0.6;
                by = 0;

                cx = cellSizeX * 0.4;
                cy = 0;

                dx = 0;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 55:

                // Single pentagon 2001.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 56:

                // Single rectangle 2002.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 57:

                // Saddle 2010.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 0:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX * 0.6;
                        by = 0;

                        cx = cellSizeX * 0.4;
                        cy = 0;

                        dx = 0;
                        dy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = cellSizeX * 0.6;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                    default:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = cellSizeX * 0.6;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 58:

                // Single hexagon 2011.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX * 0.8;
                dy = 0;

                ex = cellSizeX * 0.2;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 59:

                // Single pentagon 2012.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = cellSizeX * 0.6;
                dy = 0;

                ex = cellSizeX * 0.4;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 60:

                // Saddle 2020.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 0:

                        // Polygon no. 1.
                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX * 0.6;
                        by = 0;

                        cx = cellSizeX * 0.4;
                        cy = 0;

                        dx = 0;
                        dy = cellSizeY * 0.4;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = cellSizeX * 0.4;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.4;

                        hx = 0;
                        hy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy, hx, hy);
                        this.getChildren().add(polygon);

                        break;
                    case 2:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX;
                        by = cellSizeY * 0.6;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = 0;
                        dy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX;
                        by = cellSizeY * 0.4;

                        cx = cellSizeX * 0.6;
                        cy = 0;

                        dx = cellSizeX * 0.4;
                        dy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(additionalPolygon);

                        break;
                }

                break;
            case 61:

                // Saddle 2021.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 1:

                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = cellSizeX * 0.4;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                    case 2:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = 0;
                        cy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX;
                        by = cellSizeY * 0.4;

                        cx = cellSizeX * 0.6;
                        cy = 0;

                        dx = cellSizeX * 0.4;
                        dy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    default:

                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = cellSizeY * 0.4;

                        ex = cellSizeX * 0.6;
                        ey = 0;

                        fx = cellSizeX * 0.4;
                        fy = 0;

                        gx = 0;
                        gy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 62:

                // Single trapezoid 2022.
                ax = cellSizeX;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.4;

                cx = cellSizeX * 0.6;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 63:

                // Single pentagon 2100.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 64:

                // Single hexagon 2101.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = 0;

                ex = cellSizeX * 0.4;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 65:

                // Single pentagon 2102.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = 0;

                ex = cellSizeX * 0.4;
                ey = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 66:

                // Single hexagon 2110.
                ax = 0;
                ay = cellSizeY * 0.8;

                bx = cellSizeX * 0.4;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY;

                dx = cellSizeX;
                dy = 0;

                ex = cellSizeX * 0.4;
                ey = 0;

                fx = 0;
                fy = cellSizeY * 0.2;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                this.getChildren().add(polygon);

                break;
            case 67:

                // Single pentagon 2111.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = 0;

                dx = cellSizeX * 0.6;
                dy = 0;

                ex = 0;
                ey = cellSizeY * 0.6;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 68:

                // Single rectangle 2112.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = 0;

                dx = cellSizeX * 0.4;
                dy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 69:

                // Saddle 2120.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 1:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                    case 2:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY * 0.6;

                        bx = cellSizeX;
                        by = cellSizeY * 0.6;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.4;

                        dx = 0;
                        dy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX * 0.4;
                        ay = 0;

                        bx = cellSizeX;
                        by = cellSizeY * 0.6;

                        cx = cellSizeX;
                        cy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(additionalPolygon);

                        break;
                    default:

                        ax = cellSizeX * 0.4;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        gx = 0;
                        gy = cellSizeY * 0.6;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy);
                        this.getChildren().add(polygon);

                        break;
                }

                break;
            case 70:

                // Saddle 2121.
                switch (ternaryIndexOfAverageOfCorners) {
                    case 1:

                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = cellSizeX;
                        cy = cellSizeY * 0.6;

                        dx = cellSizeX;
                        dy = 0;

                        ex = cellSizeX * 0.4;
                        ey = 0;

                        fx = 0;
                        fy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy);
                        this.getChildren().add(polygon);

                        break;
                    case 2:

                        // Polygon no. 1.
                        ax = 0;
                        ay = cellSizeY;

                        bx = cellSizeX * 0.6;
                        by = cellSizeY;

                        cx = 0;
                        cy = cellSizeY * 0.4;

                        polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(polygon);

                        // Polygon no. 2.
                        ax = cellSizeX * 0.4;
                        ay = 0;

                        bx = cellSizeX;
                        by = cellSizeY * 0.6;

                        cx = cellSizeX;
                        cy = 0;

                        additionalPolygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                        this.getChildren().add(additionalPolygon);

                        break;
                }

                break;
            case 71:

                // Single triangle 2122.
                ax = cellSizeX * 0.4;
                ay = 0;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = 0;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                this.getChildren().add(polygon);

                break;
            case 72:

                // Single rectangle 2200.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX;
                by = cellSizeY * 0.6;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = 0;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 73:

                // Single pentagon 2201.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = cellSizeY * 0.4;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 74:

                // Single trapezoid 2202.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.6;

                dx = cellSizeX;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 75:

                // Single pentagon 2210.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX * 0.4;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY;

                dx = cellSizeX;
                dy = cellSizeY * 0.4;

                ex = 0;
                ey = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy, ex, ey);
                this.getChildren().add(polygon);

                break;
            case 76:

                // Single rectangle 2211.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                dx = 0;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 77:

                // Single triangle 2212.
                ax = cellSizeX * 0.4;
                ay = cellSizeY;

                bx = cellSizeX;
                by = cellSizeY;

                cx = cellSizeX;
                cy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                this.getChildren().add(polygon);

                break;
            case 78:

                // Single trapezoid 2220.
                ax = 0;
                ay = cellSizeY * 0.6;

                bx = cellSizeX * 0.4;
                by = cellSizeY;

                cx = cellSizeX * 0.6;
                cy = cellSizeY;

                dx = 0;
                dy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy, dx, dy);
                this.getChildren().add(polygon);

                break;
            case 79:

                // Single triangle 2221.
                ax = 0;
                ay = cellSizeY;

                bx = cellSizeX * 0.6;
                by = cellSizeY;

                cx = 0;
                cy = cellSizeY * 0.4;

                polygon.getPoints().addAll(ax, ay, bx, by, cx, cy);
                this.getChildren().add(polygon);

                break;
        }
    }

    public void setTernaryIndexOfAverageOfCorners(int ternaryIndexOfAverageOfCorners) {
        this.ternaryIndexOfAverageOfCorners = ternaryIndexOfAverageOfCorners;
    }
}
