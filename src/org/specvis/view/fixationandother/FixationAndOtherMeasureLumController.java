package org.specvis.view.fixationandother;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.specvis.StartApplication;
import org.specvis.model.FixationAndOther;
import org.specvis.model.Functions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Piotr Dzwiniel on 2016-02-28.
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

public class FixationAndOtherMeasureLumController implements Initializable {

    private Functions functions;
    private FixationAndOther fixationAndOther;

    @FXML
    private Pane panePreview;

    @FXML
    private Spinner spinnerLuminance;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        functions = StartApplication.getFunctions();

        fixationAndOther = StartApplication.getSpecvisData().getFixationAndOther();

        setBackgroundColorOfPanePreview();
    }

    private void setBackgroundColorOfPanePreview() {

        Color color = fixationAndOther.getFixationPointColor();
        String colorWeb = functions.toHexCode(color);
        String colorForBackground = "-fx-background-color: " + colorWeb + ";";

        panePreview.setStyle(colorForBackground);
    }

    @FXML
    private void setOnActionCancelButton() {

        // Close window.
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void setOnActionSaveButton() throws IOException {

        // Set measured luminance value.
        double luminance = Double.valueOf(spinnerLuminance.getValue().toString());
        double rounded = functions.round(luminance, 2);
        fixationAndOther.setFixationPointLuminance(rounded);
        StartApplication.getSpecvisData().setFixationAndOther(fixationAndOther);

        // Update textFieldFixation
        StartApplication.getFixationAndOtherController().updateValueForTextFieldFixationPointLuminance();

        // Close window.
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        stage.close();
    }
}
