package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    public void switchToHome(ActionEvent event) {
        App.changeScreen("home");
    }

    @FXML
    public void switchToCreate(ActionEvent event) {
        App.changeScreen("create");
    }

    @FXML
    void switchToRead(ActionEvent event) {
        App.changeScreen("read");
    }

    @FXML
    void switchToUpdate(ActionEvent event) {
        App.changeScreen("update");
    }

    @FXML
    void switchToDelete(ActionEvent event) {
        App.changeScreen("delete");
    }

}