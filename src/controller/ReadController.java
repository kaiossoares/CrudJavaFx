package controller;

import java.io.IOException;

import bd.daos.RaValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ReadController {

    public void initialize() {
        txtRa.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRa.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        App.changeScreen("home");
    }

    @FXML
    private TextField txtRa;

    public void validaRa(int Ra) throws Exception {
        if (txtRa.getText().length() != 5) {
            throw new Exception("Informe um Ra que contenha apenas 5 caracteres.");
        }

        if(RaValidator.isRaNotExists(Ra)) {
            throw new Exception("RA já existe no banco de dados.");
        }
    }

    @FXML
    public void btnBuscarAction(ActionEvent event) {
        buscarAluno();
    }
    
    private void buscarAluno() {
        try {
            int Ra = Integer.parseInt(txtRa.getText());
            validaRa(Ra);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao buscar aluno!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

}