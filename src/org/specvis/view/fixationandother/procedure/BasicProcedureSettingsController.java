package org.specvis.view.fixationandother.procedure;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.specvis.StartApplication;
import org.specvis.model.procedure.BasicProcedureSettings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Piotr Dzwiniel on 2016-03-09.
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

public class BasicProcedureSettingsController implements Initializable {

    @FXML
    private CheckBox checkBox;

    @FXML
    private TextField textField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initValuesForFields();
    }

    private void initValuesForFields() {
        BasicProcedureSettings basicProcedureSettings = StartApplication.getSpecvisData().getBasicProcedureSettings();
        if (basicProcedureSettings != null) {
            checkBox.setSelected(basicProcedureSettings.isSpreadValuesLogarithmically());
            textField.setText(String.valueOf(basicProcedureSettings.getVisualFieldTestBrightnessVectorLength()));
        }
    }

    @FXML
    private void setOnActionForCancelButton() throws IOException {
        StartApplication.setSceneFixationAndOther();
    }

    @FXML
    private void setOnActionForSaveButton() throws IOException {
        if (isLengthOfTheTestedBrightnessVectorValid(textField.getText())) {
            BasicProcedureSettings basicProcedureSettings = StartApplication.getSpecvisData().getBasicProcedureSettings();
            basicProcedureSettings.setSpreadValuesLogarithmically(checkBox.isSelected());
            basicProcedureSettings.setVisualFieldTestBrightnessVectorLength(Integer.valueOf(textField.getText()));
            StartApplication.getSpecvisData().setBasicProcedureSettings(basicProcedureSettings);
            StartApplication.setSceneFixationAndOther();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid value for length of the tested brightness vector.");
            alert.setContentText("You must provide an integer value equal to 5 + 4n, eg. 9, 13, 17, 21, 25 etc.");
            alert.showAndWait();
        }
    }

    private boolean isLengthOfTheTestedBrightnessVectorValid(String string) {

        if (string.matches("^-?\\d+$")) {
            if (Integer.valueOf(string) % 4 == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
