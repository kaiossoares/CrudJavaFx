package bd.daos;

import java.sql.*;
import javax.swing.JOptionPane;
import bd.*;
import bd.core.*;

public class Cursos {

    public static MeuResultSet listarCurso() throws Exception {
        try {
            String sql = "SELECT id, NomeCurso + ' (' + PeriodoCurso + ')' AS curso_periodo FROM Cursos;";

            BDSQLServer.COMANDO.prepareStatement(sql);
            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();
            return resultado;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lista Curso" + e.getMessage());
            return null;
        }
    }

}