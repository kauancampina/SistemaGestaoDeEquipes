package Modelo;

public class Projeto {

    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataTermino;
    private String status;
    private Usuario gerente;

    public Projeto(String nome, String descricao, String dataInicio,
                   String dataTermino, String status, Usuario gerente) {

        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.status = status;
        this.gerente = gerente;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public String getStatus() {
        return status;
    }

    public Usuario getGerente() {
        return gerente;
    }

    @Override
    public String toString() {
        return "Projeto: " + nome +
                " | Status: " + status +
                " | Gerente: " + gerente.getNomeCompleto();
    }
}
aa
