package org.specvis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.specvis.model.Functions;
import org.specvis.model.SpecvisData;
import org.specvis.view.fixationandother.FixationAndOtherController;
import org.specvis.view.fixationandother.FixationAndOtherMonitorSettingsBlindspotController;
import org.specvis.view.fixationandother.FixationAndOtherMonitorSettingsFixPointChangeController;
import org.specvis.view.fixationandother.procedure.BasicProcedureSettingsController;
import org.specvis.view.patient.*;
import org.specvis.view.procedure.ProcedureController;
import org.specvis.view.procedure.ProcedureShowResultsController;
import org.specvis.view.screenandlumscale.*;
import org.specvis.view.stimulusandbackground.StimulusAndBackgroundController;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Piotr Dzwiniel on 2016-01-24.
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

public class StartApplication extends Application {

    private static Stage window;
    private static SpecvisData specvisData;
    private static Functions functions;

    private static PatientController patientController;
    private static PatientNewController patientNewController;
    private static PatientExistingController patientExistingController;
    private static PatientEditController patientEditController;
    private static PatientResultsController patientResultsController;
    private static PatientResultsInfoController patientResultsInfoController;
    private static PatientResultsMapController patientResultsMapController;
    private static ScreenAndLumScaleController screenAndLumScaleController;
    private static ScreenAndLumScaleNewController screenAndLumScaleNewController;
    private static ScreenAndLumScaleExistingController screenAndLumScaleExistingController;
    private static ScreenAndLumScaleEditController screenAndLumScaleEditController;
    private static ScreenAndLumScaleFitController screenAndLumScaleFitController;
    private static StimulusAndBackgroundController stimulusAndBackgroundController;
    private static FixationAndOtherController fixationAndOtherController;
    private static FixationAndOtherMonitorSettingsBlindspotController fixationAndOtherMonitorSettingsBlindspotController;
    private static FixationAndOtherMonitorSettingsFixPointChangeController fixationAndOtherMonitorSettingsFixPointChangeController;
    private static BasicProcedureSettingsController basicProcedureSettingsController;
    private static ProcedureController procedureController;
    private static ProcedureShowResultsController procedureShowResultsController;

    private static Scene sceneProcedure;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        specvisData = new SpecvisData();
        functions = new Functions();
        setScenePatient();
        window.setTitle("Specvis");
        window.getIcons().add(new Image("/org/specvis/graphics/SpecvisIcon.png"));

        window.show();

