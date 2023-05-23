package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bd.core.MeuResultSet;
import bd.daos.Alunos;
import bd.daos.Cursos;
import bd.dbos.Aluno;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DeleteController {
    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        App.changeScreen("home");
    }

    @FXML
    private TableView<Aluno> tableAluno;
    @FXML
    private TableColumn<Aluno, Integer> colRa;
    @FXML
    private TableColumn<Aluno, String> colNome;
    @FXML
    private TableColumn<Aluno, String> colTelefone;
    @FXML
    private TableColumn<Aluno, String> colEmail;

    public void initialize() {
        preencherComboBox(cbxCurso);

        colRa.setCellValueFactory(new PropertyValueFactory<>("ra"));

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableAluno.getColumns().addAll(colRa, colNome, colTelefone, colEmail);
    }

    public String formatarTelefone(String telefone) {
        telefone = telefone.replaceAll("[^0-9]", "");

        String ddd = telefone.substring(0, 2);
        String numeroPrincipal = telefone.substring(2, 7);
        String numeroAdicional = telefone.substring(7);

        telefone = "(" + ddd + ") " + numeroPrincipal + " " + numeroAdicional;

        return telefone;
    }

    @FXML
    private ComboBox<String> cbxCurso;

    ArrayList<Integer> idCurso = new ArrayList<Integer>();

    public void preencherComboBox(ComboBox<String> comboBox) {
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
    void comboBoxSelection(ActionEvent event) {
        String cursoSelecionado = cbxCurso.getValue();

        if (cursoSelecionado != null) {
            int cursoId = idCurso.get(cbxCurso.getSelectionModel().getSelectedIndex());
            ArrayList<Aluno> alunos = obterAlunosDoBancoDeDados(cursoId);
    
            tableAluno.setItems(FXCollections.observableArrayList(alunos));
        }
    }

    private ArrayList<Aluno> obterAlunosDoBancoDeDados(int CursoId) {
        ArrayList<Aluno> alunos = new ArrayList<>();

        try {
            Alunos alunosDao = new Alunos();

            List<Aluno> alunosDoCurso = alunosDao.getAlunoPeloCurso(CursoId);

            alunos.addAll(alunosDoCurso);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return alunos;
    }

    @FXML
    void btnExcluirAction(ActionEvent event) {

    }
}