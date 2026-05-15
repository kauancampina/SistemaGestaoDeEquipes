package Modelo;

public class Tarefa {

    private String titulo;
    private String descricao;
    private String prioridade;
    private String status;
    private Usuario responsavel;

    public Tarefa(String titulo, String descricao,
                  String prioridade, String status,
                  Usuario responsavel) {

        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
        this.responsavel = responsavel;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return "Tarefa: " + titulo +
                " | Prioridade: " + prioridade +
                " | Status: " + status +
                " | Responsável: " + responsavel.getNomeCompleto();
    }
}
aa
