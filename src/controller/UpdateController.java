package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UpdateController {
    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        App.changeScreen("home");
    }
}