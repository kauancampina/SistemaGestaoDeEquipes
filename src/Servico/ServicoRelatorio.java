package Servico;

import Modelo.PerfilUsuario;
import Modelo.StatusProjeto;
import Modelo.StatusTarefa;
import Modelo.Usuario;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ServicoRelatorio {

    private final ServicoUsuario servicoUsuario;
    private final ServicoProjeto servicoProjeto;
    private final ServicoEquipe servicoEquipe;
    private final ServicoTarefa servicoTarefa;

    public ServicoRelatorio(ServicoUsuario servicoUsuario, ServicoProjeto servicoProjeto,
                            ServicoEquipe servicoEquipe, ServicoTarefa servicoTarefa) {
        this.servicoUsuario = servicoUsuario;
        this.servicoProjeto = servicoProjeto;
        this.servicoEquipe = servicoEquipe;
        this.servicoTarefa = servicoTarefa;
    }

    public String gerarResumoGeral() {
        long projetosConcluidos = servicoProjeto.listarProjetos().stream()
                .filter(projeto -> projeto.getStatus() == StatusProjeto.CONCLUIDO)
                .count();
        long projetosEmAndamento = servicoProjeto.listarProjetos().stream()
                .filter(projeto -> projeto.getStatus() == StatusProjeto.EM_ANDAMENTO)
                .count();
        long tarefasPendentes = servicoTarefa.listarTarefas().stream()
                .filter(tarefa -> tarefa.getStatus() == StatusTarefa.PENDENTE)
                .count();
        long tarefasConcluidas = servicoTarefa.listarTarefas().stream()
                .filter(tarefa -> tarefa.getStatus() == StatusTarefa.CONCLUIDA)
                .count();
        long equipesAtivas = servicoEquipe.listarEquipes().stream()
                .filter(equipe -> equipe.isAtiva())
                .count();

        String usuariosPorPerfil = Arrays.stream(PerfilUsuario.values())
                .map(perfil -> perfil + ": " + contarUsuariosPorPerfil(perfil))
                .collect(Collectors.joining(" | "));

        return "Total de projetos: " + servicoProjeto.listarProjetos().size() + "\n" +
                "Projetos concluidos: " + projetosConcluidos + "\n" +
                "Projetos em andamento: " + projetosEmAndamento + "\n" +
                "Tarefas pendentes: " + tarefasPendentes + "\n" +
                "Tarefas concluidas: " + tarefasConcluidas + "\n" +
                "Equipes ativas: " + equipesAtivas + "\n" +
                "Usuarios por perfil: " + usuariosPorPerfil;
    }

    private long contarUsuariosPorPerfil(PerfilUsuario perfil) {
        return servicoUsuario.listarUsuarios().stream()
                .filter((Usuario usuario) -> usuario.getPerfil() == perfil)
                .count();
    }
}
