package bd.daos;

import java.sql.SQLException;

import bd.BDSQLServer;
import bd.core.MeuResultSet;
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

    public Aluno getAluno(int Ra) throws Exception {
        Aluno aluno = null;
        try {
            String sql = "SELECT * FROM Alunos WHERE Ra = ?";
            BDSQLServer.COMANDO.prepareStatement(sql);
            BDSQLServer.COMANDO.setInt(1, Ra);
            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            if (resultado.next()) {
                aluno = new Aluno();
                aluno.setRa(resultado.getInt("Ra"));
                aluno.setNome(resultado.getString("Nome"));
                aluno.setIdCurso(resultado.getInt("CursoId"));
                aluno.setTelefone(resultado.getString("Telefone"));
                aluno.setEmail(resultado.getString("Email"));
                aluno.setCep(resultado.getString("Cep"));
                aluno.setNumeroEndereco(resultado.getString("NumeroEndereco"));
                aluno.setComplemento(resultado.getString("Complemento_Endereco"));
            }
        } catch (SQLException e) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao buscar Aluno");
        }

        return aluno;
    }

    public String obterNomeCursoPorAluno(int Ra) throws Exception {
        String nomeCurso = null;

        try {
            String sql = "SELECT CONCAT(Cursos.NomeCurso, ' (', Cursos.PeriodoCurso, ')')" +
                    "FROM Alunos INNER JOIN Cursos ON Alunos.CursoId = Cursos.id WHERE Alunos.Ra = ?;";
            BDSQLServer.COMANDO.prepareStatement(sql);
            BDSQLServer.COMANDO.setInt(1, Ra);
            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            if (resultado.next()) {
                nomeCurso = resultado.getString(1);
            }
        } catch (SQLException e) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao buscar Aluno");
        }

        return nomeCurso;
    }

    public void alterarAluno(Aluno aluno) throws Exception {
        try {
            System.out.println("Aluno a ser atualizado: " + aluno.toString());
            String sql = "UPDATE Alunos SET Nome=?, CursoId=?, Telefone=?, Email=?, Cep=?," +
            "NumeroEndereco=?, Complemento_Endereco=? WHERE Ra=?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setString(1, aluno.getNome());
            BDSQLServer.COMANDO.setInt(2, aluno.getIdCurso());
            BDSQLServer.COMANDO.setString(3, aluno.getTelefone());
            BDSQLServer.COMANDO.setString(4, aluno.getEmail());
            BDSQLServer.COMANDO.setString(5, aluno.getCep());
            BDSQLServer.COMANDO.setString(6, aluno.getNumeroEndereco());
            BDSQLServer.COMANDO.setString(7, aluno.getComplemento());
            BDSQLServer.COMANDO.setInt(8, aluno.getRa());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao atualizar aluno:" + erro.getMessage());
        }
    }

}
