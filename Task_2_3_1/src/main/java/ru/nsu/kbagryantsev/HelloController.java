package ru.nsu.kbagryantsev;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * HelloController.
 */
public class HelloController {
    /**
     * welcomeText.
     */
    @FXML
    private Label welcomeText;

    @FXML
    protected final void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
