package controller;

import java.io.IOException;
import java.util.ArrayList;

import bd.daos.Alunos;
import bd.daos.Cursos;
import bd.daos.RaValidator;
import bd.dbos.Aluno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.ViaCepService;
import util.ViaCepResponse;
import bd.core.MeuResultSet;

public class UpdateController {

    public void initialize() {
        preencherComboBox(cbxCurso);

        txtRa.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRa.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtNome.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[\\p{L} ]*")) {
                txtNome.setText(oldValue);
            }
        });

        txtTelefone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTelefone.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtCep.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCep.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtNumeroEndereco.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtNumeroEndereco.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private TextField txtRa;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCep;
    @FXML
    private Label labelLogradouro;
    @FXML
    private Label labelBairro;
    @FXML
    private TextField txtNumeroEndereco;
    @FXML
    private TextArea txtComplemento;
    @FXML
    private Label labelCidadeUf;

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        App.changeScreen("home");
    }

    public void validaRa(int Ra) throws Exception {
        if (RaValidator.isRaNotExists(Ra)) {
            throw new Exception("Ra não existe no banco de dados.");
        }
    }

    @FXML
    private ComboBox<String> cbxCurso;

    ArrayList<Integer> idCurso = new ArrayList<Integer>();

    public void preencherComboBox(ComboBox<String> comboBox) {
        idCurso.clear();
        comboBox.getItems().clear();
        try {
            MeuResultSet resultado = Cursos.listarCurso();

            while (resultado.next()) {
                idCurso.add(resultado.getInt("id"));
                comboBox.getItems().add(resultado.getString("curso_periodo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selecionarCursoPorRA(int ra) throws Exception {
        Alunos alunos = new Alunos();
        String nomeCurso = alunos.obterNomeCursoPorAluno(ra);
        cbxCurso.getSelectionModel().select(nomeCurso);
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
            txtNome.setText(aluno.getNome());
            txtTelefone.setText(aluno.getTelefone());
            txtEmail.setText(aluno.getEmail());
            txtCep.setText(aluno.getCep());
            txtNumeroEndereco.setText(aluno.getNumeroEndereco());
            txtComplemento.setText(aluno.getComplemento());

            ViaCepResponse endereco = ViaCepService.buscaEnderecoPelo(aluno.getCep());

            labelLogradouro.setText(endereco.getLogradouro());
            labelBairro.setText(endereco.getBairro());
            labelCidadeUf.setText(endereco.getCidade() + ", " + endereco.getUf());

            selecionarCursoPorRA(Ra);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao buscar aluno!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void btnSalvarAction(ActionEvent event) throws Exception {
        salvarAlteracao();
    }

    public void salvarAlteracao() throws Exception {
        try {
            CreateController createcontroller = new CreateController();

            String Telefone = txtTelefone.getText();
            createcontroller.validaTelefone(Telefone);

            String Email = txtEmail.getText();
            createcontroller.validaEmail(Email);

            String Cep = txtCep.getText();
            createcontroller.validaCep(Cep);

            String Nome = txtNome.getText();
            int CursoId = idCurso.get(cbxCurso.getSelectionModel().getSelectedIndex());
            String NumeroEndereco = txtNumeroEndereco.getText();
            String Complemento = txtComplemento.getText();

            Aluno objAlunoDbo = new Aluno();
            objAlunoDbo.setRa(Integer.parseInt(txtRa.getText()));
            objAlunoDbo.setNome(Nome);
            objAlunoDbo.setIdCurso(CursoId);
            objAlunoDbo.setTelefone(Telefone);
            objAlunoDbo.setEmail(Email);
            objAlunoDbo.setCep(Cep);
            objAlunoDbo.setNumeroEndereco(NumeroEndereco);
            objAlunoDbo.setComplemento(Complemento);

            if (objAlunoDbo == null || objAlunoDbo.getNome() == null || objAlunoDbo.getTelefone() == null
                    || objAlunoDbo.getEmail() == null || objAlunoDbo.getNumeroEndereco() == null
                    || objAlunoDbo.getComplemento() == null) {
                throw new Exception("Aluno com valor nulo em uma de suas propriedades");
            }

            Alunos objAlunoDao = new Alunos();
            objAlunoDao.alterarAluno(objAlunoDbo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aluno alterado com sucesso!");
            alert.setContentText("O aluno " + Nome + " foi alterado com sucesso.");
            alert.showAndWait();

            txtRa.clear();
            txtNome.clear();
            txtTelefone.clear();
            txtEmail.clear();
            txtCep.clear();
            txtNumeroEndereco.clear();
            txtComplemento.clear();
            cbxCurso.getSelectionModel().clearSelection();
        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao selecionar um item");
            alert.setContentText("Selecione um curso para alterar o aluno!");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao alterar aluno!");
            alert.setContentText("Ocorreu um erro ao alterar o aluno: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

}
