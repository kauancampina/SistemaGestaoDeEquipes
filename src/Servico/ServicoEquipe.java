package Servico;

import Modelo.Equipe;
import Modelo.Projeto;
import Modelo.Usuario;
import Repository.EquipeRepository;
import Utilitario.Validacoes;

import java.util.List;
import java.util.Optional;

public class ServicoEquipe {

    private final EquipeRepository repository;

    public ServicoEquipe(EquipeRepository repository) {
        this.repository = repository;
    }

    public Equipe cadastrarEquipe(Equipe equipe) {
        validarEquipe(equipe);
        return repository.salvar(equipe);
    }

    public void editarEquipe(Equipe equipe) {
        Validacoes.idPositivo(equipe.getId(), "equipe");
        validarEquipe(equipe);
        repository.atualizar(equipe);
    }

    public boolean removerEquipe(int id) {
        Validacoes.idPositivo(id, "equipe");
        return repository.remover(id);
    }

    public Optional<Equipe> buscarPorId(int id) {
        Validacoes.idPositivo(id, "equipe");
        return repository.buscarPorId(id);
    }

    public List<Equipe> buscarPorNome(String nome) {
        Validacoes.campoObrigatorio(nome, "Nome");
        return repository.buscarPorNome(nome);
    }

    public List<Equipe> listarEquipes() {
        return repository.listarTodos();
    }

    public void adicionarMembro(Equipe equipe, Usuario usuario) {
        if (equipe == null || usuario == null) {
            throw new IllegalArgumentException("Equipe e usuario devem existir.");
        }
        equipe.adicionarMembro(usuario);
        repository.atualizar(equipe);
    }

    public void adicionarProjeto(Equipe equipe, Projeto projeto) {
        if (equipe == null || projeto == null) {
            throw new IllegalArgumentException("Equipe e projeto devem existir.");
        }
        equipe.adicionarProjeto(projeto);
        repository.atualizar(equipe);
    }

    private void validarEquipe(Equipe equipe) {
        if (equipe == null) {
            throw new IllegalArgumentException("Equipe nao pode ser nula.");
        }
        Validacoes.campoObrigatorio(equipe.getNome(), "Nome da equipe");
        Validacoes.campoObrigatorio(equipe.getDescricao(), "Descricao");
    }
}
