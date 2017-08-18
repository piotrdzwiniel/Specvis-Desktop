package org.specvis.view.screenandlumscale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.specvis.StartApplication;
import org.specvis.datastructures.luminancescale.ExistingLuminanceScale;
import org.specvis.datastructures.luminancescale.LuminanceScale;
import org.specvis.logic.Functions;
import org.specvis.logic.CustomTableView;
import org.specvis.view.miscellaneous.ViewExceptionDialog;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Piotr Dzwiniel on 2016-02-08.
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

public class ViewExistingLumScaleController implements Initializable {

    private Functions functions;

    @FXML
    private VBox vBox;

    @FXML
    private TableView tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        functions = StartApplication.getFunctions();

        setTableView();
    }

    private void setTableView() {
        ObservableList<ExistingLuminanceScale> tableData = getExistingLuminanceScalesData();
        String[] tableColumnsNames = new String[] {"ID", "Name", "Hue", "Saturation", "Preview", "Min. luminance", "Max. luminance"};
        String[] tableFieldsNames = ExistingLuminanceScale.getFieldsNames();
        tableView = new CustomTableView<>(tableColumnsNames, tableFieldsNames, tableData);
        vBox.getChildren().clear();
        vBox.getChildren().add(tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
    }

    private ObservableList<ExistingLuminanceScale> getExistingLuminanceScalesData() {
        ObservableList<ExistingLuminanceScale> existingLuminanceScalesData = FXCollections.observableArrayList();
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
            if (array.size() > 0) {
                ExistingLuminanceScale[] existingLuminanceScales = new ExistingLuminanceScale[array.size()];
                for (int i = 0; i < array.size(); i++) {
                    String[] str = array.get(i).split("\t");

                    int hue = (int) functions.round(Double.valueOf(str[2]), 0);
                    int saturation = (int) functions.round(Double.valueOf(str[3]), 0);

                    Label labelPreviewColor = new Label();
                    labelPreviewColor.setStyle(
                            "-fx-background-color: hsb(" + hue + ", " + saturation + "%, " + 100 + "%); " +
                                    "-fx-border-width: 1px; " +
                                    "-fx-border-color: black;"
                    );
                    labelPreviewColor.setPrefWidth(Double.MAX_VALUE);

                    ExistingLuminanceScale existingLuminanceScale = new ExistingLuminanceScale(
                            str[0],
                            str[1],
                            hue,
                            saturation,
                            labelPreviewColor,
                            functions.round(Double.valueOf(str[4]), 2),
                            functions.round(Double.valueOf(str[9]), 2)
                    );
                    existingLuminanceScales[i] = existingLuminanceScale;
                }
                existingLuminanceScalesData = FXCollections.observableArrayList(existingLuminanceScales);
            }
        } catch (Exception ex) {
            ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }
        return existingLuminanceScalesData;
    }

    @FXML
    private void deleteExistingScale() throws IOException {

        if (tableView.getSelectionModel().getSelectedItem() != null) {

            // Load chosen patient.
            ExistingLuminanceScale existingLuminanceScale = (ExistingLuminanceScale) tableView.getSelectionModel().getSelectedItem();
            String scaleID = existingLuminanceScale.getId();

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Are you sure you want to delete selected luminance scale?");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.get() == ButtonType.OK) {

                deleteSelectedLuminanceScale(scaleID);
                setTableView();
            }

            // Show information dialog.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Selected luminance scale was removed.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must select an existing luminance scale first.");
            alert.showAndWait();
        }
    }

    private void deleteSelectedLuminanceScale(String scaleID) {
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
            for (int i = 0; i < array.size(); i++) {
                String[] str = array.get(i).split("\t");
                if (str[0].equals(scaleID)) {
                    array.remove(i);
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
                        for (String anArray : array) {
                            bufferedWriter.write(anArray + "\n");
                        }
                        bufferedWriter.close();
                    } catch (IOException ex) {
                        ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
                        ed.setTitle("Exception");
                        ed.setHeaderText(ex.getClass().getName());
                        ed.showAndWait();
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            ViewExceptionDialog ed = new ViewExceptionDialog(Alert.AlertType.ERROR, ex);
            ed.setTitle("Exception");
            ed.setHeaderText(ex.getClass().getName());
            ed.showAndWait();
        }
    }
    
    @FXML
    private void setSceneScreenAndLumScale() throws IOException {
        StartApplication.setSceneScreenAndLumScale();
    }

    @FXML
    private void chooseExistingScale() throws IOException {
        
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            
            // Load chosen existing luminance scale.
            ExistingLuminanceScale existingLuminanceScale = (ExistingLuminanceScale) tableView.getSelectionModel().getSelectedItem();
            String scaleID = existingLuminanceScale.getId();
            LuminanceScale luminanceScale = functions.findExistingLuminanceScaleByID(scaleID);

            // Assign created luminance scale in SpecvisData.
            if (StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().isThisWindowOpenedForStimulus()) {
                StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setStimulusLuminanceScale(luminanceScale);
            } else {
                StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setBackgroundLuminanceScale(luminanceScale);
            }
            
            // Set scene screen and luminance scale.
            setSceneScreenAndLumScale();
            
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must select an existing luminance scale first.");
            alert.showAndWait();
        }
    }
}
