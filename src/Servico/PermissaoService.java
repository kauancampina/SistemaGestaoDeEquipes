package Servico;

import Modelo.PerfilUsuario;
import Modelo.Tarefa;
import Modelo.Usuario;

public class PermissaoService {

    private final Sessao sessao;

    public PermissaoService(Sessao sessao) {
        this.sessao = sessao;
    }

    public void exigirAutenticacao() {
        if (!sessao.estaAutenticado()) {
            throw new SecurityException("E necessario realizar login para acessar esta funcionalidade.");
        }
    }

    public void exigirAdministrador() {
        exigirAutenticacao();
        if (sessao.getUsuarioLogado().getPerfil() != PerfilUsuario.ADMINISTRADOR) {
            throw new SecurityException("Acesso permitido apenas para administrador.");
        }
    }

    public void exigirGestao() {
        exigirAutenticacao();
        // A regra de acesso fica centralizada para evitar verificacoes duplicadas nos menus e controllers.
        PerfilUsuario perfil = sessao.getUsuarioLogado().getPerfil();
        if (perfil != PerfilUsuario.ADMINISTRADOR && perfil != PerfilUsuario.GERENTE) {
            throw new SecurityException("Acesso permitido apenas para administrador ou gerente.");
        }
    }

    public void exigirPodeAtualizarTarefa(Tarefa tarefa) {
        exigirAutenticacao();
        Usuario usuario = sessao.getUsuarioLogado();
        if (usuario.getPerfil() == PerfilUsuario.ADMINISTRADOR || usuario.getPerfil() == PerfilUsuario.GERENTE) {
            return;
        }
        if (tarefa == null || tarefa.getResponsavel() == null || tarefa.getResponsavel().getId() != usuario.getId()) {
            throw new SecurityException("Colaboradores so podem atualizar tarefas atribuidas a eles.");
        }
    }
}