        window.setOnCloseRequest(event -> {

            event.consume();

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Are you sure you want to exit Specvis?");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.get() == ButtonType.OK) {
                System.exit(-1);
            }
        });
    }

    /* PATIENT SCENES */
    public static void setScenePatient() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/Patient.fxml"));
        Parent root = fxmlLoader.load();
        patientController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientNew() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/PatientNew.fxml"));
        Parent root = fxmlLoader.load();
        patientNewController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientExisting() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/PatientExisting.fxml"));
        Parent root = fxmlLoader.load();
        patientExistingController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientEdit() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/PatientEdit.fxml"));
        Parent root = fxmlLoader.load();
        patientEditController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientResults() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/PatientResults.fxml"));
        Parent root = fxmlLoader.load();
        patientResultsController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientResultsInfo() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/PatientResultsInfo.fxml"));
        Parent root = fxmlLoader.load();
        patientResultsInfoController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientResultsMap() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/PatientResultsMap.fxml"));
        Parent root = fxmlLoader.load();
        patientResultsMapController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    /* SCREEN AND LUMINANCE SCENES */
    public static void setSceneScreenAndLumScale() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ScreenAndLumScale.fxml"));
        Parent root = fxmlLoader.load();
        screenAndLumScaleController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneScreenAndLumScaleNew() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ScreenAndLumScaleNew.fxml"));
        Parent root = fxmlLoader.load();
        screenAndLumScaleNewController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneScreenAndLumScaleExisting() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ScreenAndLumScaleExisting.fxml"));
        Parent root = fxmlLoader.load();
        screenAndLumScaleExistingController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneScreenAndLumScaleEdit() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ScreenAndLumScaleEdit.fxml"));
        Parent root = fxmlLoader.load();
        screenAndLumScaleEditController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneScreenAndLumScaleFit() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ScreenAndLumScaleFit.fxml"));
        Parent root = fxmlLoader.load();
        screenAndLumScaleFitController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    /* STIMULUS AND BACKGROUND SCENES */
    public static void setSceneStimulusAndBackground() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/stimulusandbackground/StimulusAndBackground.fxml"));
        Parent root = fxmlLoader.load();
        stimulusAndBackgroundController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    /* FIXATION AND OTHER SCENES */
    public static void setSceneFixationAndOther() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/fixationandother/FixationAndOther.fxml"));
        Parent root = fxmlLoader.load();
        fixationAndOtherController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneFixationAndOtherMonitorSettingsBlindspot() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/fixationandother/FixationAndOtherMonitorSettingsBlindspot.fxml"));
        Parent root = fxmlLoader.load();
        fixationAndOtherMonitorSettingsBlindspotController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneFixationAndOtherMonitorSettingsFixPointChange() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/fixationandother/FixationAndOtherMonitorSettingsFixPointChange.fxml"));
        Parent root = fxmlLoader.load();
        fixationAndOtherMonitorSettingsFixPointChangeController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneBasicProcedureSettings() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/fixationandother/procedure/BasicProcedureSettings.fxml"));
        Parent root = fxmlLoader.load();
        basicProcedureSettingsController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    /* PROCEDURE SCENES */
    public static void setSceneProcedure() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/procedure/Procedure.fxml"));
        Parent root = fxmlLoader.load();
        procedureController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        sceneProcedure = scene;
        getWindow().setScene(scene);
    }

    public static void setExistingSceneProcedure() {
        getWindow().setScene(sceneProcedure);
    }

    public static void setSceneProcedureShowResults() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/procedure/ProcedureShowResults.fxml"));
        Parent root = fxmlLoader.load();
        procedureShowResultsController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage getWindow() {
        return window;
    }

    public static SpecvisData getSpecvisData() {
        return specvisData;
    }

    public static Functions getFunctions() {
        return functions;
    }

    public static PatientController getPatientController() {
        return patientController;
    }

    public static PatientNewController getPatientNewController() {
        return patientNewController;
    }

    public static PatientExistingController getPatientExistingController() {
        return patientExistingController;
    }

    public static PatientEditController getPatientEditController() {
        return patientEditController;
    }

    public static PatientResultsController getPatientResultsController() {
        return patientResultsController;
    }

    public static PatientResultsInfoController getPatientResultsInfoController() {
        return patientResultsInfoController;
    }

    public static PatientResultsMapController getPatientResultsMapController() {
        return patientResultsMapController;
    }

    public static ScreenAndLumScaleController getScreenAndLumScaleController() {
        return screenAndLumScaleController;
    }

    public static ScreenAndLumScaleNewController getScreenAndLumScaleNewController() {
        return screenAndLumScaleNewController;
    }

    public static ScreenAndLumScaleExistingController getScreenAndLumScaleExistingController() {
        return screenAndLumScaleExistingController;
    }

    public static ScreenAndLumScaleEditController getScreenAndLumScaleEditController() {
        return screenAndLumScaleEditController;
    }

    public static ScreenAndLumScaleFitController getScreenAndLumScaleFitController() {
        return screenAndLumScaleFitController;
    }

    public static StimulusAndBackgroundController getStimulusAndBackgroundController() {
        return stimulusAndBackgroundController;
    }

    public static FixationAndOtherController getFixationAndOtherController() {
        return fixationAndOtherController;
    }

    public static FixationAndOtherMonitorSettingsBlindspotController getFixationAndOtherMonitorSettingsBlindspotController() {
        return fixationAndOtherMonitorSettingsBlindspotController;
    }

    public static FixationAndOtherMonitorSettingsFixPointChangeController getFixationAndOtherMonitorSettingsFixPointChangeController() {
        return fixationAndOtherMonitorSettingsFixPointChangeController;
    }

    public static BasicProcedureSettingsController getBasicProcedureSettingsController() {
        return basicProcedureSettingsController;
    }

    public static ProcedureController getProcedureController() {
        return procedureController;
    }

    public static ProcedureShowResultsController getProcedureShowResultsController() {
        return procedureShowResultsController;
    }
}
