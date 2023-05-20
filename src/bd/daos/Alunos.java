package bd.daos;

import java.sql.SQLException;

import bd.BDSQLServer;
import bd.dbos.Aluno;

public class Alunos {

    public void cadastrarAluno(Aluno objAlunoDbo) throws Exception {

        try {
            String sql = "INSERT INTO Alunos " +
                    "(Ra, Nome, CursoId, Telefone, Email, Cep, NumeroEndereco, Complemento_Endereco) " +
                    "VALUES (?,?,?,?,?,?,?,?)";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, objAlunoDbo.getRa());
            BDSQLServer.COMANDO.setString(2, objAlunoDbo.getNome());
            BDSQLServer.COMANDO.setInt(3, objAlunoDbo.getIdCurso());
            BDSQLServer.COMANDO.setString(4, objAlunoDbo.getTelefone());
            BDSQLServer.COMANDO.setString(5, objAlunoDbo.getEmail());
            BDSQLServer.COMANDO.setString(6, objAlunoDbo.getCep());
            BDSQLServer.COMANDO.setString(7, objAlunoDbo.getNumeroEndereco());
            BDSQLServer.COMANDO.setString(8, objAlunoDbo.getComplemento());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();

        } catch (SQLException e) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao inserir Aluno");
        }
    }

}
