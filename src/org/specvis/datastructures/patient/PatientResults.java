package org.specvis.datastructures.patient;

import java.lang.reflect.Field;

/**
 * Created by Piotr Dzwiniel on 2016-03-23.
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

public class PatientResults {

    private String id;
    private String date;
    private String eye;
    private String visualField;
    private String procedure;
    private String fixationMonitor;
    private String testDuration;

    public PatientResults() {

    }

    public PatientResults(String id, String date, String eye, String visualField, String procedure, String fixationMonitor, String testDuration) {
        this.id = id;
        this.date = date;
        this.eye = eye;
        this.visualField = visualField;
        this.procedure = procedure;
        this.fixationMonitor = fixationMonitor;
        this.testDuration = testDuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getVisualField() {
        return visualField;
    }

    public void setVisualField(String visualField) {
        this.visualField = visualField;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getFixationMonitor() {
        return fixationMonitor;
    }

    public void setFixationMonitor(String fixationMonitor) {
        this.fixationMonitor = fixationMonitor;
    }

    public String getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(String testDuration) {
        this.testDuration = testDuration;
    }

    public static String[] getFieldsNames() {
        Field[] fields = PatientResults.class.getDeclaredFields();
        String[] fieldsNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldsNames[i] = fields[i].getName();
        }
        return fieldsNames;
    }
}
