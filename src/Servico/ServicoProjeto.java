package Servico;

import Modelo.Equipe;
import Modelo.PerfilUsuario;
import Modelo.Projeto;
import Modelo.StatusProjeto;
import Modelo.Usuario;
import Repository.ProjetoRepository;
import Utilitario.Validacoes;

import java.util.List;
import java.util.Optional;

public class ServicoProjeto {

    private final ProjetoRepository repository;

    public ServicoProjeto(ProjetoRepository repository) {
        this.repository = repository;
    }

    public Projeto cadastrarProjeto(Projeto projeto) {
        validarProjeto(projeto);
        return repository.salvar(projeto);
    }

    public void editarProjeto(Projeto projeto) {
        Validacoes.idPositivo(projeto.getId(), "projeto");
        validarProjeto(projeto);
        repository.atualizar(projeto);
    }

    public boolean removerProjeto(int id) {
        Validacoes.idPositivo(id, "projeto");
        return repository.remover(id);
    }

    public Optional<Projeto> buscarPorId(int id) {
        Validacoes.idPositivo(id, "projeto");
        return repository.buscarPorId(id);
    }

    public List<Projeto> buscarPorNome(String nome) {
        Validacoes.campoObrigatorio(nome, "Nome");
        return repository.buscarPorNome(nome);
    }

    public List<Projeto> listarProjetos() {
        return repository.listarTodos();
    }

    public void adicionarEquipeAoProjeto(Projeto projeto, Equipe equipe) {
        if (projeto == null || equipe == null) {
            throw new IllegalArgumentException("Projeto e equipe devem existir.");
        }
        projeto.adicionarEquipe(equipe);
        repository.atualizar(projeto);
    }

    public void atualizarStatus(Projeto projeto, StatusProjeto status) {
        if (projeto == null || status == null) {
            throw new IllegalArgumentException("Projeto e status sao obrigatorios.");
        }
        projeto.setStatus(status);
        repository.atualizar(projeto);
    }

    private void validarProjeto(Projeto projeto) {
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto nao pode ser nulo.");
        }
        Validacoes.campoObrigatorio(projeto.getNome(), "Nome do projeto");
        Validacoes.campoObrigatorio(projeto.getDescricao(), "Descricao");
        Validacoes.periodo(projeto.getDataInicio(), projeto.getDataTermino());
        if (projeto.getStatus() == null) {
            throw new IllegalArgumentException("Status do projeto e obrigatorio.");
        }
        Usuario gerente = projeto.getGerente();
        if (gerente == null || gerente.getId() <= 0) {
            throw new IllegalArgumentException("O projeto deve possuir um gerente real cadastrado.");
        }
        if (gerente.getPerfil() != PerfilUsuario.GERENTE && gerente.getPerfil() != PerfilUsuario.ADMINISTRADOR) {
            throw new IllegalArgumentException("O gerente do projeto deve ter perfil GERENTE ou ADMINISTRADOR.");
        }
    }
}
