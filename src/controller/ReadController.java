package controller;

import java.io.IOException;

import bd.daos.Alunos;
import bd.dbos.Aluno;
import bd.daos.RaValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.ViaCepService;
import util.ViaCepResponse;

public class ReadController {

    public void initialize() {
        txtRa.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRa.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private TextField txtRa;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelTelefone;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelCurso;
    @FXML
    private Label labelCep;
    @FXML
    private Label labelLogradouro;
    @FXML
    private Label labelBairro;
    @FXML
    private Label labelCidade;
    @FXML
    private Label labelUf;
    @FXML
    private Label labelNumeroEndereco;
    @FXML
    private TextArea txtComplemento;

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        txtRa.clear();
        labelNome.setText("");
        labelTelefone.setText("");
        labelEmail.setText("");
        labelCurso.setText("");
        labelCep.setText("");
        labelLogradouro.setText("");
        labelBairro.setText("");
        labelCidade.setText("");
        labelUf.setText("");
        labelNumeroEndereco.setText("");
        txtComplemento.clear();
        App.changeScreen("home");
    }

    public void validaRa(int Ra) throws Exception {
        if (RaValidator.isRaNotExists(Ra)) {
            throw new Exception("Ra não existe no banco de dados.");
        }
    }

    public String formatarTelefone(String telefone) {
        telefone = telefone.replaceAll("[^0-9]", "");

        String ddd = telefone.substring(0, 2);
        String numeroPrincipal = telefone.substring(2, 7);
        String numeroAdicional = telefone.substring(7);

        telefone = "(" + ddd + ") " + numeroPrincipal + " " + numeroAdicional;

        return telefone;
    }

    public String formatarCEP(String cep) {
        cep = cep.replaceAll("[^0-9]", "");

        String parte1 = cep.substring(0, 5);
        String parte2 = cep.substring(5);

        cep = parte1 + " - " + parte2;

        return cep;
    }

    @FXML
    public void btnBuscarAction(ActionEvent event) {
        buscarAluno();
    }

    private void buscarAluno() {
        try {
            String raText = txtRa.getText();

            if (raText.isEmpty() || raText.length() != 5) {
                throw new Exception("Informe um Ra que contenha apenas 5 caracteres.");
            }

            int Ra = Integer.parseInt(txtRa.getText());
            validaRa(Ra);

            Alunos alunos = new Alunos();
            Aluno aluno = alunos.getAluno(Ra);
            labelNome.setText(aluno.getNome());
            labelTelefone.setText(formatarTelefone(aluno.getTelefone()));
            labelEmail.setText(aluno.getEmail());
            labelCep.setText(formatarCEP(aluno.getCep()));
            labelNumeroEndereco.setText(aluno.getNumeroEndereco());

            String nomeCurso = alunos.obterNomeCursoPorAluno(Ra);
            labelCurso.setText(nomeCurso);

            ViaCepResponse endereco = ViaCepService.buscaEnderecoPelo(aluno.getCep());

            labelLogradouro.setText(endereco.getLogradouro());
            labelBairro.setText(endereco.getBairro());
            txtComplemento.setText(aluno.getComplemento() +" "+ endereco.getComplemento());
            labelCidade.setText(endereco.getCidade());
            labelUf.setText(endereco.getUf());

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