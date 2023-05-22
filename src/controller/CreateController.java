package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bd.core.MeuResultSet;
import bd.daos.Alunos;
import bd.daos.Cursos;
import bd.daos.RaValidator;
import bd.dbos.Aluno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.ViaCepResponse;
import util.ViaCepService;

public class CreateController {

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
    public void switchToHome(ActionEvent event) throws IOException {
        App.changeScreen("home");
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
    private TextField txtNumeroEndereco;
    @FXML
    private TextArea txtComplemento;

    public void validaRa(int Ra) throws Exception {
        if (txtRa.getText().length() != 5) {
            throw new Exception("Informe um Ra que contenha apenas 5 caracteres.");
        }

        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int doisUltimosDigitosAno = anoAtual % 100;
        int doisPrimeirosDigitosRa = Ra / 1000;

        if (doisPrimeirosDigitosRa > doisUltimosDigitosAno) {
            throw new Exception("Ra não condiz com o ano atual!");
        }

        if(RaValidator.isRaAlreadyExists(Ra)) {
            throw new Exception("RA já existe no banco de dados.");
        }
    }

    public void validaTelefone(String Telefone) throws Exception {
        boolean TelefoneIsValid = false;

        if (Telefone != null && Telefone.length() == 11) {
            String expressao = "^\\d{2}9\\d{8}$";
            TelefoneIsValid = Telefone.matches(expressao);
        }

        if (!TelefoneIsValid) {
            throw new Exception("Telefone inválido!");
        }
    }

    public void validaEmail(String Email) throws Exception {
        boolean emailIsValid = false;

        if (Email != null && Email.length() > 0) {
            String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(Email);
            if (matcher.matches()) {
                emailIsValid = true;
            }
        }

        if (!emailIsValid) {
            throw new Exception("Email inválido!");
        }
    }

    public void validaCep(String Cep) throws Exception {
        if (Cep.length() != 8) {
            throw new Exception("Informe um Cep válido que contenha apenas 8 caracteres.");
        }

        ViaCepResponse endereco = ViaCepService.buscaEnderecoPelo(Cep);
        if (endereco.getCep() == null || endereco.getCep().isEmpty()) {
            throw new Exception("O CEP informado é inválido ou não existe!");
        }
    }

    public void btnCadastrarAction(ActionEvent event) throws Exception {
        CadastrarAluno();
    }

    private void CadastrarAluno() {
        try {
            int Ra = Integer.parseInt(txtRa.getText());
            String Nome = txtNome.getText();
            int CursoId = idCurso.get(cbxCurso.getSelectionModel().getSelectedIndex());
            String Telefone = txtTelefone.getText();
            String Email = txtEmail.getText();
            String Cep = txtCep.getText();
            String NumeroEndereco = txtNumeroEndereco.getText();
            String Complemento = txtComplemento.getText();

            validaRa(Ra);
            validaCep(Cep);
            validaEmail(Email);
            validaTelefone(Telefone);

            if (Ra == 0 || Nome.isEmpty() || Telefone.isEmpty() || Email.isEmpty() || Cep.isEmpty()
                    || NumeroEndereco.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("Campos obrigatórios não preenchidos!");
                alert.setContentText("Preencnha todos os campos obrigatórios para efetuar o cadastro.");
                alert.showAndWait();
                return;
            }

            Aluno objAlunoDbo = new Aluno();
            objAlunoDbo.setRa(Ra);
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
            objAlunoDao.cadastrarAluno(objAlunoDbo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Aluno cadastrado com sucesso!");
            alert.setContentText("O aluno " + Nome + " foi cadastrado com sucesso.");
            alert.showAndWait();

            txtRa.clear();
            txtNome.clear();
            txtTelefone.clear();
            txtEmail.clear();
            txtCep.clear();
            txtNumeroEndereco.clear();
            txtComplemento.clear();

        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao selecionar um item");
            alert.setContentText("Selecione um curso para cadastrar o aluno!");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("RA inválido!");
            alert.setContentText("Por favor, insira um RA válido de 5 caracteres.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao cadastrar aluno!");
            alert.setContentText("Ocorreu um erro ao cadastrar o aluno: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

}