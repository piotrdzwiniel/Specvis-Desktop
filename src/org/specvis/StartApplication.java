package org.specvis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.specvis.logic.Functions;
import org.specvis.datastructures.SpecvisData;
import org.specvis.view.fixationandother.*;
import org.specvis.view.patient.*;
import org.specvis.view.procedure.ViewProcedurePreviewController;
import org.specvis.view.procedure.ViewProcedureResultsController;
import org.specvis.view.screenandlumscale.*;
import org.specvis.view.stimulusandbackground.ViewStimulusAndBackgroundController;

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

    private static ViewStartWindowController viewStartWindowController;
    private static ViewNewPatientController viewNewPatientController;
    private static ViewExistingPatientController viewExistingPatientController;
    private static ViewEditPatientController viewEditPatientController;
    private static ViewPatientResultsController viewPatientResultsController;
    private static ViewPatientResultsInfoController viewPatientResultsInfoController;
    private static ViewPatientResultsMapController viewPatientResultsMapController;
    private static ViewScreenAndLumScaleController viewScreenAndLumScaleController;
    private static ViewNewLumScaleController viewNewLumScaleController;
    private static ViewExistingLumScaleController viewExistingLumScaleController;
    private static ViewEditLumScaleController viewEditLumScaleController;
    private static ViewFitLumScaleController viewFitLumScaleController;
    private static ViewStimulusAndBackgroundController viewStimulusAndBackgroundController;
    private static ViewFixationAndOtherController viewFixationAndOtherController;
    private static ViewFixMonitorBlindspotController viewFixMonitorBlindspotController;
    private static ViewFixMonitorFixPointChangeController viewFixMonitorFixPointChangeController;
    private static ViewFixMonitorBothController viewFixMonitorBothController;
    private static ViewProcedureBasicController viewProcedureBasicController;
    private static ViewProcedurePreviewController viewProcedurePreviewController;
    private static ViewProcedureResultsController viewProcedureResultsController;

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

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/ViewStartWindow.fxml"));
        Parent root = fxmlLoader.load();
        viewStartWindowController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientNew() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/ViewNewPatient.fxml"));
        Parent root = fxmlLoader.load();
        viewNewPatientController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientExisting() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/ViewExistingPatient.fxml"));
        Parent root = fxmlLoader.load();
        viewExistingPatientController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientEdit() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/ViewEditPatient.fxml"));
        Parent root = fxmlLoader.load();
        viewEditPatientController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientResults() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/ViewPatientResults.fxml"));
        Parent root = fxmlLoader.load();
        viewPatientResultsController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientResultsInfo() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/ViewPatientResultsInfo.fxml"));
        Parent root = fxmlLoader.load();
        viewPatientResultsInfoController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setScenePatientResultsMap() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/patient/ViewPatientResultsMap.fxml"));
        Parent root = fxmlLoader.load();
        viewPatientResultsMapController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    /* SCREEN AND LUMINANCE SCENES */
    public static void setSceneScreenAndLumScale() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ViewScreenAndLumScale.fxml"));
        Parent root = fxmlLoader.load();
        viewScreenAndLumScaleController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneScreenAndLumScaleNew() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ViewNewLumScale.fxml"));
        Parent root = fxmlLoader.load();
        viewNewLumScaleController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneScreenAndLumScaleExisting() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ViewExistingLumScale.fxml"));
        Parent root = fxmlLoader.load();
        viewExistingLumScaleController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneScreenAndLumScaleEdit() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ViewEditLumScale.fxml"));
        Parent root = fxmlLoader.load();
        viewEditLumScaleController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneScreenAndLumScaleFit() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/screenandlumscale/ViewFitLumScale.fxml"));
        Parent root = fxmlLoader.load();
        viewFitLumScaleController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    /* STIMULUS AND BACKGROUND SCENES */
    public static void setSceneStimulusAndBackground() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/stimulusandbackground/ViewStimulusAndBackground.fxml"));
        Parent root = fxmlLoader.load();
        viewStimulusAndBackgroundController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    /* FIXATION AND OTHER SCENES */
    public static void setSceneFixationAndOther() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/fixationandother/ViewFixationAndOther.fxml"));
        Parent root = fxmlLoader.load();
        viewFixationAndOtherController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneFixationAndOtherMonitorSettingsBlindspot() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/fixationandother/ViewFixMonitorBlindspot.fxml"));
        Parent root = fxmlLoader.load();
        viewFixMonitorBlindspotController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneFixationAndOtherMonitorSettingsFixPointChange() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/fixationandother/ViewFixMonitorFixPointChange.fxml"));
        Parent root = fxmlLoader.load();
        viewFixMonitorFixPointChangeController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneFixationAndOtherMonitorSettingsBoth() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/fixationandother/ViewFixMonitorBoth.fxml"));
        Parent root = fxmlLoader.load();
        viewFixMonitorBothController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    public static void setSceneBasicProcedureSettings() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/fixationandother/ViewProcedureBasic.fxml"));
        Parent root = fxmlLoader.load();
        viewProcedureBasicController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        getWindow().setScene(scene);
    }

    /* PROCEDURE SCENES */
    public static void setSceneProcedure() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/procedure/ViewProcedurePreview.fxml"));
        Parent root = fxmlLoader.load();
        viewProcedurePreviewController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        sceneProcedure = scene;
        getWindow().setScene(scene);
    }

    public static void setExistingSceneProcedure() {
        getWindow().setScene(sceneProcedure);
    }

    public static void setSceneProcedureShowResults() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("view/procedure/ViewProcedureResults.fxml"));
        Parent root = fxmlLoader.load();
        viewProcedureResultsController = fxmlLoader.getController();
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

    public static ViewStartWindowController getViewStartWindowController() {
        return viewStartWindowController;
    }

    public static ViewNewPatientController getViewNewPatientController() {
        return viewNewPatientController;
    }

    public static ViewExistingPatientController getViewExistingPatientController() {
        return viewExistingPatientController;
    }

    public static ViewEditPatientController getViewEditPatientController() {
        return viewEditPatientController;
    }

    public static ViewPatientResultsController getViewPatientResultsController() {
        return viewPatientResultsController;
    }

    public static ViewPatientResultsInfoController getViewPatientResultsInfoController() {
        return viewPatientResultsInfoController;
    }

    public static ViewPatientResultsMapController getViewPatientResultsMapController() {
        return viewPatientResultsMapController;
    }

    public static ViewScreenAndLumScaleController getViewScreenAndLumScaleController() {
        return viewScreenAndLumScaleController;
    }

    public static ViewNewLumScaleController getViewNewLumScaleController() {
        return viewNewLumScaleController;
    }

    public static ViewExistingLumScaleController getViewExistingLumScaleController() {
        return viewExistingLumScaleController;
    }

    public static ViewEditLumScaleController getViewEditLumScaleController() {
        return viewEditLumScaleController;
    }

    public static ViewFitLumScaleController getViewFitLumScaleController() {
        return viewFitLumScaleController;
    }

    public static ViewStimulusAndBackgroundController getViewStimulusAndBackgroundController() {
        return viewStimulusAndBackgroundController;
    }

    public static ViewFixationAndOtherController getViewFixationAndOtherController() {
        return viewFixationAndOtherController;
    }

    public static ViewFixMonitorBlindspotController getViewFixMonitorBlindspotController() {
        return viewFixMonitorBlindspotController;
    }

    public static ViewFixMonitorFixPointChangeController getViewFixMonitorFixPointChangeController() {
        return viewFixMonitorFixPointChangeController;
    }

    public static ViewFixMonitorBothController getViewFixMonitorBothController() {
        return viewFixMonitorBothController;
    }

    public static ViewProcedureBasicController getViewProcedureBasicController() {
        return viewProcedureBasicController;
    }

    public static ViewProcedurePreviewController getViewProcedurePreviewController() {
        return viewProcedurePreviewController;
    }

    public static ViewProcedureResultsController getViewProcedureResultsController() {
        return viewProcedureResultsController;
    }
}
