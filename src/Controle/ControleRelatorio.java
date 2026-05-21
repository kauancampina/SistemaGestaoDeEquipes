package Controle;

import Servico.PermissaoService;
import Servico.ServicoRelatorio;

public class ControleRelatorio {

    private final ServicoRelatorio servicoRelatorio;
    private final PermissaoService permissaoService;

    public ControleRelatorio(ServicoRelatorio servicoRelatorio, PermissaoService permissaoService) {
        this.servicoRelatorio = servicoRelatorio;
        this.permissaoService = permissaoService;
    }

    public String gerarResumoGeral() {
        permissaoService.exigirAutenticacao();
        return servicoRelatorio.gerarResumoGeral();
    }
}
