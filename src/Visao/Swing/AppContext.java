package Visao.Swing;

import Controle.ControleEquipe;
import Controle.ControleProjeto;
import Controle.ControleRelatorio;
import Controle.ControleTarefa;
import Controle.ControleUsuario;
import Repository.EquipeRepository;
import Repository.ProjetoRepository;
import Repository.TarefaRepository;
import Repository.UsuarioRepository;
import Servico.PermissaoService;
import Servico.ServicoEquipe;
import Servico.ServicoProjeto;
import Servico.ServicoRelatorio;
import Servico.ServicoTarefa;
import Servico.ServicoUsuario;
import Servico.Sessao;

public class AppContext {

    private final ControleUsuario controleUsuario;
    private final ControleProjeto controleProjeto;
    private final ControleEquipe controleEquipe;
    private final ControleTarefa controleTarefa;
    private final ControleRelatorio controleRelatorio;

    public AppContext() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ProjetoRepository projetoRepository = new ProjetoRepository();
        EquipeRepository equipeRepository = new EquipeRepository();
        TarefaRepository tarefaRepository = new TarefaRepository();

        Sessao sessao = new Sessao();
        PermissaoService permissaoService = new PermissaoService(sessao);

        ServicoUsuario servicoUsuario = new ServicoUsuario(usuarioRepository);
        ServicoProjeto servicoProjeto = new ServicoProjeto(projetoRepository);
        ServicoEquipe servicoEquipe = new ServicoEquipe(equipeRepository);
        ServicoTarefa servicoTarefa = new ServicoTarefa(tarefaRepository, projetoRepository);
        ServicoRelatorio servicoRelatorio = new ServicoRelatorio(servicoUsuario, servicoProjeto, servicoEquipe, servicoTarefa);

        this.controleUsuario = new ControleUsuario(servicoUsuario, sessao, permissaoService);
        this.controleProjeto = new ControleProjeto(servicoProjeto, permissaoService);
        this.controleEquipe = new ControleEquipe(servicoEquipe, permissaoService);
        this.controleTarefa = new ControleTarefa(servicoTarefa, permissaoService, sessao);
        this.controleRelatorio = new ControleRelatorio(servicoRelatorio, permissaoService);
    }

    public ControleUsuario getControleUsuario() {
        return controleUsuario;
    }

    public ControleProjeto getControleProjeto() {
        return controleProjeto;
    }

    public ControleEquipe getControleEquipe() {
        return controleEquipe;
    }

    public ControleTarefa getControleTarefa() {
        return controleTarefa;
    }

    public ControleRelatorio getControleRelatorio() {
        return controleRelatorio;
    }
}
