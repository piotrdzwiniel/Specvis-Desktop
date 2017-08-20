package org.specvis.view.screenandlumscale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import org.specvis.StartApplication;
import org.specvis.datastructures.settings.UISettingsScreenAndLuminanceScale;
import org.specvis.logic.Functions;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Piotr Dzwiniel on 2016-02-08.
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

public class ViewScreenAndLumScaleController implements Initializable {

    private Functions functions;

    @FXML
    private ComboBox<String> comboBoxScreen;

    @FXML
    private TextField textFieldScreenResolutionX;

    @FXML
    private TextField textFieldScreenResolutionY;

    @FXML
    private Spinner<Integer> spinnerScreenWidth;

    @FXML
    private Spinner<Integer> spinnerScreenHeight;

    @FXML
    private Spinner<Integer> spinnerPatientDistanceFromTheScreen;

    @FXML
    private TextField textFieldInvolvedVisualFieldX;

    @FXML
    private TextField textFieldInvolvedVisualFieldY;

    @FXML
    private TextField textFieldStimulusLuminanceScaleName;

    @FXML
    private TextField textFieldBackgroundLuminanceScaleName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        functions = StartApplication.getFunctions();

        setItemsForComboBoxScreen();
        initValuesForFields();
        getResolutionOfTheSelectedScreen();
        calculateInvolvedVisualField();
    }

    private void initValuesForFields() {
        UISettingsScreenAndLuminanceScale uiSettingsScreenAndLuminanceScale = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale();
        if (uiSettingsScreenAndLuminanceScale != null) {
            comboBoxScreen.getSelectionModel().select(uiSettingsScreenAndLuminanceScale.getChosenScreenIndex());
            spinnerScreenWidth.getValueFactory().setValue(uiSettingsScreenAndLuminanceScale.getScreenWidth());
            spinnerScreenHeight.getValueFactory().setValue(uiSettingsScreenAndLuminanceScale.getScreenHeight());
            spinnerPatientDistanceFromTheScreen.getValueFactory().setValue(uiSettingsScreenAndLuminanceScale.getPatientDistanceFromTheScreen());
            if (uiSettingsScreenAndLuminanceScale.getStimulusLuminanceScale() != null) {
                textFieldStimulusLuminanceScaleName.setText(uiSettingsScreenAndLuminanceScale.getStimulusLuminanceScale().getName());
            }
            if (uiSettingsScreenAndLuminanceScale.getBackgroundLuminanceScale() != null) {
                textFieldBackgroundLuminanceScaleName.setText(uiSettingsScreenAndLuminanceScale.getBackgroundLuminanceScale().getName());
            }
        }
    }

    private void setValuesForScreenAndLuminanceFields() {
        UISettingsScreenAndLuminanceScale uiSettingsScreenAndLuminanceScale = StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale();
        if (uiSettingsScreenAndLuminanceScale == null) {
            uiSettingsScreenAndLuminanceScale = new UISettingsScreenAndLuminanceScale();
            StartApplication.getSpecvisData().setUiSettingsScreenAndLuminanceScale(uiSettingsScreenAndLuminanceScale);
        }

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] graphicsDevices = graphicsEnvironment.getScreenDevices();
        for (GraphicsDevice i : graphicsDevices) {
            if (i.toString().equals(comboBoxScreen.getSelectionModel().getSelectedItem())) {
                uiSettingsScreenAndLuminanceScale.setScreen(i);
                break;
            }
        }

        uiSettingsScreenAndLuminanceScale.setChosenScreenIndex(comboBoxScreen.getSelectionModel().getSelectedIndex());
        uiSettingsScreenAndLuminanceScale.setScreenResolutionX(Integer.valueOf(textFieldScreenResolutionX.getText()));
        uiSettingsScreenAndLuminanceScale.setScreenResolutionY(Integer.valueOf(textFieldScreenResolutionY.getText()));
        uiSettingsScreenAndLuminanceScale.setScreenWidth(Integer.valueOf(spinnerScreenWidth.getValue().toString()));
        uiSettingsScreenAndLuminanceScale.setScreenHeight(Integer.valueOf(spinnerScreenHeight.getValue().toString()));
        uiSettingsScreenAndLuminanceScale.setPatientDistanceFromTheScreen(Integer.valueOf(spinnerPatientDistanceFromTheScreen.getValue().toString()));
        uiSettingsScreenAndLuminanceScale.setInvolvedVisualFieldX(Double.valueOf(textFieldInvolvedVisualFieldX.getText()));
        uiSettingsScreenAndLuminanceScale.setInvolvedVisualFieldY(Double.valueOf(textFieldInvolvedVisualFieldY.getText()));
    }

    private void setItemsForComboBoxScreen() {
        comboBoxScreen.getItems().clear();
        comboBoxScreen.setItems(getListOfTheScreens());
        comboBoxScreen.getSelectionModel().select(0);
    }

    private void getResolutionOfTheSelectedScreen() {

        // Get list of active screens.
//        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice[] graphicsDevices = graphicsEnvironment.getScreenDevices();

        // Get index of selected screen in comboBoxScreen.
//        int selectedScreenIndex = comboBoxScreen.getSelectionModel().getSelectedIndex();

        // Get resolution of the selected screen.
//        GraphicsDevice selectedScreen = graphicsDevices[selectedScreenIndex];
//        int x = selectedScreen.getDisplayMode().getWidth();
//        int y = selectedScreen.getDisplayMode().getHeight();

        java.util.List<Screen> screens = Screen.getScreens();
        Screen activeScreen = screens.get(comboBoxScreen.getSelectionModel().getSelectedIndex());
        Rectangle2D activeScreenVisualBounds = activeScreen.getVisualBounds();

        // Set text for text fields.
        textFieldScreenResolutionX.setText(String.valueOf((int) activeScreenVisualBounds.getWidth()));
        textFieldScreenResolutionY.setText(String.valueOf((int) activeScreenVisualBounds.getHeight()));
    }

    @FXML
    private void actionOnScreenSelection() {
        if (comboBoxScreen.getItems().size() > 0) {
            getResolutionOfTheSelectedScreen();
            calculateInvolvedVisualField();
        }
    }

    @FXML
    private void calculateInvolvedVisualField() {

        // Get values from spinner and cast them to int.
        int screenWidthInMM = Integer.valueOf(spinnerScreenWidth.getValue().toString());
        int screenHeightInMM = Integer.valueOf(spinnerScreenHeight.getValue().toString());
        int patientDistanceInMM = Integer.valueOf(spinnerPatientDistanceFromTheScreen.getValue().toString());

        // Calculate involved visual field.
        double screenWidthInCM = (double) screenWidthInMM / 10;
        double screenHeightInCM = (double) screenHeightInMM / 10;
        double patientDistanceInCM = (double) patientDistanceInMM / 10;
        double visualFieldX = 2 * Math.atan2(screenWidthInCM, 2 * patientDistanceInCM) * (180 / Math.PI);
        double visualFieldY = 2 * Math.atan2(screenHeightInCM, 2 * patientDistanceInCM) * (180 / Math.PI);

        // Set text for text fields.
        textFieldInvolvedVisualFieldX.setText(String.valueOf(functions.round(visualFieldX, 2)));
        textFieldInvolvedVisualFieldY.setText(String.valueOf(functions.round(visualFieldY, 2)));
    }

    private ObservableList<String> getListOfTheScreens() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] graphicsDevices = graphicsEnvironment.getScreenDevices();
        for (GraphicsDevice i : graphicsDevices) {
            observableList.add(i.toString());
        }
        return observableList;
    }

    @FXML
    private void refreshComboBoxScreen() {
        setItemsForComboBoxScreen();
    }

    @FXML
    private void setSceneScreenAndLumScaleNew_Stimulus() throws IOException {
        setValuesForScreenAndLuminanceFields();
        StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setIsThisWindowOpenedForStimulus(true);
        StartApplication.setSceneScreenAndLumScaleNew();
    }

    @FXML
    private void setSceneScreenAndLumScaleNew_Background() throws IOException {
        setValuesForScreenAndLuminanceFields();
        StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setIsThisWindowOpenedForStimulus(false);
        StartApplication.setSceneScreenAndLumScaleNew();
    }

    @FXML
    private void setSceneScreenAndLumScaleExisting_Stimulus() throws IOException {
        setValuesForScreenAndLuminanceFields();
        StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setIsThisWindowOpenedForStimulus(true);
        StartApplication.setSceneScreenAndLumScaleExisting();
    }

    @FXML
    private void setSceneScreenAndLumScaleExisting_Background() throws IOException {
        setValuesForScreenAndLuminanceFields();
        StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setIsThisWindowOpenedForStimulus(false);
        StartApplication.setSceneScreenAndLumScaleExisting();
    }

    @FXML
    private void setSceneScreenAndLumScaleEdit_Stimulus() throws IOException {
        if (!textFieldStimulusLuminanceScaleName.getText().equals("")) {
            setValuesForScreenAndLuminanceFields();
            StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setIsThisWindowOpenedForStimulus(true);
            StartApplication.setSceneScreenAndLumScaleEdit();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must choose stimulus luminance scale first.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setSceneScreenAndLumScaleEdit_Background() throws IOException {
        if (!textFieldBackgroundLuminanceScaleName.getText().equals("")) {
            setValuesForScreenAndLuminanceFields();
            StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setIsThisWindowOpenedForStimulus(false);
            StartApplication.setSceneScreenAndLumScaleEdit();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must choose background luminance scale first.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setSceneScreenAndLumScaleFit_Stimulus() throws IOException {
        if (!textFieldStimulusLuminanceScaleName.getText().equals("")) {
            setValuesForScreenAndLuminanceFields();
            StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setIsThisWindowOpenedForStimulus(true);
            StartApplication.setSceneScreenAndLumScaleFit();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must choose stimulus luminance scale first.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setSceneScreenAndLumScaleFit_Background() throws IOException {
        if (!textFieldBackgroundLuminanceScaleName.getText().equals("")) {
            setValuesForScreenAndLuminanceFields();
            StartApplication.getSpecvisData().getUiSettingsScreenAndLuminanceScale().setIsThisWindowOpenedForStimulus(false);
            StartApplication.setSceneScreenAndLumScaleFit();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must choose background luminance scale first.");
            alert.showAndWait();
        }
    }

    @FXML
    private void setScenePatient() throws IOException {
        setValuesForScreenAndLuminanceFields();
        StartApplication.setScenePatient();
    }

    @FXML
    private void setSceneStimulusAndBackground() throws IOException {
        if (!textFieldStimulusLuminanceScaleName.getText().equals("") && !textFieldBackgroundLuminanceScaleName.getText().equals("")) {
            setValuesForScreenAndLuminanceFields();
            StartApplication.setSceneStimulusAndBackground();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Lack of key information.");
            alert.setContentText("You must choose stimulus and background luminance scales first.");
            alert.showAndWait();
        }
    }
}
