package Controle;

import Modelo.Equipe;
import Modelo.Projeto;
import Modelo.Usuario;
import Servico.PermissaoService;
import Servico.ServicoEquipe;

import java.util.List;
import java.util.Optional;

public class ControleEquipe {

    private final ServicoEquipe servicoEquipe;
    private final PermissaoService permissaoService;

    public ControleEquipe(ServicoEquipe servicoEquipe, PermissaoService permissaoService) {
        this.servicoEquipe = servicoEquipe;
        this.permissaoService = permissaoService;
    }

    public void cadastrarEquipe(Equipe equipe) {
        permissaoService.exigirGestao();
        servicoEquipe.cadastrarEquipe(equipe);
    }

    public void editarEquipe(Equipe equipe) {
        permissaoService.exigirGestao();
        servicoEquipe.editarEquipe(equipe);
    }

    public boolean removerEquipe(int id) {
        permissaoService.exigirGestao();
        return servicoEquipe.removerEquipe(id);
    }

    public Optional<Equipe> buscarPorId(int id) {
        permissaoService.exigirAutenticacao();
        return servicoEquipe.buscarPorId(id);
    }

    public List<Equipe> buscarPorNome(String nome) {
        permissaoService.exigirAutenticacao();
        return servicoEquipe.buscarPorNome(nome);
    }

    public List<Equipe> listarEquipes() {
        permissaoService.exigirAutenticacao();
        return servicoEquipe.listarEquipes();
    }

    public void adicionarMembro(Equipe equipe, Usuario usuario) {
        permissaoService.exigirGestao();
        servicoEquipe.adicionarMembro(equipe, usuario);
    }

    public void adicionarProjeto(Equipe equipe, Projeto projeto) {
        permissaoService.exigirGestao();
        servicoEquipe.adicionarProjeto(equipe, projeto);
    }
}
