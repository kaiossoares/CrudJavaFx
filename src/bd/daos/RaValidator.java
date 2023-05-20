package bd.daos;

import java.sql.SQLException;
import javax.swing.JOptionPane;

import bd.BDSQLServer;
import bd.core.MeuResultSet;

public class RaValidator {
    public static boolean isRaAlreadyExists(int Ra) throws Exception {
        try {
            String sql = "SELECT COUNT(*) FROM alunos WHERE ra = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);
            BDSQLServer.COMANDO.setInt(1, Ra);
            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            if (resultado.next()) {
                int count = resultado.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Procurar Aluno " + e.getMessage());
        }
        return false;
    }

    public static boolean isRaNotExists(int Ra) throws Exception {
        try {
            String sql = "SELECT COUNT(*) FROM alunos WHERE ra = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);
            BDSQLServer.COMANDO.setInt(1, Ra);
            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            if (resultado.next()) {
                int count = resultado.getInt(1);
                return count == 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Procurar Aluno " + e.getMessage());
        }
        return true;
    }
}