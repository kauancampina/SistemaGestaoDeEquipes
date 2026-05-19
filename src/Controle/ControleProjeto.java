package Controle;

import Modelo.Projeto;
import Servico.ServicoProjeto;

public class ControleProjeto {

    private ServicoProjeto servicoProjeto;

    public ControleProjeto(ServicoProjeto servicoProjeto) {
        this.servicoProjeto = servicoProjeto;
    }

    public void cadastrarProjeto(Projeto projeto) {
        servicoProjeto.cadastrarProjeto(projeto);
    }

    public void listarProjetos() {

        for (Projeto projeto : servicoProjeto.listarProjetos()) {
            System.out.println(projeto);
        }
    }
}