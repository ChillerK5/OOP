package ru.nsu.kbagryantsev;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * HelloApplication.
 */
public final class HelloApplication extends Application {
    @Override
    public void start(final Stage stage) throws IOException {
        URL url = HelloApplication.class.getResource("hello-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main.
     *
     * @param args args
     */
    public static void main(final String[] args) {
        launch();
    }
}
