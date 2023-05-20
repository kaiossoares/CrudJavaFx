package util;

public class ViaCepResponse {
    private String cep;
    private String logradouro;
    private String bairro;
    private String complemento;
    private String localidade;
    private String uf;

    public void setCep(String Cep) {
        this.cep = Cep;
    }

    public void setLogradouro(String Logradouro) {
        this.cep = Logradouro;
    }

    public void setBairro(String Bairro) {
        this.bairro = Bairro;
    }

    public void setComplemento(String Complemento) {
        this.complemento = Complemento;
    }

    public void setCidade(String Cidade) {
        this.localidade = Cidade;
    }

    public void setUf(String Uf) {
        this.uf = Uf;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

    @Override
    public String toString() {
        return "Endereco{" + "cep=" + cep + ", logradouro=" + logradouro + ", bairro=" + bairro +
                ", complemento=" + complemento + ", localidade=" + localidade + ", uf=" + uf + '}';
    }
}
