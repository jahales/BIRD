package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller for Simulation view.
 * User inputs appropriate files for simulator.
 * Call run function to run simulation.
 * 
 * @author Brian Woodruff
 *
 */
public class SimulationController {

    @FXML
    private TextField engineFile;

    @FXML
    private TextField length;

    @FXML
    private TextField asimuthAngle;

    @FXML
    private TextField polarAngle;

    @FXML
    private TextField rocketFile;

    @FXML
    private TextField AtmosphereFile;

    /**
     * Run simulation.
     * 
     * @param event
     */
    @FXML
    void run(ActionEvent event) {

    }
}
