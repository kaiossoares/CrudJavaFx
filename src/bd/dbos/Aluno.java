package bd.dbos;

public class Aluno implements Cloneable {
    private int Ra;
    private String Nome;
    private int CursoId;
    private String Telefone;
    private String Email;
    private String Cep;
    private String NumeroEndereco;
    private String Complemento_Endereco;

    public void setRa(int Ra) throws Exception {
        if (Ra <= 0)
            throw new Exception("Ra inválido");

        this.Ra = Ra;
    }

    public void setNome(String Nome) throws Exception {
        if (Nome == null || Nome.equals(""))
            throw new Exception("Nome não fornecido");

        this.Nome = Nome;
    }

    public void setIdCurso(int CursoId) throws Exception {
        if (CursoId <= 0)
            throw new Exception("CursoId invalido");

        this.CursoId = CursoId;
    }

    public void setTelefone(String Telefone) throws Exception {
        if (Telefone == null || Telefone.equals(""))
            throw new Exception("Telefone não fornecido");

        this.Telefone = Telefone;
    }

    public void setEmail(String Email) throws Exception {
        if (Email == null || Email.equals(""))
            throw new Exception("Email não fornecido");

        this.Email = Email;
    }

    public void setCep(String Cep) throws Exception {
        this.Cep = Cep;
    }

    public void setNumeroEndereco(String NumeroEndereco) throws Exception {
        if (NumeroEndereco == null || NumeroEndereco.equals(""))
            throw new Exception("NumeroEndereco não fornecido");

        this.NumeroEndereco = NumeroEndereco;
    }

    public void setComplemento(String Complemento) throws Exception {
        if (Complemento == null)
            throw new Exception("Complemento nulo.");

        this.Complemento_Endereco = Complemento;
    }

    public int getRa() {
        return this.Ra;
    }

    public String getNome() {
        return this.Nome;
    }

    public int getIdCurso() {
        return this.CursoId;
    }

    public String getTelefone() {
        return this.Telefone;
    }

    public String getEmail() {
        return this.Email;
    }

    public String getCep() {
        return this.Cep;
    }

    public String getNumeroEndereco() {
        return this.NumeroEndereco;
    }

    public String getComplemento() {
        return this.Complemento_Endereco;
    }

    public Aluno() {
        this.Ra = 0;
        this.Nome = "";
        this.CursoId = 0;
        this.Telefone = "";
        this.Email = "";
        this.Cep = "";
        this.NumeroEndereco = "";
        this.Complemento_Endereco = "";
    }

    @Override
    public Aluno clone() throws CloneNotSupportedException {
        Aluno clone = (Aluno) super.clone();
        return clone;
    }

    @Override
    public String toString() {
        return "Aluno [Ra=" + Ra + ", Nome=" + Nome + ", CursoId=" + CursoId + ", Telefone=" + Telefone + ", Email="
                + Email
                + ", Cep=" + Cep + ", NumeroEndereco=" + NumeroEndereco + ", Complemento_Endereco="
                + Complemento_Endereco
                + "]";
    }

}
