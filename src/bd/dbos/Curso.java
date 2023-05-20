package bd.dbos;

public class Curso implements Cloneable {

    private int id;
    private String nome;
    private String periodoCurso;

    public void setId(int id) throws Exception {
        if (id <= 0)
            throw new Exception("Id invalido");

        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPeriodoCurso(String periodoCurso) {
        this.periodoCurso = periodoCurso;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getPeriodoCurso() {
        return this.periodoCurso;
    }

    public Curso(Curso modelo) throws Exception {
        this.id = modelo.id;
        this.nome = modelo.nome;
        this.periodoCurso = modelo.periodoCurso;
    }

    public Object clone() {
        Curso ret = null;

        try {
            ret = new Curso(this);
        } catch (Exception e) {
        }

        return ret;
    }
}