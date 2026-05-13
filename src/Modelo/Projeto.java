package Modelo;

public class Projeto {

    private int id;
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private String status;

    private Usuario gerente;

    public Projeto(int id,
                   String nome,
                   String descricao,
                   String dataInicio,
                   String dataFim,
                   String status,
                   Usuario gerente) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.gerente = gerente;
    }

    public String getNome() {
        return nome;
    }

    public Usuario getGerente() {
        return gerente;
    }

    @Override
    public String toString() {

        return "\nProjeto: " + nome +
                "\nDescrição: " + descricao +
                "\nStatus: " + status +
                "\nGerente: " + gerente.getNomeCompleto();
    }
}