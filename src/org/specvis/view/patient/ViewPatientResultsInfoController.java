package org.specvis.view.patient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.specvis.StartApplication;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class ViewPatientResultsInfoController implements Initializable {

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTextArea();
    }

    private void setTextArea() {
        ArrayList<String> arrayList = StartApplication.getSpecvisData().getPatient().getResultsInfo();

        for (int i = 0; i < arrayList.size(); i++) {
            textArea.appendText(arrayList.get(i) + "\n");
        }
    }

    @FXML
    private void setActionForCloseButton() throws Exception {
        StartApplication.setScenePatientResults();
    }
}
