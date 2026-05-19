package Servico;

import Modelo.Projeto;

import java.util.ArrayList;

public class ServicoProjeto {

    private ArrayList<Projeto> projetos = new ArrayList<>();

    public void cadastrarProjeto(Projeto projeto) {
        projetos.add(projeto);
    }

    public ArrayList<Projeto> listarProjetos() {
        return projetos;
    }
}