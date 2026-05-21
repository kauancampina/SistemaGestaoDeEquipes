package Controle;

import Modelo.Equipe;
import Modelo.Projeto;
import Modelo.StatusProjeto;
import Servico.PermissaoService;
import Servico.ServicoProjeto;

import java.util.List;
import java.util.Optional;

public class ControleProjeto {

    private final ServicoProjeto servicoProjeto;
    private final PermissaoService permissaoService;

    public ControleProjeto(ServicoProjeto servicoProjeto, PermissaoService permissaoService) {
        this.servicoProjeto = servicoProjeto;
        this.permissaoService = permissaoService;
    }

    public void cadastrarProjeto(Projeto projeto) {
        permissaoService.exigirGestao();
        servicoProjeto.cadastrarProjeto(projeto);
    }

    public void editarProjeto(Projeto projeto) {
        permissaoService.exigirGestao();
        servicoProjeto.editarProjeto(projeto);
    }

    public boolean removerProjeto(int id) {
        permissaoService.exigirGestao();
        return servicoProjeto.removerProjeto(id);
    }

    public Optional<Projeto> buscarPorId(int id) {
        permissaoService.exigirAutenticacao();
        return servicoProjeto.buscarPorId(id);
    }

    public List<Projeto> buscarPorNome(String nome) {
        permissaoService.exigirAutenticacao();
        return servicoProjeto.buscarPorNome(nome);
    }

    public List<Projeto> listarProjetos() {
        permissaoService.exigirAutenticacao();
        return servicoProjeto.listarProjetos();
    }

    public void adicionarEquipeAoProjeto(Projeto projeto, Equipe equipe) {
        permissaoService.exigirGestao();
        servicoProjeto.adicionarEquipeAoProjeto(projeto, equipe);
    }

    public void atualizarStatus(Projeto projeto, StatusProjeto status) {
        permissaoService.exigirGestao();
        servicoProjeto.atualizarStatus(projeto, status);
    }
}
